package com.sankeerthan.search.display;

import java.util.ArrayList;
import java.util.HashMap;

import com.sankeerthan.MainActivity;
import com.sankeerthan.model.Bhajan;
import com.sankeerthan.search.SearchBhajan;
import com.sankeerthan.search.SearchDeity;
import com.sankeerthan.search.SearchInfo;
import com.sankeerthan.search.SearchRaaga;
import com.sankeerthan.tabs.SearchTab;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

public class GenericDisplay {
	 
	public Context context; 
	public SearchBhajan searchBhajan;
	public SearchRaaga searchRaaga; 
	public SearchDeity searchDeity;
	public Bundle bundle = new Bundle();
	public HashMap<String,String> bhajanDetails = new HashMap<String, String>();
	public Intent intent = null;
	public int classId = 0;
	public ProgressDialog pd;
	private SearchInfo searchClass;
	 
	public GenericDisplay(SearchInfo searchClass, SearchTab searchTab)  {
		this.searchClass = searchClass; 
		this.context = searchTab.getActivity();
    }
	
   public void processErrorsOrDisplay() {
	   getSearchClass();
	   if(searchClass != null && searchClass.serverErrors.size() > 0) {
		   processServerErrors(); 
		   return;
       }
	   processResultErrorsorDisplay();
  }
   

   public void processServerErrors() {
       ((Activity) this.context).runOnUiThread(new Runnable() {
           public void run() {
              //Your code here
        	   Toast.makeText(GenericDisplay.this.context, searchClass.serverErrors.get(0), Toast.LENGTH_LONG).show();		
           }
        });
   }

   public void processResultErrorsorDisplay() {
	   if(searchClass.errorMsg.length() > 0 && !searchClass.errorMsg.isEmpty() && searchClass.errorMsg!="") {
		   navigateToErrorActivity(searchClass.errorMsg);	
	   }
		
	   else	{
		   switch (classId) {
		   case 1:
			   searchBhajan = (SearchBhajan) searchClass;
			   if(searchBhajan.result != null) {
				   bundle.putString("raaga", searchBhajan.result.raaga);
				   bundle.putString("lyrics", searchBhajan.result.lyrics);
				   bundle.putString("meaning", searchBhajan.result.meaning);
				   bundle.putString("deity", searchBhajan.result.deity);
				   bundle.putString("bhajan", searchBhajan.result.name);
				   bundle.putString("url", searchBhajan.result.url);
				   navigateToDisplayActivity(bundle);
			   }	
			   else {	
        	   navigateToErrorActivity("We could not retreive the data you requested! Please try again later! #12");
			   }
			   break;
           
		   case 2: 
			   SearchRaaga searchRaaga = (SearchRaaga) searchClass;
			   if(searchRaaga.list != null && searchRaaga.list.size() > 0) populateBhajanList(searchRaaga.list);
			   else {
				   navigateToErrorActivity("We could not retreive the data you requested! Please try again later! #13");
			   }  
			   break;
		   case 3:
			   SearchDeity searchDeity = (SearchDeity) searchClass;
			   if(searchDeity.list != null && searchDeity.list.size() > 0) populateBhajanList(searchDeity.list);
			   else
			   {
				navigateToErrorActivity("We could not retreive the data you requested! Please try again later! #14");
			   }
			   break;
		   default: 
			   break;
		   }
	   }
	}

	public void navigateToDisplayActivity(Bundle bundle) {
		android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager(); 
        FragmentTransaction ft = fragmentManager.beginTransaction();
    	Looper.prepare();
		switch (classId) {
        case 1:
            System.out.println("Here before creating fragment");
            BhajanDetailsFragment fragment = new BhajanDetailsFragment(bundle);
            System.out.println("Here before going to fragment");
            ft.replace(android.R.id.content, fragment).addToBackStack( "search" );
            break;
        case 2: case 3:      
        	BhajanResultsFragment fragment1 = new BhajanResultsFragment(bundle);
            ft.replace(android.R.id.content, fragment1).addToBackStack( "search" );
            break;
        default: 
        	break;
		}	
		
        ft.commit();
	}
	
	public void navigateToErrorActivity(final String errorMessage) {
		System.out.println("Error in navaigate to eror activity");
		Activity act = (Activity) this.context;
       	act.runOnUiThread(new Runnable() {
   	        public void run() {
   	        	Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
   	    }});
	}
	
	public void getSearchClass() {
		if(SearchBhajan.class.isAssignableFrom(searchClass.getClass())) {
			classId = 1;	
		}
		else if(SearchRaaga.class.isAssignableFrom(searchClass.getClass())) {
			classId = 2;	
		}
		else //if(SearchBhajan.class.isAssignableFrom(searchClass.getClass()))
		{
			classId = 3;	
		}
	}
	
	public void populateBhajanList(ArrayList<String> list) {
		String arrayResponse[] = new String[list.size()];
		for (int i=0;i<arrayResponse.length;i++) 
			arrayResponse[i]=list.toArray()[i].toString();
		bundle.putStringArray("bhajan", arrayResponse);
		navigateToDisplayActivity(bundle);
	}
	
}
