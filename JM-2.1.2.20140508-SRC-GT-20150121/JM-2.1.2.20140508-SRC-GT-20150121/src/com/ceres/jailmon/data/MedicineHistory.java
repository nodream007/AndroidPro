package com.ceres.jailmon.data;

import java.util.List;

public class MedicineHistory {
	private List<MedicinePatientInfo> medicinePatientInfoList;

public List<MedicinePatientInfo> getMedicinePatientInfoList() {
	return medicinePatientInfoList;
}

public void setMedicinePatientInfoList(
		List<MedicinePatientInfo> medicinePatientInfoList) {
	this.medicinePatientInfoList = medicinePatientInfoList;
}

}
