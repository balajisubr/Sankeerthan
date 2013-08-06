package com.bhajans.lookup;

import java.util.ArrayList;
import java.util.HashMap;

import com.bhajans.AppConfig;

import android.content.Context;
import android.util.Log;

public class LookUpData {
	public static Context context = null;
	public static HashMap<String, Long> lookedUpTime= new HashMap<String, Long>();
	public static boolean fetchData = true;
	public static CacheDB cacheDB = null;
	static LookUpInfo lookUp;
	static LookUpInfo raagaLookUp;
	static LookUpInfo deityLookUp;
	public static ArrayList<String> bhajanList = new ArrayList<String>();
	public static ArrayList<String> raagaList = new ArrayList<String>();
	public static ArrayList<String> deityList = new ArrayList<String>();
	public static HashMap<String, ArrayList<String>> values = new HashMap<String, ArrayList<String>>(){

    private static final long serialVersionUID = 1L;};
	static
	 {
	   values.put(AppConfig.BHAJANS, bhajanList);
	   values.put(AppConfig.RAAGAS, raagaList);	
	   values.put(AppConfig.DEITIES, deityList);	
  	 }
	
	public static ArrayList<String> getData(String type) {
		cacheDB = LookUpData.getCacheDB();  
			if(values.get(type).size() > 0) {
			    if(System.currentTimeMillis() - getLastLookedUpTime(type) > 60000)
			    {
			        fetchDataFromServer(type);
			    }
			}
			else { 
				if(cacheDB.fetchData(type).size() > 0) {
					ArrayList<String> data = (ArrayList<String>) cacheDB.fetchData(type);
					values.put(type, data);
				}
				else {
					fetchDataFromServer(type);
				}
			}
			return values.get(type);
	}	
	
  
  public static void fetchDataFromServer(String infoType) {
	  Log.d("LookUpData", "Fetching Data from server for" + infoType) ;
	  cacheDB = LookUpData.getCacheDB();
	  lookUp = new LookUpInfo(infoType,"/lookup_all/");
	  lookUp.lookupInfo();
	  if(lookUp.list.size() > 0) {
		  if(values.get(infoType).size() > 0 ) {
			  cacheDB.performOperation(AppConfig.DELETE, infoType, values.get(infoType));  
		  }
		  values.put(infoType, lookUp.list);
		  cacheDB.performOperation(AppConfig.INSERT, infoType, values.get(infoType));				  
	  }
			  
	  ArrayList<String> bhajans = (ArrayList<String>) cacheDB.fetchData(infoType);
	  System.out.println("The size of bhajans is" + bhajans.size());
	  updateLastLookedUpTime(infoType, System.currentTimeMillis());
	  }  
  	
    
  	public synchronized static void setContext(Context context) {
  		LookUpData.context = context;
  	}
  
  	public synchronized static Context getContext() {
  		return LookUpData.context;
  	}
  
  	public synchronized static CacheDB getCacheDB() {
  		if(cacheDB == null)
  			cacheDB = new CacheDB(getContext());
  		return cacheDB;
  	}
  	
  	public synchronized static void updateLastLookedUpTime(String key, long timeInMsec)
  	{
  		System.out.println("updating last looked up time with "  + timeInMsec + "for" + key);
  	    lookedUpTime.put(key, timeInMsec);
  	}
  	
  	public synchronized static long getLastLookedUpTime(String key)
  	{
  		System.out.println("Fetching last looked up time for" + key);
  		System.out.println("The last looked up time is" + lookedUpTime.get(key) + "for" + key);
  	    return lookedUpTime.get(key);	
  	}
}
