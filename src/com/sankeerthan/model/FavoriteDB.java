package com.sankeerthan.model;

import java.util.ArrayList;

import android.content.Context;


public class FavoriteDB {
	public static CacheDB cacheDB = null;
	public static CacheDB getCacheDB() {
		if(cacheDB == null) {
           System.out.println("cachedb is null");  
		   cacheDB = new CacheDB(getContext());
        }
		return cacheDB;
	}
	
	public static void setContext(Context context) {
		LookUpData.context = context;
	}
	  
	public static Context getContext() {
		return LookUpData.context;
	}
	  
	public static void addBhajan(String bhajan) {
		ArrayList<String> bhajanToAdd = new ArrayList<String>();
		bhajanToAdd.add(bhajan);
		FavoriteDB.getCacheDB().performOperation("INSERT", "favorites", bhajanToAdd);    
	}
	  
	public static void removeBhajan(String bhajan) {
		ArrayList<String> bhajanToAdd = new ArrayList<String>();
		bhajanToAdd.add(bhajan);
		FavoriteDB.getCacheDB().performOperation("DELETE", "favorites", bhajanToAdd);    
	}
	  
	public static int selectBhajan(String bhajan) {
		ArrayList<String> bhajanToAdd = new ArrayList<String>();
		bhajanToAdd.add(bhajan);
		int count = FavoriteDB.getCacheDB().fetchCount("favorites", bhajan);
		return count;
	}
	  
	public static ArrayList<String> fetchBhajans(){
		ArrayList<String> favBhajans = (ArrayList<String>) FavoriteDB.getCacheDB().fetchData("favorites");
		if(favBhajans.size().equals(0)) { 
			favBhajans.add("No Favorites");	
		}
		return favBhajans; 
	}


}
