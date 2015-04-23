package com.ceres.jailmon.data;

import android.graphics.Bitmap;

public class Police {
	String mID;
	String mName;
	Bitmap m_bmpPhoto;
	String mType;

	public String getmType() {
		return mType;
	}

	public void setmType(String mType) {
		this.mType = mType;
	}

	Police() {
	}

	Police(String strID, String strName, String type) {
		mID = strID;
		mName = strName;
		mType = type;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getName() {
		return mName;
	}

	public void setmID(String mID) {
		this.mID = mID;
	}

	public String getID() {
		return mID;
	}

	public Bitmap getPhoto() {
		return m_bmpPhoto;
	}

	public void setPhoto(Bitmap bmpPhoto) {
		m_bmpPhoto = bmpPhoto;
	}

	public void recycle() {
		if (m_bmpPhoto != null) {
			m_bmpPhoto.recycle();
			m_bmpPhoto = null;
		}
	}

	@Override
	public String toString() {
		return mName;
	}
}
