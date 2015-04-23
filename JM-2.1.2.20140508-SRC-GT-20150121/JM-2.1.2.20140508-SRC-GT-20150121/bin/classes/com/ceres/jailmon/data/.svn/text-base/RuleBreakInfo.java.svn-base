package com.ceres.jailmon.data;

public class RuleBreakInfo extends BaseData{
	String police;
	String time;
	String content;
	String solution;
	
	@Override
	public String getHeadline() {
		if (m_strHeadline == null) {
			m_strHeadline = String.format("时间：%s", time);
		}

		return m_strHeadline;
	}

	@Override
	public String getContent() {
		if (m_strContent == null) {
			m_strContent = String.format(
					"处置民警：%s\n违规内容： %s\n处理办法：%s",
					police, content, solution);
		}

		return m_strContent;
	}
}
