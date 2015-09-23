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

public class DatabaseActivity extends Activity{

	Button btn1,btn3;
	EditText edt1,edt2,edt3,edt4,edt5;
	private ProgressDialog pDialog;
	private static String url_addmessage = "http://www.sakshi-gupta.com/cashair/register.php";
	private JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	String str1,str2,str3,str4,str5;
	String id,message;
	
	Boolean isInternetPresent = false;
    ConnectionDetector cd;
	
	 @Override
     public void onBackPressed()
         {
		 
				DatabaseActivity.this.finish();
				 Intent intent = new Intent(DatabaseActivity.this,LoginActivity.class);
		         startActivity(intent);   	
         }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        btn1=(Button) findViewById(R.id.btnRegister);
        btn3=(Button) findViewById(R.id.btnLinkToLoginScreen);
        edt1=(EditText) findViewById(R.id.registerEmail);
        edt2=(EditText) findViewById(R.id.amounttopay);
        edt3=(EditText) findViewById(R.id.registerPassword);
        edt4=(EditText) findViewById(R.id.registerMobile);
        edt5=(EditText) findViewById(R.id.registerEmailid);
        cd = new ConnectionDetector(getApplicationContext());
         
       btn1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				
				str1=edt2.getText().toString();
				str2=edt1.getText().toString();
				str3=edt3.getText().toString();
				str5=edt5.getText().toString();
				str4=edt4.getText().toString();
				
				
				 if(str2.equals(""))
					{
						Toast.makeText(getApplicationContext(), "Enter Your Name", Toast.LENGTH_SHORT).show();
						edt2.requestFocus();
					}
					
					else if(str1.equals(""))
						{
							Toast.makeText(getApplicationContext(), "Enter the User Name", Toast.LENGTH_SHORT).show();
							edt1.requestFocus();
						}
					else if(str3.equals(""))
					{
						Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
						edt3.requestFocus();
					}
				
					else if(str5.equals(""))
					{
						Toast.makeText(getApplicationContext(), "Enter Your Email Address", Toast.LENGTH_SHORT).show();
						edt5.requestFocus();
					}
				
					else if(str4.equals(""))
					{
						Toast.makeText(getApplicationContext(), "Enter Your Mobile No.", Toast.LENGTH_SHORT).show();
						edt4.requestFocus();
					}
				
					
						else
						{
							Log.i("Strings", str1+str2+str3+str5+str4);
								isInternetPresent = cd.isConnectingToInternet();
							 
			                if (isInternetPresent) {
			                	
			                	new RegisterMe().execute();
			                } 
			                else
			                {
			                 
			                	showAlertDialog(DatabaseActivity.this, "CashAir",
			                            "Oops!There is no internet Connection. Check your network connection.", false);
			                }
							}
			  		}
		});
        
       btn3.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				Intent in=new Intent(DatabaseActivity.this,LoginActivity.class);
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
			pDialog = new ProgressDialog(DatabaseActivity.this);
			pDialog.setMessage("Processing...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args) {
			
					
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("message", "1"));
			params.add(new BasicNameValuePair("name", str1));
			params.add(new BasicNameValuePair("email", str2));
			params.add(new BasicNameValuePair("pass", str3));
			params.add(new BasicNameValuePair("emailid",str5 ));
			params.add(new BasicNameValuePair("mobile",str4));

			
		 JSONObject json = jsonParser.makeHttpRequest(url_addmessage, "GET", params);
		
			Log.d("Create Response", json.toString());
			
			try{
				int success = json.getInt(TAG_SUCCESS);
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
	            Toast.makeText(DatabaseActivity.this, "You're awesome. Thanks for registering.",Toast.LENGTH_LONG).show();
	            
	            Intent in=new Intent(DatabaseActivity.this,generatedpin.class);
	           in.putExtra("extra_pi",id);
	            startActivity(in);
			}
			else{
				
	            Toast.makeText(DatabaseActivity.this, "Oops! Somebody has already claimed rights to this Username. Please Try Again with a different Username.",Toast.LENGTH_LONG).show();
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
			
			Builder builder= new Builder(DatabaseActivity.this);
			builder.setMessage("Do you want to exit ?");
			builder.setCancelable(false);
			builder.create();
			
			builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
				DatabaseActivity.this.finish();
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
        Builder alertDialog = new Builder(DatabaseActivity.this);
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
				DatabaseActivity.this.finish();
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