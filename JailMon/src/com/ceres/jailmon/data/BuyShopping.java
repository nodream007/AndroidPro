package com.ceres.jailmon.data;

public class BuyShopping {

	private int id;
	private String name;
	private String picture;
	private String explain;
	private String price;
	
	private String cid;
	private String count;
	
	public String getCid() {
		return cid;
	}
	public void setCid(String c_id) {
		this.cid = c_id;
	}
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
