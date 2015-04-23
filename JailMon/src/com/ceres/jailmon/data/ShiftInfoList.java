package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.ceres.jailmon.AppException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.content.res.XmlResourceParser;
import android.util.Xml;

public class ShiftInfoList {
	
	private List<ShiftInfo> m_list = new ArrayList<ShiftInfo>(); 
	
	public List<ShiftInfo> getList()
	{
		return m_list;
	}
	
	public int getNum()
	{
		if( m_list != null )
			return m_list.size();
		else
			return 0;
	}

	static public ShiftInfoList parse(InputStream in)
			throws AppException {

		ShiftInfoList infolist = new ShiftInfoList();
		
		ShiftInfo info;
		
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equals("shiftinfo")) {
						info = new ShiftInfo();

						if (info != null) {
							info.setTime( parser.getAttributeValue(null, "time") );
							info.setPolice( parser.getAttributeValue(null,"person") );
							info.setDetail( parser.getAttributeValue(null, "detail") );
							infolist.m_list.add(info);
						}
					}
				} else if (parser.getEventType() == XmlPullParser.END_TAG) {
				} else if (parser.getEventType() == XmlPullParser.TEXT) {
				}
				parser.next();
			}
		} 
		catch (IOException e) {
			throw AppException.xml(e);
        }
		catch (XmlPullParserException e) {
			throw AppException.xml(e);
        }  

		return infolist;
	}
}
