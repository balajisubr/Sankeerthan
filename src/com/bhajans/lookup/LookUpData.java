package com.bhajans.lookup;

import java.util.ArrayList;

public class LookUpData {
  public static boolean fetchData = true;
  static LookUpInfo bhajanLookUp;
  static LookUpInfo raagaLookUp;
  static LookUpInfo deityLookUp;
  public static ArrayList<String> bhajanList = new ArrayList<String>();
  public static ArrayList<String> raagaList = new ArrayList<String>();
  public static ArrayList<String> deityList = new ArrayList<String>();

  public static void fetchData()
  {
   shouldFetchData();	  
   if (fetchData)
    {
	 bhajanLookUp = new LookUpInfo("bhajans","/lookup_all/");  
	 raagaLookUp = new LookUpInfo("raagas","/lookup_all/");
	 deityLookUp = new LookUpInfo("deities","/lookup_all/");
     bhajanLookUp.lookupInfo();
     raagaLookUp.lookupInfo();
     deityLookUp.lookupInfo();
     if(bhajanLookUp.list.size() > 0)
     bhajanList = bhajanLookUp.list;
     if(raagaLookUp.list.size() > 0)
     raagaList = raagaLookUp.list;
     if(deityLookUp.list.size() > 0)
     deityList = deityLookUp.list;
     fetchData = false;
    }
  }
  
  private static void shouldFetchData()
  {
   //TODO: Implement cache stale
   if(bhajanList.size()==0 || raagaList.size() ==0 || deityList.size() == 0) 
	 fetchData = true;
  }
  
  

}
