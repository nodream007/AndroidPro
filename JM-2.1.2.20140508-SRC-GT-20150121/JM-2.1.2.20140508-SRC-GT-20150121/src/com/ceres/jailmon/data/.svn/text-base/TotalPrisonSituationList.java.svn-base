package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import com.ceres.jailmon.AppException;

public class TotalPrisonSituationList {
	public  String mPrisonNum;
	public  String mSeriouslyIllPrisonNum;
	public  Map<String,List<String>> mIllTypeIncludeMedicineMap = new HashMap<String, List<String>>();

	public static TotalPrisonSituationList parse(InputStream in)
			throws AppException {
		TotalPrisonSituationList infolist = new TotalPrisonSituationList();
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			List<String> personNameList = new ArrayList<String>();
			String illType = "";
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();
					if (name.equals("illtype")) {
						illType = parser.getAttributeValue(null,
								"name");
					} else if (name.equals("person")) {
						String personName = parser.getAttributeValue(null,
								"name");
						personNameList.add(personName);
					} else if (name.equals("totalPrisonerNum")) {
						infolist.mPrisonNum = parser.getAttributeValue(null,
								"num");
					} else if (name.equals("seriouslyIllPrisonNum")) {
						infolist.mSeriouslyIllPrisonNum = parser
								.getAttributeValue(null,
										"num");
					}
				} else if (parser.getEventType() == XmlPullParser.END_TAG) {
					String name = parser.getName();
					if (name.equals("illtype")) {
						infolist.mIllTypeIncludeMedicineMap.put(illType, personNameList);
						personNameList = new ArrayList<String>();
					}
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
