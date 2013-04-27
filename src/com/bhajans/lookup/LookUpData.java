package com.bhajans.lookup;

import java.util.ArrayList;

import android.content.Context;

public class LookUpData {
  public static Context context = null;	
  public static boolean fetchData = true;
  public static CacheDB cacheDB = null;
  static LookUpInfo bhajanLookUp;
  static LookUpInfo raagaLookUp;
  static LookUpInfo deityLookUp;
  public static ArrayList<String> bhajanList = new ArrayList<String>();
  public static ArrayList<String> raagaList = new ArrayList<String>();
  public static ArrayList<String> deityList = new ArrayList<String>();

  public static ArrayList<String> getData(String type)
  {
	cacheDB = LookUpData.getCacheDB();  
	if(type.equals("bhajans"))
	{
		System.out.println("Here 001");
		
     if(bhajanList.size() > 0) 		System.out.println("Here 002");
 
     else
       { 
    	 if(cacheDB.fetchData(type).size() > 0)
    	 {
    			System.out.println("Here 003");
    	   bhajanList = (ArrayList<String>) cacheDB.fetchData("bhajans");
    	 }
    	 else  
    	 {
    			System.out.println("Here 004");
    	   fetchDataFromServer(type);
    	 }
       }
     return bhajanList;
	}
	else if(type .equals( "raagas"))
	{
	  if(raagaList.size() > 0) ;
	    else
	      { 
	    	 if(cacheDB.fetchData(type).size() > 0)
	      	   raagaList = (ArrayList<String>) cacheDB.fetchData("raagas");
	      	 else  
	      	   fetchDataFromServer(type);
	      }
	  return raagaList;
	}
	else //if(type .equals( "deities")
	{
	if(deityList.size() > 0) ;
	else
	  { 
	   if(cacheDB.fetchData(type).size() > 0)
	     raagaList = (ArrayList<String>) cacheDB.fetchData("raagas");
	   else  
	     fetchDataFromServer(type);
	  }
		  return deityList;
	}		
  }
  
  public static void fetchDataFromServer(String infoType)
  {
	 System.out.println("Fetching Data from server for" + infoType); 
   //shouldFetchData();	  
  // if (fetchData)
		cacheDB = LookUpData.getCacheDB();  
    {
     if(infoType .equals( "bhajans"))
     {
    	 bhajanLookUp = new LookUpInfo("bhajans","/lookup_all/");
         bhajanLookUp.lookupInfo();
         if(bhajanLookUp.list.size() > 0)
             bhajanList = bhajanLookUp.list;  
         cacheDB.performOperation("insert", "bhajans", bhajanList);
     }
     else if(infoType.equals("raagas")) 	
     {
    	 raagaLookUp = new LookUpInfo("raagas","/lookup_all/");
         raagaLookUp.lookupInfo();
         if(raagaLookUp.list.size() > 0)
             raagaList = raagaLookUp.list;
         cacheDB.performOperation("insert", "raagas", raagaList);
     }
     else if(infoType.equals("deities")) 	
     {
    	 deityLookUp = new LookUpInfo("deities","/lookup_all/");
         deityLookUp.lookupInfo();
         if(deityLookUp.list.size() > 0)
             deityList = deityLookUp.list;
         cacheDB.performOperation("insert", "deities", deityList);
     }
     //fetchData = false;
    }
  }
  
  private static void shouldFetchData()
  {
   //TODO: Implement cache stale
   if(bhajanList.size()==0 || raagaList.size() ==0 || deityList.size() == 0) 
	 fetchData = true;
  }
  
  public static void setContext(Context context)
  {
	LookUpData.context = context;
  }
  
  public static Context getContext()
  {
	  return LookUpData.context;
  }
  
  public static CacheDB getCacheDB()
  {
   if(cacheDB == null)
	   cacheDB = new CacheDB(getContext());
   return cacheDB;
  }
}
