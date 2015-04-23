package com.ceres.jailmon.data;

import android.graphics.Bitmap;

/**
 * Prisoner Brief class
 * 
 * @author Jie Zhuang <jiezhuang@163.com>
 * @created June, 2013
 */
public class Prisoner {
	private String m_strID;
	private String m_strName;
	private Bitmap m_bmpPhoto = null;
	private boolean m_bVIP = false;

	Prisoner(String strID, String strName, boolean bVIP) {
		m_strID = strID;
		m_strName = strName;
		m_bVIP = bVIP;
	}

	public String getID() {
		return m_strID;
	}

	public void setID(String strID) {
		m_strID = strID;
	}

	public String getName() {
		return m_strName;
	}

	public void setName(String strName) {
		m_strName = strName;
	}

	public boolean getVIP() {
		return m_bVIP;
	}

	public void setVIP(boolean bVIP) {
		m_bVIP = bVIP;
	}

	public Bitmap getPhoto() {
		return m_bmpPhoto;
	}

	public void setPhoto(Bitmap bmpPhoto) {		
		m_bmpPhoto = bmpPhoto;
	}
	
	public void recycle()
	{
		if( m_bmpPhoto != null )
		{
			m_bmpPhoto.recycle();
			m_bmpPhoto = null;
		}
	}
	

	@Override
	public String toString() {
		return m_strName;
	}
}
