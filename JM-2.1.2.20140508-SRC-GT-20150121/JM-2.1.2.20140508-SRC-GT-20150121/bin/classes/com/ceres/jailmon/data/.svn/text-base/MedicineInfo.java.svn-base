package com.ceres.jailmon.data;

public class MedicineInfo extends BaseData{
	public String id;
	String prisoner;
	String deliver;
	String time;
	String med_name;
	String med_num;
	String med_content;
	String comment;

	String desp;

	@Override
	public String toString() {

		if (desp == null)
			desp = String.format("%s, %s, %s, %s, %s, %s, %s", prisoner, time,
					deliver, med_name, med_num, med_content, comment);

		return desp;
	}

	@Override
	public String getHeadline() {
		if (m_strHeadline == null) {
			m_strHeadline = String.format("用药人员：%s", prisoner);
		}

		return m_strHeadline;
	}

	@Override
	public String getContent() {
		if (m_strContent == null) {
			m_strContent = String.format(
					"发药医生：%s\n发药时间：%s\n药物名称：%s\n用药剂量：%s\n用药说明：%s\n备注说明：%s",
					deliver, time, med_name, med_num, med_content, comment);
		}

		return m_strContent;
	}
}
