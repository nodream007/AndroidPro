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

public class PatrolHistoryParse {

	public static List<PatrolHistory> parse(InputStream in)
			throws AppException {
		List<PatrolHistory> patrolHistoryList = new ArrayList<PatrolHistory>();
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					PatrolHistory info = new PatrolHistory();
					String name = parser.getName();
					if (name.equals("PatrolInfo")) {
						String policeName = parser.getAttributeValue(null,
								"PolicemanName");
						info.setName(policeName);
						String address = parser.getAttributeValue(null,
								"PatrolAddr");
						info.setAddress(address);
						String time = parser.getAttributeValue(null,
								"PatrolTime");
						info.setTime(time);
						patrolHistoryList.add(info);
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
		return patrolHistoryList;
	}
}
