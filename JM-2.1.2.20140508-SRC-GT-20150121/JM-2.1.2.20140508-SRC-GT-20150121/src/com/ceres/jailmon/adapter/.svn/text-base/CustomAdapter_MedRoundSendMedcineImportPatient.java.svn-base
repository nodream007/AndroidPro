package com.ceres.jailmon.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ceres.jailmon.R;
import com.ceres.jailmon.data.MedRoundSendImportPatient;
import com.ceres.jailmon.data.Police;
import com.ceres.jailmon.data.PrisonerList;

public class CustomAdapter_MedRoundSendMedcineImportPatient extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;

	protected List<MedRoundSendImportPatient> m_infolist;

	static class ListItemView {
		public TextView patientName;
		public TextView patientType;
	};

	public CustomAdapter_MedRoundSendMedcineImportPatient(Context ctx, List<MedRoundSendImportPatient> infolist) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		m_infolist = infolist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(R.layout.med_round_send_med_patient_type, null);

			listItemView.patientName = (TextView) convertView
					.findViewById(R.id.round_send_medcine_pation_name);
			listItemView.patientType = (TextView) convertView
					.findViewById(R.id.round_send_medcine_ill_type);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		String personNameStr = m_infolist.get(position).getPatientName();
		String personTypeStr = m_infolist.get(position).getPationtType();
		listItemView.patientName.setText(personNameStr);
		listItemView.patientType.setText(personTypeStr);
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
