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

public class MedSendMedicineInHealthyInfoParse {

	public static MedicineHealthyInfo parse(InputStream in) throws AppException {
		MedicineHealthyInfo medicineHistory = new MedicineHealthyInfo();
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();
					if (name.equals("PersonHealthyinfo")) {
						medicineHistory.setHealthyTime(parser.getAttributeValue(null,
								"HealthyTime"));
						medicineHistory.setHealthyType(parser.getAttributeValue(null,
								"HealthyType"));
						medicineHistory.setHeight(parser.getAttributeValue(null,
								"nHeight"));
						medicineHistory.setWeight(parser.getAttributeValue(null,
								"nWeight"));
						medicineHistory.setBloodPre(parser.getAttributeValue(null,
								"BloodPre"));
						medicineHistory.setPoliceName(parser.getAttributeValue(null,
								"PoliceName"));
						medicineHistory.setMyMedHis(parser.getAttributeValue(null,
								"MyMedHis"));
						medicineHistory.setFamilyMedHis(parser.getAttributeValue(null,
								"FamilyMedHis"));
						medicineHistory.setDoctorSug(parser.getAttributeValue(null,
								"DoctorSug"));
						medicineHistory.setRemark(parser.getAttributeValue(null,
								"Remark"));
						medicineHistory.setBloodType(parser.getAttributeValue(null,
								"BloodType"));
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
		return medicineHistory;
	}

}
