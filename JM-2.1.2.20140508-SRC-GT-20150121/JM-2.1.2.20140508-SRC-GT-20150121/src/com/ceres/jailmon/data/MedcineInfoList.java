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

public class MedcineInfoList {

	public List<MedicineInfo> m_list = new ArrayList<MedicineInfo>();
	public List<Police> m_listDoctor = new ArrayList<Police>();

	public static MedcineInfoList parse(InputStream in) throws AppException {

		MedcineInfoList infolist = new MedcineInfoList();
		MedicineInfo info;

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equals("medicine")) {
						info = new MedicineInfo();

						if (info != null) {
							info.id = parser.getAttributeValue(null, "id");
							info.prisoner = parser.getAttributeValue(null,
									"useperson");
							info.deliver = parser.getAttributeValue(null,
									"sendman");
							info.time = parser.getAttributeValue(null,
									"sendtime");
							info.med_name = parser.getAttributeValue(null,
									"medicname");
							info.med_num = parser.getAttributeValue(null,
									"medicnum");
							info.med_content = parser.getAttributeValue(null,
									"mediccontent");
							info.comment = parser.getAttributeValue(null, "bz");
							infolist.m_list.add(info);
						}
					} else if (name.equals("doctor")) {
						String id = parser.getAttributeValue(null, "id");

						String pname = parser.getAttributeValue(null, "name");

						infolist.m_listDoctor.add(new Police(id, pname,""));
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
