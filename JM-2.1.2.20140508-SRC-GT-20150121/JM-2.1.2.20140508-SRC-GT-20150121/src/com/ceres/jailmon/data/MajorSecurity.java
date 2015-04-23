package com.ceres.jailmon.data;

import java.util.List;

public class MajorSecurity {
	private String num;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public List<IndexOutPrisoner> getOurPrisoner() {
		return ourPrisoner;
	}

	public void setOurPrisoner(List<IndexOutPrisoner> ourPrisoner) {
		this.ourPrisoner = ourPrisoner;
	}

	private List<IndexOutPrisoner> ourPrisoner;

}
