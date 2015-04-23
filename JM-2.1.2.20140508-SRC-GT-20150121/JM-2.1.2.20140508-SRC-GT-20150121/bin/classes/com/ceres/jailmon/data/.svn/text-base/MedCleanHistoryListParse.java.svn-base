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

public class MedCleanHistoryListParse {

	public static MedicineCleanHistory parse(InputStream in)
			throws AppException {
		MedicineCleanInfo medicineCleanInfo = null;
		List<MedicineCleanCoperateInfo> medicineCleanInfos = new ArrayList<MedicineCleanCoperateInfo>();
		MedicineCleanCoperateInfo medicineCleanCoperateInfo = null;
		List<MedicineCleanCoperateInfo> medicineInfoList = null;
		List<MedicineCleanInfo> medicineCleanInfoList = new ArrayList<MedicineCleanInfo>();
		;
		MedicineCleanHistory medicineCleanHistory = new MedicineCleanHistory();
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();
					medicineCleanCoperateInfo = new MedicineCleanCoperateInfo();
					if (name.equals("cleanInfo")) {
						medicineCleanInfos = new ArrayList<MedicineCleanCoperateInfo>();
						medicineCleanInfo = new MedicineCleanInfo();
						medicineCleanInfo.setType(parser.getAttributeValue(
								null, "type"));
						medicineCleanInfo.setLeader(parser.getAttributeValue(
								null, "leader"));
						medicineCleanInfo.setTime(parser.getAttributeValue(
								null, "time"));
						medicineCleanInfo.setWay(parser.getAttributeValue(null,
								"way"));
						medicineCleanInfo.setRemark(parser.getAttributeValue(
								null, "remark"));
					} else if (name.equals("corperate")) {
						medicineCleanCoperateInfo.setName(parser
								.getAttributeValue(null, "name"));
					} 
				} else if (parser.getEventType() == XmlPullParser.END_TAG) {
					String name = parser.getName();
					if (name.equals("corperate")) {
						medicineCleanInfos.add(medicineCleanCoperateInfo);
					} else if (name.equals("cleanInfo")) {
						medicineCleanInfo.setCleanInfoList(medicineCleanInfos);
						medicineCleanInfoList.add(medicineCleanInfo);
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
		medicineCleanHistory.setCleanInfoList(medicineCleanInfoList);
		return medicineCleanHistory;
	}

}
