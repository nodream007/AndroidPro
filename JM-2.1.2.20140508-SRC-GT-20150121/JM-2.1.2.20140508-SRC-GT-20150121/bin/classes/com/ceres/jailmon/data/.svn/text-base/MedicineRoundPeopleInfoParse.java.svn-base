package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.Xml;

import com.ceres.jailmon.AppException;

public class MedicineRoundPeopleInfoParse {
	public static MedicinePeopleInfo parse(InputStream in)
			throws AppException {
		MedicinePeopleInfo peopleInfo = null;
		try{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
		   			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();
					Log.d("jiayy","name = "+name);
					if (name.equals("peopleInfo")) {
						peopleInfo = new MedicinePeopleInfo();
						peopleInfo.setpId(parser.getAttributeValue(null, "id"));
						peopleInfo.setName(parser.getAttributeValue(null, "name"));
						peopleInfo.setAge(parser.getAttributeValue(null, "age"));
						peopleInfo.setRoom(parser.getAttributeValue(null, "room"));
						peopleInfo.setCreateTime(parser.getAttributeValue(null, "createTime"));
						peopleInfo.setReason(parser.getAttributeValue(null, "reason"));
						peopleInfo.setPicUrl(parser.getAttributeValue(null, "picUrl"));
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
		return peopleInfo;
	}
}
