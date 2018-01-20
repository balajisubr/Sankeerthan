package com.sankeerthan.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.sankeerthan.Sankeerthan;
import com.sankeerthan.lookup.LookUpInfo;

import android.content.Context;
import android.content.SharedPreferences;

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
	   values.put(Sankeerthan.BHAJANS, bhajanList);
	   values.put(Sankeerthan.RAAGAS, raagaList);	
	   values.put(Sankeerthan.DEITIES, deityList);	
  	 }
	
	public synchronized static ArrayList<String> getData(String type) {
		cacheDB = LookUpData.getCacheDB();  
			if(values.get(type).size() > 0) {
			    if(System.currentTimeMillis() - getLastLookedUpTime(type) > Sankeerthan.SERVER_DATA_REFRESH_PERIOD)
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
	  cacheDB = LookUpData.getCacheDB();
	  lookUp = new LookUpInfo(infoType,"/lookup_all/");
	  lookUp.lookupInfo();
	  if(lookUp.list.size() > 0) {
		  if(values.get(infoType).size() > 0 ) {
			  cacheDB.performOperation(Sankeerthan.DELETE, infoType, values.get(infoType));  
		  }
		  values.put(infoType, lookUp.list);
		  cacheDB.performOperation(Sankeerthan.INSERT, infoType, values.get(infoType));				  
	  }
			  
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
  		SharedPreferences settings = getContext().getSharedPreferences(key, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, timeInMsec);
        editor.commit();
  	}
  	
  	public synchronized static long getLastLookedUpTime(String key)
  	{   
  		SharedPreferences settings = getContext().getSharedPreferences(key, 0);
  		Long lookedUpTime = settings.getLong(key, System.currentTimeMillis());
  		return lookedUpTime;
  	}
  
}
