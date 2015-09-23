package com.example.cash;

import java.util.ArrayList;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Data extends Activity{
ArrayList<Model> modelList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data);
		
		modelList= new ArrayList<Model>();
		
		mydb db= new mydb(Data.this);
		
		db.open();
		Cursor c= db.getAllValues("register");
		if(c!=null)
			c.moveToFirst();
		
		for(int i =0;i<c.getCount();i++){
			Model mod=new Model();
			mod.setEmail(c.getString(1));
			mod.setName(c.getString(0));
			mod.setPassword(c.getString(2));
			mod.setMobile(c.getString(3));
			mod.setPinno(c.getString(c.getColumnIndex("pinno")));
			mod.setSno(c.getString(c.getColumnIndex("sno")));
		    //mod.setSno(c.getString(5));
			mod.setBalance("100");
			
			modelList.add(mod);
			c.moveToNext();
			}
		c.close();
		db.close();
		
		TableLayout ll=(TableLayout) findViewById(R.id.ll);
		
			TableRow row1 = new TableRow(this);
			TextView t1= new TextView(this);
			TextView t2= new TextView(this);
			TextView t3= new TextView(this);
			TextView t4= new TextView(this);
			TextView t5= new TextView(this);
			TextView t6= new TextView(this);
			TextView t7= new TextView(this);

			
			t1.setText("Name");
			t2.setText("UserName");
			t3.setText("Password");
			t4.setText("Pin No");
			t5.setText("Mob No");
			t6.setText("Bal");
			t7.setText("Sno");
			ll.addView(row1);
			
			    row1.addView(t1);
			    row1.addView(t2);
			    row1.addView(t3);
			    row1.addView(t4);
			    row1.addView(t5);
			    row1.addView(t6);
			    row1.addView(t7);
				
			    for(int x=0;x<modelList.size();++x)
			    {
			    	TableRow tr=new TableRow(Data.this);
			    
			    	
		               TextView tv = new TextView(getBaseContext());
					   tv.setText(modelList.get(x).getName());
					   tr.addView(tv);
					
					   TextView tv1 = new TextView(getBaseContext());
					   tv1.setText(modelList.get(x).getEmail());
					   tr.addView(tv1);
					   
					   TextView tv2 = new TextView(getBaseContext());
					   tv2.setText(modelList.get(x).getPassword());
					   tr.addView(tv2);
					   
					   TextView tv3 = new TextView(getBaseContext());
					   tv3.setText(modelList.get(x).getPinno());
					   tr.addView(tv3);
					   
					   TextView tv4 = new TextView(getBaseContext());
					   tv4.setText(modelList.get(x).getMobile());
					   tr.addView(tv4);
					   		  
					   TextView tv5 = new TextView(getBaseContext());
					   tv5.setText(modelList.get(x).getBalance());
					   tr.addView(tv5);
					   
					   TextView tv6 = new TextView(getBaseContext());
					   tv6.setText(modelList.get(x).getSno());
					   tr.addView(tv6);  
					   ll.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
					
		}
	}

}