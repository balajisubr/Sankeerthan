package com.sankeerthan.tabs;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;

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

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
        if (mFragment == null) {
            mFragment = Fragment.instantiate(mActivity, mClass.getName(), mbundle);
            ft.add(android.R.id.content, mFragment, mTag);
        } else {
            ft.attach(mFragment);
        }
    }

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
           ft.detach(mFragment);
        	//ft.remove(mFragment);
        }
    }

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
    	ft.attach(mFragment);
    }


}