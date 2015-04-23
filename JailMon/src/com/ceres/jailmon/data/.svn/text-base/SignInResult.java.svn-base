package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.ceres.jailmon.AppException;

public class SignInResult extends CommonResult{
	public int mMonitorType;
	
	public static SignInResult parse(InputStream in) throws AppException {    	
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
                    
                    if(name.equals("ret"))
                    {   
                    	SignInResult ret = new SignInResult();
                    	
                    	String bRet = parser.getAttributeValue(null, "result");
                    	
                    	String type = parser.getAttributeValue(null, "type");
                    	
                    	if( ret != null )
                    	{
                    		ret.m_bOK = bRet.equalsIgnoreCase("OK");
                    		ret.mMonitorType = type.equals("1")? 1:0;
                    	}
                    	
                    	return ret;
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

        return null;
	}
}
