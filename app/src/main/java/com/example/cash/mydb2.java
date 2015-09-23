package com.example.cash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class mydb2 {

	private final static String db_name="Transaction.db";
	

	
	
	private final static String CREATE_TRANSACTION_TABLE="create table 'trans' " + " (transId ,"
	           + " pinno ,"
		       + " pay ,"
	           + " datetime DATE ,"
		       + " sno INTEGER PRIMARY KEY AUTOINCREMENT ,"
		       + " pbalance ,"
	           + " nbalance ,"
		       + " success ,"
		       + " pinno1 );";
	
	private SQLiteDatabase db;
	private myinner mi;
	
	public mydb2(Context con){
		mi=new myinner(con,db_name,null,1);
	}
	
	public mydb2 open(){
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
		
		
		public int update12345(String rowId , String nbal, String suc,String table)
		{
			ContentValues args=new ContentValues();
			
			args.put("nbalance", nbal);
			args.put("success", suc);
			String[] whereArgs = new String[] {String.valueOf(rowId)};
			return db.update(table, args, "sno" + "= ?" , whereArgs )  ;
			 
		}
		
		public int update1234(String rowId ,String npbal, String succ,String table)
		{
			ContentValues args=new ContentValues();
			
			args.put("nbalance", npbal);
			args.put("success", succ);
			String[] whereArgs = new String[] {String.valueOf(rowId)};
			return db.update(table, args, "sno" + "= ?" , whereArgs )  ;
			 
		}
		
		public int updatetd(String rowId , String tid, String table)
		{
			ContentValues args=new ContentValues();
			
			args.put("transId", tid);
			
			String[] whereArgs = new String[] {String.valueOf(rowId)};
			
			return db.update(table, args, "sno" + "= ?" , whereArgs )  ;
			 
		}
		
		public int updateptd(String rowId , String ptid, String table)
		{
			ContentValues args=new ContentValues();
			
			args.put("transId", ptid);
			
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
			//db.execSQL(CREATE_NEWCUSTOMER_TABLE);
			db.execSQL(CREATE_TRANSACTION_TABLE);
				
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion,
					int newVersion) {
				// TODO Auto-generated method stub
				onCreate(db);
				
			}
			
		}	
	
}
