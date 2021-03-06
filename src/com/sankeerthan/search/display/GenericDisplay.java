package com.sankeerthan.search.display;

import java.util.ArrayList;
import java.util.HashMap;

import com.sankeerthan.Sankeerthan;
import com.sankeerthan.display.SankeerthanDialog;
import com.sankeerthan.search.SearchBhajan;
import com.sankeerthan.search.SearchDeity;
import com.sankeerthan.search.SearchInfo;
import com.sankeerthan.search.SearchRaaga;
import com.sankeerthan.tabs.SearchTab;
import com.sankeerthan.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
	   Activity act = (Activity) this.context;
	   act.runOnUiThread(new Runnable() {
		   public void run(){
			   if(searchClass != null && searchClass.serverErrors.size() > 0) {
				   processServerErrors(); 
				   return;
		       }
			   processResultErrorsorDisplay();		   
		   }
	   } ); 
  }
   
   public void processServerErrors() {
       ((Activity) this.context).runOnUiThread(new Runnable() {
           public void run() {
        	   AlertDialog alert = SankeerthanDialog.getAlertDialog(GenericDisplay.this.context, Sankeerthan.formatServerErrors(searchClass.serverErrors));
        	   alert.show();
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
        MainActivity act = (MainActivity) context;
    	//Looper.prepare();
		switch (classId) {
        case 1:
            BhajanDetailsFragment fragment = new BhajanDetailsFragment(bundle);
            act.searchTabFragments += 1;
            //act.lastFragment = fragment;
            //act.activeFragment = "details";
            ft.replace(android.R.id.content, fragment);//.addToBackStack( null );
            break;
        case 2: case 3:      
        	BhajanResultsFragment fragment1 = new BhajanResultsFragment(bundle);
            act.searchTabFragments += 1;
            //act.lastFragment = fragment1;
            //act.activeFragment ="list";
            ft.replace(android.R.id.content, fragment1);//.addToBackStack( null );
            break;
        default: 
        	break;
		}	
		
        ft.commit();
	}
	
	public void navigateToErrorActivity(final String errorMessage) {
		Activity act = (Activity) this.context;
       	act.runOnUiThread(new Runnable() {
   	        public void run() {
          	   AlertDialog alert = SankeerthanDialog.getAlertDialog(context, errorMessage);
          	   alert.show();
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
