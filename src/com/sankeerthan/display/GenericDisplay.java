package com.sankeerthan.display;

import java.util.ArrayList;
import java.util.HashMap;

import com.sankeerthan.BhajanDetailsFragment;
import com.sankeerthan.BhajanResultsFragment;
import com.sankeerthan.MainActivity1;
import com.sankeerthan.MainActivity2;
import com.sankeerthan.model.Bhajan;
import com.sankeerthan.search.SearchBhajan;
import com.sankeerthan.search.SearchDeity;
import com.sankeerthan.search.SearchInfo;
import com.sankeerthan.search.SearchRaaga;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
	private SearchInfo searchClass;
	 
	public GenericDisplay(SearchInfo searchClass, MainActivity2 mainActivity2)  {
		this.searchClass = searchClass; 
		this.context = mainActivity2.getActivity();
    }
	
   public void processErrorsOrDisplay() {
	   this.getSearchClass();
	   if(searchClass != null && searchClass.serverErrors.size() > 0) {
		   processServerErrors(); 
		   return;
       }
	   processResultErrorsorDisplay();
   }

   public void processServerErrors() {
	   Toast.makeText(this.context, searchClass.serverErrors.get(0), Toast.LENGTH_LONG).show();		
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
        	   navigateToErrorActivity("We could not retreive the data you requested! Please try again later!");
			   }
			   break;
           
		   case 2: 
			   SearchRaaga searchRaaga = (SearchRaaga) searchClass;
			   if(searchRaaga.list != null || searchRaaga.list.size() > 0) populateBhajanList(searchRaaga.list);
			   else {
				   navigateToErrorActivity("We could not retreive the data you requested! Please try again later!");
			   }
			   
			   break;
		   case 3:
			   SearchDeity searchDeity = (SearchDeity) searchClass;
			   if(searchDeity.list != null || searchDeity.list.size() > 0) populateBhajanList(searchDeity.list);
			   else
			   {
				navigateToErrorActivity("We could not retreive the data you requested! Please try again later!");
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

		switch (classId) {
        case 1: 
            BhajanDetailsFragment fragment = new BhajanDetailsFragment(bundle);
            ft.replace(android.R.id.content, fragment).addToBackStack( "search" );
            break;
        case 2: case 3:      
        	BhajanResultsFragment fragment1 = new BhajanResultsFragment(bundle);
            ft.replace(android.R.id.content, fragment1).addToBackStack( "search" );
            break;
        default: 
        	break;
		}	
		
		//fragmentManager.addOnBackStackChangedListener(null);
        ft.commit();
        fragmentManager.executePendingTransactions();      //
	}
	
	public void navigateToErrorActivity(String errorMessage) {
		Toast.makeText(this.context, errorMessage, Toast.LENGTH_LONG).show();		
		/* TODO: Check if a fragment can be used
		 intent = new Intent(context,ErrorDisplayActivity.class);
	     bundle.putString("error",errorMessage);
	     intent.putExtras(bundle);
	     context.startActivity(intent);
	     */
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
