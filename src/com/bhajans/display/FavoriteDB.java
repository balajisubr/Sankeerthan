package com.bhajans.display;

import java.util.ArrayList;

import android.content.Context;

import com.bhajans.lookup.CacheDB;
import com.bhajans.lookup.LookUpData;

public class FavoriteDB {
	public static CacheDB cacheDB = null;
	public static CacheDB getCacheDB()
	  {
	   if(cacheDB == null)
		   cacheDB = new CacheDB(getContext());
	   return cacheDB;
	  }
	
	  public static void setContext(Context context)
	  {
		LookUpData.context = context;
	  }
	  
	  public static Context getContext()
	  {
		  return LookUpData.context;
	  }
	  
	  public static void addBhajan(String bhajan)
	  {
	  ArrayList<String> bhajanToAdd = new ArrayList<String>();
		System.out.println("The add bhajan has the value" + bhajan);
		bhajanToAdd.add(bhajan);
		FavoriteDB.getCacheDB().performOperation("INSERT", "favorites", bhajanToAdd);    
		System.out.println("The size of the favorites DB is " + FavoriteDB.fetchBhajans().size());
	  }
	  
	  public static void removeBhajan(String bhajan)
	  {
		  ArrayList<String> bhajanToAdd = new ArrayList<String>();
			System.out.println("The add bhajan has the value" + bhajan);
			bhajanToAdd.add(bhajan);
			FavoriteDB.getCacheDB().performOperation("DELETE", "favorites", bhajanToAdd);    
			System.out.println("The size of the favorites DB is " + FavoriteDB.fetchBhajans().size());

	  }
	  
	  public static ArrayList<String> fetchBhajans(){
		ArrayList<String> favBhajans = (ArrayList<String>) FavoriteDB.getCacheDB().fetchData("favorites");
		if(favBhajans.size()==0)
		  { 
		   favBhajans.add("No Favorites");	
	      }
		return favBhajans; 
	  }


}
