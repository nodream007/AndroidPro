package com.ceres.jailmon.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ceres.jailmon.R;
import com.ceres.jailmon.adapter.CustomAdapter_MedHistory.ListItemView;
import com.ceres.jailmon.data.MedicinePatientInfo;
import com.ceres.jailmon.data.MedicinePatientMedicineInfo;
import com.ceres.jailmon.data.PatrolHistory;

public class CustomAdapter_PatrolHistory extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;
	protected List<PatrolHistory> mPatrolHistroylist;
	static class ListItemView {
		public TextView nameText;
		public TextView addressText;
		public TextView timeText;
	};

	public CustomAdapter_PatrolHistory(Context ctx, List<PatrolHistory> infolist) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		mPatrolHistroylist = infolist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(R.layout.patrol_history_item, null);

			listItemView.nameText = (TextView) convertView
					.findViewById(R.id.police);
			listItemView.addressText = (TextView) convertView
					.findViewById(R.id.address);
			listItemView.timeText = (TextView) convertView
					.findViewById(R.id.time);
			
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		PatrolHistory patrolHistory = mPatrolHistroylist.get(position);
		String name = patrolHistory.getName();
		String addr = patrolHistory.getAddress();
		String time = patrolHistory.getTime();
		listItemView.nameText.setText(name);
		listItemView.addressText.setText(addr);
		listItemView.timeText.setText(time);
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
		return mPatrolHistroylist.size();
	}

	public final Object getItem(int position) {
		return mPatrolHistroylist.get(position);
	}

	public final long getItemId(int position) {
		return position;
	}
}
