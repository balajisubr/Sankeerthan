
 package com.sankeerthan;

import java.io.Serializable;
import java.util.Stack;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.sankeerthan.tabs.FAQFeedback;
import com.sankeerthan.tabs.FeedbackTab;
import com.sankeerthan.tabs.IntroductionTab;
import com.sankeerthan.tabs.FavoritesTab;
import com.sankeerthan.tabs.SearchTab;
import com.sankeerthan.tabs.Streams;
import com.sankeerthan.tabs.TabsListener;
import com.sankeerthan.tabs.ThoughtForDayTab;

public class MainActivity extends Activity {
		
	public int searchTabFragments= 0;
	public Fragment lastFragment;
	public String activeFragment = "";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        Tab tab = actionBar.newTab()
        		.setText("About Sankeerthan")
                .setTabListener(new TabsListener<IntroductionTab>(
                        this, "about", IntroductionTab.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
        		.setText("Search Bhajans")
                .setTabListener(new TabsListener<SearchTab>(
                        this, "search", SearchTab.class));
        actionBar.addTab(tab);


        tab = actionBar.newTab()
        		.setText("Favorites")
        		.setTabListener(new TabsListener<FavoritesTab>(
                    this, "favorites", FavoritesTab.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
        		.setText("RadioSai Bhajan Stream")
        		.setTabListener(new TabsListener<Streams>(
                    this, "stream", Streams.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
        		.setText("RadioSai's Sai Inspires")
        		.setTabListener(new TabsListener<ThoughtForDayTab>(
                    this, "thought", ThoughtForDayTab.class));
        actionBar.addTab(tab);    
        
        tab = actionBar.newTab()
        		.setText("Frequently Asked Questions (FAQ)")
        		.setTabListener(new TabsListener<FAQFeedback>(
                    this, "faq", FAQFeedback.class));
        actionBar.addTab(tab);        
        tab = actionBar.newTab()
        		.setText("Feedback")
        		.setTabListener(new TabsListener<FeedbackTab>(
                    this, "faq", FeedbackTab.class));
        actionBar.addTab(tab);
    }
    
    public void onBackPressed() {
        new AlertDialog.Builder(this)
               .setMessage("Are you sure you want to exit Sankeerthan?")
               .setCancelable(true)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                   }
               })
               .setNegativeButton("No", null)
               .show();
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	String bundleString = savedInstanceState.getString("activeFragment");
    	if(bundleString != null)
    	    activeFragment = bundleString; 
     }
    
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	outState.putString("activeFragment", activeFragment);
    }

   
}
 

