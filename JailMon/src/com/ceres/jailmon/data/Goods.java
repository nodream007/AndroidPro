package com.ceres.jailmon.data;

public class Goods {
	public String id;
	String name;
	String type;
	public float price;
	public int num;
	
	String desp;
	String desp_price;
	
	@Override
	public String toString() {
		
		if( desp == null )
			desp = String.format( "%s", name );
		
		return desp;
	}
	
public String price_toString() {
		
		if( desp_price == null )
			desp_price = String.format( "%.2f", price );
		
		return desp_price;
	}
}
