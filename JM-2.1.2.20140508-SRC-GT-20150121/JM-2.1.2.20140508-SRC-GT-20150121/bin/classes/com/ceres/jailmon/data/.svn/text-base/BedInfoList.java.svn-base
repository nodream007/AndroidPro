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

public class BedInfoList {
	
	public List<BedInfo> m_list = new ArrayList<BedInfo>();

	public static BedInfoList parse(InputStream in) throws AppException {
		
		BedInfoList infolist = new BedInfoList();

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {

				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equalsIgnoreCase("Bed")) {

						String id = parser.getAttributeValue(null, "id");

						String pname = parser.getAttributeValue(null, "name");

						infolist.m_list.add(new BedInfo(id, pname));
					}
				}

				else if (parser.getEventType() == XmlPullParser.END_TAG) {
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
