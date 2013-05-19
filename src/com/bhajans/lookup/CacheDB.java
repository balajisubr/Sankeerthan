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
		System.out.println("Before constructing ");
		if(context == null ) 		System.out.println("CONTENT IS NULL!!!!!!!!!!! ");
		else 		System.out.println("CONTENT IS NOT NUL!!!!!!! AND IS  " + context.getClass());
	    this.dbHelper = new CacheDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		System.out.println("After constructing ");

	}
	private Cursor bhajanCursor;
	private Cursor raagaCursor;
	private Cursor deityCursor;
	private Context context;
	private CacheDBHelper dbHelper;
	private SQLiteDatabase db;
	private static final String DATABASE_NAME = "BHAJANS";
	   private static final int DATABASE_VERSION = 1;
	   private static final String BHAJAN_TABLE_NAME = "bhajans";
	   private static final String RAAGA_TABLE_NAME = "raagas";
	   private static final String DEITY_TABLE_NAME = "deities";
	   private static final String COLUMN_NAME = "name";
	    String selectBhajanQuery = "SELECT  * FROM " + BHAJAN_TABLE_NAME;
	    String selectRaagaQuery = "SELECT  * FROM " + RAAGA_TABLE_NAME;
	    String selectDeityQuery = "SELECT  * FROM " + DEITY_TABLE_NAME;


	   
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
 //      writeDb = this.getWritableDatabase();
       //readDb = this.getReadableDatabase();
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
	        if(readDb == null)
	          readDb = this.getReadableDatabase();
	        else; 
	        return	readDb;
	       }
		public SQLiteDatabase getWriteDb()
	       {
	        if(writeDb == null)
	          writeDb = this.getReadableDatabase();
	        else;
	        return	writeDb;
	       }
   }
  
   @SuppressLint("NewApi")
public void performOperation(String Operation, String table, ArrayList<String> array1)
   {
	   System.out.println("The size of the data to be inserted is" + array1.size());
	   System.out.println("Inside the performOperation");
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
   
   public List<String> fetchData(String table)
   {
	   System.out.println("Trying to fetch data from the DB for" + table);
	   List<String> result = new ArrayList<String>();
	   SQLiteDatabase db = this.dbHelper.getReadDb(); 
	   result = this.fetchDatafromDB(table, db);
	   //db.close();
       //dbHelper.close();
	   return result;
   }
   
   
   public List<String> fetchDatafromDB(String table, SQLiteDatabase db) {
	    List<String> list = new ArrayList<String>(); 
	    String selectQuery = "SELECT  * FROM " + table;    
	 System.out.println("The cursor plac eeee");
	   
	 System.out.println("The table is" + table);

	   if(table == "bhajans")
	   {
		 System.out.println("select query is " + selectQuery);
	     bhajanCursor = db.rawQuery(selectQuery, null);
	     list = parseCursor(bhajanCursor);
	     System.out.println("AFTER THE PARSE CURSOR IN IF STATEMENT");
	     //System.out.println("From the bhajan cursor the first element is " + list.get(0));
	     //bhajanCursor.close();
	   }
	   else if(table == "raagas")
	   {
		   raagaCursor = db.rawQuery(selectQuery, null);
	     list = parseCursor(raagaCursor);
//	     raagaCursor.close();
	   }
	   else
	   {
		   deityCursor = db.rawQuery(selectQuery, null);
	     list = parseCursor(deityCursor);
	  //   deityCursor.close();
	   }
	//     db.close();
	     return list;
}

  public List<String> parseCursor(Cursor cursor)
  {
	  System.out.println("Here inside PARSECURSOR!!!");
	  List<String> list = new ArrayList<String>();
	 if (list.size() > 0) System.out.println("THE FIRST ELEMENT IN THE LIST IS " + list.get(0));
	    if (cursor.moveToFirst()) {
	        do {
	            list.add(cursor.getString(0));
	        } while (cursor.moveToNext());
	    }
	    else
	  	  System.out.println("CURSOR IS EMPTY");

	    	cursor.close();
	    	if (list.size() > 0)	  System.out.println("THE FIRST ELEMENT IN THE LIST IS " + list.get(0));
	    return list; 
  }
  
}
