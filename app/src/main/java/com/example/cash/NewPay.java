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
//import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;

public class NewPay extends Activity {

	EditText pin,amt;
	Button pay,exit;
	String frwdbal,sender_id,text5,text9,name,receiver_id,amounttopay,crntbalofreceiver;
	String tid,nameofreceiver,mobileofreceiver, receivermob;
	int backbal,amountpay;
	//TextView registerErrorMsg;
	Boolean isInternetPresent = false;
    ConnectionDetector cd;
	
	
private ProgressDialog pDialog;
private static String url_addmessage = "http://www.sakshi-gupta.com/cashair/pay.php";
private JSONParser jsonParser = new JSONParser();
private static final String TAG_SUCCESS = "success";

@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        
        pay=(Button) findViewById(R.id.btnRegister);
        pin=(EditText) findViewById(R.id.enterpin);
        amt=(EditText) findViewById(R.id.amounttopay);
        exit=(Button) findViewById(R.id.btnLinkToLoginScreen);
        cd = new ConnectionDetector(getApplicationContext());
        sender_id=getIntent().getStringExtra("extra_pi");
		text5=getIntent().getStringExtra("extra_bal");
		text9=getIntent().getStringExtra("extra_name");
		//registerErrorMsg = (TextView) findViewById(R.id.register_error);	
		
        exit.setOnClickListener(new OnClickListener() {
    		
    		public void onClick(View arg0) {
    			// TODO Auto-generated method stub
    		Intent in=new Intent(NewPay.this,MainPage.class);
    		in.putExtra("extra_pi", sender_id);
    		in.putExtra("extra_bal", text5);
    		in.putExtra("extra_name", text9);
    		
    			startActivity(in);				
    		}
    	});

  pay.setOnClickListener(new OnClickListener() {
    		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
        amounttopay=amt.getText().toString();
        receiver_id=pin.getText().toString();
		
        if(receiver_id.equals(""))
		{
			Toast.makeText(getApplicationContext(), "Enter the Pin No.", Toast.LENGTH_SHORT).show();
			pin.requestFocus();
		}
		
		else if(amounttopay.equals(""))
			{
				Toast.makeText(getApplicationContext(), "Enter the amount to pay", Toast.LENGTH_SHORT).show();
				amt.requestFocus();
			}
        
			else
			{
				amountpay=0;
				backbal=0;
				try
				{
				backbal= Integer.valueOf(text5);
				amountpay= Integer.valueOf(amounttopay);
				}
				catch(NumberFormatException nfe)
				{
					System.out.println("NumberFormatException: " + nfe.getMessage());
				}
				if(backbal>=amountpay)
				{
				Log.i("Strings", receiver_id+amounttopay);
				
				 isInternetPresent = cd.isConnectingToInternet();
				 
	                if (isInternetPresent) {
	                	
	                	new RegisterMe().execute();
	                } 
	                else
	                {
	                 
	                	showAlertDialog(NewPay.this, "CashAir",
	                            "Oops!There is no internet Connection. Check your network connection.", false);
	                }
					}
				else{
		            Toast.makeText(NewPay.this, "Invalid Pin No./Insufficient Balance",Toast.LENGTH_LONG).show();
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
			pDialog = new ProgressDialog(NewPay.this);
			pDialog.setMessage("Sending Transaction Id \n to the Receiver");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args) {
						
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("message", "1"));
			params.add(new BasicNameValuePair("receiver_id", receiver_id));
			params.add(new BasicNameValuePair("amounttopay", amounttopay));
			params.add(new BasicNameValuePair("sender_id", sender_id));
			params.add(new BasicNameValuePair("sender_bal_b4", text5));
			
			JSONObject json = jsonParser.makeHttpRequest(url_addmessage, "GET", params);
		
			Log.d("Create Response", json.toString());
			
			try{
				int success = json.getInt(TAG_SUCCESS);
				tid = json.getString("tid");
				receivermob = json.getString("phone");
				
				if(success == 1){
						Message = "Done";
					Log.d("Work Done", "Message Added");
					
									}
				else{
					
					//registerErrorMsg.setText("Invalid Pin No./ Insufficient Balance");
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
				
			 	String sendTo=receivermob;
		    	String myMessage="You are receiving Rs." + amt.getText().toString() + " " + "from" + " " + text9 + "." + " " + "Transaction Id is" + " " + tid;
			
		    	SmsManager.getDefault().sendTextMessage(sendTo, null, myMessage, null, null);
		    	Toast.makeText(NewPay.this, "Enter transaction id to complete the transaction",Toast.LENGTH_LONG).show();
	          Intent in = new Intent(NewPay.this,transactionid.class);
			    in.putExtra("extra_transid", tid);
			    in.putExtra("extra_pi", sender_id);
			    in.putExtra("extra_bal", text5);
			    in.putExtra("extra_receiverid", receiver_id);
			    in.putExtra("extra_recbal", crntbalofreceiver);
			    in.putExtra("extra_name", text9);
			    in.putExtra("extra_recmobile", receivermob);
			    in.putExtra("extra_amt2pay", amounttopay);
			    startActivity(in);
		
			}
			else{
	            Toast.makeText(NewPay.this, "Reciver's Pin No. is Incorrect",Toast.LENGTH_LONG).show();
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
			
			Builder builder= new Builder(NewPay.this);
			builder.setMessage("Do you want to exit ?");
			builder.setCancelable(false);
			builder.create();
			
			builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
				NewPay.this.finish();
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
        Builder alertDialog = new Builder(NewPay.this);
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
			NewPay.this.finish();
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
			
	