package com.ceres.jailmon.data;

import java.util.List;

public class MedicineHistory {
	private List<MedicinePatientInfo> medicinePatientInfoList;
	private List<MedicinePatientOutInfo> medicinePatientOutInfo;

public List<MedicinePatientInfo> getMedicinePatientInfoList() {
	return medicinePatientInfoList;
}

public void setMedicinePatientInfoList(
		List<MedicinePatientInfo> medicinePatientInfoList) {
	this.medicinePatientInfoList = medicinePatientInfoList;
}

public List<MedicinePatientOutInfo> getMedicinePatientOutInfo() {
	return medicinePatientOutInfo;
}

public void setMedicinePatientOutInfo(
		List<MedicinePatientOutInfo> medicinePatientOutInfo) {
	this.medicinePatientOutInfo = medicinePatientOutInfo;
}

}
