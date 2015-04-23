package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.ceres.jailmon.AppException;

public class ReceiptResult {

	private String m_bOK;

	public String getM_bOK() {
		return m_bOK;
	}

	public void setM_bOK(String m_bOK) {
		this.m_bOK = m_bOK;
	}

	/*
	 * public static PurchaseResult parse(InputStream in) throws AppException {
	 * 
	 * try { XmlPullParser parser = Xml.newPullParser();
	 * parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	 * parser.setInput(in, null); parser.nextTag();
	 * 
	 * while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) { if
	 * (parser.getEventType() == XmlResourceParser.START_TAG) { String name =
	 * parser.getName();
	 * 
	 * if(name.equals("purchaseret")) { PurchaseResult ret = new
	 * PurchaseResult();
	 * 
	 * String bRet = parser.getAttributeValue(null, "result");
	 * 
	 * if( ret != null ) ret.m_bOK = bRet.equalsIgnoreCase("OK");
	 * 
	 * return ret; } } else if (parser.getEventType() == XmlPullParser.END_TAG)
	 * { } else if (parser.getEventType() == XmlPullParser.TEXT) { }
	 * parser.next(); } } catch (IOException e) { throw AppException.xml(e); }
	 * catch (XmlPullParserException e) { throw AppException.xml(e); }
	 * 
	 * return null; }
	 */

}
