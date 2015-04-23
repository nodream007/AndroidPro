package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.ceres.jailmon.AppException;

import android.content.res.XmlResourceParser;
import android.util.Xml;

public class PrisonerDetail {
	
	public String pid;
	public String name;
	public String gender;
	public String nation;
	public String birthday;
	public String idcard;
	public String address;
	public String engagedate;
	public String vip;
	public String litigation;
	public String case_name;
	public String case_unit;
	public String case_detail;
	public String comment;
	
	
	public static PrisonerDetail parse(InputStream in) throws AppException {
    	
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
                    
                    if(name.equals("Personbaseinfo"))
                    {   
                    	PrisonerDetail prisoner = new PrisonerDetail();
                    	
                    	if( prisoner != null )
                    	{
                    	
                    	prisoner.pid = parser.getAttributeValue(null, "PersonID");
                    	prisoner.name = parser.getAttributeValue(null, "PersonName");
                    	prisoner.gender = parser.getAttributeValue(null, "sex");
                    	prisoner.nation = parser.getAttributeValue(null, "Nation");
                    	prisoner.birthday = parser.getAttributeValue(null, "Birthday");
                    	prisoner.idcard = parser.getAttributeValue(null, "IDCard");
                    	prisoner.address = parser.getAttributeValue(null, "homeAddress");
                    	prisoner.engagedate = parser.getAttributeValue(null, "EncageDt");
                    	prisoner.vip = parser.getAttributeValue(null, "IfImportant");
                    	prisoner.case_name = parser.getAttributeValue(null, "CaseName");
                    	prisoner.litigation = parser.getAttributeValue(null, "Litigation");
                    	prisoner.case_unit = parser.getAttributeValue(null, "CaseUint");
                    	prisoner.case_detail = parser.getAttributeValue(null, "CaseDetail");
                    	prisoner.comment = parser.getAttributeValue(null, "comment");
                    	}
                    	
                    	return prisoner;
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
