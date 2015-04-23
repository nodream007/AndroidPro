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

public class RuleBreakItemsList {

	public List<RuleBreakItems> m_list_reason = new ArrayList<RuleBreakItems>();
	public List<RuleBreakItems> m_list_level = new ArrayList<RuleBreakItems>();
	public List<RuleBreakItems> m_list_police = new ArrayList<RuleBreakItems>();
	public List<RuleBreakItems> m_list_solution = new ArrayList<RuleBreakItems>();
	
	static RuleBreakItems parseItem( XmlPullParser parser )
	{
		RuleBreakItems info;
		info = new RuleBreakItems();

		if (info != null) {
			info.id = parser.getAttributeValue(null, "id");
			info.content = parser.getAttributeValue(null,
				"content");
			info.lid = parser.getAttributeValue(null,
				"levelid");
		}
		
		return info;
	}

	static public RuleBreakItemsList parse(InputStream in) throws AppException {

		RuleBreakItemsList infolist = new RuleBreakItemsList();		

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equals("reason"))
						infolist.m_list_reason.add(parseItem(parser));
					else if (name.equals("level"))
						infolist.m_list_level.add(parseItem(parser));
					else if (name.equals("police"))
						infolist.m_list_police.add(parseItem(parser));
					else if (name.equals("solution"))
						infolist.m_list_solution.add(parseItem(parser));
					
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

		return infolist;
	}
}