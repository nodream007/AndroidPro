package com.ceres.jailmon.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ceres.jailmon.R;
import com.ceres.jailmon.data.Goods;

class ListItemView { // 自定义控件集合
	public TextView txtGoods;
	public TextView txtPrice;
	public TextView txtNum;
	public Button btnAdd;
	public Button btnSub;
}

public class GoodsListAdapter extends BaseAdapter {
	public Context context;
	public List<Goods> items;
	private LayoutInflater listContainer;
	private onPurchaseListenser m_listener;

	public GoodsListAdapter(Context ctx, List<Goods> list,
			onPurchaseListenser listener) {
		listContainer = LayoutInflater.from(ctx);
		context = ctx;
		items = list;
		m_listener = listener;
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
		final int selectID = position;

		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(
					R.layout.custom_list_item_goods, null);

			listItemView.txtGoods = (TextView) convertView
					.findViewById(R.id.textGoodsName);
			listItemView.txtPrice = (TextView) convertView
					.findViewById(R.id.textGoodsPrice);
			listItemView.btnAdd = (Button) convertView
					.findViewById(R.id.buttonGoodsAddX);
			listItemView.txtNum = (TextView) convertView
					.findViewById(R.id.textGoodsNum);
			listItemView.btnSub = (Button) convertView
					.findViewById(R.id.buttonGoodsSubsX);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		listItemView.txtGoods.setText(items.get(position).toString());
		listItemView.txtPrice.setText(items.get(position).price_toString());
		listItemView.txtNum.setText(Integer.toString(items.get(position).num));

		listItemView.btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Goods info = items.get(selectID);

				if (info != null && m_listener.onAdd(info))
					notifyDataSetChanged();
			}
		});

		listItemView.btnSub.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Goods info = items.get(selectID);

				if (info != null && m_listener.onSub(info))
					notifyDataSetChanged();

			}
		});

		return convertView;
	}

}