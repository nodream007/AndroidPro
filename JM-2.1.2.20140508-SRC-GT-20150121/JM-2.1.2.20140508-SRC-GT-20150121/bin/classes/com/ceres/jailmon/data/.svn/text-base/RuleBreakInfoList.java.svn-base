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

public class RuleBreakInfoList {
private List<RuleBreakInfo> m_list = new ArrayList<RuleBreakInfo>(); 
	
	public List<RuleBreakInfo> getList()
	{
		return m_list;
	}
	
	public int getSize()
	{
		return m_list.size();
	}

	static public RuleBreakInfoList parse(InputStream in)
			throws AppException {

		RuleBreakInfoList infolist = new RuleBreakInfoList();
		
		RuleBreakInfo info;
		
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equals("item")) {
						info = new RuleBreakInfo();

						if (info != null) {
							info.time = parser.getAttributeValue(null, "time");
							info.police = parser.getAttributeValue(null,"police");
							info.content = parser.getAttributeValue(null, "content");
							info.solution = parser.getAttributeValue(null, "solution");
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
