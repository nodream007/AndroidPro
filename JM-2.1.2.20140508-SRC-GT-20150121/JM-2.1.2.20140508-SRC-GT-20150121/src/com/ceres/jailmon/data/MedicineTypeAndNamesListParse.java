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

public class MedicineTypeAndNamesListParse {
	public  Map<String,List<Medicine>> mMedicineTypeIncludeMedicineMap = new HashMap<String, List<Medicine>>();

	public static MedicineTypeAndNamesListParse parse(InputStream in)
			throws AppException {
		MedicineTypeAndNamesListParse infolist = new MedicineTypeAndNamesListParse();
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			List<Medicine> medicineList = new ArrayList<Medicine>();
			String medicineType = "";
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					Medicine medicine = new Medicine();
					String name = parser.getName();
					if (name.equals("medicinetype")) {
						medicineType = parser.getAttributeValue(null,
								"name");
					} else if (name.equals("medicine")) {
						String medicineName = parser.getAttributeValue(null,
								"name");
						String medicineId = parser.getAttributeValue(null,
								"id");
						medicine.setName(medicineName);
						medicine.setId(medicineId);
						medicineList.add(medicine);
					}
				} else if (parser.getEventType() == XmlPullParser.END_TAG) {
					String name = parser.getName();
					if (name.equals("medicinetype")) {
						infolist.mMedicineTypeIncludeMedicineMap.put(medicineType, medicineList);
						medicineList = new ArrayList<Medicine>();
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
