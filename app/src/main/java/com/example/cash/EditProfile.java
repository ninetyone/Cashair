package com.example.cash;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import boilingstocks.objects.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends Activity{
	
	Button ok;
	EditText enterpassword;
	String password,text,text23, text56;
	private ProgressDialog pDialog;
	private static String url_addmessage = "http://www.sakshi-gupta.com/cashair/validatepassword.php";
	private JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	

	Boolean isInternetPresent = false;
    ConnectionDetector cd;
    
	@Override
    public void onBackPressed()
        {
		 		 Intent in = new Intent(EditProfile.this,MainPage.class);
		 		in.putExtra("extra_pi", text);
			    in.putExtra("extra_bal",text23);
			    in.putExtra("extra_name", text56);
		         startActivity(in);
		 }
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editprofile);
		cd = new ConnectionDetector(getApplicationContext());
		ok=(Button) findViewById(R.id.btnRegister);
		enterpassword=(EditText) findViewById(R.id.enterpassword);
		 text = getIntent().getStringExtra("extra_pi");
		 text23 = getIntent().getStringExtra("extra_bal");
		 text56 = getIntent().getStringExtra("extra_name");
	
		ok.setOnClickListener(new OnClickListener() {
			
   			public void onClick(View arg0) {
   				// TODO Auto-generated method stub
   				password=enterpassword.getText().toString();
   			 isInternetPresent = cd.isConnectingToInternet();
			 
             if (isInternetPresent) {
             	
             	new RegisterMe().execute();
             } 
             else
             {
              
             	showAlertDialog(EditProfile.this, "CashAir",
                         "Oops!There is no internet Connection. Check your network connection.", false);
             }	
   			}
   		});
	 
	}
	
	class RegisterMe extends AsyncTask<String, String, String>{
		String Message=null;
		
		public boolean workdone(String Message){
			
			boolean x=false;
			if(Message == "Done"){
				x=true;
			}
			return x;
		}
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(EditProfile.this);
			pDialog.setMessage("Please Wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args) {
			
					
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("message", "1"));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("sender_id", text));
			
			JSONObject json = jsonParser.makeHttpRequest(url_addmessage, "GET", params);
		
			Log.d("Create Response", json.toString());
			
			try{
	
				int success = json.getInt(TAG_SUCCESS);
				if(success == 1){
					Message = "Done";
					Log.d("Work Done", "Message Added");
				}
				else{
					
					Log.d("Work Done", "Message Not Added");
				}
			} catch(JSONException e){
				e.printStackTrace();

			}

			return null;
			
		}
		
		protected void onPostExecute(String file_url){
			pDialog.dismiss();

			if(new RegisterMe().workdone(Message)){
	           Intent in=new Intent(EditProfile.this,ChangeAttributes.class);
	           in.putExtra("extra_pi", text);
	           in.putExtra("extra_bal", text23);
	           in.putExtra("extra_name", text56);
  				startActivity(in);
  				}
			
			else{
	            Toast.makeText(EditProfile.this, "Incorrect Password",Toast.LENGTH_LONG).show();
			}	
		}
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	MenuInflater obj=getMenuInflater();
    	obj.inflate(R.menu.mymenu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch (item.getItemId()) {
		case R.id.abt:
			Intent in = new Intent(getBaseContext(),Aboutus.class);
			startActivity(in);
			
			break;

		case R.id.help:
			Intent in2 = new Intent(getBaseContext(),HelpUs.class);
			startActivity(in2);
			break;
			
		case R.id.btnLinkToLoginScreen:
			
			Builder builder= new Builder(EditProfile.this);
			builder.setMessage("Are you sure do you want to sign out ?");
			builder.setCancelable(false);
			builder.create();
			
			builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
				EditProfile.this.finish();
				Intent intent = new Intent(Intent.ACTION_MAIN);
 				intent.addCategory(Intent.CATEGORY_HOME);
 				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 				startActivity(intent);
					
				}
			});
			builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					
				}
			});
			
			builder.show();   	
    	break;
    }
    	
		return false;
    	
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        Builder alertDialog = new Builder(EditProfile.this);
       alertDialog.create();
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
         
        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
 
        alertDialog.setPositiveButton("OK!Try Again",new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();	
			}
		});
		alertDialog.setNegativeButton("Come Back Later", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				EditProfile.this.finish();
				Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					
			}
		});
		        // Showing Alert Message
        alertDialog.show();
    }

}
