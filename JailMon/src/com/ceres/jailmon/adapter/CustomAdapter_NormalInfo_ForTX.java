/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceres.jailmon.R;
import com.ceres.jailmon.data.BaseData;
import com.ceres.jailmon.data.OutInfo;

public class CustomAdapter_NormalInfo_ForTX extends BaseAdapter {
	private 

	static class ListItemView {
		public TextView tvHeadline;
		public TextView pIdText;
		public TextView policeText;
		public TextView roomText;
		public TextView jshText;
		public TextView remarkText;
		public ImageView image;
		public TextView txText;
		public TextView timeText;
	};

	protected LayoutInflater listContainer;
	protected Context context;
	protected List<OutInfo> items;
	private btnListener mListener;

	public CustomAdapter_NormalInfo_ForTX(Context ctx, List<?> list) {
		listContainer = LayoutInflater.from(ctx);
		items = (List<OutInfo>) list;
	}
	
	public void setListener(btnListener listener) {
		mListener = listener;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(
					R.layout.custom_list_item_normal_fortx, null);

			listItemView.tvHeadline = (TextView) convertView
					.findViewById(R.id.textHeadline);
			listItemView.pIdText = (TextView) convertView
					.findViewById(R.id.prisonerId);
			listItemView.policeText = (TextView) convertView
					.findViewById(R.id.police);
			listItemView.roomText = (TextView) convertView
					.findViewById(R.id.room);
			listItemView.jshText = (TextView) convertView
					.findViewById(R.id.jsh);
			listItemView.remarkText = (TextView) convertView
					.findViewById(R.id.remark);
			listItemView.image = (ImageView) convertView.findViewById(R.id.pic);
			listItemView.txText = (TextView) convertView.findViewById(R.id.tx_btn);
			listItemView.timeText = (TextView) convertView.findViewById(R.id.time);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		OutInfo info = items.get(position);

		if (info != null) {
			String strHeadline = info.getHeadline();
			if (strHeadline != null)
				listItemView.tvHeadline.setText(strHeadline);

			String id = info.getpId();
			id = String.format("人员编号：%s", id);
			if (!TextUtils.isEmpty(id))
				listItemView.pIdText.setText(id);
			String police = info.getPolice();
			police = String.format("处置民警: %s", police);
			String room = info.getTxroom();
			room = String.format("提讯房间：%s", room);
			String des = info.getDesp();
			des = String.format("备注说明：%s", des);
			String txTime = info.getTime_begin();
			txTime = String.format("发起时间：%s", txTime);
			listItemView.timeText.setText(txTime);
			String jsh = info.getJsh();
			jsh = String.format("监室号：%s", jsh);
			if (!TextUtils.isEmpty(id))
				listItemView.pIdText.setText(id);
			if (!TextUtils.isEmpty(police))
				listItemView.policeText.setText(police);
			if (!TextUtils.isEmpty(room))
				listItemView.roomText.setText(room);
			if (!TextUtils.isEmpty(jsh))
				listItemView.jshText.setText(jsh);
			Bitmap bmpPhoto = info.getPhoto();
			if (bmpPhoto != null)
				listItemView.image.setImageBitmap(bmpPhoto);
			else
				listItemView.image.setImageResource(R.drawable.prisoner_sample);
			String txFlag = info.getTxFlag();
			if("0".equals(txFlag)){
				listItemView.txText.setText("未提");
			}else if("1".equals(txFlag)){
				listItemView.txText.setText("已提未送回");
			}
			listItemView.txText.setTag(R.id.tx_prionser_list_tx_flag,txFlag);
			listItemView.txText.setTag(R.id.tx_prionser_list_position,position);
			listItemView.txText.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                	String flag = (String) view.getTag(R.id.tx_prionser_list_tx_flag);
                	int position = (Integer) view.getTag(R.id.tx_prionser_list_position);
                	OutInfo outInfo = items.get(position);
                	String txID = outInfo.getTxId();
                	if("0".equals(flag)){
                		outInfo.setTxFlag("1");
                		notifyDataSetChanged();
                		mListener.submit("1",txID);
        			}else if("1".equals(flag)){
        				items.remove(position);
        				notifyDataSetChanged();
        				mListener.submit("2",txID);
        			}
                }
            });

		}

		return convertView;
	}
	
	public interface btnListener {
		public void submit(String txFlag,String txID);
	}

}