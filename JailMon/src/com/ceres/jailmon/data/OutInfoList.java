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

public class OutInfoList {

	public List<OutInfo> m_list = new ArrayList<OutInfo>();

	public static OutInfoList parse(InputStream in) throws AppException {

		OutInfoList info_list = new OutInfoList();
		OutInfo info;

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equals("outroom")) {
						info = new OutInfo();

						if (info != null) {
							/*
							 * info.time_begin = parser.getAttributeValue(null,
							 * "outtime"); info.time_end =
							 * parser.getAttributeValue(null, "backtime");
							 */
							info.txId = parser.getAttributeValue(null, "txID");
							info.room = parser.getAttributeValue(null,
									"outroomid");
							info.prisoner = parser.getAttributeValue(null,
									"outpersom");
							info.police = parser
									.getAttributeValue(null, "domj");
							info.jsh = parser.getAttributeValue(null, "jsh");
							/*
							 * info.type = parser.getAttributeValue(null,
							 * "outtype");
							 */
							info.txroom = parser.getAttributeValue(null,
									"txroom");
							info.hjroom = parser.getAttributeValue(null,
									"hjroom");
							info.comment = parser.getAttributeValue(null, "bz");
							info.pId = parser.getAttributeValue(null,
									"outpersonId");
							info.time_begin = parser.getAttributeValue(null,
									"DealTime");
							info.setTxFlag("0");
							info_list.m_list.add(info);
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

		return info_list;
	}
}
