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


public class PoliceInfo {
	
	public static List<Police> parse(InputStream in) throws AppException {
		
		List<Police> policeList = new ArrayList<Police>();
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
                    
                    if(name.equals("policeinfo"))
                    {   
                    	Police police = new Police(null,null,null);
                    	if( police != null )
                    	{
                    		String id = parser.getAttributeValue(null, "id");
                    		String policeName = parser.getAttributeValue(null, "name");
                    		police.setmID(id);
                    		police.setmName(policeName);
                    		policeList.add(police);
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
    

        return policeList;
    }  
}
