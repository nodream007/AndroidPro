package com.ceres.jailmon.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.ceres.jailmon.AppContext;
import com.ceres.jailmon.R;
import com.ceres.jailmon.data.ReceiptProduct;

public class ReceiptFragment extends BaseFragment {
	private View mContentView;
	private TextView mTotalBalanceView;
	private TextView mLastTimeView;
	private TextView mTotalPriceView;
	private TextView mRemainBalanceView;
	private ListView mListView;
	private ReceiptAdapter mReceiptAdapter;
	private List<ReceiptProduct> mReceiptProductList;
	
	private String roomId;
	private String pid;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_receipt, null);
		m_AppContext = (AppContext) this.getActivity().getApplication();
		mTotalBalanceView = (TextView) mContentView
				.findViewById(R.id.textBalance);
		mRemainBalanceView = (TextView) mContentView
				.findViewById(R.id.textRemain);
		mLastTimeView = (TextView) mContentView.findViewById(R.id.textLastTime);
		mTotalPriceView = (TextView) mContentView.findViewById(R.id.textSum);
		mListView = (ListView) mContentView.findViewById(R.id.lstReceipt);
		mReceiptAdapter = new ReceiptAdapter();
		mListView.setAdapter(mReceiptAdapter);
		loadData(roomId, pid);
		return mContentView;
	}
	
	public void setData(String roomId, String pid) {
		this.roomId = roomId;
		this.pid = pid;
	}

	public void loadData(String roomId, String pid) {
		setData(roomId, pid);
		getReceiptList(m_basehandler, roomId, pid);
	}
	
	@Override
	protected void onReceiveReceiptList(List<ReceiptProduct> receiptList) {
		mReceiptProductList = receiptList;
		if(mReceiptAdapter != null) {
			mReceiptAdapter.notifyDataSetChanged();
		}
	}
	
	private class ReceiptAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return mReceiptProductList == null ? 0 : mReceiptProductList.size();
		}

		@Override
		public ReceiptProduct getItem(int arg0) {
			return mReceiptProductList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ReceiptProduct product = getItem(arg0);
			if(product == null) {
				return null;
			}
			ViewHolder holder;
			if(arg1 == null) {
				arg1 = LayoutInflater.from(getActivity()).inflate(R.layout.receipt_list_item, null);
				holder = new ViewHolder();
				holder.mProductNameView = (TextView) arg1.findViewById(R.id.tv_product_name);
				holder.mSinglePriceView = (TextView) arg1.findViewById(R.id.tv_single_price);
				holder.mCountView = (TextView) arg1.findViewById(R.id.tv_product_count);
				//holder.mCheckBox = (CheckBox) arg1.findViewById(R.id.cb_product);
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}
			holder.mProductNameView.setText(product.getName());
			holder.mSinglePriceView.setText("￥" + product.getPrice());
			holder.mCountView.setText(String.valueOf(product.getCount()));
			return arg1;
		}
		
	}
	
	private static class ViewHolder {
		TextView mProductNameView;
		TextView mSinglePriceView;
		TextView mCountView;
		CheckBox mCheckBox;
	}
}
