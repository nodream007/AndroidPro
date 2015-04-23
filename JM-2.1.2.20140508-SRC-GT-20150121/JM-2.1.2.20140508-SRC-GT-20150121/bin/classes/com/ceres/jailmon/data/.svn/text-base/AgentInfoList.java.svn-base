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

public class AgentInfoList {

	public List<AgentInfo> m_list = new ArrayList<AgentInfo>();
	public String m_comment;

	public static AgentInfoList parse(InputStream in) throws AppException {
		
		AgentInfoList infolist = new AgentInfoList();

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {

				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equals("agent")) {

						String timeStart = parser.getAttributeValue(null, "start_time");
						String timeEnd = parser.getAttributeValue(null, "end_time");
						String pid = parser.getAttributeValue(null, "pid");
						String pname = parser.getAttributeValue(null, "name");						

						infolist.m_list.add(new AgentInfo(timeStart, timeEnd, pid, pname));
					}
					else if (name.equals("comment"))
					{
						infolist.m_comment = parser.getAttributeValue(null, "content");	
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
