package com.bhajans.lookup;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class CacheDB {
	
	public CacheDB(Context context){
		System.out.println("Before constructing ");
	  	this.context = context;
	    this.dbHelper = new CacheDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		System.out.println("After constructing ");

	}
	private Context context;
	private CacheDBHelper dbHelper;
	private SQLiteDatabase db;
	private static final String DATABASE_NAME = "";
	   private static final int DATABASE_VERSION = 1;
	   private static final String BHAJAN_TABLE_NAME = "bhajans";
	   private static final String RAAGA_TABLE_NAME = "raagas";
	   private static final String DEITY_TABLE_NAME = "deities";
	   private static final String COLUMN_NAME = "name";
	   
	   private static final String BHAJAN_TABLE_CREATE =
	            "CREATE TABLE " + BHAJAN_TABLE_NAME + " (" +
	            COLUMN_NAME + " TEXT);";
			    
	   private static final String RAAGA_TABLE_CREATE =
			            "CREATE TABLE " + RAAGA_TABLE_NAME + " (" +
			            COLUMN_NAME + " TEXT);";

	   private static final String DEITY_TABLE_CREATE =
			            "CREATE TABLE " + DEITY_TABLE_NAME + " (" +
			            COLUMN_NAME + " TEXT);";
   class CacheDBHelper extends SQLiteOpenHelper{ 
     SQLiteDatabase readDb = null;
     SQLiteDatabase writeDb = null;
   public CacheDBHelper(Context context, String name, CursorFactory factory,
			int version) {
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
       
//	   System.out.println("Before the cachedbhelper");
	   System.out.println("After the cachedbhelper");

	}

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	    	System.out.println("Here inside the oncreate of cacheDBHelper");
	        db.execSQL(BHAJAN_TABLE_CREATE);
	        db.execSQL(RAAGA_TABLE_CREATE);
	        db.execSQL(DEITY_TABLE_CREATE);

	    }

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
		
		public SQLiteDatabase getReadDb()
	       {
	        if(readDb.equals(null))
	          readDb = this.getReadableDatabase();
	        else; 
	        return	readDb;
	       }
		public SQLiteDatabase getWriteDb()
	       {
	        if(writeDb.equals(null))
	          writeDb = this.getReadableDatabase();
	        else;
	        return	writeDb;
	       }
   }
  
   @SuppressLint("NewApi")
public void performOperation(String Operation, String table, ArrayList<String> array1)
   {
	   SQLiteDatabase db = dbHelper.getWriteDb();

	   String INSERT = "insert into "   
	            + table + " (" + COLUMN_NAME + ") values (?)";
	   
	   String DELETE = "delete from " + table; 
	   
	   String FETCH = "select DISTINCT(" + COLUMN_NAME + "from " + table + ")";

       db.beginTransaction();

       SQLiteStatement dbStmt = db.compileStatement(Operation.equals("INSERT") ? INSERT : DELETE);

	   if(Operation.equals("INSERT"))
	   {  
	        int aSize = array1.size();

	     //   try {

	            for (int i = 0; i < aSize; i++) {
	                dbStmt.bindString(1, array1.get(i));
	                dbStmt.executeInsert();
	       //     }

	         //.setTransactionSuccessful();
	        }
	   }
	   
	   if(Operation.equals("DELETE"))
	   {
		   dbStmt.executeUpdateDelete();
	        
	   }
	   
	   if(Operation.equals("SELECT"))
	   {
		   fetchDatafromDB(table);
	   }
	   //catch (SQLException e) {
         //  e.printStackTrace();
       //}
	   db.setTransactionSuccessful();
	   db.endTransaction();
//	        }

	   try {
	        db.close();
	       } catch (Exception e) {
	        e.printStackTrace();
	       }
	    }
   
   public List<String> fetchDatafromDB(String table) {
	   
//	    CacheDBHelper dbHelper = new CacheDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	    SQLiteDatabase db = this.dbHelper.getReadDb();

	    List<String> list = new ArrayList<String>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + table;
	 
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            cursor.getString(0);
	        } while (cursor.moveToNext());
	    }
	    // return contact list
	    return list;
	}
}
