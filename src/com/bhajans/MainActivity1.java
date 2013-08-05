
 package com.bhajans;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;

import com.bhajans.display.BStreamWebView;
import com.bhajans.display.FavoriteFragment;
import com.bhajans.display.ThoughtForDay;

public class MainActivity1 extends Activity {
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        Tab tab = actionBar.newTab()
        		.setText("About Sankeerthan")
                .setTabListener(new TabsListener<MainActivity2>(
                        this, "search", MainActivity2.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
        		.setText("Search Bhajans")
                .setTabListener(new TabsListener<MainActivity2>(
                        this, "search", MainActivity2.class));
        actionBar.addTab(tab);


        tab = actionBar.newTab()
        		.setText("Favorites")
        		.setTabListener(new TabsListener<FavoriteFragment>(
                    this, "top", FavoriteFragment.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
        		.setText("RadioSai Bhajan Stream")
        		.setTabListener(new TabsListener<BStreamWebView>(
                    this, "top", BStreamWebView.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
        		.setText("RadioSai Thought for the Day")
        		.setTabListener(new TabsListener<ThoughtForDay>(
                    this, "top", ThoughtForDay.class));
        actionBar.addTab(tab);        
    }
}
 
