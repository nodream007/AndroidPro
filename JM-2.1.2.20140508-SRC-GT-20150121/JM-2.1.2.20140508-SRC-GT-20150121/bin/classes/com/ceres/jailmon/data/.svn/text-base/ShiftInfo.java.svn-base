package com.ceres.jailmon.data;

public class ShiftInfo extends BaseData {

	private String m_strPolice;
	private String m_strtime;
	private String m_strDetail;

	public void setTime(String strTime) {
		m_strtime = strTime;
	}

	public void setPolice(String strPolice) {
		m_strPolice = strPolice;
	}

	public void setDetail(String strDetail) {
		m_strDetail = strDetail;
	}

	@Override
	public String getHeadline() {
		if (m_strHeadline == null) {
			m_strHeadline = String.format("时间：%s", m_strtime);
		}

		return m_strHeadline;
	}

	@Override
	public String getContent() {
		if (m_strContent == null) {
			m_strContent = String
					.format("交班人：%s\n交班事项：%s", m_strPolice, m_strDetail);
		}

		return m_strContent;
	}
}
