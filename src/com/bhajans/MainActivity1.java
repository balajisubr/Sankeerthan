
 package com.bhajans;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;

import com.bhajans.display.FavoriteFragment;

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
        		.setText("Search Bhajans")
                .setTabListener(new TabsListener<MainActivity2>(
                        this, "artist", MainActivity2.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
        		.setText("Favorites")
        		.setTabListener(new TabsListener<FavoriteFragment>(
                    this, "top", FavoriteFragment.class));
        actionBar.addTab(tab);       
    }
}
 