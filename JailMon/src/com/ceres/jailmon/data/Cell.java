package com.ceres.jailmon.data;

public class Cell {

	private String m_strID;
	private String m_strType;
	private String m_strManager;
	private String m_strPoliceName;

	Cell(String id, String type, String manager, String jname) {
		m_strID = id;
		m_strType = type;
		m_strManager = manager;
		m_strPoliceName = jname;
	}
	public String getM_strType() {
		return m_strType;
	}

	public void setM_strType(String m_strType) {
		this.m_strType = m_strType;
	}
	public String getID()
	{
		return m_strID;
	}
	
	public void setID(String strID)
	{
		m_strID = strID;
	}

	@Override
	public String toString() {
		return m_strID;
	}

}
