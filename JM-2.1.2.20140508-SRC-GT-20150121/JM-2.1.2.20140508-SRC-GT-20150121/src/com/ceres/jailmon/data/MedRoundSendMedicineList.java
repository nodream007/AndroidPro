package com.ceres.jailmon.data;

import java.util.ArrayList;
import java.util.List;

public class MedRoundSendMedicineList {
	private String MedRoundSendMedTotalPatientNum;
	private List<MedRoundSendImportPatient> medImportPatientList = new ArrayList<MedRoundSendImportPatient>();
	private List<MedRoundSendMedTotalPatient> medTotalPatietList = new ArrayList<MedRoundSendMedTotalPatient>();
	public String getMedRoundSendMedTotalPatientNum() {
		return MedRoundSendMedTotalPatientNum;
	}

	public void setMedRoundSendMedTotalPatientNum(
			String medRoundSendMedTotalPatientNum) {
		MedRoundSendMedTotalPatientNum = medRoundSendMedTotalPatientNum;
	}


	public List<MedRoundSendImportPatient> getMedImportPatientList() {
		return medImportPatientList;
	}

	public void setMedImportPatientList(
			List<MedRoundSendImportPatient> medImportPatientList) {
		this.medImportPatientList = medImportPatientList;
	}

	public List<MedRoundSendMedTotalPatient> getMedTotalPatietList() {
		return medTotalPatietList;
	}

	public void setMedTotalPatietList(List<MedRoundSendMedTotalPatient> medTotalPatietList) {
		this.medTotalPatietList = medTotalPatietList;
	}
}
