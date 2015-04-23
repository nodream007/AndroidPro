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


public class GoodsList {
	
	public List<Goods> m_list = new ArrayList<Goods>();


	public static GoodsList parse(InputStream in) throws AppException {
		
		
		GoodsList infolist = new GoodsList();
		
		String strPrice;
		
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
                    
                    if(name.equals("goods"))
                    {   
                    	Goods info = new Goods();
                    		
                    	if( info != null )
                    	{
                    		info.id = parser.getAttributeValue(null, "id");
                    		info.name = parser.getAttributeValue(null, "name");
                   			info.type = parser.getAttributeValue(null, "type");
                   			strPrice = parser.getAttributeValue(null, "price");
                   			if( strPrice != null && strPrice.matches("^[0-9]+\\.{0,1}[0-9]{0,2}$") )
                   			info.price = Float.parseFloat(strPrice);
                   			
                   			infolist.m_list.add( info );
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
  

        return infolist;
	}
}
