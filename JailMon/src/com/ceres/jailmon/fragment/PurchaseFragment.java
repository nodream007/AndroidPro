package com.ceres.jailmon.fragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceres.jailmon.AppContext;
import com.ceres.jailmon.R;
import com.ceres.jailmon.data.Account;
import com.ceres.jailmon.data.BuyShopping;
import com.ceres.jailmon.database.Goods;
import com.ceres.jailmon.database.GoodsDao;
import com.ceres.jailmon.util.BitmapUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

@SuppressLint("ValidFragment")
public class PurchaseFragment extends BaseFragment implements OnClickListener {
	private View mContentView;
	private View mDailyTextView;
	private View mLaborTextView;
	private View mFoodTextView;
	private TextView mRemainBalanceView;
	private TextView mLastTimeView;
	private TextView mTotalPriceView;
	private ListView mContentListView;
	private ListViewAdapter mListViewAdapter;
	private String roomId;
	private String pid;
	private GoodsDao dao; // 数据库操作类声明
	private String[] tableArray = { "daily_t", "labor_t", "food_t" }; // 三张表的数组
	private String[] countArray = { "dailyCount", "laborCount", "foodCount" };// count_t表的三个字段
	private int sqlCount;
	private int index = AppContext.TYPE_DAILY;
	private int[] ids = new int[99];
	private Dialog dialog = null;
	private boolean isLoadingData;
	private List<Goods> mGoodsList = new ArrayList<Goods>();
	private Account mAccount;
	private String m_labBalance, m_labLastTime, m_labSum;
	private String mTotalPurchase = "0.00";
	private float mBalance;
	public Map<Integer, Goods> mSelectedGoodsMap = new HashMap<Integer, Goods>();

	public float getmBalance() {
		return mBalance;
	}

	public String getmTotalPurchase() {
		return mTotalPurchase;
	}

	public PurchaseFragment(Account m_accountinfo) {
		mAccount = m_accountinfo;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_purchase, null);
		m_AppContext = (AppContext) this.getActivity().getApplication();
		mDailyTextView = mContentView.findViewById(R.id.textView1);
		mLaborTextView = mContentView.findViewById(R.id.textView2);
		mFoodTextView = mContentView.findViewById(R.id.textView3);
		mDailyTextView.setOnClickListener(this);
		mLaborTextView.setOnClickListener(this);
		mFoodTextView.setOnClickListener(this);
		mRemainBalanceView = (TextView) mContentView
				.findViewById(R.id.textBalance);
		mLastTimeView = (TextView) mContentView.findViewById(R.id.textLastTime);
		mTotalPriceView = (TextView) mContentView.findViewById(R.id.textSum);
		mContentListView = (ListView) mContentView
				.findViewById(R.id.lstPurGoods);
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dao = new GoodsDao(getActivity());
		mListViewAdapter = new ListViewAdapter();
		mContentListView.setAdapter(mListViewAdapter);
		mContentView.setBackgroundResource(R.drawable.content_bg1);
		loadData(roomId, pid, AppContext.TYPE_DAILY);
		m_labSum = getString(R.string.pur_sum);
		showAccountInfo(mAccount);
		changeTotalPurchase();
		return mContentView;
	}

	/*
	 * public void showPurchaseSum(String sum) { if (mTotalPriceView != null) {
	 * mTotalPriceView.setText(sum); } }
	 */

	/*
	 * public void showAccountInfo(String balance, String lastTime) {
	 * Log.d("jiayy"
	 * ,"mRemainBalanceView = "+mRemainBalanceView+"mLastTimeView = "
	 * +mLastTimeView); if (mRemainBalanceView == null) { return; } if
	 * (mLastTimeView == null) { return; } mRemainBalanceView.setText(balance);
	 * mLastTimeView.setText(lastTime); }
	 */

	public void showAccountInfo(Account info) {
		mBalance = (info != null) ? info.getBalance() : 0.0f;
		String strLastTime = (info != null) ? info.getLastTime()
				: "Invalid Time";
		m_labBalance = getString(R.string.pur_balance);
		m_labLastTime = getString(R.string.pur_lasttime);
		String t1 = String.format("%s：%.2f", m_labBalance, mBalance);
		if (TextUtils.isEmpty(strLastTime)) {
			strLastTime = "";
		}
		String t2 = String.format("%s：%s", m_labLastTime, strLastTime);
		mRemainBalanceView.setText(t1);
		mLastTimeView.setText(t2);
	}

	private void changeTotalPurchase() {
		String t1 = String.format("%s：%s", m_labSum, mTotalPurchase);
		if (mTotalPriceView != null) {
			mTotalPriceView.setText(t1);
		}
	}

	public void setData(String roomId, String pid) {
		this.roomId = roomId;
		this.pid = pid;
	}

	public void loadData(String roomId, String pid, int type) {
		if (mContentView != null) {
			switch (type) {
			case AppContext.TYPE_DAILY:
				mContentView.setBackgroundResource(R.drawable.content_bg1);
				break;
			case AppContext.TYPE_LABOR:
				mContentView.setBackgroundResource(R.drawable.content_bg2);
				break;
			case AppContext.TYPE_FOOD:
				mContentView.setBackgroundResource(R.drawable.content_bg3);
				break;
			default:
				break;
			}
		}
		setData(roomId, pid);
		isLoadingData = true;
		getBuyShoppingList(m_basehandler, roomId, pid, type);
	}

	public class ListViewAdapter extends BaseAdapter {
		View[] itemViews;

		public ListViewAdapter() {
		}

		public int getCount() {
			return mGoodsList.size(); // 显示当前表中数据的条数
		}

		public Object getItem(int position) {
			return mGoodsList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				// 使用View的对象itemView与R.layout.item关联
				convertView = inflater.inflate(R.layout.buy_list, null);
				holder = new ViewHolder();
				// 通过findViewById()方法实例R.layout.item内各组件
				holder.selectImg = (TextView) convertView
						.findViewById(R.id.buy_list_radio);
				holder.text1 = (TextView) convertView
						.findViewById(R.id.buy_item_textView1); // 名称
				holder.text2 = (TextView) convertView
						.findViewById(R.id.buy_item_textView2); // 单价
				holder.text3 = (TextView) convertView
						.findViewById(R.id.buy_item_textView3); // 简介
				holder.text4 = (TextView) convertView
						.findViewById(R.id.buy_item_textView4);
				holder.subBtn = (ImageView) convertView
						.findViewById(R.id.sub_id);
				holder.addBtn = (ImageView) convertView
						.findViewById(R.id.goods_num_add_id);
				holder.numEdit = (EditText) convertView
						.findViewById(R.id.goods_num_edit);
				holder.imgview = (ImageView) convertView
						.findViewById(R.id.buy_item_imageView1);
				holder.selectLinear = (LinearLayout) convertView
						.findViewById(R.id.buy_list_select_linear);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Goods goods = mGoodsList.get(position);
			String name = goods.getName();
			holder.text1.setText(name);
			String explain = goods.getDescrip();
			holder.text3.setText(explain);
			String price = goods.getPrice();
			holder.text2.setText(price);
			byte[] in = goods.getPic();
			if (in == null || in.length == 0) {
				String picStr = goods.getPicStrUrl();
				
				if (!TextUtils.isEmpty(picStr)) {
					int resID = getResources().getIdentifier(picStr,
							"drawable", getActivity().getPackageName());
					Log.d("jiayy","picStr = "+picStr+" resID = "+resID);
					holder.imgview.setBackgroundResource(resID);
				}
			} else {
				try {
					Bitmap bit = BitmapFactory
							.decodeByteArray(in, 0, in.length);
					holder.imgview.setImageBitmap(bit);
				} catch (Exception e) {
					e.printStackTrace();
					Log.i("TS", "Bitmap.error:" + e.getMessage());
				}
			}
			boolean selected = goods.isSelected();
			if (selected) {
				holder.selectImg
						.setBackgroundResource(R.drawable.list_product_selet1);
			} else {
				holder.selectImg
						.setBackgroundResource(R.drawable.list_product_selet);
			}
			holder.selectLinear.setTag(position);
			holder.selectLinear.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					int position = (Integer) view.getTag();
					Goods goods = mGoodsList.get(position);
					boolean selected = goods.isSelected();
					if (selected) {
						goods.setSelected(false);
						goods.setNum("0");
					} else {
						goods.setSelected(true);
					}
					notifyDataSetChanged();
				}
			});
			String num = goods.getNum();
			holder.numEdit.setTag(position);
			holder.numEdit.setText(num);
			holder.numEdit.setSelection(num.length());
			holder.subBtn.setTag(position);
			holder.subBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Log.d("jiayy", "sub Onclick begin");
					int index = (Integer) view.getTag();
					Goods goods = mGoodsList.get(index);
					String numStr = goods.getNum();
					int num = Integer.valueOf(numStr);
					boolean selected = goods.isSelected();
					if (selected) {
						if (num != 0) {
							num = num - 1;
							goods.setNum(String.valueOf(num));
							holder.numEdit.setText(String.valueOf(num));
							getTotalPrice();
							// notifyDataSetChanged();
						}
					} else {
						Toast.makeText(m_AppContext, "请先点击药品名称选中后再进行数量增减",
								Toast.LENGTH_SHORT).show();
					}
				}
			});

			holder.addBtn.setTag(position);
			holder.addBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Log.d("jiayy", "add Onclick begin");
					int index = (Integer) view.getTag();
					Goods goods = mGoodsList.get(index);
					boolean selected = goods.isSelected();
					if (selected) {
						String numStr = goods.getNum();
						int num = Integer.valueOf(numStr);
						num = num + 1;
						goods.setNum(String.valueOf(num));
						Log.d("jiayy", "num = " + num);
						holder.numEdit.setText(String.valueOf(num));
						getTotalPrice();
						// notifyDataSetChanged();
					} else {
						Toast.makeText(m_AppContext, "请先点击药品名称选中后再进行数量增减",
								Toast.LENGTH_SHORT).show();
					}
				}
			});

			holder.numEdit.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
				}

				@Override
				public void afterTextChanged(Editable s) {
					int position = (Integer) holder.numEdit.getTag();
					Goods goods = mGoodsList.get(position);
					boolean selected = goods.isSelected();
					if (selected) {
						String num = holder.numEdit.getText().toString();
						Log.d("jiayy11", "selected = " + selected + " num = "
								+ num + "positin after = " + position);
						goods.setNum(num);
						getTotalPrice();
					} /*
					 * else { Log.d("jiayy","afterTextChanged");
					 * holder.medNumEdit.setText("0"); Toast.makeText(m_context,
					 * "请先点击药品名称选中后再进行数量增减", Toast.LENGTH_SHORT).show(); }
					 */
				}
			});

			return convertView;
		}
	}

	@Override
	protected void onReceiveBuyShoppingList(List<BuyShopping> shoppingList) {
		// showAccountInfo(mAccount);
		isLoadingData = false;
		Cursor css = null;
		try {
			css = dao.idQueryCount(countArray[index]);
			while (css.moveToNext()) {
				sqlCount = css.getInt(0); // 取出数据库中count值，用于判断是读取数据库还是直接显示
			}
		} finally {
			css.close();
		}
		if (!shoppingList.isEmpty()) {
			String count = shoppingList.get(0).getCount();
			int countInt = 0;
			if (!TextUtils.isEmpty(count)) {
				countInt = Integer.valueOf(count);
			}
			if (countInt == sqlCount) {
				if (sqlCount == 0) {
					insertShoppingData(shoppingList);
				}
			} else if (countInt > sqlCount) {
				dao.deleteTable(tableArray[index]);
				dao.createTable(tableArray[index]);
				insertShoppingData(shoppingList);
				dao.updateCount(countArray[index], count);
			}
			getAdapterGoodsList();
		}
	}

	private void insertShoppingData(List<BuyShopping> shoppingList) {
		for (int i = 1; i < shoppingList.size(); i++) {
			BuyShopping shopping = shoppingList.get(i);
			String picUrl = "";
			int goodsId = 0;
			if (shopping != null) {
				picUrl = shopping.getPicture();
				goodsId = shopping.getId();
			}
			byte[] imgByte = null;
			/*if (!TextUtils.isEmpty(picUrl)) {
				try {
					imgByte = BitmapUtil.getImage(picUrl);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			Goods data = new Goods(shopping.getId(), shopping.getName(),
					shopping.getExplain(), shopping.getPrice(), imgByte,
					shopping.getPicture());
			dao.addData(tableArray[index], data); // 商品信息存入数据库（不含图片信息）
			insertImg(goodsId, picUrl, dao, tableArray[index]);
			// getImg(goodsId,picUrl);
			getAdapterGoodsList();
		}
	}

	protected void onReceiveAllGoodsPhotoNotify() {
		getAdapterGoodsList();
	}

	private void getTotalPrice() {
		float totoalPrice = 0;
		for (Goods goods : mGoodsList) {
			String priceStr = goods.getPrice();
			float price = Float.parseFloat(priceStr);
			String goodsNum = goods.getNum();
			float priceFloat;
			if (!TextUtils.isEmpty(goodsNum)) {
				int num = Integer.parseInt(goodsNum);
				// priceFloat = num * price;
				if (num != 0) {
					mSelectedGoodsMap.put(goods.getId(), goods);
				}
			}
		}
		if (!mSelectedGoodsMap.isEmpty()) {
			Iterator iter = mSelectedGoodsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				int id = (Integer) entry.getKey();
				Goods goods = (Goods) entry.getValue();
				String num = goods.getNum();
				int numInt = Integer.valueOf(num);
				String priceStr = goods.getPrice();
				float price = Float.parseFloat(priceStr);
				totoalPrice = totoalPrice + numInt * price;
			}
		}
		mTotalPurchase = String.format("%.2f", totoalPrice);
		changeTotalPurchase();
	}

	public void getAdapterGoodsList() {
		Cursor cs = dao.queryTable(tableArray[index]);
		mGoodsList.clear();
		while (cs.moveToNext()) {
			Goods goods = new Goods();
			int id = cs.getInt(0);
			goods.setId(id);
			String name = cs.getString(1);
			goods.setName(name);
			String des = cs.getString(2);
			goods.setDescrip(des);
			String price = cs.getString(3);
			goods.setPrice(price);
			byte[] in = cs.getBlob(4);
			goods.setPic(in);
			String picStr = cs.getString(5);
			goods.setPicStrUrl(picStr);
			goods.setNum("0");
			if (!mSelectedGoodsMap.isEmpty()
					&& mSelectedGoodsMap.containsKey(id)) {
				goods.setSelected(true);
				Goods goodsSelected = mSelectedGoodsMap.get(id);
				String numStr = goodsSelected.getNum();
				goods.setNum(numStr);
			}
			mGoodsList.add(goods);
		}
		mListViewAdapter.notifyDataSetChanged();
	}

	/*
	 * 图片入库 <p>根据图片的路径获取图片然后以二进制格式储存到数据库中</p>
	 * 
	 * @params url 图片的路径
	 * 
	 * @return null
	 */
	public byte[] getImg(final int id, String url) {
		// Log.i(TAG,"getImg_url:"+url);
		AsyncHttpClient client = new AsyncHttpClient();
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statuCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				if (statuCode == 200) {

					BitmapFactory factory = new BitmapFactory();
					Bitmap bitmap = factory.decodeByteArray(responseBody, 0,
							responseBody.length);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
					dao.updateDataPic(os.toByteArray(), id, tableArray[index]);// 根据id更新图片（添加图片）

				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(), "图片加载失败........",
				// Toast.LENGTH_SHORT).show();
			}
		});
		return os.toByteArray();
	}

	/*
	 * 将数据库转到存数组中
	 */
	public void addDataToArray() {
		int i = 0;
		Cursor cs = dao.queryTable(tableArray[index]);// listview显示内容一样，光标可以下移
		while (cs.moveToNext()) {
			ids[i] = cs.getInt(0);//
			i++;
		}
		cs.close();// /
	}

	static class ViewHolder {
		private TextView text1;
		private TextView text2;
		private TextView text3;
		private TextView text4;
		TextView selectImg;
		ImageView subBtn;
		ImageView addBtn;
		EditText numEdit;
		private ImageView imgview;
		LinearLayout selectLinear;
	}

	@Override
	public void onClick(View v) {
		if (isLoadingData) {
			return;
		}
		switch (v.getId()) {
		case R.id.textView1:
			if (index != AppContext.TYPE_DAILY) {
				index = AppContext.TYPE_DAILY;
			}
			break;
		case R.id.textView2:
			if (index != AppContext.TYPE_LABOR) {
				index = AppContext.TYPE_LABOR;
			}
			break;
		case R.id.textView3:
			if (index != AppContext.TYPE_FOOD) {
				index = AppContext.TYPE_FOOD;
			}
			break;
		}
		loadData(roomId, pid, index);
	}

	public Map<Integer, Goods> getGoodsMap() {
		return mSelectedGoodsMap;
	}

	public void clearGoodsList(List<Goods> goodsList) {

	}

}
