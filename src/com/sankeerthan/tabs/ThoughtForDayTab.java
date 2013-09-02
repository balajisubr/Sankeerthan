package com.sankeerthan.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThoughtForDayTab extends CustomWebView {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        setWebViewURL("http://www.radiosai.org/pages/thought.asp");
        return super.onCreateView(inflater, container, savedInstanceState);
        }

    }