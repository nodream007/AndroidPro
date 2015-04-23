package com.ceres.jailmon.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ceres.jailmon.R;
import com.ceres.jailmon.SendMedicineActivity;
import com.ceres.jailmon.data.MedRoundSendMedTotalPatient;

public class CustomAdapter_MedRoundSendMedcineTotalPatient extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;
	private String mCid;
	protected List<MedRoundSendMedTotalPatient> m_infolist;
	public static final String CELL_ID = "cell_id";
	public static final String PERSON_ID = "person_id";
	
	static class ListItemView {
		public TextView patientName;
	};

	public CustomAdapter_MedRoundSendMedcineTotalPatient(Context ctx, List<MedRoundSendMedTotalPatient> infolist,String cId) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		m_infolist = infolist;
		mCid = cId;
	}
	
	public void setListener(){
		
	};
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(R.layout.med_round_send_med_total_patient, null);

			listItemView.patientName = (TextView) convertView
					.findViewById(R.id.round_send_medcine_pation_name);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		String personNameStr = m_infolist.get(position).getPatientName();
		String personId = m_infolist.get(position).getPatientId();
		convertView.setTag(R.id.med_round_send_medicine_person,personId);
		listItemView.patientName.setText(personNameStr);
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(m_context,SendMedicineActivity.class);
				intent.putExtra(CELL_ID, mCid);
				String pid = (String) view.getTag(R.id.med_round_send_medicine_person);
				intent.putExtra(PERSON_ID, pid);
				
				m_context.startActivity(intent);
			}
		});
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
