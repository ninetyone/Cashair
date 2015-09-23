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
import android.telephony.SmsManager;
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

public class transactionid extends Activity{
	
	
	EditText tid;
	Button submit,cancel;
	TextView showid;
	String text5,text9,moneytopay,amtsub,text12,text15,text2,text10,transid, aftertransaction,recmobile,datetime,sendermob;
	
	Boolean isInternetPresent = false;
    ConnectionDetector cd;
	
	
	private ProgressDialog pDialog;
	private static String url_addmessage = "http://www.sakshi-gupta.com/cashair/transaction.php";
	private JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";

	@Override
    public void onBackPressed()
        {
		 
			Builder builder= new Builder(transactionid.this);
			builder.setMessage("Are you sure you want to cancel the transaction ?");
			builder.setCancelable(false);
			builder.create();
			
			builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
				transactionid.this.finish();
				 Intent intent = new Intent(transactionid.this,MainPage.class);
				 intent.putExtra("extra_pi", text10);
				 intent.putExtra("extra_bal", text2);
			     intent.putExtra("extra_name", text9);
		         startActivity(intent);
		        
					
				}
			});
			builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					
				}
			});
			
			builder.show();   	
        }
   
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transactionid);
		
   	   	    submit=(Button) findViewById(R.id.submit);
	        tid=(EditText) findViewById(R.id.entertid);
	        cancel=(Button) findViewById(R.id.cancel);
	        showid=(TextView) findViewById(R.id.td);
		    cd = new ConnectionDetector(getApplicationContext());
		    
		    text10=getIntent().getStringExtra("extra_pi");
			text2=getIntent().getStringExtra("extra_bal");
			recmobile=getIntent().getStringExtra("extra_recmobile");
			text9=getIntent().getStringExtra("extra_name");
			text15=getIntent().getStringExtra("extra_receiverid");
			moneytopay=getIntent().getStringExtra("extra_amt2pay");
			text12=getIntent().getStringExtra("extra_recbal");
		    text5=getIntent().getStringExtra("extra_transid");	   
		    showid.setText(text5);
	        
	        cancel.setOnClickListener(new OnClickListener() {
	        	
	    		public void onClick(View arg0) {
	    			// TODO Auto-generated method stub
	    		Intent in=new Intent(transactionid.this,MainPage.class);
	    		 in.putExtra("extra_pi",text10);
				 in.putExtra("extra_bal",text2);
				 in.putExtra("extra_name",text9);
				
	    			startActivity(in);				
	    		}
	    	});

	        submit.setOnClickListener(new OnClickListener() {
				
				public void onClick(View arg0) {
					
					transid= tid.getText().toString();

					
					if(text5.equals(tid.getText().toString()))
					  {
						 isInternetPresent = cd.isConnectingToInternet();
						 
			                if (isInternetPresent) {
			                	
			                	new RegisterMe().execute();
			                } 
			                else
			                {
			                 
			                	showAlertDialog(transactionid.this, "CashAir",
			                            "Oops!There is no internet Connection. Check your network connection.", false);
			                }
			      }
					
					else{
			            Toast.makeText(transactionid.this, "Transaction Terminated.",Toast.LENGTH_LONG).show();
			            Intent in = new Intent(transactionid.this,MainPage.class);
			            in.putExtra("extra_pi",text10);
						 in.putExtra("extra_bal",text2);
						 in.putExtra("extra_name",text9);
			            startActivity(in);
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
			pDialog = new ProgressDialog(transactionid.this);
			pDialog.setMessage("Processing Transaction...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args) {
			
					
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("message", "1"));
			params.add(new BasicNameValuePair("tid", text5));
			params.add(new BasicNameValuePair("receiver_id", text15));
			params.add(new BasicNameValuePair("amounttopay", moneytopay));
			params.add(new BasicNameValuePair("sender_id", text10));
			params.add(new BasicNameValuePair("sender_bal_b4", text2));
			
			JSONObject json = jsonParser.makeHttpRequest(url_addmessage, "GET", params);
		
			Log.d("Create Response", json.toString());
			
			try{
				int success = json.getInt(TAG_SUCCESS);
				aftertransaction = json.getString("senderafterbal");
				datetime = json.getString("date");
				sendermob = json.getString("mobile");
				
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
	           Toast.makeText(transactionid.this, "Transaction Complete",Toast.LENGTH_LONG).show();
	           
	           String sendTo=recmobile;
		    	String myMessage="Transaction of Rs." + moneytopay + " " + "on" + " " + datetime + " " + "successful.";
			
		    	SmsManager.getDefault().sendTextMessage(sendTo, null, myMessage, null, null);
		    
		  
		    	Intent in = new Intent(transactionid.this,MainPage.class);
			    in.putExtra("extra_pi", text10);
			    in.putExtra("extra_bal",aftertransaction);
			    in.putExtra("extra_name", text9);
				startActivity(in);
			
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
			
			Builder builder= new Builder(transactionid.this);
			builder.setMessage("Do you want to exit ?");
			builder.setCancelable(false);
			builder.create();
			
			builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
				transactionid.this.finish();
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
        Builder alertDialog = new Builder(transactionid.this);
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
				transactionid.this.finish();
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
