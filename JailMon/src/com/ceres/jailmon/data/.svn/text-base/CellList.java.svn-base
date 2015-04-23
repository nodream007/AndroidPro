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

public class CellList {

	private final static String XML_TAG_NAME = "Room";
	private final static String XML_TAG_ATTR_ID = "jsh";
	private final static String XML_TAG_ATTR_TYPE = "jstype";
	private final static String XML_TAG_ATTR_MGR = "jsmanager";
	private final static String XML_TAG_ATTR_PO = "jsmjname";

	private List<Cell> m_list = new ArrayList<Cell>();

	public Cell getCell(int index) {
		if (m_list != null)
			return m_list.get(index);
		else
			return null;
	}

	public int getNum() {
		if (m_list != null)
			return m_list.size();
		return 0;
	}
	
	public List<Cell> getList()
	{
		return m_list;
	}

	public static CellList parse(InputStream in) throws AppException {

		CellList cellist = new CellList();

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {

				if (parser.getEventType() == XmlResourceParser.START_TAG) {

					String name = parser.getName();

					if (name.equals(XML_TAG_NAME)) {

						parser.require(XmlPullParser.START_TAG, null,
								XML_TAG_NAME);

						String id = parser.getAttributeValue(null,
								XML_TAG_ATTR_ID);

						String type = parser.getAttributeValue(null,
								XML_TAG_ATTR_TYPE);

						String manager = parser.getAttributeValue(null,
								XML_TAG_ATTR_MGR);

						String jname = parser.getAttributeValue(null,
								XML_TAG_ATTR_PO);

						cellist.m_list.add(new Cell(id, type, manager, jname));
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

		return cellist;
	}
}
