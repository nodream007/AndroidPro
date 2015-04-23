package com.ceres.jailmon.database;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/*
 * ˵������Ʒ��
 * ��;����ݿ�Ķ�ȡ��д��
 */
public class Goods {
	private int id;
	private String name;
	private String description;
	private String price;
	private byte[] pic;
	private String num;
	private boolean selected;
	private String picStrUrl;
	
	public String getPicStrUrl() {
		return picStrUrl;
	}

	public void setPicStrUrl(String picStrUrl) {
		this.picStrUrl = picStrUrl;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
   
	public Goods()
    {
    	
    }

	public Goods(int id,String name,String description,String price,byte[] pic,String picUrlStr)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.pic = pic;
		this.picStrUrl = picUrlStr;
	}
	
	public Goods(int i,String name,String price,String description)
	{
		this.id = i;
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public Goods(byte[] pic)
	{
		this.pic = pic;
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

	public String getDescrip() {
		return description;
	}
	public void setDescrip(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public byte[] getPic() {
		return pic;
	}
	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	
	public String toString()
	{
		return "��ƣ�"+ name + "   ������"+description + "   Id��"+  id + "   �۸�"+price ;
	}	
}
