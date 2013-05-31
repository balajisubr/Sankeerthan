package com.bhajans.lookup;

import java.util.ArrayList;
import java.util.List;

import com.bhajans.MainActivity2;

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
	    this.dbHelper = new CacheDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public CacheDB()
	{
	 	
	}
	
	private Cursor bhajanCursor;
	private Cursor raagaCursor;
	private Cursor deityCursor;
	private Cursor favoriteCursor;
	private Context context;
	private CacheDBHelper dbHelper;
	private SQLiteDatabase db;
	private static final String DATABASE_NAME = "BHAJANS";
	private static final int DATABASE_VERSION = 1;
	private static final String BHAJAN_TABLE_NAME = "bhajans";
	private static final String RAAGA_TABLE_NAME = "raagas";
	private static final String DEITY_TABLE_NAME = "deities";
	private static final String FAV_TABLE_NAME = "favorites";
	private static final String COLUMN_NAME = "name";
	
	private String selectBhajanQuery = "SELECT  * FROM " + BHAJAN_TABLE_NAME;
	private String selectRaagaQuery = "SELECT  * FROM " + RAAGA_TABLE_NAME;
	private String selectDeityQuery = "SELECT  * FROM " + DEITY_TABLE_NAME;
	private String selectFavQuery = "SELECT  * FROM " + FAV_TABLE_NAME;

	private static final String BHAJAN_TABLE_CREATE = "CREATE TABLE " 
	+ BHAJAN_TABLE_NAME + " (" + COLUMN_NAME + " TEXT);";
			    
	private static final String RAAGA_TABLE_CREATE = "CREATE TABLE " 
	+ RAAGA_TABLE_NAME + " (" + COLUMN_NAME + " TEXT);";

	private static final String DEITY_TABLE_CREATE = "CREATE TABLE " 
	+ DEITY_TABLE_NAME + " (" + COLUMN_NAME + " TEXT);";
	   
	private static final String FAV_TABLE_CREATE = "CREATE TABLE " 
	+ FAV_TABLE_NAME + " (" + COLUMN_NAME + " TEXT);";
	   
	class CacheDBHelper extends SQLiteOpenHelper { 
		SQLiteDatabase readDb = null;
		SQLiteDatabase writeDb = null;
		public CacheDBHelper(Context context, String name, 
				CursorFactory factory, int version) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(BHAJAN_TABLE_CREATE);
	        db.execSQL(RAAGA_TABLE_CREATE);
	        db.execSQL(DEITY_TABLE_CREATE);
	        db.execSQL(FAV_TABLE_CREATE);
	    }

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
		
		public SQLiteDatabase getReadDb() {
			if(readDb == null)
				readDb = this.getReadableDatabase();
	        else; 
	        return	readDb;
	    }
		
		public SQLiteDatabase getWriteDb() {
	        if(writeDb == null)
	        	writeDb = this.getReadableDatabase();
	        else;
	        return	writeDb;
	    }
	}
  
   @SuppressLint("NewApi")
   
   public void performOperation(String Operation, String table, ArrayList<String> array1) {
	   SQLiteDatabase db = dbHelper.getWriteDb();

	   String INSERT = "insert into "   
	            + table + " (" + COLUMN_NAME + ") values (?)";
	   
	   String DELETE = "delete from " + table 
			   + " where " + COLUMN_NAME + "= (?)"  ; 
	   
       db.beginTransaction();

       SQLiteStatement dbStmt = db.compileStatement(
    		   Operation.equals("INSERT") ? INSERT : DELETE);

	   if(Operation.equals("INSERT")) {  
		   int aSize = array1.size();
		   for (int i = 0; i < aSize; i++) {
			   dbStmt.bindString(1, array1.get(i));
			   dbStmt.executeInsert();
	       //     }
	       //.setTransactionSuccessful();
	        }
	   }
	   
	   if(Operation.equals("DELETE")) {
		   int aSize = array1.size();
		   
		   for (int i = 0; i < aSize; i++) {
			   dbStmt.bindString(1, array1.get(i));
			   dbStmt.executeUpdateDelete();
	       }
		   
	       //.setTransactionSuccessful();
	       // }
	   }
	  /* 
	   if(Operation.equals("SELECT"))
	   {
		   for (int i = 0; i < aSize; i++) {
               dbStmt.bindString(1, array1.get(i));
               dbStmt.
           }   
	   }
	   */
	   
	   //catch (SQLException e) {
         //  e.printStackTrace();
       //}
	   db.setTransactionSuccessful();
	   db.endTransaction();
//	        }

	   try {
	   //     db.close();
	  //      dbHelper.close();
	       } catch (Exception e) {
	        e.printStackTrace();
	       }
	    }
   
   public List<String> fetchData(String table) {
	   List<String> result = new ArrayList<String>();
	   SQLiteDatabase db = this.dbHelper.getReadDb(); 
	   result = this.fetchDatafromDB(table, db);
	   //db.close();
       //dbHelper.close();
	   return result;
   }
   
   public int fetchCount(String table, String value) {
	  SQLiteDatabase db = dbHelper.getWriteDb(); 
	  String FETCH = "select COUNT(*) " + "from " + table + " where " + COLUMN_NAME + " = " + "'" + value+ "'"  ;
      SQLiteStatement dbStmt = db.compileStatement(FETCH);
      return (int) dbStmt.simpleQueryForLong();
   }
   
   public List<String> fetchDatafromDB(String table, SQLiteDatabase db) {
	   List<String> list = new ArrayList<String>(); 
	   String selectQuery = "SELECT  * FROM " + table;    
	   
	   if(table.equals("bhajans")) {
		   bhajanCursor = db.rawQuery(selectQuery, null);
		   list = parseCursor(bhajanCursor);
	   }
	   else if(table.equals("raagas")) {
		   raagaCursor = db.rawQuery(selectQuery, null);
		   list = parseCursor(raagaCursor);
	   }
	   else if(table.equals("deities")) {
		   deityCursor = db.rawQuery(selectQuery, null);
		   list = parseCursor(deityCursor);
	   }
	   
	   else if(table.equals("favorites")) {
		   favoriteCursor = db.rawQuery(selectQuery, null);
		   list = parseCursor(favoriteCursor);
	   }
	//     db.close();
	   return list;
   }

   public List<String> parseCursor(Cursor cursor) {
	   List<String> list = new ArrayList<String>();
	   if (cursor.moveToFirst()) {
		   do {
			   list.add(cursor.getString(0));
	      } while (cursor.moveToNext());
	   }
	   else
		   cursor.close();
	   return list; 
   }
  }
