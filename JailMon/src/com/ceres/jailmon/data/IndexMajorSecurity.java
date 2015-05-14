package com.ceres.jailmon.data;

import android.graphics.Bitmap;

public class IndexMajorSecurity {
	private String id;
	private String name;
	private Bitmap bmp;

	public Bitmap getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
