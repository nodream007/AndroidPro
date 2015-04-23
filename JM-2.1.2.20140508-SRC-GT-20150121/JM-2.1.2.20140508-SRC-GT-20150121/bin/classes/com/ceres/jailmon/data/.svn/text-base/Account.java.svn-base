package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.ceres.jailmon.AppException;

import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.Xml;

public class Account {
	private float m_fBalance;

	public void setM_fBalance(float m_fBalance) {
		this.m_fBalance = m_fBalance;
	}

	private String m_strLastTime;

	public void setM_strLastTime(String m_strLastTime) {
		this.m_strLastTime = m_strLastTime;
	}

	public float getBalance() {
		return m_fBalance;
	}

	public String getLastTime() {
		return m_strLastTime;
	}

	public static Account parse(InputStream in) throws AppException {

		try {
			XmlPullParser parser = Xml.newPullParser();

			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equals("remainder")) {
						String strLastTime = parser.getAttributeValue(null,
								"lasttime");
						String strBalance = parser.getAttributeValue(null,
								"balance");

						Account info = new Account();

						if (info != null) {
							if (strBalance != null)
								info.m_fBalance = Float.parseFloat(strBalance);
							if(TextUtils.isEmpty(strLastTime)){
								strLastTime = "";
							}
							info.m_strLastTime = strLastTime;
						}

						return info;
					}
				} else if (parser.getEventType() == XmlPullParser.END_TAG) {
				} else if (parser.getEventType() == XmlPullParser.TEXT) {
				}
				parser.next();
			}
		} catch (IOException e) {
			throw AppException.xml(e);
		} catch (XmlPullParserException e) {
			throw AppException.xml(e);
		}

		return null;
	}
}
