package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.ceres.jailmon.AppException;

public class PrisonerList {

	public List<Prisoner> m_list = new ArrayList<Prisoner>();
	public List<Police> m_listPolice = new ArrayList<Police>();

	public Prisoner getPrisoner(int index) {
		if (m_list != null)
			return m_list.get(index);
		else
			return null;
	}

	public int getNum() {
		if (m_list != null)
			return m_list.size();
		else
			return 0;
	}

	public Police getPolice(int index) {
		if (m_listPolice != null)
			return m_listPolice.get(index);
		else
			return null;
	}

	public int getPolicetNum() {
		if (m_listPolice != null)
			return m_listPolice.size();
		else
			return 0;
	}

	public void recycle() {
		Prisoner prisoner;

		for (int i = 0; i < getNum(); i++) {
			prisoner = m_list.get(i);

			if (prisoner != null)
				prisoner.recycle();
		}

		Police police;

		for (int i = 0; i < getPolicetNum(); i++) {
			police = m_listPolice.get(i);

			if (police != null)
				police.recycle();
		}
	}

	public static PrisonerList parse(InputStream in) throws AppException {

		PrisonerList infolist = new PrisonerList();

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equals("Personbaseinfo")) {
						parser.require(XmlPullParser.START_TAG, null,
								"Personbaseinfo");

						String id = parser.getAttributeValue(null, "PersonID");

						String pname = parser.getAttributeValue(null,
								"PersonName");

						String vip = parser.getAttributeValue(null, "VIP");

						boolean bVip = (vip != null && vip
								.equalsIgnoreCase("Y"));

						infolist.m_list.add(new Prisoner(id, pname, bVip));
					} else if (name.equals("Police")) {
						parser.require(XmlPullParser.START_TAG, null, "Police");

						String id = parser.getAttributeValue(null, "ID");

						String pname = parser.getAttributeValue(null, "Name");
						String pType = "("
								+ parser.getAttributeValue(null, "type") + ")";

						infolist.m_listPolice.add(new Police(id, pname, pType));
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

		return infolist;
	}

}
