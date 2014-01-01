package com.sankeerthan.tabs;
import com.sankeerthan.MainActivity;
import com.sankeerthan.search.display.BhajanDetailsFragment;
import com.sankeerthan.search.display.BhajanResultsFragment;

import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class TabsListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;
    private Bundle mbundle;

    public TabsListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }
    
    public TabsListener(Activity activity, String tag, Class<T> clz, Bundle bundle) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
        mbundle = bundle;
    }
    /*
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
    	 if (mFragment == null) {
    		 Log.e("Sankeerthan mFragment is", "NULL");
             mFragment = Fragment.instantiate(mActivity, mClass.getName(), mbundle);
             ft.add(android.R.id.content, mFragment, mTag);
                 if(mTag.equals("search")) {
             
             MainActivity act = (MainActivity) mActivity;
             if(!act.activeFragment.isEmpty()){
	    		Log.e("Sankeerthan ACTIVEFRAGMENT PART 1", "THE ACTIVEFRAGMENT" + act.activeFragment);
 			    if(act.activeFragment.equals("details")){
 			       mFragment = new BhajanDetailsFragment();
    	           ft.replace(android.R.id.content, mFragment);
 			    }
 			    if(act.activeFragment.equals("list")){
 			       mFragment = new BhajanResultsFragment();
 	               ft.replace(android.R.id.content, mFragment);
 			    }
 			    }
 			}
 			
         } else {
        	 Log.e("Sankeerthan mFragment is", "NOT NULL");
         	MainActivity act = (MainActivity) mFragment.getActivity();
         	if(act !=null)
    			Log.e("Error", "THE size is" + act.searchTabFragments);	
         
    	    if(mTag.equals("search")){
    	    	if(act !=null)
    	    		if(act.searchTabFragments != 0){
    	    			Log.e("Sankeerthan", "THE size of number of fragments is" + act.searchTabFragments);	
    	    			return;
    	    		}
    	    		else{ 
    	    			if(!act.activeFragment.isEmpty()){
        	    			Log.e("Sankeerthan ACTIVEFRAGMENT PART 2", "THE ACTIVEFRAGMENT" + act.activeFragment);
    	    			    if(act.activeFragment.equals("details"))
    	    			       mFragment = new BhajanDetailsFragment();
    	    			    else
    	    			    	mFragment = new BhajanResultsFragment();
    	    			}
    	    		}
    	    }
    	    ft.attach(mFragment);
        }
    } *******/
    
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
        if (mFragment == null) {
            mFragment = Fragment.instantiate(mActivity, mClass.getName(), mbundle);
            ft.add(android.R.id.content, mFragment, mTag);
        } else {
        	MainActivity act = (MainActivity) mFragment.getActivity();
        	if(act !=null)
        	if((mTag.equals("search") && 
        			(act !=null &&
        			act.searchTabFragments == 0)) || 
        			!mTag.equals("search"))
            ft.attach(mFragment);
            // ft.addToBackStack("search");
        }
    } 	
    
    
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
        	ft.detach(mFragment);
        }
    }

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
    	ft.attach(mFragment);
    }
	


}