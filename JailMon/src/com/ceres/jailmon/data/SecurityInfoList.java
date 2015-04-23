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

class SEC_Door extends BaseData {
	String time;
	String area;
	String person;
	String type;
	String comment;

	String desp;

	@Override
	public String toString() {
		if (desp == null)
			desp = String.format("%s, %s, %s, %s, %s", time, area, person,
					type, comment);

		return desp;
	}

	@Override
	public String getHeadline() {
		if (m_strHeadline == null) {
			m_strHeadline = time;
		}

		return m_strHeadline;
	}

	@Override
	public String getContent() {
		if (m_strContent == null) {
			m_strContent = String.format(
					"门禁地点： %s\n开门人员： %s\n进出类型： %s\n备注说明： %s", area, person,
					type, comment);
		}

		return m_strContent;
	}
}

class SEC_Call extends BaseData {
	String time;
	String room;
	String person;
	String reason;
	String act;
	String comment;

	String desp;

	@Override
	public String toString() {

		if (desp == null)
			desp = String.format("%s, %s, %s, %s, %s, %s", time, room, person,
					reason, act, comment);

		return desp;
	}

	@Override
	public String getHeadline() {
		if (m_strHeadline == null) {
			m_strHeadline = time;
		}

		return m_strHeadline;
	}

	@Override
	public String getContent() {
		if (m_strContent == null) {
			m_strContent = String.format(
					"对讲房间：%s\n呼叫人员：%s\n呼叫事由：%s\n处置措施：%s\n备注说明： %s", room,
					person, reason, act, comment);
		}

		return m_strContent;
	}
}

class SEC_Patrol extends BaseData {
	String area;
	String time;
	String person;
	String comment;

	String desp;

	@Override
	public String toString() {

		if (desp == null)
			desp = String.format("%s, %s, %s, %s", time, area, person, comment);

		return desp;
	}

	@Override
	public String getHeadline() {
		if (m_strHeadline == null) {
			m_strHeadline = time;
		}

		return m_strHeadline;
	}

	@Override
	public String getContent() {
		if (m_strContent == null) {
			m_strContent = String.format("巡更地点： %s\n巡更人员： %s\n备注说明： %s", area,
					person, comment);
		}

		return m_strContent;
	}
}

class SEC_Alert extends BaseData {
	String area;
	String time;
	String type;
	String act;
	String comment;

	String desp;

	@Override
	public String toString() {

		if (desp == null)
			desp = String.format("%s, %s, %s, %s, %s", time, area, type, act,
					comment);

		return desp;
	}

	@Override
	public String getHeadline() {
		if (m_strHeadline == null) {
			m_strHeadline = time;
		}

		return m_strHeadline;
	}

	@Override
	public String getContent() {
		if (m_strContent == null) {
			m_strContent = String.format("报警地点：%s\n报警类别：%s\n处置方案：%s\n备注说明：%s",
					area, type, act, comment);
		}

		return m_strContent;
	}
}

class SEC_Breakrule extends BaseData {
	String room;
	String time;
	String prisoner;
	String type;
	String content;
	String actperson;
	String act;
	String comment;

	String desp;

	@Override
	public String toString() {

		if (desp == null)
			desp = String.format("%s, %s, %s, %s, %s, %s, %s, %s", time, room,
					prisoner, type, content, actperson, act, comment);

		return desp;
	}

	@Override
	public String getHeadline() {
		if (m_strHeadline == null) {
			m_strHeadline = time;
		}

		return m_strHeadline;
	}

	@Override
	public String getContent() {
		if (m_strContent == null) {
			m_strContent = String
					.format("违规房间：%s\n违规人员：%s\n违规类型：%s\n违规内容：%s\n处置民警：%s\n处置办法：%s\n备注说明：%s",
							room, prisoner, type, content, actperson, act,
							comment);
		}

		return m_strContent;
	}

}

class SEC_Room extends BaseData {
	String room;
	String time;
	String person;
	String reason;
	String comment;

	String desp;

	@Override
	public String toString() {

		if (desp == null)
			desp = String.format("%s, %s, %s, %s, %s", time, room, person,
					reason, comment);

		return desp;
	}

	@Override
	public String getHeadline() {
		if (m_strHeadline == null) {
			m_strHeadline = time;
		}

		return m_strHeadline;
	}

	@Override
	public String getContent() {
		if (m_strContent == null) {
			m_strContent = String.format("门开房间：%s\n门开时间：%s\n开门民警：%s\n备注说明：%s",
					room, person, reason, comment);
		}

		return m_strContent;
	}
}

public class SecurityInfoList {

	private List<SEC_Door> listDoor = new ArrayList<SEC_Door>();;
	private List<SEC_Call> listCall = new ArrayList<SEC_Call>();
	private List<SEC_Patrol> listPatrol = new ArrayList<SEC_Patrol>();
	private List<SEC_Alert> listAlert = new ArrayList<SEC_Alert>();
	private List<SEC_Breakrule> listBreakrule = new ArrayList<SEC_Breakrule>();
	private List<SEC_Room> listRoom = new ArrayList<SEC_Room>();

	public List<SEC_Door> getListDoor(){
		return listDoor;
	}
	
	public List<SEC_Call> getListCall(){
		return listCall;
	}
	
	public List<SEC_Patrol> getListPatrol(){
		return listPatrol;
	}
	
	public List<SEC_Alert> getListAlert(){
		return listAlert;
	}
	
	public List<SEC_Breakrule> getListBreakrule(){
		return listBreakrule;
	}
	
	public List<SEC_Room> getListRoom(){
		return listRoom;
	}
	
	public static SecurityInfoList parse(InputStream in) throws AppException {

		SecurityInfoList infolist = new SecurityInfoList();

		SEC_Door door;
		SEC_Call call;
		SEC_Patrol patrol;
		SEC_Alert alert;
		SEC_Breakrule breakrule;
		SEC_Room room;
		String type;

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();

			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String name = parser.getName();

					if (name.equals("safetydefenseinfo")) {
						type = parser.getAttributeValue(null, "type");

						if (type.equals("mj")) {
							door = new SEC_Door();

							if (door != null) {
								door.time = parser.getAttributeValue(null,
										"opendoortime");
								door.area = parser.getAttributeValue(null,
										"doorarea");
								door.person = parser.getAttributeValue(null,
										"dopersonid");
								door.type = parser.getAttributeValue(null,
										"dotype");
								door.comment = parser.getAttributeValue(null,
										"bz");
								infolist.listDoor.add(door);
							}
						} else if (type.equals("dj")) {
							call = new SEC_Call();
							if (call != null) {
								call.time = parser.getAttributeValue(null,
										"djtime");
								call.room = parser.getAttributeValue(null,
										"djroom");
								call.person = parser.getAttributeValue(null,
										"djperson");
								call.reason = parser.getAttributeValue(null,
										"djcaseinfo");
								call.act = parser.getAttributeValue(null,
										"docase");
								call.comment = parser.getAttributeValue(null,
										"bz");
								infolist.listCall.add(call);
							}
						} else if (type.equals("xg")) {
							patrol = new SEC_Patrol();
							if (patrol != null) {
								patrol.area = parser.getAttributeValue(null,
										"xgarea");
								patrol.time = parser.getAttributeValue(null,
										"xgtime");
								patrol.person = parser.getAttributeValue(null,
										"xgperson");
								patrol.comment = parser.getAttributeValue(null,
										"bz");
								infolist.listPatrol.add(patrol);
							}
						} else if (type.equals("bj")) {
							alert = new SEC_Alert();
							if (alert != null) {
								alert.area = parser.getAttributeValue(null,
										"bjarea");
								alert.time = parser.getAttributeValue(null,
										"bjtime");
								alert.type = parser.getAttributeValue(null,
										"bjtype");
								alert.act = parser.getAttributeValue(null,
										"docase");
								alert.comment = parser.getAttributeValue(null,
										"bz");
								infolist.listAlert.add(alert);
							}
						} else if (type.equals("wg")) {
							breakrule = new SEC_Breakrule();
							if (breakrule != null) {
								breakrule.room = parser.getAttributeValue(null,
										"wgroom");
								breakrule.time = parser.getAttributeValue(null,
										"wgtime");
								breakrule.prisoner = parser.getAttributeValue(
										null, "wgperson");
								breakrule.type = parser.getAttributeValue(null,
										"wgtype");
								breakrule.content = parser.getAttributeValue(
										null, "wgcontent");
								breakrule.actperson = parser.getAttributeValue(
										null, "doperson");
								breakrule.act = parser.getAttributeValue(null,
										"docase");
								breakrule.comment = parser.getAttributeValue(
										null, "bz");
								infolist.listBreakrule.add(breakrule);
							}

						} else if (type.equals("mk")) {
							room = new SEC_Room();
							if (room != null) {
								room.room = parser.getAttributeValue(null,
										"mkroom");
								room.time = parser.getAttributeValue(null,
										"mktime");
								room.person = parser.getAttributeValue(null,
										"mkperson");
								room.reason = parser.getAttributeValue(null,
										"mkcase");
								room.comment = parser.getAttributeValue(null,
										"bz");
								infolist.listRoom.add(room);
							}
						}
					}
				} else if (parser.getEventType() == XmlPullParser.END_TAG) {
				} else if (parser.getEventType() == XmlPullParser.TEXT) {
				}
				parser.next();
			}
		} catch (IOException e) {
			throw AppException.xml(e);
		} catch (XmlPullParserException e) {
			throw AppException.xml(e);
		}
		return infolist;
	}
}
