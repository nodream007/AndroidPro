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

public class MedSendMedicineOutHistoryListParse {

	public static MedicineHistory parse(InputStream in) throws AppException {
		MedicinePatientOutInfo patientInfo = null;
		List<MedicinePatientOutInfo> medicinePatientOutfoList = new ArrayList<MedicinePatientOutInfo>();
		;
		MedicineHistory medicineHistory = new MedicineHistory();
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();
					if (name.equals("patientInfo")) {
						patientInfo = new MedicinePatientOutInfo();
						patientInfo.setApplyTime(parser.getAttributeValue(null,
								"ApplyTime"));
						patientInfo.setDoctorName(parser.getAttributeValue(null,
								"DoctorName"));
						patientInfo.setIllNessExp(parser.getAttributeValue(null,
								"IllNessExp"));
						patientInfo.setIllNessType(parser.getAttributeValue(null,
								"IllNessType"));
						patientInfo.setMedicalTime(parser.getAttributeValue(null,
								"MedicalTime"));
						patientInfo.setName(parser.getAttributeValue(null,
								"xm"));
						patientInfo.setOutExplain(parser.getAttributeValue(null,
								"OutExplain"));
						patientInfo.setRemark(parser.getAttributeValue(null,
								"Remark"));
					}
				} else if (parser.getEventType() == XmlPullParser.END_TAG) {
					String name = parser.getName();
					if (name.equals("patientInfo")) {
						medicinePatientOutfoList.add(patientInfo);
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
		medicineHistory.setMedicinePatientOutInfo(medicinePatientOutfoList);
		return medicineHistory;
	}

}
