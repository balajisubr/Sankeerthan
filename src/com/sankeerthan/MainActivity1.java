
 package com.sankeerthan;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;

import com.sankeerthan.tabs.IntroductionTab;
import com.sankeerthan.tabs.BhajanStreamTab;
import com.sankeerthan.tabs.FavoritesTab;
import com.sankeerthan.tabs.TabsListener;
import com.sankeerthan.tabs.ThoughtForDayTab;

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
                .setTabListener(new TabsListener<IntroductionTab>(
                        this, "search", IntroductionTab.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
        		.setText("Search Bhajans")
                .setTabListener(new TabsListener<MainActivity2>(
                        this, "search", MainActivity2.class));
        actionBar.addTab(tab);


        tab = actionBar.newTab()
        		.setText("Favorites")
        		.setTabListener(new TabsListener<FavoritesTab>(
                    this, "top", FavoritesTab.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
        		.setText("RadioSai Bhajan Stream")
        		.setTabListener(new TabsListener<BhajanStreamTab>(
                    this, "top", BhajanStreamTab.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
        		.setText("RadioSai Thought for the Day")
        		.setTabListener(new TabsListener<ThoughtForDayTab>(
                    this, "top", ThoughtForDayTab.class));
        actionBar.addTab(tab);        
    }
}
 
