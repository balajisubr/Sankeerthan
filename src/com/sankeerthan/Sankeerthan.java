package com.sankeerthan;

import java.util.ArrayList;
import java.util.Iterator;

public final class Sankeerthan {
    public final static String URL = "http://192.241.133.198";
    public final static String BHAJANS = "bhajans";
    public final static String DEITIES = "deities";
    public final static String RAAGAS = "raagas";
    public final static String LYRICS = "lyrics";
    public final static String FAVORITES = "favorites";
    public final static String INSERT = "INSERT";
    public final static String DELETE = "DELETE";
    public final static long SERVER_DATA_REFRESH_PERIOD = 86400000;
    public Sankeerthan(){}
    
    public static String formatServerErrors(ArrayList<String> errors){
    	String consolidatedError = "";
    	String error = "";
    	Iterator<String> it = errors.iterator();
    	while(it.hasNext())
    	{
    	    error = it.next();
    	    consolidatedError +=  ' ' + error;
    	}
    	return consolidatedError;
    }
    
}
