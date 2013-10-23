package com.sankeerthan.tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebView extends Fragment {

    public CustomerWebView() {

    }
	protected String url = "http://google.com";
	protected WebView webView;
    protected boolean webViewAvailable;
	
	protected void setWebViewURL(String url){
		this.url = url;
	}
	
	protected String getWebViewURL(){
		return url;
	}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        if (webView != null);
        
        else {
        	webView = new WebView(getActivity());
        
            webView.setOnKeyListener(new OnKeyListener(){

            
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                 if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                     webView.goBack();
                     return true;
                    }
                    return false;
            }
        });
            
            webView.setWebViewClient(new InnerWebViewClient()); // forces it to open in app
            webView.loadUrl(getWebViewURL());
        
            webViewAvailable = true;
        }
        
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        return webView;
    }
    
    public void onPause() { 	
        super.onPause();
        webView.onPause();
    }

    public void onResume() {
        webView.onResume();
        super.onResume();
    }

   
    public void onDestroyView() {
        webViewAvailable = false;
        super.onDestroyView();
    }

  
    public void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    public WebView getWebView() {
        return webViewAvailable ? webView : null;
    }

    public class InnerWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    
    public void loadUrl(String url) {
        if (webViewAvailable) getWebView().loadUrl(getWebViewURL());
        else Log.w("CustomerWebView", "WebView cannot be loaded");
    }

}

