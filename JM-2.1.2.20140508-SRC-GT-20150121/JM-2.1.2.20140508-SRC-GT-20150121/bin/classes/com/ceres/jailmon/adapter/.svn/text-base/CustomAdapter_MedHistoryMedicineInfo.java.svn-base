package com.ceres.jailmon.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ceres.jailmon.R;
import com.ceres.jailmon.data.MedicinePatientMedicineInfo;

public class CustomAdapter_MedHistoryMedicineInfo extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;

	protected List<MedicinePatientMedicineInfo> m_infolist;

	static class ListItemView {
		public TextView medicieName;
		public TextView medicineNum;
	};

	public CustomAdapter_MedHistoryMedicineInfo(Context ctx, List<MedicinePatientMedicineInfo> infolist) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		m_infolist = infolist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(R.layout.med_round_send_med_patient_type, null);

			listItemView.medicieName = (TextView) convertView
					.findViewById(R.id.round_send_medcine_pation_name);
			listItemView.medicineNum = (TextView) convertView
					.findViewById(R.id.round_send_medcine_ill_type);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		String personNameStr = m_infolist.get(position).getName();
		String personTypeStr = m_infolist.get(position).getCount();
		listItemView.medicieName.setText(personNameStr);
		listItemView.medicineNum.setText(personTypeStr);
		return convertView;
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
