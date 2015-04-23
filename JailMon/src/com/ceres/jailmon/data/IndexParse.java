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

public class IndexParse {

	public static List<RoomIndex> parse(InputStream in) throws AppException {
		List<RoomIndex> roomIndexList = new ArrayList<RoomIndex>();
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			MajorSecurity majorSecurity = new MajorSecurity();
			List<Police> policeList = new ArrayList<Police>();
			List<IndexDuty> dutyList = new ArrayList<IndexDuty>();
			List<IndexOutPrisoner> outPrisonerList = new ArrayList<IndexOutPrisoner>();
			List<DisciplineWarning> disciplineWarningList = new ArrayList<DisciplineWarning>();
			List<DoctorWarning> doctorWarningList = new ArrayList<DoctorWarning>();
			List<CustodyWarning> custodyWarningList = new ArrayList<CustodyWarning>();
			List<SpecialWarning> specialWarningList = new ArrayList<SpecialWarning>();
			Warning warning = new Warning();
			List<Warning> warningList = new ArrayList<Warning>();
			RoomIndex roomIndex = new RoomIndex();
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();
					if (name.equals("RoomIndex")) {
						roomIndex = new RoomIndex();
						majorSecurity = new MajorSecurity();
						String id = parser.getAttributeValue(null, "roomId");
						roomIndex.setId(id);
						String roomName = parser.getAttributeValue(null, "name");
						roomIndex.setName(roomName);
						String riskDegree = parser.getAttributeValue(null, "riskDegree");
						roomIndex.setRiskDegree(riskDegree);
						String outRoomNum = parser.getAttributeValue(null, "outRoomNum");
						roomIndex.setOutRoomNum(outRoomNum);
						String forArraNum = parser.getAttributeValue(null, "forArraNum");
						roomIndex.setForArraNum(forArraNum);
						String securityNum = parser.getAttributeValue(null, "securityNum");
						roomIndex.setSecurityNum(securityNum);
						roomIndexList.add(roomIndex);
					}
					else if (name.equals("Police")) {
						Police police = new Police();
						String id = parser.getAttributeValue(null, "policeId");
						police.setmID(id);
						String policeName = parser.getAttributeValue(null, "name");
						police.setmName(policeName);
						String type = parser.getAttributeValue(null, "type");
						police.setmType(type);
						policeList.add(police);
						roomIndex.setPoliceList(policeList);
					}
					else if (name.equals("MajorSecurity")) {
						String num = parser.getAttributeValue(null, "num");
						majorSecurity.setNum(num);
					}else if (name.equals("OurPrisoner")) {
						IndexOutPrisoner outPrisoner = new IndexOutPrisoner();
						String id = parser.getAttributeValue(null, "id");
						outPrisoner.setId(id);
						String outPrisonerName = parser.getAttributeValue(null, "name");
						outPrisoner.setName(outPrisonerName);
						outPrisonerList.add(outPrisoner);
						majorSecurity.setOurPrisoner(outPrisonerList);
					}
					else if (name.equals("Duty")) {
						IndexDuty duty = new IndexDuty();
						String id = parser.getAttributeValue(null, "id");
						duty.setId(id);
						String dutyName = parser.getAttributeValue(null, "name");
						duty.setName(dutyName);
						dutyList.add(duty);
					}
					else if (name.equals("DisciplineWarning")) {
						DisciplineWarning disciplineWarning = new DisciplineWarning();
						String disciplineWarningName = parser.getAttributeValue(null, "name");
						disciplineWarning.setName(disciplineWarningName);
						String disciplineWarningBaseInfo = parser.getAttributeValue(null, "baseinfo");
						disciplineWarning.setBaseInfo(disciplineWarningBaseInfo);
						String time = parser.getAttributeValue(null, "time");
						disciplineWarning.setTime(time);
						disciplineWarningList.add(disciplineWarning);
						warning.setType("disciplineWarning");
						warning.setWarningList(disciplineWarningList);
						warningList.add(warning);
					}
					else if (name.equals("DoctorWarning")) {
						DoctorWarning doctorWarning = new DoctorWarning();
						String doctorWarningName = parser.getAttributeValue(null, "name");
						doctorWarning.setName(doctorWarningName);
						String doctorWarningBaseInfo = parser.getAttributeValue(null, "baseinfo");
						doctorWarning.setBaseInfo(doctorWarningBaseInfo);
						String time = parser.getAttributeValue(null, "time");
						doctorWarning.setTime(time);
						doctorWarningList.add(doctorWarning);
						warning.setType("DoctorWarning");
						warning.setWarningList(doctorWarningList);
					}
					else if (name.equals("CustodyWarning")) {
						CustodyWarning custodyWarning = new CustodyWarning();
						String custodyWarningName = parser.getAttributeValue(null, "name");
						custodyWarning.setName(custodyWarningName);
						String custodyWarningBaseInfo = parser.getAttributeValue(null, "baseinfo");
						custodyWarning.setBaseInfo(custodyWarningBaseInfo);
						String time = parser.getAttributeValue(null, "time");
						custodyWarning.setTime(time);
						custodyWarningList.add(custodyWarning);
						warning.setType("CustodyWarning");
						warning.setWarningList(custodyWarningList);
					}
					else if (name.equals("SpecialWarning")) {
						SpecialWarning specialWarning = new SpecialWarning();
						String specialWarningName = parser.getAttributeValue(null, "name");
						specialWarning.setName(specialWarningName);
						String specialWarningBaseInfo = parser.getAttributeValue(null, "baseinfo");
						specialWarning.setBaseInfo(specialWarningBaseInfo);
						String time = parser.getAttributeValue(null, "time");
						specialWarning.setTime(time);
						specialWarningList.add(specialWarning);
						warning.setType("SpecialWarning");
						warning.setWarningList(specialWarningList);
					}
					
				} else if (parser.getEventType() == XmlPullParser.END_TAG) {
					String name = parser.getName();
					if (name.equals("MajorSecurity")) {
						roomIndex.setMajorSecurity(majorSecurity);	
					}else if(name.equals("RoomIndex")){
						roomIndexList.add(roomIndex);
					}

				} else if (parser.getEventType() == XmlPullParser.TEXT) {
				}
				parser.next();
			}
		} catch (IOException e) {
			throw AppException.xml(e);
		} catch (XmlPullParserException e) {
			throw AppException.xml(e);
		}
		return roomIndexList;
	}
}
