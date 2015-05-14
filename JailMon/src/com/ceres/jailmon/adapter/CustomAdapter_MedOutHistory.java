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
import com.ceres.jailmon.data.MedicinePatientOutInfo;

public class CustomAdapter_MedOutHistory extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;

	protected List<MedicinePatientOutInfo> m_infolist;
	
	static class ListItemView {
		public TextView name;
		public TextView illnesstype;
		public TextView illnessexp;
		public TextView applytime;
		public TextView medicaltime;
		public TextView doctorname;
		public TextView outexplain;
		public TextView remark;
	};

	public CustomAdapter_MedOutHistory(Context ctx, List<MedicinePatientOutInfo> infolist) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		m_infolist = infolist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(R.layout.medicine_out_history, null);

			listItemView.name = (TextView) convertView
					.findViewById(R.id.history_out_name);
			listItemView.illnesstype = (TextView) convertView
					.findViewById(R.id.history_out_illnesstype);
			listItemView.illnessexp = (TextView) convertView
					.findViewById(R.id.history_out_illnessexp);
			listItemView.applytime = (TextView) convertView
					.findViewById(R.id.history_out_applytime);
			listItemView.medicaltime = (TextView) convertView
					.findViewById(R.id.history_out_medicaltime);
			listItemView.doctorname = (TextView) convertView
					.findViewById(R.id.history_out_doctorname);
			listItemView.outexplain = (TextView) convertView
					.findViewById(R.id.history_out_outexplain);
			listItemView.remark = (TextView) convertView
					.findViewById(R.id.history_out_remark);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		listItemView.name.setText(m_infolist.get(position).getName());
		listItemView.illnesstype.setText(m_infolist.get(position).getIllNessType());
		listItemView.illnessexp.setText(m_infolist.get(position).getIllNessExp());
		listItemView.applytime.setText(m_infolist.get(position).getApplyTime());
		listItemView.medicaltime.setText(m_infolist.get(position).getMedicalTime());
		listItemView.doctorname.setText(m_infolist.get(position).getDoctorName());
		listItemView.outexplain.setText(m_infolist.get(position).getOutExplain());
		listItemView.remark.setText(m_infolist.get(position).getRemark());
		
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
