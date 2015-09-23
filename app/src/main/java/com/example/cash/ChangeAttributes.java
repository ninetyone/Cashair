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
import android.widget.TextView;
import android.widget.Toast;

public class ChangeAttributes extends Activity {

	EditText newpass, retypepass,newmobile,newemail;
	Button submit1, submit2, submit3, changepassword, updatemobile, updateemail,back;
	String newpassword,retypepassword,text, newmobileno, newemailid,text12,text13;
	TextView enternewpass, enterretypepass, enternewmobile, enternewemail;
	
	Boolean isInternetPresent = false;
    ConnectionDetector cd;
	
	private ProgressDialog pDialog;
	private static String url_addmessage = "http://www.sakshi-gupta.com/cashair/changepassword.php";
	private static String url_addmessage1 = "http://www.sakshi-gupta.com/cashair/updatemobile.php";
	private static String url_addmessage2 = "http://www.sakshi-gupta.com/cashair/updateemail.php";
	private JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	
	@Override
    public void onBackPressed()
        {
		 
			Builder builder= new Builder(ChangeAttributes.this);
			builder.setMessage("Done with editing profile ?");
			builder.setCancelable(false);
			builder.create();
			
			builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
				ChangeAttributes.this.finish();
				 Intent in = new Intent(ChangeAttributes.this,LoginActivity.class);
				 startActivity(in);
		        
					
				}
			});
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					
				}
			});
			
			builder.show();   	
        }
   
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        
        newpass=(EditText) findViewById(R.id.newpassword);
        retypepass=(EditText) findViewById(R.id.retypepassword);
        newmobile=(EditText) findViewById(R.id.newmobile);
        newemail=(EditText) findViewById(R.id.newemail);
        submit1=(Button) findViewById(R.id.submit1);
        submit2=(Button) findViewById(R.id.submit2);
        submit3=(Button) findViewById(R.id.submit3);
        back=(Button) findViewById(R.id.button1);
        changepassword=(Button) findViewById(R.id.btnupdatepassword);
        updatemobile=(Button) findViewById(R.id.btnupdatemobile);
        updateemail=(Button) findViewById(R.id.btnupdateemail);
        enternewpass=(TextView) findViewById(R.id.textView4);
        enterretypepass=(TextView) findViewById(R.id.textView3);
        enternewmobile=(TextView) findViewById(R.id.textView2);
        enternewemail=(TextView) findViewById(R.id.textView1);
        text = getIntent().getStringExtra("extra_pi");
        text12 = getIntent().getStringExtra("extra_bal");
        text13 = getIntent().getStringExtra("extra_name");
        cd = new ConnectionDetector(getApplicationContext());
        
        newpass.setVisibility(View.GONE);
		retypepass.setVisibility(View.GONE);
		submit1.setVisibility(View.GONE);
		enterretypepass.setVisibility(View.GONE);
		enternewpass.setVisibility(View.GONE);
		newmobile.setVisibility(View.GONE);
		submit2.setVisibility(View.GONE);
		enternewmobile.setVisibility(View.GONE);
		newemail.setVisibility(View.GONE);
		submit3.setVisibility(View.GONE);
		enternewemail.setVisibility(View.GONE);
    
 back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent in=new Intent(ChangeAttributes.this,LoginActivity.class);
   				startActivity(in);
			}
 });
 
 
 changepassword.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				newpass.setVisibility(View.VISIBLE);
				retypepass.setVisibility(View.VISIBLE);
				submit1.setVisibility(View.VISIBLE);
				enterretypepass.setVisibility(View.VISIBLE);
				enternewpass.setVisibility(View.VISIBLE);
				newmobile.setVisibility(View.GONE);
				submit2.setVisibility(View.GONE);
				enternewmobile.setVisibility(View.GONE);
				newemail.setVisibility(View.GONE);
				submit3.setVisibility(View.GONE);
				enternewemail.setVisibility(View.GONE);
			}
 });
    
 updatemobile.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			newmobile.setVisibility(View.VISIBLE);
			submit2.setVisibility(View.VISIBLE);
			enternewmobile.setVisibility(View.VISIBLE);
			newpass.setVisibility(View.GONE);
			retypepass.setVisibility(View.GONE);
			submit1.setVisibility(View.GONE);
			enterretypepass.setVisibility(View.GONE);
			enternewpass.setVisibility(View.GONE);
			newemail.setVisibility(View.GONE);
			submit3.setVisibility(View.GONE);
			enternewemail.setVisibility(View.GONE);
		}
});
 
 updateemail.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			newemail.setVisibility(View.VISIBLE);
			submit3.setVisibility(View.VISIBLE);
			enternewemail.setVisibility(View.VISIBLE);
			 newpass.setVisibility(View.GONE);
				retypepass.setVisibility(View.GONE);
				submit1.setVisibility(View.GONE);
				enterretypepass.setVisibility(View.GONE);
				enternewpass.setVisibility(View.GONE);
				newmobile.setVisibility(View.GONE);
				submit2.setVisibility(View.GONE);
				enternewmobile.setVisibility(View.GONE);
		}
});

 
  submit1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				
				newpassword=newpass.getText().toString();
				retypepassword=retypepass.getText().toString();
				

				 if(newpassword.equals(""))
					{
						Toast.makeText(getApplicationContext(), "Enter new password", Toast.LENGTH_SHORT).show();
						newpass.requestFocus();
					}
					
					else if(retypepassword.equals(""))
						{
							Toast.makeText(getApplicationContext(), "Enter retype password", Toast.LENGTH_SHORT).show();
							retypepass.requestFocus();
						}
				
					else if (newpassword.equals(retypepassword))
					{
						 isInternetPresent = cd.isConnectingToInternet();
						 
			                if (isInternetPresent) {
			                	
			                	new RegisterMe().execute();
			                } 
			                else
			                {
			                 
			                	showAlertDialog(ChangeAttributes.this, "CashAir",
			                            "Oops!There is no internet Connection. Check your network connection.", false);
			                }
		     	}
					else
						 Toast.makeText(ChangeAttributes.this, "Passwords do not match",Toast.LENGTH_LONG).show();
			}
			});
  
  submit2.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			
			newmobileno=newmobile.getText().toString();
			

			 if(newmobileno.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Enter new mobile no.", Toast.LENGTH_SHORT).show();
					newmobile.requestFocus();
				}
				
				else	
				{
					isInternetPresent = cd.isConnectingToInternet();
				 
                if (isInternetPresent) {
                	
                	new RegisterMobile().execute();
                } 
                else
                {
                 
                	showAlertDialog(ChangeAttributes.this, "CashAir",
                            "Oops!There is no internet Connection. Check your network connection.", false);
                }
				}
  		}
		});
  
  submit3.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			
			newemailid=newemail.getText().toString();
			

			 if(newemailid.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Enter new email id", Toast.LENGTH_SHORT).show();
					newemail.requestFocus();
				}
				
				else	
				{
					isInternetPresent = cd.isConnectingToInternet();
				 
                if (isInternetPresent) {
                	
                	new RegisterEmail().execute();
                } 
                else
                {
                 
                	showAlertDialog(ChangeAttributes.this, "CashAir",
                            "Oops!There is no internet Connection. Check your network connection.", false);
                }
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
			pDialog = new ProgressDialog(ChangeAttributes.this);
			pDialog.setMessage("Processing...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args) {
			
					
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("message", "1"));
			params.add(new BasicNameValuePair("newpassword", newpassword));
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
	           Toast.makeText(ChangeAttributes.this, "Password changed successfully",Toast.LENGTH_LONG).show();
			}
			
		}	
	}
		
		class RegisterMobile extends AsyncTask<String, String, String>{
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
				pDialog = new ProgressDialog(ChangeAttributes.this);
				pDialog.setMessage("Processing...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}
			
			@Override
			protected String doInBackground(String... args) {
				
						
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("message", "1"));
				params.add(new BasicNameValuePair("newmobile", newmobileno));
				params.add(new BasicNameValuePair("sender_id", text));
				
				JSONObject json = jsonParser.makeHttpRequest(url_addmessage1, "GET", params);
			
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
		           Toast.makeText(ChangeAttributes.this, "Mobile No updated successfully.",Toast.LENGTH_LONG).show();
				}
				
			}
}
		class RegisterEmail extends AsyncTask<String, String, String>{
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
				pDialog = new ProgressDialog(ChangeAttributes.this);
				pDialog.setMessage("Processing...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}
			
			@Override
			protected String doInBackground(String... args) {
				
						
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("message", "1"));
				params.add(new BasicNameValuePair("newemail", newemailid));
				params.add(new BasicNameValuePair("sender_id", text));
				
				JSONObject json = jsonParser.makeHttpRequest(url_addmessage2, "GET", params);
			
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
		           Toast.makeText(ChangeAttributes.this, "Email Id changed successfully",Toast.LENGTH_LONG).show();
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
				
				Builder builder= new Builder(ChangeAttributes.this);
				builder.setMessage("Do you want to exit ?");
				builder.setCancelable(false);
				builder.create();
				
				builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
					ChangeAttributes.this.finish();
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
	        Builder alertDialog = new Builder(ChangeAttributes.this);
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
					ChangeAttributes.this.finish();
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