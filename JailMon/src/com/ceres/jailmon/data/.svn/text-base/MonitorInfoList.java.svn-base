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

public class MonitorInfoList {

		public List< MonitorInfo> m_list = new ArrayList< MonitorInfo>();

		public static MonitorInfoList parse(InputStream in) throws AppException {

			MonitorInfoList infolist = new MonitorInfoList();
			MonitorInfo info;

			try {
				XmlPullParser parser = Xml.newPullParser();
				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
				parser.setInput(in, null);
				parser.nextTag();

				while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
					if (parser.getEventType() == XmlResourceParser.START_TAG) {
						String name = parser.getName();

						if (name.equals("mtinfo")) {
							info = new MonitorInfo();

							if (info != null) {
								info.id = parser.getAttributeValue(null,
										"id");
								info.ip = parser.getAttributeValue(null,
										"ip");
								
								String strPort = parser.getAttributeValue(null,
										"port");
								info.port = Integer.parseInt(strPort);
								info.user = parser.getAttributeValue(null,
										"user");
								info.passwd = parser.getAttributeValue(null,
										"passwd");
								info.channel = parser.getAttributeValue(null,
										"channel");
								info.logined = false;
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
