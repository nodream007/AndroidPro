package com.ceres.jailmon.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Log;
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
			List<IndexMajorSecurity> outPrisonerList = new ArrayList<IndexMajorSecurity>();
			List<DisciplineWarning> disciplineWarningList = new ArrayList<DisciplineWarning>();
			List<DoctorWarning> doctorWarningList = new ArrayList<DoctorWarning>();
			List<CustodyWarning> custodyWarningList = new ArrayList<CustodyWarning>();
			List<SpecialWarning> specialWarningList = new ArrayList<SpecialWarning>();
			List<Warning> warningList = new ArrayList<Warning>();
			RoomIndex roomIndex = new RoomIndex();
			Warning warning = new Warning();
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();
					warning = new Warning();
					if (name.equals("RoomIndex")) {
						disciplineWarningList = new ArrayList<DisciplineWarning>();
						doctorWarningList = new ArrayList<DoctorWarning>();
						custodyWarningList =  new ArrayList<CustodyWarning>();
						specialWarningList = new ArrayList<SpecialWarning>();
						dutyList = new ArrayList<IndexDuty>();
						roomIndex = new RoomIndex();
						warningList = new ArrayList<Warning>();
						outPrisonerList = new ArrayList<IndexMajorSecurity>();
						majorSecurity = new MajorSecurity();
						String id = parser.getAttributeValue(null, "roomId");
						roomIndex.setId(id);
						String roomName = parser
								.getAttributeValue(null, "name");
						roomIndex.setName(roomName);
						String riskDegree = parser.getAttributeValue(null,
								"riskDegree");
						roomIndex.setRiskDegree(riskDegree);
						String num =  parser.getAttributeValue(null,
								"num");
						roomIndex.setNum(num);
						String roomType =  parser.getAttributeValue(null,
								"roomType");
						roomIndex.setRoomType(roomType);
						String outRoomNum = parser.getAttributeValue(null,
								"outRoomNum");
						roomIndex.setOutRoomNum(outRoomNum);
						String forArraNum = parser.getAttributeValue(null,
								"forArraNum");
						roomIndex.setForArraNum(forArraNum);
						String securityNum = parser.getAttributeValue(null,
								"securityNum");
						roomIndex.setSecurityNum(securityNum);
						// roomIndexList.add(roomIndex);
					} else if (name.equals("Police")) {
						Police police = new Police();
						String id = parser.getAttributeValue(null, "policeId");
						police.setmID(id);
						String policeName = parser.getAttributeValue(null,
								"name");
						police.setmName(policeName);
						String type = parser.getAttributeValue(null, "type");
						police.setmType(type);
						policeList.add(police);
						roomIndex.setPoliceList(policeList);
					} else if (name.equals("MajorSecurity")) {
						String num = parser.getAttributeValue(null, "num");
						majorSecurity.setNum(num);
					} else if (name.equals("OurPrisoner")) {
						IndexMajorSecurity outPrisoner = new IndexMajorSecurity();
						String id = parser.getAttributeValue(null, "id");
						outPrisoner.setId(id);
						String outPrisonerName = parser.getAttributeValue(null,
								"name");
						outPrisoner.setName(outPrisonerName);
						outPrisonerList.add(outPrisoner);
					} else if (name.equals("Duty")) {
						IndexDuty duty = new IndexDuty();
						String id = parser.getAttributeValue(null, "id");
						duty.setId(id);
						String time = parser.getAttributeValue(null, "time");
						duty.setTime(time);
						String dutyName = parser
								.getAttributeValue(null, "name");
						duty.setName(dutyName);
						dutyList.add(duty);
						roomIndex.setDutyList(dutyList);
					} else if (name.equals("DisciplineWarning")) {
						DisciplineWarning disciplineWarning = new DisciplineWarning();
						String disciplineWarningName = parser
								.getAttributeValue(null, "name");
						disciplineWarning.setName(disciplineWarningName);
						String disciplineWarningBaseInfo = parser
								.getAttributeValue(null, "baseinfo");
						disciplineWarning
								.setBaseInfo(disciplineWarningBaseInfo);
						String time = parser.getAttributeValue(null, "time");
						disciplineWarning.setTime(time);
						Log.d("jiayy",
								"disciplineWarningList.add(disciplineWarning)");
						disciplineWarningList.add(disciplineWarning);
						// warning.setType("disciplineWarning");
						warning.setWarningList(disciplineWarningList);
						// warningList.add(warning);
					} else if (name.equals("DoctorWarning")) {
						DoctorWarning doctorWarning = new DoctorWarning();
						String doctorWarningName = parser.getAttributeValue(
								null, "name");
						doctorWarning.setName(doctorWarningName);
						String doctorWarningBaseInfo = parser
								.getAttributeValue(null, "baseinfo");
						doctorWarning.setBaseInfo(doctorWarningBaseInfo);
						String time = parser.getAttributeValue(null, "time");
						doctorWarning.setTime(time);
						doctorWarningList.add(doctorWarning);
						// warning.setType("DoctorWarning");
						warning.setWarningList(doctorWarningList);
						// warningList.add(warning);
					} else if (name.equals("CustodyWarning")) {
						CustodyWarning custodyWarning = new CustodyWarning();
						String custodyWarningName = parser.getAttributeValue(
								null, "name");
						custodyWarning.setName(custodyWarningName);
						String custodyWarningBaseInfo = parser
								.getAttributeValue(null, "baseinfo");
						custodyWarning.setBaseInfo(custodyWarningBaseInfo);
						String time = parser.getAttributeValue(null, "time");
						custodyWarning.setTime(time);
						custodyWarningList.add(custodyWarning);
						// warning.setType("CustodyWarning");
						warning.setWarningList(custodyWarningList);
						// warningList.add(warning);
					} else if (name.equals("SpecialWarning")) {
						SpecialWarning specialWarning = new SpecialWarning();
						String specialWarningName = parser.getAttributeValue(
								null, "name");
						specialWarning.setName(specialWarningName);
						String specialWarningBaseInfo = parser
								.getAttributeValue(null, "baseinfo");
						specialWarning.setBaseInfo(specialWarningBaseInfo);
						String time = parser.getAttributeValue(null, "time");
						specialWarning.setTime(time);
						specialWarningList.add(specialWarning);
						warning.setWarningList(specialWarningList);
						// warningList.add(warning);
					}

				} else if (parser.getEventType() == XmlPullParser.END_TAG) {
					String name = parser.getName();
					if (name.equals("MajorSecurity")) {
						majorSecurity.setOurPrisoner(outPrisonerList);
						roomIndex.setMajorSecurity(majorSecurity);
					} else if (name.equals("DisciplineType")) {
						warning.setType("管教预警");
						warningList.add(warning);
					} else if (name.equals("DoctorType")) {
						warning.setType("医生预警");
						warningList.add(warning);
					} else if (name.equals("CustodyType")) {
						warning.setType("收押预警");
						warningList.add(warning);
					} else if (name.equals("SpecialType")) {
						warning.setType("特别嘱咐");
						warningList.add(warning);
					} else if (name.equals("RoomIndex")) {
						/*
						 * roomIndex.setDisciplineWarningList(disciplineWarningList
						 * ); roomIndex.setDoctorWarningList(doctorWarningList);
						 * roomIndex.setCustodyWarningList(custodyWarningList);
						 * roomIndex.setSpecialWarningList(specialWarningList);
						 */
						roomIndex.setWarningList(warningList);
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
