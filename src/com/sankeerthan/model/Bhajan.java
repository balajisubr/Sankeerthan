package com.sankeerthan.model;

public class Bhajan
{
	public String raaga   = "";
	public String meaning = "";
	public String lyrics  = "";
	public String deity   = "";
	public String name    = "";
	public String url     = "";
  
	public Bhajan(String raaga,String meaning,String lyrics, String deity, String name, String url) {
		this.raaga   = raaga;	
		this.meaning = meaning;
		this.lyrics  = lyrics;
		this.deity   = deity;
		this.name    = name;
	    this.url     = url;
	}
}