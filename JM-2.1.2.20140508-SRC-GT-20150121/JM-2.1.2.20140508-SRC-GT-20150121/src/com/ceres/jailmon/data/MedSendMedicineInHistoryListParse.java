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

public class MedSendMedicineInHistoryListParse {

	public static MedicineHistory parse(InputStream in) throws AppException {
		MedicinePatientInfo patientInfo = null;
		MedicinePeopleMedicineInfo medicineInfo = null;
		List<MedicinePatientMedicineInfo> medicinePatientMedicineInfos = new ArrayList<MedicinePatientMedicineInfo>();
		MedicinePatientMedicineInfo medicinePatientMedicineInfo = null;
		MedicinePeopleMedicineType medicineType = null;
		List<MedicinePeopleMedicineInfo> medicineInfoList = null;
		List<MedicinePatientInfo> medicinePatientInfoList = new ArrayList<MedicinePatientInfo>();;
		List<MedicinePeopleMedicineType> medicineTypeList = null;
		MedicineHistory medicineHistory = new MedicineHistory();
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					medicineInfo = new MedicinePeopleMedicineInfo();
					medicineTypeList = new ArrayList<MedicinePeopleMedicineType>();
					String name = parser.getName();
					medicinePatientMedicineInfo = new MedicinePatientMedicineInfo();
					if (name.equals("patientInfo")) {
						medicinePatientMedicineInfos = new ArrayList<MedicinePatientMedicineInfo>();
						patientInfo = new MedicinePatientInfo();
						patientInfo.setType(parser.getAttributeValue(null,
								"type"));
						patientInfo.setDoctor(parser.getAttributeValue(null,
								"doctor"));
						patientInfo.setTime(parser.getAttributeValue(null,
								"time"));
						patientInfo.setMedicineNotice(parser.getAttributeValue(
								null, "medichineNotice"));
						patientInfo.setRemark(parser.getAttributeValue(null,
								"remark"));
					} else if (name.equals("medicine")) {
						medicinePatientMedicineInfo.setName(parser
								.getAttributeValue(null, "name"));
						medicinePatientMedicineInfo.setCount(parser
								.getAttributeValue(null, "count"));
					} else if (name.equals("medichineType")) {
						medicineType = new MedicinePeopleMedicineType();
						medicineInfoList = new ArrayList<MedicinePeopleMedicineInfo>();
						medicineType.setType(parser.getAttributeValue(null,
								"type"));
					} else if (name.equals("medichineInfo")) {
						medicineInfo = new MedicinePeopleMedicineInfo();
						medicineInfo.setUnit(parser.getAttributeValue(null,
								"unit"));
						medicineInfo.setName(parser.getAttributeValue(null,
								"name"));
					}
				} else if (parser.getEventType() == XmlPullParser.END_TAG) {
					String name = parser.getName();
					if (name.equals("medicine")) {
						medicinePatientMedicineInfos
								.add(medicinePatientMedicineInfo);
					} else if (name.equals("medichineType")) {
						medicineType.setMedicineInfo(medicineInfoList);
						medicineTypeList.add(medicineType);
					} else if (name.equals("medichineInfo")) {
						medicineInfoList.add(medicineInfo);
					} else if (name.equals("patientInfo")) {
						patientInfo
								.setMedicineInfoList(medicinePatientMedicineInfos);
						medicinePatientInfoList.add(patientInfo);
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
		medicineHistory.setMedicinePatientInfoList(medicinePatientInfoList);
		return medicineHistory;
	}

}
