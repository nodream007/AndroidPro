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

public class MapLocationList {
	
	public List<MapLocation> m_list = new ArrayList<MapLocation>();

	public static MapLocationList parse(InputStream in) throws AppException {
		
		MapLocationList infolist = new MapLocationList();
		MapLocation info;
		String sx, sy;

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equals("coordinateinfo")) {
						info = new MapLocation();

						if (info != null) {
							info.id = parser.getAttributeValue(null, "mjid");
							info.name = parser
									.getAttributeValue(null, "mjname");

							sx = parser.getAttributeValue(null, "mj_x");
							if (sx != null)
								info.x = Integer.parseInt(sx);
							sy = parser.getAttributeValue(null, "mj_y");
							if (sy != null)
								info.y = Integer.parseInt(sy);
							info.status = parser.getAttributeValue(null,
									"mjstatus");
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
        }
		catch (XmlPullParserException e) {
			throw AppException.xml(e);
        }  

		return infolist;
	}
}