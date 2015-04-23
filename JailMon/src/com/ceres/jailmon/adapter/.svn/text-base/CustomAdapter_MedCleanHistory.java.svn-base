package com.ceres.jailmon.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ceres.jailmon.R;
import com.ceres.jailmon.data.MedicineCleanCoperateInfo;
import com.ceres.jailmon.data.MedicineCleanInfo;
import com.ceres.jailmon.data.MedicinePatientInfo;
import com.ceres.jailmon.data.MedicinePatientMedicineInfo;

public class CustomAdapter_MedCleanHistory extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;

	protected List<MedicineCleanInfo> m_infolist;
	
	protected List<MedicineCleanCoperateInfo> m_cleanCoperatelist;
	
	static class ListItemView {
		public TextView cleanTypeText;
		public TextView cleanWayText;
		public TextView cleanCreateTimeText;
		public TextView cleanLeaderText;
		public TextView cleanCoperateText;
		public TextView cleanRemarkText;
	};

	public CustomAdapter_MedCleanHistory(Context ctx, List<MedicineCleanInfo> infolist) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		m_infolist = infolist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(R.layout.medicine_clean, null);

			listItemView.cleanTypeText = (TextView) convertView
					.findViewById(R.id.clean_type);
			listItemView.cleanWayText = (TextView) convertView
					.findViewById(R.id.clean_way);
			listItemView.cleanCreateTimeText = (TextView) convertView
					.findViewById(R.id.clean_create_time);
			listItemView.cleanLeaderText = (TextView) convertView
					.findViewById(R.id.clean_leader);
			listItemView.cleanCoperateText = (TextView) convertView
					.findViewById(R.id.clean_leader);
			
			listItemView.cleanRemarkText = (TextView) convertView
					.findViewById(R.id.clean_remark);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		String cleanType = m_infolist.get(position).getType();
		String cleanWay = m_infolist.get(position).getWay();
		String createTime = m_infolist.get(position).getTime();
		m_cleanCoperatelist = m_infolist.get(position).getCleanInfoList();
		String leader = m_infolist.get(position).getLeader();
		listItemView.cleanTypeText.setText(cleanType);
		listItemView.cleanWayText.setText(cleanWay);
		listItemView.cleanCreateTimeText.setText(createTime);
		String corperater = getCorperateStr(m_cleanCoperatelist);
		listItemView.cleanCoperateText.setText(corperater);
		String remark = m_infolist.get(position).getRemark();
		listItemView.cleanRemarkText.setText(remark);
		return convertView;
	}
	private String  getCorperateStr(List<MedicineCleanCoperateInfo> medicinelist){
		String s = "";
		for(MedicineCleanCoperateInfo medicine:medicinelist){
		 s  = s+ medicine.getName()+" ";
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
