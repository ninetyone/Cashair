package com.example.cash;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class transactionshow extends Activity{

	String text;
	        protected void onCreate(Bundle savedInstanceState) {		
			super.onCreate(savedInstanceState);
			setContentView(R.layout.transactionshow);
			
			text = getIntent().getStringExtra("extra_pi");
			WebView wv = (WebView)findViewById(R.id.my_webview);
			wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
			}
			});
			wv.loadUrl("http://sakshi-gupta.com/cashair/checktrans.php?sender_id=" + text);
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
				
				Builder builder= new Builder(transactionshow.this);
				builder.setMessage("Do you want to exit ?");
				builder.setCancelable(false);
				builder.create();
				
				builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
					transactionshow.this.finish();
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
}