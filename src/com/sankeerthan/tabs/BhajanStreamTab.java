package com.sankeerthan.tabs;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BhajanStreamTab extends CustomWebView {

    public BhajanStreamTab() {
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        setWebViewURL("http://media.radiosai.org/www/Bhajan.html");
        return super.onCreateView(inflater, container, savedInstanceState);
        }
    }