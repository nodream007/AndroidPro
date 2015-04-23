package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.ceres.jailmon.AppException;

import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.util.Xml;


public class MapInfo {
	public String url;
	public int  ver;
	public Bitmap m_bmp;
	
	public static MapInfo parse(InputStream in) throws AppException {
		
		MapInfo info = null;
		
		try
        {   
        	XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            
            while (parser.getEventType() != XmlResourceParser.END_DOCUMENT)
            {    
                if (parser.getEventType() == XmlResourceParser.START_TAG)
            	{   
            		String name = parser.getName();
                    
                    if(name.equals("mapinfo"))
                    {   
                    	info = new MapInfo();
                    		
                    	if( info != null )
                    	{
                    		info.url = parser.getAttributeValue(null, "jpegurl");
                    		String ver = parser.getAttributeValue(null, "ver");
                    		info.ver = Integer.parseInt(ver);
                    		return info;
                    	}
                    }
            	}            	
                else if (parser.getEventType() == XmlPullParser.END_TAG)
                {    
                }
                else if (parser.getEventType() == XmlPullParser.TEXT)
                {    
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
    

        return info;
    }  
}
