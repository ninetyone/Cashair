package com.example.cash;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
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
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import boilingstocks.objects.JSONParser;

public class MainPage extends Activity {

    Button out, trans, update;
    ImageButton clickpay;
    TextView pin, bal, nam;
    String text, text5, text1;

    Boolean isInternetPresent = false;
    ConnectionDetector cd;

    private ProgressDialog pDialog;
    private static String url_addmessage = "http://www.sakshi-gupta.com/cashair/checktrans.php";
    private JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";


    @Override
    public void onBackPressed() {

        Builder builder = new Builder(MainPage.this);
        builder.setMessage("Are you sure you want to Sign Out ?");
        builder.setCancelable(false);
        builder.create();

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                MainPage.this.finish();
                Intent intent = new Intent(MainPage.this, LoginActivity.class);
                startActivity(intent);


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
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterlogin);
        out = (Button) findViewById(R.id.signout);
        trans = (Button) findViewById(R.id.trans);
        pin = (TextView) findViewById(R.id.generatenumber1);
        bal = (TextView) findViewById(R.id.currentbal);
        nam = (TextView) findViewById(R.id.ur_name);
        clickpay = (ImageButton) findViewById(R.id.clicktopay);
        update = (Button) findViewById(R.id.btntoupdate);
        cd = new ConnectionDetector(getApplicationContext());

        text = getIntent().getStringExtra("extra_pi");
        pin.setText(text);
        text1 = getIntent().getStringExtra("extra_bal");
        bal.setText(text1);
        text5 = getIntent().getStringExtra("extra_name");
        nam.setText(text5);


        out.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent in = new Intent(MainPage.this, LoginActivity.class);
                startActivity(in);
            }
        });

        trans.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent) {

                    new RegisterMe().execute();
                } else {

                    showAlertDialog(MainPage.this, "CashAir",
                            "Oops!There is no internet Connection. Check your network connection.", false);
                }
            }
        });

        update.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent in = new Intent(MainPage.this, EditProfile.class);
                in.putExtra("extra_pi", text);
                in.putExtra("extra_bal", text1);
                in.putExtra("extra_name", text5);
                startActivity(in);
            }
        });

        clickpay.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //String =getIntent().getStringExtra("extra_text");
                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent) {

                    Intent in1 = new Intent(MainPage.this, NewPay.class);
                    in1.putExtra("extra_pi", text);
                    in1.putExtra("extra_bal", text1);
                    in1.putExtra("extra_name", text5);
                    startActivity(in1);
                } else {

                    showAlertDialog(MainPage.this, "CashAir",
                            "Oops!There is no internet Connection. Check your network connection.", false);
                }
            }
        });
    }

    class RegisterMe extends AsyncTask<String, String, String> {
        String Message = null;

        public boolean workdone(String Message) {

            boolean x = false;
            if (Message == "Done") {
                x = true;
            }
            return x;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainPage.this);
            pDialog.setMessage("Your transactions are on the way!");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("message", "1"));
            params.add(new BasicNameValuePair("sender_id", text));

            JSONObject json = jsonParser.makeHttpRequest(url_addmessage, "GET", params);

            Log.d("Create Response", json.toString());

            try {

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Message = "Done";
                    Log.d("Work Done", "Message Added");
                } else {

                    Log.d("Work Done", "Message Not Added");
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();

            if (new RegisterMe().workdone(Message)) {
                Intent in = new Intent(MainPage.this, transactionshow.class);
                in.putExtra("extra_pi", text);
                startActivity(in);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater obj = getMenuInflater();
        obj.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.abt:
                Intent in = new Intent(getBaseContext(), Aboutus.class);
                startActivity(in);

                break;

            case R.id.help:
                Intent in2 = new Intent(getBaseContext(), HelpUs.class);
                startActivity(in2);
                break;

            case R.id.btnLinkToLoginScreen:

                Builder builder = new Builder(MainPage.this);
                builder.setMessage("Are you sure you want to sign out ?");
                builder.setCancelable(false);
                builder.create();

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        MainPage.this.finish();
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
        Builder alertDialog = new Builder(MainPage.this);
        alertDialog.create();
        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        alertDialog.setPositiveButton("OK!Try Again", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setNegativeButton("Come Back Later", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                MainPage.this.finish();
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
