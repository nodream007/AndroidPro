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

public class TrainingList {
	
	public String balance;
	
	public List<Training> m_listTV = new ArrayList<Training>(); 
	public List<Training> m_listMovie = new ArrayList<Training>(); 
	public List<Training> m_listMusic = new ArrayList<Training>(); 
	
	static public TrainingList parse(InputStream in)
			throws AppException {

		TrainingList infolist = new TrainingList();
		
		Training info;
		
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();
					
					if (name.equals("item")) {
						info = new Training();

						if (info != null) {
							info.id = parser.getAttributeValue(null, "id");
							info.name = parser.getAttributeValue(null, "name");
							info.type = parser.getAttributeValue(null, "type");
					
							if( info.type.equals("tv") )
								infolist.m_listTV.add(info);
							else if( info.type.equals("movie") )
								infolist.m_listMovie.add(info);
							else if( info.type.equals("music") )
								infolist.m_listMusic.add(info);
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
