package com.example.cash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

	public class mydb {
		private final static String db_name="registration.db";
		
		
		private final static String  CREATE_NEWCUSTOMER_TABLE="create table 'register'" + "(name text , "
		+"email ,"
		+"password ,"
		+"mobile  ,"
		+"balance ,"
		+"sno INTEGER PRIMARY KEY AUTOINCREMENT ,"
		+"pinno );";
		
		
	
		
		private SQLiteDatabase db;
		private myinner mi;
		
		public mydb(Context con){
			mi=new myinner(con,db_name,null,1);
		}
		
		public mydb open(){
			try{
				db=mi.getWritableDatabase();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return this;
		}
			
			public void close(){
				db.close();
			}
			
			public long insertRows(ContentValues values,String table){
				
				//ContentValues in = new ContentValues();
				//in.putAll(values);
				
				long val=db.insert(table, null, values);
				return val;
		}
			
			public int update123(String rowId , String bd, String table)
			{
				ContentValues args=new ContentValues();
				
				args.put("balance", bd);
				
				String[] whereArgs = new String[] {String.valueOf(rowId)};
				
				return db.update(table, args, "pinno" + "= ?" , whereArgs )  ;
				 
			}
			
			public int update12(String rowId , String bd1, String table)
			{
				ContentValues args=new ContentValues();
				
				args.put("balance", bd1);
				
				String[] whereArgs = new String[] {String.valueOf(rowId)};
				
				return db.update(table, args, "pinno" + "= ?" , whereArgs )  ;
				 
			}
			
			public int update1(String rowId , String pid, String table)
			{
				ContentValues args=new ContentValues();
				
				args.put("pinno", pid);
				
				String[] whereArgs = new String[] {String.valueOf(rowId)};
				
				return db.update(table, args, "sno" + "= ?" , whereArgs )  ;
				 
			}
			
			public Cursor getAllValues(String table)
			{
				Cursor myResult;
				myResult=db.query(table, null, null, null, null, null, null,null);
				return myResult;
			}
			
			private static class myinner extends SQLiteOpenHelper{

				public myinner(Context context, String name, CursorFactory factory,
						int version) {
					super(context, name, factory, version);
					// TODO Auto-generated constructor stub
				}

				@Override
				public void onCreate(SQLiteDatabase db) {
					// TODO Auto-generated method stub
				db.execSQL(CREATE_NEWCUSTOMER_TABLE);
	
					
				}

				@Override
				public void onUpgrade(SQLiteDatabase db, int oldVersion,
						int newVersion) {
					// TODO Auto-generated method stub
					onCreate(db);
					
				}
				
			}	
	}