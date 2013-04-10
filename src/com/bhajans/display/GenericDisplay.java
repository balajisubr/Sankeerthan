package com.bhajans.display;

import java.util.ArrayList;

import com.bhajans.BhajanResultsActivity;
import com.bhajans.MainActivity2;
import com.bhajans.search.SearchBhajan;
import com.bhajans.search.SearchDeity;
import com.bhajans.search.SearchInfo;
import com.bhajans.search.SearchRaaga;

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
    public Intent intent = null;
    public int classId = 0;
	//private Class<? extends SearchInfo> searchClass;
	private SearchInfo searchClass;
	
	public GenericDisplay(SearchInfo searchClass, MainActivity2 context)
	{
		this.searchClass = searchClass;
		this.context = context;
	}
	
	public void processErrorsOrDisplay()
	{
	  this.getSearchClass();
	  if(searchClass != null && searchClass.serverErrors.size() > 0) 
		{
		  processServerErrors(); 
		 return;
		 }
	  processResultErrorsorDisplay();
	}

	public void processServerErrors() {
	 	  System.out.println("Number of errors is" + searchClass.serverErrors.size());
	      Toast.makeText(this.context, searchClass.serverErrors.get(0), Toast.LENGTH_LONG).show();		
	}

	public void processResultErrorsorDisplay() {
		System.out.println("search class error msg"  + searchClass.errorMsg);
		if(searchClass.errorMsg == "") System.out.println("Here 5 and the size is " + searchClass.errorMsg.length() + searchClass.errorMsg.isEmpty());
		if(searchClass.errorMsg != "") System.out.println("Here 6"); 
		if(searchClass.errorMsg.length() > 0 && !searchClass.errorMsg.isEmpty() && searchClass.errorMsg!="") 
	    {
		 System.out.println("errorMsg is ++++" + searchClass.errorMsg);	
		 navigateToErrorActivity(searchClass.errorMsg);	
		}
		
	    else
		{
	        System.out.println("Here 6.99");
	
		switch (classId) {
        case 1: SearchBhajan searchBhajan = (SearchBhajan) searchClass;
        System.out.println("Here 7");
        if(searchBhajan.result != null) 
        {
            bundle.putString("raaga", searchBhajan.result.raaga);
            bundle.putString("lyrics", searchBhajan.result.lyrics);
            bundle.putString("meaning", searchBhajan.result.meaning);
            bundle.putString("deity", searchBhajan.result.deity);
        	navigateToDisplayActivity(bundle);
        }
        else
        {
            System.out.println("Here 8");
        	navigateToErrorActivity("We could not retreive the data you requested! Please try again later!");
        }
        break;
        case 2: SearchRaaga searchRaaga = (SearchRaaga) searchClass;
        if(searchRaaga.list != null || searchRaaga.list.size() > 0) populateBhajanList(searchRaaga.list);
        else
        {
            System.out.println("Here 9");
        	navigateToErrorActivity("We could not retreive the data you requested! Please try again later!");
        }
        break;
        case 3: SearchDeity searchDeity = (SearchDeity) searchClass;
        if(searchDeity.list != null || searchDeity.list.size() > 0) populateBhajanList(searchDeity.list);
        else
        {
            System.out.println("Here 10");
        	navigateToErrorActivity("We could not retreive the data you requested! Please try again later!");
        }
        break;
        default: 
        break;
       }
	 }
	}

	public void navigateToDisplayActivity(Bundle bundle) {
		Intent intent = null;
		switch (classId) {
        case 1: 
          intent = new Intent(context,DisplayBhajanDetails.class);
          break;
        case 2: case 3:          
          intent = new Intent(context,BhajanResultsActivity.class);
          break;
        default: 
        break;
       }		 
         intent.putExtras(bundle);
         context.startActivity(intent);
	}
	
	public void navigateToErrorActivity(String errorMessage)
	{
		 intent = new Intent(context,ErrorDisplayActivity.class);
	     bundle.putString("error",errorMessage);
	     intent.putExtras(bundle);
	     context.startActivity(intent);
	}
	
	public void getSearchClass()
	{
		if(SearchBhajan.class.isAssignableFrom(searchClass.getClass()))
		{
		  classId = 1;	
		}
		else if(SearchRaaga.class.isAssignableFrom(searchClass.getClass()))
		{
	      classId = 2;	
		}
		else //if(SearchBhajan.class.isAssignableFrom(searchClass.getClass()))
		{
		  classId = 3;	
		}
	}
	
	public void populateBhajanList(ArrayList<String> list)
	{
	 System.out.println("in populateBhajanlistactivity");	
	  String arrayResponse[] = new String[list.size()];
	  for (int i=0;i<arrayResponse.length;i++) 
		  arrayResponse[i]=list.toArray()[i].toString();
	  
	  bundle.putStringArray("bhajan", arrayResponse);
      navigateToDisplayActivity(bundle);
	}


}
