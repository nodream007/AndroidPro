package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.ceres.jailmon.AppException;

public class TradeInfoList {
	
	public String balance;
	
	public List<TradeInfo> m_list = new ArrayList<TradeInfo>(); 
	
	public List<TradeInfo> getList()
	{
		return m_list;
	}
	
	public int getSize()
	{
		return m_list.size();
	}

	static public TradeInfoList parse(InputStream in)
			throws AppException {

		TradeInfoList infolist = new TradeInfoList();
		
		TradeInfo info = new TradeInfo( "时间","类型", "金额", "余额" );
		infolist.m_list.add(info);
		
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();
					
					if (name.equals("account")) {
						
						infolist.balance = parser.getAttributeValue(null, "balance");
					}
					else if (name.equals("item")) {
						info = new TradeInfo();

						if (info != null) {
							info.time = parser.getAttributeValue(null, "time");
							info.type = parser.getAttributeValue(null,"type");
							info.money = parser.getAttributeValue(null, "money");
							info.balance = parser.getAttributeValue(null, "balance");
							infolist.m_list.add(info);
						}
					}
				} else if (parser.getEventType() == XmlPullParser.END_TAG) {
				} else if (parser.getEventType() == XmlPullParser.TEXT) {
				}
				parser.next();
			}
		} 
		catch (IOException e) {
			throw AppException.xml(e);
        }
		catch (XmlPullParserException e) {
			throw AppException.xml(e);
        }  

		return infolist;
	}
}
