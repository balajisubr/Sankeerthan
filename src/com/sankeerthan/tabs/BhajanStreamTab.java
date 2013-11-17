package com.sankeerthan.tabs;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BhajanStreamTab extends CustomWebView {

    public BhajanStreamTab() {
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        setWebViewURL("http://www.radiosai.org/mobile/radiosai.html");
        return super.onCreateView(inflater, container, savedInstanceState);
        }
    }