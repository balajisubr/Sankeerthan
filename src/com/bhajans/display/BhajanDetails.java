package com.bhajans.display;

import java.util.ArrayList;

import com.bhajans.BhajanDetailsFragment;
import com.bhajans.MainActivity2;
import com.bhajans.OPlayerFragment;
import com.bhajans.model.Bhajan;
import com.bhajans.search.SearchBhajan;

import android.R;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class BhajanDetails implements IDisplayResults {
	
	public Context context;
	public SearchBhajan searchBhajan;
	public Bundle bundle = null;
    public Intent intent = null;

	
	public BhajanDetails(SearchBhajan search, MainActivity2 context){
		this.searchBhajan = search;
		this.context = context.getActivity();
		bundle = new Bundle();
	}
	
	public void processErrorsOrDisplay()
	{
	  if(searchBhajan.serverErrors.size() > 0) processServerErrors();
	  else if(searchBhajan.errorMsg != "" || searchBhajan.result == null) processResultErrors();
	  else navigateToDisplayActivity();
	}

	public void processServerErrors() {
	     if(searchBhajan.serverErrors.size() > 0)
	       {
	 	    System.out.println("Number of errors is" + searchBhajan.serverErrors.size());
	        Toast.makeText(this.context, searchBhajan.serverErrors.get(0) ,
	        Toast.LENGTH_LONG).show();
	       }		
	}

	public void processResultErrors() {
	    System.out.println("the error message is NOTT empty here");
	    intent = new Intent(context,ErrorDisplayActivity.class);
        bundle.putString("error",searchBhajan.errorMsg == "" ? "No such bhajans could be found." : searchBhajan.errorMsg);
        intent.putExtras(bundle);
        context.startActivity(intent);
	}

	public void navigateToDisplayActivity() {
		Bhajan result = searchBhajan.result;
		android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager(); 
		ArrayList<String> bhajans = new ArrayList<String>();
		bhajans.add(result.deity);bhajans.add(result.raaga);bhajans.add(result.lyrics);bhajans.add(result.meaning);
        BhajanDetailsFragment fragment = new BhajanDetailsFragment(bhajans);
        fragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit();
       // fragmentManager.beginTransaction().replace(android.R.id.content, pfragment).commit();
       

		 //intent = new Intent(context,DisplayBhajanDetails.class);
        // Bhajan result = searchBhajan.result;
         //System.out.println("raaga in method is " + result.raaga);
         //bundle.putString("raaga", result.raaga);
         //bundle.putString("lyrics", result.lyrics);
         //bundle.putString("meaning", result.meaning);
         //bundle.putString("deity", result.deity);
         //intent.putExtras(bundle);
         //context.startActivity(intent);
		
	}


}
