package com.example.cash;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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

import boilingstocks.objects.JSONParser;

public class LoginActivity extends Activity{

	EditText user,paswrd;
	Button login1,reg;
	
	Boolean isInternetPresent = false;
    ConnectionDetector cd;
	
    private ProgressDialog pDialog;
	private static String url_addmessage = "http://www.sakshi-gupta.com/cashair/login.php";
	private JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	String str1,str2;
	String id,crntbal,urnam;
	
			@Override
    public void onBackPressed()
        {
		 
				LoginActivity.this.finish();
            LoginActivity.this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
		         }
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
	
        login1=(Button) findViewById(R.id.btnRegister);
        reg=(Button) findViewById(R.id.btnLinkToLoginScreen);
        user=(EditText) findViewById(R.id.loginEmail);
        paswrd=(EditText) findViewById(R.id.amounttopay);
        cd = new ConnectionDetector(getApplicationContext());
        
      login1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
	
				
				str1=user.getText().toString();
				str2=paswrd.getText().toString();
	
				 isInternetPresent = cd.isConnectingToInternet();
				 
	                if (isInternetPresent) {
	                	
	                	new RegisterMe().execute();
	                } 
	                else
	                {
	                 
	                	showAlertDialog(LoginActivity.this, "CashAir",
	                            "Oops!There is no internet Connection. Check your network connection.", false);
	                }
      }
	});
      
      reg.setOnClickListener(new OnClickListener() {
			
   			public void onClick(View arg0) {
   				// TODO Auto-generated method stub
   			
   				Intent in=new Intent(LoginActivity.this,DatabaseActivity.class);
   				startActivity(in);
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
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Matching Credentials...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args) {
			
					
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", str1));
			params.add(new BasicNameValuePair("pass", str2));
			
			JSONObject json = jsonParser.makeHttpRequest(url_addmessage, "GET", params);
		
			Log.d("Create Response", json.toString());
			
			try{
	
				int success = json.getInt(TAG_SUCCESS);
				crntbal = json.getString("balance");
				urnam = json.getString("email");
				id = json.getString("pin");
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
	           Toast.makeText(LoginActivity.this, "Verification Succesfull.",Toast.LENGTH_LONG).show();
	           Intent in = new Intent(LoginActivity.this,MainPage.class);
			    in.putExtra("extra_pi", id);
			    in.putExtra("extra_bal",crntbal);
			    in.putExtra("extra_name", urnam);
				startActivity(in);	
			}
			else{
	            Toast.makeText(LoginActivity.this, "Incorrect Username/Password",Toast.LENGTH_LONG).show();
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
			
			Builder builder= new Builder(LoginActivity.this);
			builder.setMessage("Do you want to exit ?");
			builder.setCancelable(false);
			builder.create();
			
			builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
				LoginActivity.this.finish();
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
        Builder alertDialog = new Builder(LoginActivity.this);
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
				LoginActivity.this.finish();
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