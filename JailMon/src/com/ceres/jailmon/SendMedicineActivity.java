package com.ceres.jailmon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceres.jailmon.adapter.CustomAdapter_MedRoundSendMedcineTotalPatient;
import com.ceres.jailmon.data.MedcineResult;
import com.ceres.jailmon.data.MedicinePeopleInfo;
import com.ceres.jailmon.data.SendMedicineData;
import com.ceres.jailmon.fragment.SendMedFragment;
import com.ceres.jailmon.fragment.SendMedHealthPrisonFragment;
import com.ceres.jailmon.fragment.SendMedHistoryPrisonFragment;

public class SendMedicineActivity extends BaseFragmentActivity implements
		OnClickListener {
	private FragmentManager mFragmentManager;
	private static final int TAB_HISTORY = 0;
	private static final int TAB_HEALTH = 1;
	private static final int TAB_SEND_MED = 2;
	private int mIndex = TAB_HISTORY;
	private SendMedHistoryPrisonFragment mSendMedHistoryPrisonFragment;
	private SendMedHealthPrisonFragment mSendMedHealthPrisonFragment;
	private SendMedFragment mSendMedFragment;
	private String mCellId;
	private String mPId;
	public static final String MED_HISTORY_PATIENT = "patient";
	private TextView mNameText;
	private TextView mAgeText;
	private TextView mRoomText;
	private TextView mCreateTimeText;
	private TextView mCreateReasonText;
	private ImageView mPersonImgView;
	private RelativeLayout fragmentLayout;
	private Button mOkBtn;
	private static final int TAB_TOTAL_SITUATION = 0;
	public static final int TAB_ROUNDS_SEND_MED = 1;
	public static final int TAB_SEND_MED_HISTORY_IN_PRISON = 2;
	public static final int TAB_SEND_MED_HISTORY_OUT_PRISON = 3;
	public static final int TAB_HEALTH_HISTORY = 4;
	public static final int HDL_GET_PERSON_BITMAP = 0;
	private List<SendMedicineData> mSendMedicineList;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HDL_GET_PERSON_BITMAP:
				Bitmap bitmap = (Bitmap) msg.obj;
				mPersonImgView.setImageBitmap(bitmap);
				break;

			default:
				break;
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_medicine);
		initView();
		loadSendMedPersonInfo();
	}

	private void loadSendMedPersonInfo() {
		getSendMedPersonInfo(m_basehandler, mPId, mCellId);
	}

	private void initView() {
		mFragmentManager = getSupportFragmentManager();
		TextView historyText = (TextView) findViewById(R.id.person_history);
		TextView healthText = (TextView) findViewById(R.id.person_health);
		TextView sendMedicineText = (TextView) findViewById(R.id.medicie_detail);
		/*
		 * TextView totolSituationText = (TextView)
		 * findViewById(R.id.total_situation_in_send_med); TextView
		 * roundsSendMedText = (TextView)
		 * findViewById(R.id.rounds_send_med_in_send_med); TextView
		 * sendMedHistoryInPrison = (TextView)
		 * findViewById(R.id.send_med_history_in_prison_in_send_med); TextView
		 * sendMedHistoryOutPrison = (TextView)
		 * findViewById(R.id.send_med_history_out_prison_in_send_med); TextView
		 * healthHistory = (TextView)
		 * findViewById(R.id.health_history_in_send_med);
		 */
		/*
		 * totolSituationText.setOnClickListener(this);
		 * roundsSendMedText.setOnClickListener(this);
		 * sendMedHistoryInPrison.setOnClickListener(this);
		 * sendMedHistoryOutPrison.setOnClickListener(this);
		 * healthHistory.setOnClickListener(this);
		 * roundsSendMedText.setBackgroundResource(R.drawable.menu0_down);
		 */
		historyText.setOnClickListener(this);
		healthText.setOnClickListener(this);
		sendMedicineText.setOnClickListener(this);
		// initBackButton(R.id.buttonBack);
		mNameText = (TextView) findViewById(R.id.people_name_info);
		mAgeText = (TextView) findViewById(R.id.people_age_info);
		mRoomText = (TextView) findViewById(R.id.people_room_info);
		mCreateTimeText = (TextView) findViewById(R.id.create_time_info);
		mCreateReasonText = (TextView) findViewById(R.id.create_reason_info);
		mPersonImgView = (ImageView) findViewById(R.id.people_base_info_img);
		fragmentLayout = (RelativeLayout) findViewById(R.id.history_relative);
		initOkButton(R.id.buttonOK);
		initBackButton(R.id.buttonBack);
		Intent intent = getIntent();
		mCellId = intent
				.getStringExtra(CustomAdapter_MedRoundSendMedcineTotalPatient.CELL_ID);
		mPId = intent
				.getStringExtra(CustomAdapter_MedRoundSendMedcineTotalPatient.PERSON_ID);
		getPrisonserPhoto(m_basehandler, mPId);
		changeTab();
	}

	protected void initBackButton(int resid) {
		Button btnBack = (Button) findViewById(resid);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				// resumeMedicineActivity(TAB_ROUNDS_SEND_MED);
			}

		});
	}

	protected void initOkButton(int resid) {
		mOkBtn = (Button) findViewById(resid);
		mOkBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSendMedicineList = mSendMedFragment.getSendMedicine();
				for (SendMedicineData data : mSendMedicineList) {
					if (data.getSelected() && Integer.valueOf(data.getMedNum()) > 0) {
						showMedicineInfoListDialog();
						break;
					}else {
						Toast.makeText(SendMedicineActivity.this, "请先选中药品并且选择药品数量",
								Toast.LENGTH_SHORT).show();
						break;
					}
				}
				
				//submitMedicineData();
			}

		});
	}

	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		return formatter.format(new Date());
	}
	/**
	 * 确认药品信息后弹出购买药品清单dialog
	 * @param position
	 */
	public void showMedicineInfoListDialog() {
		
		final Dialog dialog = new Dialog(this, R.style.dialog);
		dialog.setContentView(R.layout.medicine_info_list_dialog);
		ListView mListView = (ListView) dialog.findViewById(R.id.medicine_info_list);
		mListView.setAdapter(new MdeicineInfoListAdapter());
		((Button) dialog.findViewById(R.id.med_ok)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				submitMedicineData();
				dialog.cancel();
			}
		});
		((Button) dialog.findViewById(R.id.med_cancel)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.cancel();
				
			}
		});
		Window dialogWindow = dialog.getWindow();
		LayoutParams lp = new LayoutParams();
		// dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
		lp.x = 0; // 新位置X坐标
		lp.y = 10; // 新位置Y坐标
		lp.width = 800; // 宽度
		lp.height = 480; // 高度
		lp.alpha = 0.96f; // 透明度
		dialogWindow.setAttributes(lp);
		dialog.show();
	}
	private void submitMedicineData() {
		// 患者Id patientId 监室Id cId，时间 createTime，用药说明
		// medichineNotice，备注说明remark，发药医生doctor
		mSendMedicineList = mSendMedFragment.getSendMedicine();
		String userId = "";
		String currentTime = getDate();
		List<SendMedicineData> sendMedicineList = new ArrayList<SendMedicineData>();
		for (SendMedicineData data : mSendMedicineList) {
			if (data.getSelected()) {
				sendMedicineList.add(data);
			}
		}
		submitMedicineData(m_basehandler, mPId, mCellId, currentTime,
				sendMedicineList, userId);
	}

	private void changeTab() {

		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (mIndex) {
		case TAB_HISTORY:
			fragmentLayout.setBackgroundResource(R.drawable.content_bg7);
			mOkBtn.setVisibility(View.INVISIBLE);

			if (mSendMedHistoryPrisonFragment == null) {
				mSendMedHistoryPrisonFragment = new SendMedHistoryPrisonFragment(
						mCellId, MED_HISTORY_PATIENT, mPId);
				transaction.add(R.id.send_medicine_content_layout,
						mSendMedHistoryPrisonFragment);
			} else {
				transaction.show(mSendMedHistoryPrisonFragment);
			}
			break;
			//入所健康情况
		case TAB_HEALTH:
			fragmentLayout.setBackgroundResource(R.drawable.content_bg8);
			mOkBtn.setVisibility(View.INVISIBLE);
			if (mSendMedHealthPrisonFragment == null) {
				mSendMedHealthPrisonFragment = new SendMedHealthPrisonFragment(
						mCellId, MED_HISTORY_PATIENT, mPId);
				transaction.add(R.id.send_medicine_content_layout,
						mSendMedHealthPrisonFragment);
			} else {
				transaction.show(mSendMedHealthPrisonFragment);
			}
			break;
		case TAB_SEND_MED:
			mOkBtn.setVisibility(View.VISIBLE);
			fragmentLayout.setBackgroundResource(R.drawable.content_bg9);
			if (mSendMedFragment == null) {
				mSendMedFragment = new SendMedFragment(mCellId, mPId);
				transaction.add(R.id.send_medicine_content_layout,
						mSendMedFragment);
			} else {
				transaction.show(mSendMedFragment);
			}
			break;
		default:
			break;
		}

		transaction.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			// resumeMedicineActivity(TAB_ROUNDS_SEND_MED);
		}

		return false;

	}

	private void resumeMedicineActivity(int index) {
		Intent intent = new Intent(this, MedicineActivity.class);
		intent.putExtra("index", index);
		startActivity(intent);
	}

	private void hideFragment(FragmentTransaction transaction) {
		if (mSendMedHistoryPrisonFragment != null) {
			transaction.hide(mSendMedHistoryPrisonFragment);
		}
		if (mSendMedHealthPrisonFragment != null) {
			transaction.hide(mSendMedHealthPrisonFragment);
		}
		if (mSendMedFragment != null) {
			transaction.hide(mSendMedFragment);
		}

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int id = view.getId();
		switch (id) {
		case R.id.person_history:
			if (mIndex != TAB_HISTORY) {
				mIndex = TAB_HISTORY;
				changeTab();
			}
			break;
		case R.id.person_health:
			if (mIndex != TAB_HEALTH) {
				mIndex = TAB_HEALTH;
				changeTab();
			}
			break;
		case R.id.medicie_detail:
			if (mIndex != TAB_SEND_MED) {
				mIndex = TAB_SEND_MED;
				changeTab();
			}
			break;
		/*
		 * case R.id.total_situation_in_send_med:
		 * resumeMedicineActivity(TAB_TOTAL_SITUATION); break; case
		 * R.id.rounds_send_med_in_send_med:
		 * resumeMedicineActivity(TAB_ROUNDS_SEND_MED); break; case
		 * R.id.send_med_history_in_prison_in_send_med:
		 * resumeMedicineActivity(TAB_SEND_MED_HISTORY_IN_PRISON); break; case
		 * R.id.send_med_history_out_prison_in_send_med:
		 * resumeMedicineActivity(TAB_SEND_MED_HISTORY_OUT_PRISON); break; case
		 * R.id.health_history_in_send_med:
		 * resumeMedicineActivity(TAB_HEALTH_HISTORY); break;
		 */
		default:
			break;
		}
	}

	protected void onReceiveMedSendMedPeopleInfo(MedicinePeopleInfo info) {
		String name = info.getName();
		String age = info.getAge();
		String room = info.getRoom();
		String createTime = info.getCreateTime();
		String reason = info.getReason();
		String pid = info.getpId();
		// final String picUrl = info.getPicUrl();
		mNameText.setText(name);
		mAgeText.setText(age);
		mRoomText.setText(room);
		mCreateTimeText.setText(createTime);
		mCreateReasonText.setText(reason);
		/*
		 * try { new Runnable() {
		 * 
		 * @Override public void run() { Bitmap personBitmap; try { personBitmap
		 * = BitmapUtil.getImage( SendMedicineActivity.this, picUrl); Message
		 * msg = mHandler.obtainMessage( HDL_GET_PERSON_BITMAP, personBitmap);
		 * mHandler.sendMessage(msg); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } };
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
	}

	protected void onReceiveMedSendMedResult(MedcineResult info) {
		if (info != null) {
			String result = info.getResult();
			if ("ok".equalsIgnoreCase(result)) {
				Toast.makeText(this, "配药成功", 3000).show();
				mSendMedFragment.loadMedicineData();
			}
		}

	}

	protected void onReceivePrisonerPhoto(Bitmap bitmap) {
		mPersonImgView.setImageBitmap(bitmap);
	}
	private class MdeicineInfoListAdapter extends BaseAdapter {
		List<SendMedicineData> sendMedicineList;
		private MdeicineInfoListAdapter() {
			mSendMedicineList = mSendMedFragment.getSendMedicine();
			sendMedicineList = new ArrayList<SendMedicineData>();
			for (SendMedicineData data : mSendMedicineList) {
				if (data.getSelected()) {
					sendMedicineList.add(data);
				}
			}
		}

		@Override
		public int getCount() {
			return sendMedicineList.size();
		}

		@Override
		public SendMedicineData getItem(int arg0) {
			return sendMedicineList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			SendMedicineData medicinListData = getItem(arg0);
			if(medicinListData == null) {
				return null;
			}
			ViewHolder holder;
			if(arg1 == null) {
				arg1 = LayoutInflater.from(SendMedicineActivity.this).inflate(R.layout.medicine_info_list_item, null);
				holder = new ViewHolder();
				holder.mMedNameView = (TextView) arg1.findViewById(R.id.medicine_name);
				holder.mMedNumView = (TextView) arg1.findViewById(R.id.medicine_num);
				holder.mMedUnitView = (TextView) arg1.findViewById(R.id.medicine_unit);
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}
			holder.mMedNameView.setText(medicinListData.getMedName());
			holder.mMedNumView.setText(medicinListData.getMedNum());
			holder.mMedUnitView.setText(medicinListData.getMedUnit());
			return arg1;
		}
		
	}
	private static class ViewHolder {
		TextView mMedNameView;
		TextView mMedNumView;
		TextView mMedUnitView;
	}
}
