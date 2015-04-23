package com.ceres.jailmon.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ceres.jailmon.R;
import com.ceres.jailmon.data.MedicinePatientInfo;
import com.ceres.jailmon.data.MedicinePatientMedicineInfo;

public class CustomAdapter_MedHistory extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;

	protected List<MedicinePatientInfo> m_infolist;
	
	protected List<MedicinePatientMedicineInfo> m_medicinelist;
	
	static class ListItemView {
		public TextView illTypeText;
		public TextView historyDoctorText;
		public TextView historyCreateTimeText;
		public TextView historyMedicineNameNumText;
		public TextView historyMedicineNoticeText;
		public TextView historyRemarkText;
	};

	public CustomAdapter_MedHistory(Context ctx, List<MedicinePatientInfo> infolist) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		m_infolist = infolist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(R.layout.medicine_history, null);

			listItemView.illTypeText = (TextView) convertView
					.findViewById(R.id.history_ill_type);
			listItemView.historyDoctorText = (TextView) convertView
					.findViewById(R.id.history_doctor);
			listItemView.historyCreateTimeText = (TextView) convertView
					.findViewById(R.id.history_create_time);
			listItemView.historyMedicineNameNumText = (TextView) convertView
					.findViewById(R.id.history_medicine_name_num);
			
			listItemView.historyMedicineNoticeText = (TextView) convertView
					.findViewById(R.id.history_medichineNotice);
			listItemView.historyRemarkText = (TextView) convertView
					.findViewById(R.id.history_remark);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		String illType = m_infolist.get(position).getType();
		String doctorName = m_infolist.get(position).getDoctor();
		String createTime = m_infolist.get(position).getTime();
		m_medicinelist = m_infolist.get(position).getMedicineInfoList();
		listItemView.illTypeText.setText(illType);
		listItemView.historyDoctorText.setText(doctorName);
		listItemView.historyCreateTimeText.setText(createTime);
		String nameNumStr = getNameNumStr(m_medicinelist);
		listItemView.historyMedicineNameNumText.setText(nameNumStr);
		String medicineNotice = m_infolist.get(position).getMedicineNotice();
		listItemView.historyMedicineNoticeText.setText(medicineNotice);
		String remark = m_infolist.get(position).getRemark();
		listItemView.historyRemarkText.setText(remark);
		return convertView;
	}
	private String  getNameNumStr(List<MedicinePatientMedicineInfo> medicinelist){
		String s = "";
		for(MedicinePatientMedicineInfo medicine:medicinelist){
		 s  = s+ medicine.getName()+("(")+medicine.getCount()+(") ");
		}
		return s;
	}
	
	public final int getCount() {
		return m_infolist.size();
	}

	public final Object getItem(int position) {
		return m_infolist.get(position);
	}

	public final long getItemId(int position) {
		return position;
	}
}
