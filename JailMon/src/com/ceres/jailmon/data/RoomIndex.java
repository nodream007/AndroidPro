package com.ceres.jailmon.data;

import java.util.List;

public class RoomIndex {
	private String id;
	private String name;
	private String riskDegree;
	private String roomType;
	private String num;
	private String outRoomNum;
	private String forArraNum;
	private String securityNum;
	private List<Police> policeList;

	private MajorSecurity majorSecurity;
	private List<IndexDuty> dutyList;/*
	private List<DisciplineWarning> disciplineWarningList;
	private List<DoctorWarning> doctorWarningList;
	private List<CustodyWarning> custodyWarningList;
	private List<SpecialWarning> specialWarningList;*/
	private List<Warning> warningList;

	public List<Warning> getWarningList() {
		return warningList;
	}

	public void setWarningList(List<Warning> warningList) {
		this.warningList = warningList;
	}

	public MajorSecurity getMajorSecurity() {
		return majorSecurity;
	}

	public void setMajorSecurity(MajorSecurity majorSecurity) {
		this.majorSecurity = majorSecurity;
	}

	public List<Police> getPoliceList() {
		return policeList;
	}

	public void setPoliceList(List<Police> policeList) {
		this.policeList = policeList;
	}

	public List<IndexDuty> getDutyList() {
		return dutyList;
	}

	public void setDutyList(List<IndexDuty> dutyList) {
		this.dutyList = dutyList;
	}

	/*public List<DisciplineWarning> getDisciplineWarningList() {
		return disciplineWarningList;
	}

	public void setDisciplineWarningList(
			List<DisciplineWarning> disciplineWarningList) {
		this.disciplineWarningList = disciplineWarningList;
	}

	public List<DoctorWarning> getDoctorWarningList() {
		return doctorWarningList;
	}

	public void setDoctorWarningList(List<DoctorWarning> doctorWarningList) {
		this.doctorWarningList = doctorWarningList;
	}

	public List<CustodyWarning> getCustodyWarningList() {
		return custodyWarningList;
	}

	public void setCustodyWarningList(List<CustodyWarning> custodyWarningList) {
		this.custodyWarningList = custodyWarningList;
	}

	public List<SpecialWarning> getSpecialWarningList() {
		return specialWarningList;
	}

	public void setSpecialWarningList(List<SpecialWarning> specialWarningList) {
		this.specialWarningList = specialWarningList;
	}*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRiskDegree() {
		return riskDegree;
	}

	public void setRiskDegree(String riskDegree) {
		this.riskDegree = riskDegree;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getOutRoomNum() {
		return outRoomNum;
	}

	public void setOutRoomNum(String outRoomNum) {
		this.outRoomNum = outRoomNum;
	}

	public String getForArraNum() {
		return forArraNum;
	}

	public void setForArraNum(String forArraNum) {
		this.forArraNum = forArraNum;
	}

	public String getSecurityNum() {
		return securityNum;
	}

	public void setSecurityNum(String securityNum) {
		this.securityNum = securityNum;
	}
}
