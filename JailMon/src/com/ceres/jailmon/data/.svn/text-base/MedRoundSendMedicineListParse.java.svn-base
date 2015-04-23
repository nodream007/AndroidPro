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

public class MedRoundSendMedicineListParse {

	public static MedRoundSendMedicineList parse(InputStream in)
			throws AppException {
		MedRoundSendMedicineList medRoundSendMedicineList = new MedRoundSendMedicineList();
		List<MedRoundSendImportPatient> medImportPatientList = new ArrayList<MedRoundSendImportPatient>();
	    List<MedRoundSendMedTotalPatient> medTotalPatietList = new ArrayList<MedRoundSendMedTotalPatient>();
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
		   			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					MedRoundSendImportPatient importPatient = new MedRoundSendImportPatient();
					MedRoundSendMedTotalPatient medRoundSendMedTotalPatient = new MedRoundSendMedTotalPatient();
					String name = parser.getName();
					if (name.equals("totalprisonerNum")) {
						String medRoundSendMedTotalPatientNum = parser.getAttributeValue(null,
								"num");
						medRoundSendMedicineList.setMedRoundSendMedTotalPatientNum(medRoundSendMedTotalPatientNum);
					} else if (name.equals("importperson")) {
						String importPersonName = parser.getAttributeValue(null,
								"name");
						String illType = parser.getAttributeValue(null,
								"illtype");
						importPatient.setPatientName(importPersonName);
						importPatient.setPationtType(illType);
						medImportPatientList.add(importPatient);
					} else if (name.equals("totalprisoner")) {
						medRoundSendMedTotalPatient.setPatientId(parser.getAttributeValue(null,
								"id"));
						medRoundSendMedTotalPatient.setPatientName(parser.getAttributeValue(null,
								"name"));
						medTotalPatietList.add(medRoundSendMedTotalPatient);
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
		medRoundSendMedicineList.setMedImportPatientList(medImportPatientList);
		medRoundSendMedicineList.setMedTotalPatietList(medTotalPatietList);
		return medRoundSendMedicineList;
	}

}
