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

public class MedicineUnitsParse {

	public static MedicineUnit parse(InputStream in)
			throws AppException {
		MedicineUnit info = new MedicineUnit();
		List<String> medicineNameList = new ArrayList<String>();
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			String medicineUnit = "";
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();
					if (name.equals("DrugDoseUnits")) {
						medicineUnit = parser.getAttributeValue(null,
								"DrugDoseUnitName");
						medicineNameList.add(medicineUnit);
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
		info.setMedicineUnitList(medicineNameList);
		return info;
	}
}
