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

public class CustomWebView extends Fragment{
	
	protected String url = "http://android.com";

	protected WebView webView;

    protected boolean mIsWebViewAvailable;
	
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

            @Override
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
        
            mIsWebViewAvailable = true;
        }
        
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        return webView;
    }
    
    public void onPause() { 	
        super.onPause();
        webView.onPause();
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @Override
    public void onResume() {
        webView.onResume();
        super.onResume();
    }

    /**
     * Called when the WebView has been detached from the fragment.
     * The WebView is no longer available after this time.
     */
    @Override
    public void onDestroyView() {
        mIsWebViewAvailable = false;
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    /**
     * Gets the WebView.
     */
    public WebView getWebView() {
        return mIsWebViewAvailable ? webView : null;
    }

    /* To ensure links open within the application */
    public class InnerWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    
    public void loadUrl(String url) {
        if (mIsWebViewAvailable) getWebView().loadUrl(getWebViewURL());
        else Log.w("ImprovedWebViewFragment", "WebView cannot be found. Check the view and fragment have been loaded.");
    }

}

