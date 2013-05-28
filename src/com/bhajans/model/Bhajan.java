package com.bhajans.model;

public class Bhajan
{
  public String raaga = "";
  public String meaning = "";
  public String lyrics = "";
  public String deity = "";
  public String name = "";
  
  public Bhajan(String raaga,String meaning,String lyrics, String deity, String name)
  {
   this.raaga = raaga;	
   this.meaning = meaning;
   this.lyrics = lyrics;
   this.deity = deity;
   this.name = name;
  }
}