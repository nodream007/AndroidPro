package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.ceres.jailmon.AppException;

import android.content.res.XmlResourceParser;
import android.util.Xml;

public class PowerInfoList {

	public List<PowerInfo> m_list = new ArrayList<PowerInfo>();

	public static PowerInfoList parse(InputStream in) throws AppException {

		PowerInfoList infolist = new PowerInfoList();
		PowerInfo info;
		String strStatus;

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equals("powerinfo")) {
						info = new PowerInfo();

						if (info != null) {
							info.type = parser.getAttributeValue(null,
									"ctrltype");
							info.room = parser
									.getAttributeValue(null, "roomid");
							info.pointid = parser
									.getAttributeValue(null, "pointid");
							strStatus = parser.getAttributeValue(null,
									"curstatus");
							if (strStatus != null & strStatus.equals("on"))
								info.status = true;
							else
								info.status = false;
							infolist.m_list.add(info);
						}
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
		return infolist;
	}
}
