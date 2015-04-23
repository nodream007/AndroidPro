package com.ceres.jailmon.data;

import java.util.ArrayList;
import java.util.List;

public class Warning {
	private String type;
	
	private List<?> warningList;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<?> getWarningList() {
		return warningList;
	}

	public void setWarningList(List<?> warningList) {
		this.warningList = warningList;
	}
}
