/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ceres.jailmon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.MediaPlayer.PlayM4.Player.MPRect;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.adapter.GoodsListAdapter;
import com.ceres.jailmon.data.Account;
import com.ceres.jailmon.data.Cell;
import com.ceres.jailmon.data.CellList;
import com.ceres.jailmon.data.GoodsList;
import com.ceres.jailmon.data.Prisoner;
import com.ceres.jailmon.data.PrisonerList;
import com.ceres.jailmon.data.PurchaseResult;
import com.ceres.jailmon.data.ReceiptResult;
import com.ceres.jailmon.database.Goods;
import com.ceres.jailmon.fragment.PurchaseFragment;
import com.ceres.jailmon.fragment.ReceiptFragment;

public class PurchaseActivity extends BaseFragmentActivity implements
		OnClickListener {

	private LinearLayout m_layoutRooms, m_layoutPrisoners;
	private ListView m_listViewRoom, m_listViewPrisoner;

	private TextView m_txtBalance, m_txtLastTime, m_txtSum;
	String m_labBalance, m_labLastTime, m_labSum;

	private CellList m_celllist = null;
	private PrisonerList m_prisonerlist = null;
	private GoodsList m_goodslist = null;
	private Account m_accountinfo;
	Prisoner m_prisoner = null;

	private AnimationUtil m_aniset;

	GoodsListAdapter m_adapterGoods;

	private PurchaseFragment mPurchaseFragment;
	private ReceiptFragment mReceiptFragment;
	public static final int TAB_PURCHASE = 0;
	public static final int TAB_RECEIPT = 1;
	private int mIndex = TAB_PURCHASE;
	private FragmentManager mFragmentManager;
	private List<TextView> mViewList = new ArrayList<TextView>();
	private TextView mPurchaseView;
	private TextView mReceiptView;
	private Dialog dialog = null;
	private static final int DISSMISS_DIALOG = 0;
	private String mPwd;
	private CustomAdapter_MainList mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);
		m_aniset.loadListControl();

		setContentView(R.layout.purchase);

		m_layoutRooms = (LinearLayout) findViewById(R.id.layoutPurRooms);
		m_layoutPrisoners = (LinearLayout) findViewById(R.id.layoutPurPrisoners);
		// m_layoutPurchase = (LinearLayout)
		// findViewById(R.id.layoutPurContent);

		mPurchaseView = (TextView) findViewById(R.id.tv_purchase);
		mReceiptView = (TextView) findViewById(R.id.tv_receipt);
		mPurchaseView.setOnClickListener(this);
		mReceiptView.setOnClickListener(this);
		mViewList.add(mPurchaseView);
		mViewList.add(mReceiptView);

		initCellListView();
		initPrisonerListView();
		initContentViews();

		// m_listViewGoods = (ListView) findViewById(R.id.lstPurGoods);

		initBackButton(R.id.buttonBack);

		Button btnOK = (Button) findViewById(R.id.buttonOK);
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (mIndex) {
				case TAB_PURCHASE:
					postPurchase();
					break;
				case TAB_RECEIPT:
					showDialogPwd("");
					break;
				default:
					break;
				}

			}

		});
		mFragmentManager = getSupportFragmentManager();
		loadCellList();
		dialog = new Dialog(this, R.style.dialog);
		// loadGoodsInfo();
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DISSMISS_DIALOG:
				dialog.dismiss();
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void changeBackGround(View view) {

		for (TextView mView : mViewList) {
			if (mView.equals(view)) {
				mView.setBackgroundResource(R.drawable.menu0_down);
			} else {
				mView.setBackgroundResource(R.drawable.menu0);
			}
		}

	}

	void initCellListView() {
		m_listViewRoom = (ListView) findViewById(R.id.lstRooms);

		m_listViewRoom.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				if (m_celllist != null) {
					loadPrisoners(m_celllist.getCell(arg2));
					if (mIndex != TAB_PURCHASE) {
						mIndex = TAB_PURCHASE;
						changeTab();
					}
					ClearPurchaseInfo();
					changeBackGround(mPurchaseView);
				}
				mAdapter.setPosition(arg2);
			}

		});
	}

	private void initPrisonerListView() {
		m_listViewPrisoner = (ListView) findViewById(R.id.lstPrisoners);

		m_listViewPrisoner.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				m_prisoner = m_prisonerlist.m_list.get((int) arg2);

				if (m_prisoner != null) {
					// ClearPurchaseInfo();
					if (mIndex != TAB_PURCHASE) {
						mIndex = TAB_PURCHASE;
						changeTab();
					}
					changeBackGround(mPurchaseView);
					ClearPurchaseInfo();
					loadAccount(m_prisoner.getID());
				}
			}

		});
	}

	private void loadPurchaseData() {
		if (mPurchaseFragment == null) {
			return;
		}
		if (mPurchaseFragment.isAdded()) {
			// 此处没传roomId,请注意
			mPurchaseFragment.loadData("", m_prisoner.getID(),
					AppContext.TYPE_DAILY);

		} else {
			// 此处没传roomId,请注意
			mPurchaseFragment.setData("", m_prisoner.getID());
		}
	}

	private void loadReceiptData() {
		if (mReceiptFragment == null) {
			return;
		}
		if (mReceiptFragment.isAdded()) {
			// 此处没传roomId,请注意
			mReceiptFragment.loadData("", m_prisoner.getID());
		} else {
			// 此处没传roomId,请注意
			mReceiptFragment.setData("", m_prisoner.getID());
		}
	}

	void initContentViews() {
		m_txtBalance = (TextView) findViewById(R.id.textBalance);
		m_txtLastTime = (TextView) findViewById(R.id.textLastTime);
		m_txtSum = (TextView) findViewById(R.id.textSum);
		m_labSum = getString(R.string.pur_sum);
	}

	private void loadCellList() {

		m_layoutRooms.setVisibility(View.GONE);
		m_layoutPrisoners.setVisibility(View.GONE);
		// m_layoutPurchase.setVisibility(View.GONE);

		showProcessDialog();

		getCellList(m_basehandler);
	}

	protected void onReceiveCellList(CellList infolist) {
		closeProcessDialog();

		m_celllist = infolist;

		if (m_celllist == null)
			return;

		mAdapter = new CustomAdapter_MainList(PurchaseActivity.this,
				m_celllist.getList(), R.drawable.list_icon_home);

		m_listViewRoom.setAdapter(mAdapter);

		m_layoutRooms.setVisibility(View.VISIBLE);
		m_layoutRooms.startAnimation(m_aniset.m_aniFadeIn);

	}

	private void loadPrisoners(Cell cell) {

		if (cell != null && cell.getID() != null) {
			m_layoutPrisoners.setVisibility(View.GONE);
			// m_layoutPurchase.setVisibility(View.GONE);

			// ClearPurchaseInfo();
			showProcessDialog();

			getPrisonerList(m_basehandler, cell.getID());
		}
	}

	protected void onReceivePrisonerList(final PrisonerList infolist) {

		closeProcessDialog();

		m_prisonerlist = infolist;

		if (infolist == null)
			return;

		ArrayAdapter adapter = new ArrayAdapter(PurchaseActivity.this,
				R.layout.custom_list_item_sub, R.id.list_item_1_text,
				infolist.m_list);

		m_listViewPrisoner.setAdapter(adapter);
		if (mPurchaseFragment != null) {
			Account info = new Account();
			info.setM_fBalance(0.00f);
			info.setM_strLastTime("");
			mPurchaseFragment.showAccountInfo(info);
		}
		m_layoutPrisoners.setVisibility(View.VISIBLE);
		m_layoutPrisoners.startAnimation(m_aniset.m_AniSlideIn);

	}

	private void loadGoodsInfo() {
		getGoodsList(m_basehandler);
	}

	// protected void onReceiveGoodsList(GoodsList infolist) {
	// m_goodslist = infolist;
	//
	// if (infolist == null)
	// return;
	//
	// m_adapterGoods = new GoodsListAdapter(PurchaseActivity.this,
	// infolist.m_list, m_listener);
	//
	// m_listViewGoods.setAdapter(m_adapterGoods);
	// }

	/*
	 * void ShowAccountInfo(Account info) {
	 * 
	 * float fBalance = (info != null) ? info.getBalance() : 0.0f; String
	 * strLastTime = (info != null) ? info.getLastTime() : "Invalid Time";
	 * 
	 * String t1 = String.format("%s：%.2f", m_labBalance, fBalance); String t2 =
	 * String.format("%s：%s", m_labLastTime, strLastTime); Log.d("jiayy",
	 * "mPurchaseFragment = " + mPurchaseFragment + "t1 = " + t1 + " t2 = " +
	 * t2); if (mPurchaseFragment != null) {
	 * mPurchaseFragment.showAccountInfo(t1, t2); } }
	 */

	void ClearPurchaseInfo() {
		if (mPurchaseFragment != null) {
			if (!mPurchaseFragment.mSelectedGoodsMap.isEmpty()) {
				mPurchaseFragment.mSelectedGoodsMap.clear();
			}
			mPurchaseFragment.getAdapterGoodsList();
		}
	}

	float m_fSum = 0f;

	/*
	 * void ShowPurchaseSum() { String t1 = String.format("%s：%.2f", m_labSum,
	 * m_fSum); // m_txtSum.setText(t1); if (mPurchaseFragment != null) {
	 * mPurchaseFragment.showPurchaseSum(t1); } }
	 */

	private void loadAccount(String pid) {
		// m_layoutPurchase.setVisibility(View.GONE);
		getPrisonserAccount(m_basehandler, pid);

	}

	protected void onReceiveAccount(Account info) {
		m_accountinfo = info;
		if (m_accountinfo != null) {
			// m_layoutPurchase.setVisibility(View.VISIBLE);
			switch (mIndex) {
			case TAB_PURCHASE:
				if (mPurchaseFragment == null) {
					changeTab();
					mPurchaseView.setVisibility(View.VISIBLE);
					mReceiptView.setVisibility(View.VISIBLE);
					loadPurchaseData();
					loadReceiptData();
				} else {
					mPurchaseFragment.showAccountInfo(m_accountinfo);
				}
				break;

			default:
				break;

			// ShowAccountInfo(m_accountinfo);

			// ShowPurchaseSum();

			// m_listViewGoods.setLayoutAnimation(m_aniset.m_listController);
			// m_layoutPurchase.startAnimation(m_aniset.m_aniFadeIn);
			}
		}
	}

	private void postPurchase() {
		if (m_prisoner == null)
			return;
		Map<Integer, Goods> selectedGoodsMap = mPurchaseFragment
				.getGoodsMap();
		StringBuilder param = new StringBuilder();
		int totalNum = 0;
		if (!selectedGoodsMap.isEmpty()) {
			Iterator iter = selectedGoodsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				int id = (Integer) entry.getKey();
				Goods goods = (Goods) entry.getValue();
				String numStr = goods.getNum();
				int num = 0;
				if(numStr != null){
				 num = Integer.valueOf(numStr);
				}
				totalNum = totalNum + num;
				if (num > 0) {
					param.append(id);
					param.append(",");
					param.append(num);
					param.append("|");
				}
			}

			/*
			 * for (int i = 0; i < goodsList.size(); i++) { goods =
			 * goodsList.get(i); String numStr = goods.getNum(); int num = 0; if
			 * (!TextUtils.isEmpty(numStr)) { num = Integer.valueOf(numStr); }
			 * totalNum = totalNum + num; if (num > 0) {
			 * param.append(goods.getId()); param.append(",");
			 * param.append(num); param.append("|"); } }
			 */
			if (totalNum != 0) {
				showProcessDialog();
				postPurchaseResult(m_basehandler, m_prisoner.getID(),
						param.toString());
			} else {
				Toast.makeText(this, "请先选择商品，再进行购买", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void postReceipt(String pwd) {
		if (m_prisoner == null)
			return;
		String pId = m_prisoner.getID();
		postReceiptResult(m_basehandler, m_prisoner.getID(), pwd);
	}

	protected void onReceivePurchaseResult(PurchaseResult ret) {
		closeProcessDialog();
		String result = ret.getM_bOK();
		showDialogWin(result);
	}

	protected void onReceiveReceiptResult(ReceiptResult ret) {
		closeProcessDialog();
		String result = ret.getM_bOK();
		String[] resultArray = null;
		String resultFlag = "";
		String resultContent = "";
		if (result.contains("|")) {
			resultArray = result.trim().split("\\|");
			if (resultArray != null) {
				resultFlag = resultArray[0];
				if (resultArray.length >= 2) {
					resultContent = resultArray[1];
				}
			}
		}
		if ("true".equals(resultFlag)) {
			loadReceiptData();
			dialog.dismiss();
		} else {
			if ("密码错误".equalsIgnoreCase(resultContent)) {
				showDialogPwd("false");
			}
		}
		// showDialogPwd(result);
	}

	/*
	 * 用户购买后刷卡后弹出提示窗口 <p>提示用户的信息有6种情况：(1)提示购买信息，当前买了几件商品，总价格
	 * (2)购买成功(3)购买失败，卡内余额不足(4)购买失败，XX商品数量不足(5)购买失败，该卡尚未注册(6)购买失败，未知错误</p>
	 */
	public void showDialogWin(String result) {
		// setContentView可以设置为一个View也可以简单地指定资源ID

		LayoutInflater linear = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = linear.inflate(R.layout.purchase_result, null);
		TextView confirmTitleText = (TextView) view
				.findViewById(R.id.confirm_buy);
		ImageView carImg = (ImageView) view.findViewById(R.id.car);
		TextView totalText = (TextView) view.findViewById(R.id.left_money);
		TextView subText = (TextView) view.findViewById(R.id.sub_money);
		ImageView tipsImg = (ImageView) view.findViewById(R.id.tips_btn);
		TextView tipsText = (TextView) view.findViewById(R.id.tips);
		TextView backmodifyText = (TextView) view
				.findViewById(R.id.cancel_modify);
		backmodifyText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				dialog.dismiss();
				ClearPurchaseInfo();
			}
		});
		String[] resultArray = null;
		String resultFlag = "";
		String resultContent = "";
		if (result.contains("|")) {
			resultArray = result.trim().split("\\|");
			if (resultArray != null) {
				resultFlag = resultArray[0];
				if (resultArray.length >= 2) {
					resultContent = resultArray[1];
				}
			}
		}

		String subPrice = mPurchaseFragment.getmTotalPurchase();
		String totalSubForShow = getString(
				R.string.purchase_success_content_sub, subPrice);
		float balance = mPurchaseFragment.getmBalance();
		String balanceStrForShow = getString(
				R.string.purchase_success_content_left, balance);
		String successStr = "购买成功！3秒后返回购物界面";
		String moneyShortStr = "对不起，余额不足无法购买";
		// List<Goods> goodsList = mPurchaseFragment.getGoodsList();
		// int num = goodsList.size();
		// String numStr = String.valueOf(num);
		String stockShortStr = getString(R.string.purchase_failer_stock_less,
				"");
		backmodifyText.setVisibility(View.GONE);
		if ("true".equals(resultFlag)) {
			totalText.setText(balanceStrForShow);
			subText.setText(totalSubForShow);
			tipsImg.setBackgroundResource(R.drawable.success);
			// 清除购买项，刷新账户信息
			ClearPurchaseInfo();
			loadAccount(m_prisoner.getID());
			tipsText.setText(successStr);
			backmodifyText.setVisibility(View.GONE);
			dialog.setContentView(view);
			Window dialogWindow = dialog.getWindow();
			LayoutParams lp = new LayoutParams();
			// dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
			lp.x = 177; // 新位置X坐标
			lp.y = 65; // 新位置Y坐标
			lp.width = 570; // 宽度
			lp.height = 300; // 高度
			lp.alpha = 0.95f; // 透明度
			dialogWindow.setAttributes(lp);
			dialog.show();
			myHandler.sendEmptyMessageDelayed(DISSMISS_DIALOG, 3000);

		} else {
			/*
			 * if ("余额不足".equalsIgnoreCase(resultContent)) {
			 * tipsText.setText(moneyShortStr);
			 * tipsImg.setBackgroundResource(R.drawable.sorry); String
			 * subForShow = String.format( getResources().getString(
			 * R.string.purchase_failer_money_less), numStr, subPrice);
			 * subText.setText(subForShow);
			 * totalText.setText(balanceStrForShow);
			 * backmodifyText.setVisibility(View.VISIBLE); } else if
			 * ("库存不足".equalsIgnoreCase(resultContent)) {
			 * tipsText.setText(stockShortStr);
			 * tipsImg.setBackgroundResource(R.drawable.sorry);
			 * totalText.setText(balanceStrForShow);
			 * subText.setText(totalSubForShow);
			 * backmodifyText.setVisibility(View.VISIBLE); } else if
			 * ("有未收货商品，不允许再购买".equalsIgnoreCase(resultContent)) {
			 * tipsText.setText("有未收货商品，不允许再购买");
			 * tipsImg.setBackgroundResource(R.drawable.sorry);
			 * 
			 * totalText.setText(balanceStrForShow);
			 * subText.setText(totalSubForShow);
			 * 
			 * totalText.setVisibility(View.INVISIBLE);
			 * subText.setVisibility(View.INVISIBLE);
			 * backmodifyText.setText("返回");
			 * confirmTitleText.setVisibility(View.INVISIBLE);
			 * carImg.setVisibility(View.INVISIBLE);
			 * backmodifyText.setVisibility(View.VISIBLE); }else if
			 * ("有未收货商品，不允许再购买".equalsIgnoreCase(resultContent)) {
			 * tipsText.setText("有未收货商品，不允许再购买");
			 * tipsImg.setBackgroundResource(R.drawable.sorry);
			 * 
			 * totalText.setText(balanceStrForShow);
			 * subText.setText(totalSubForShow);
			 * 
			 * totalText.setVisibility(View.INVISIBLE);
			 * subText.setVisibility(View.INVISIBLE);
			 * backmodifyText.setText("返回");
			 * confirmTitleText.setVisibility(View.INVISIBLE);
			 * carImg.setVisibility(View.INVISIBLE);
			 * backmodifyText.setVisibility(View.VISIBLE); }
			 */
			tipsText.setText(resultContent);
			tipsImg.setBackgroundResource(R.drawable.sorry);

			totalText.setText(balanceStrForShow);
			subText.setText(totalSubForShow);

			totalText.setVisibility(View.INVISIBLE);
			subText.setVisibility(View.INVISIBLE);
			backmodifyText.setText("返回");
			confirmTitleText.setVisibility(View.INVISIBLE);
			carImg.setVisibility(View.INVISIBLE);
			backmodifyText.setVisibility(View.VISIBLE);
			dialog.setContentView(view);
			Window dialogWindow = dialog.getWindow();
			LayoutParams lp = new LayoutParams();
			// dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
			lp.x = 177; // 新位置X坐标
			lp.y = 65; // 新位置Y坐标
			lp.width = 570; // 宽度
			lp.height = 300; // 高度
			lp.alpha = 0.95f; // 透明度
			dialogWindow.setAttributes(lp);
			dialog.show();
		}
	}

	/*
	 * 用户购买后刷卡后弹出提示窗口 <p>提示用户的信息有6种情况：(1)提示购买信息，当前买了几件商品，总价格
	 * (2)购买成功(3)购买失败，卡内余额不足(4)购买失败，XX商品数量不足(5)购买失败，该卡尚未注册(6)购买失败，未知错误</p>
	 */
	public void showDialogPwd(String reason) {

		// setContentView可以设置为一个View也可以简单地指定资源ID
		LayoutInflater linear = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = linear.inflate(R.layout.receipt_pwd, null);
		final EditText pwdEdit = (EditText) view.findViewById(R.id.pwd_edit);
		TextView pwdErrorText = (TextView) view.findViewById(R.id.pwd_error);
		pwdErrorText.setVisibility(View.INVISIBLE);
		Log.d("jiayy",
				" !TextUtils.isEmpty(reason) = " + !TextUtils.isEmpty(reason));
		if (!TextUtils.isEmpty(reason)) {
			pwdErrorText.setVisibility(View.VISIBLE);
		} else {
			pwdErrorText.setVisibility(View.INVISIBLE);
		}
		TextView confirmBuy = (TextView) view
				.findViewById(R.id.confirm_buy_inpwd);
		confirmBuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// dialog.dismiss();
				mPwd = pwdEdit.getText().toString();
				postReceipt(mPwd);
			}
		});
		dialog.setContentView(view);
		Window dialogWindow = dialog.getWindow();
		LayoutParams lp = new LayoutParams();
		// dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
		lp.x = 177; // 新位置X坐标
		lp.y = 65; // 新位置Y坐标
		lp.width = 570; // 宽度
		lp.height = 300; // 高度
		lp.alpha = 0.95f; // 透明度
		dialogWindow.setAttributes(lp);
		dialog.show();
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		changeBackGround(view);
		switch (id) {
		case R.id.tv_purchase:
			if (mIndex != TAB_PURCHASE) {
				mIndex = TAB_PURCHASE;
				changeTab();
			}
			break;
		case R.id.tv_receipt:
			if (mIndex != TAB_RECEIPT) {
				mIndex = TAB_RECEIPT;
				changeTab();
			}
			break;
		}
	}

	private void changeTab() {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (mIndex) {
		case TAB_PURCHASE:
			if (mPurchaseFragment == null) {
				mPurchaseFragment = new PurchaseFragment(m_accountinfo);
				transaction.add(R.id.content_layout, mPurchaseFragment);
				mPurchaseFragment.setData("", m_prisoner.getID());
			} else {
				loadAccount(m_prisoner.getID());
				transaction.show(mPurchaseFragment);
			}
			// ShowAccountInfo(m_accountinfo);
			break;
		case TAB_RECEIPT:
			if (mReceiptFragment == null) {
				mReceiptFragment = new ReceiptFragment();
				transaction.add(R.id.content_layout, mReceiptFragment);
				mReceiptFragment.setData("", m_prisoner.getID());
			} else {
				loadReceiptData();
				transaction.show(mReceiptFragment);
			}
			break;
		default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		if (mPurchaseFragment != null) {
			transaction.hide(mPurchaseFragment);
		}
		if (mReceiptFragment != null) {
			transaction.hide(mReceiptFragment);
		}
	}
}
