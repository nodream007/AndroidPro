package com.ceres.jailmon.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ceres.jailmon.AppContext;
import com.ceres.jailmon.R;
import com.ceres.jailmon.SendMedicineActivity;
import com.ceres.jailmon.adapter.CustomAdapter_MedHistory;
import com.ceres.jailmon.adapter.CustomAdapter_MedOutHistory;
import com.ceres.jailmon.data.MedRoundSendMedicineList;
import com.ceres.jailmon.data.MedicineHistory;
import com.ceres.jailmon.data.MedicinePatientInfo;
import com.ceres.jailmon.data.MedicinePatientOutInfo;

@SuppressLint({ "ValidFragment", "NewApi" })
public class SendMedOutHistoryPrisonFragment extends BaseFragment {

	private View mContentView;
	ListView mSendMedHistoryList;
	private TextView mBeginDateText;
	private TextView mEndDateText;
	private String mCid, mMedType;
	private int year, monthOfYear, dayOfMonth;
	private String mPId;

	public SendMedOutHistoryPrisonFragment(String roomId, String medType,
			String peopleId) {
		mCid = roomId;
		mMedType = medType;
		mPId = peopleId;
	}

	public SendMedOutHistoryPrisonFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContentView = LayoutInflater.from(getActivity()).inflate(
				R.layout.med_send_medicine_in_history_fragment, null);
		LinearLayout datePickerLinear = (LinearLayout) mContentView
				.findViewById(R.id.history_date_picker);
		LinearLayout historyLinear = (LinearLayout) mContentView
				.findViewById(R.id.histroy_linear_bg);
		ImageView historyListDividerImg = (ImageView) mContentView
				.findViewById(R.id.history_top_divider);
		if (SendMedicineActivity.MED_HISTORY_PATIENT.equals(mMedType)) {
			datePickerLinear.setVisibility(View.GONE);
			historyListDividerImg.setVisibility(View.GONE);
			// historyLinear.setBackgroundResource(R.drawable.content_bg5);
			// historyRelative.setBackgroundResource(R.drawable.content_bg5);
		} else {
			historyLinear.setBackgroundResource(R.drawable.list_box_left1);
			historyListDividerImg.setVisibility(View.VISIBLE);
		}
		m_AppContext = (AppContext) this.getActivity().getApplication();
		initSendMedHistoryViews(mContentView);
		loadSendMedHistoryList();
		return mContentView;
	}

	private void initSendMedHistoryViews(View mContentView) {
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		monthOfYear = calendar.get(Calendar.MONTH);
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		mSendMedHistoryList = (ListView) mContentView
				.findViewById(R.id.send_med_in_history_list);
		/*
		 * if(SendMedicineActivity.MED_HISTORY_PATIENT.equals(mMedType)){
		 * mSendMedHistoryList.setBackgroundResource(R.drawable.content_bg4); }
		 */
		mBeginDateText = (TextView) mContentView
				.findViewById(R.id.send_med_start_time_in_history);
		mEndDateText = (TextView) mContentView
				.findViewById(R.id.send_med_end_time_in_history);
		mBeginDateText.setText(year + "/" + (monthOfYear + 1) + "/"
				+ dayOfMonth);
		mEndDateText.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
		mBeginDateText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				/**
				 * 实例化一个DatePickerDialog的对象
				 * 第二个参数是一个DatePickerDialog.OnDateSetListener匿名内部类
				 * ，当用户选择好日期点击done会调用里面的onDateSet方法
				 */
				DatePickerDialog datePickerDialog = new DatePickerDialog(
						getActivity(),
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								mBeginDateText.setText(year + "/"
										+ (monthOfYear + 1) + "/" + dayOfMonth);
								Log.d("jiayy", "loadSendMedHistoryList start");
								loadSendMedHistoryList();
							}
						}, year, monthOfYear, dayOfMonth);

				datePickerDialog.show();
			}
		});
		mEndDateText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				/**
				 * 实例化一个DatePickerDialog的对象
				 * 第二个参数是一个DatePickerDialog.OnDateSetListener匿名内部类
				 * ，当用户选择好日期点击done会调用里面的onDateSet方法
				 */
				DatePickerDialog datePickerDialog = new DatePickerDialog(
						getActivity(),
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								mEndDateText.setText(year + "/"
										+ (monthOfYear + 1) + "/" + dayOfMonth);
								Log.d("jiayy", "loadSendMedHistoryList end");
								loadSendMedHistoryList();
							}
						}, year, monthOfYear, dayOfMonth);

				datePickerDialog.show();
			}
		});
	}

	public void loadSendMedHistoryList() {
		String startDateStr = mBeginDateText.getText().toString();
		String endDateStr = mEndDateText.getText().toString();
		getSendMedHistoryOutList(m_basehandler, mCid, mPId, startDateStr,
				endDateStr, mMedType);
	}

	protected void onReceiveMedicineHistoryList(MedicineHistory medicineHistory) {
		List<MedicinePatientOutInfo> medicinePatientOutInfoList = new ArrayList<MedicinePatientOutInfo>();
		medicinePatientOutInfoList = medicineHistory.getMedicinePatientOutInfo();
		CustomAdapter_MedOutHistory historyAdapter = new CustomAdapter_MedOutHistory(
				getActivity(), medicinePatientOutInfoList);
		mSendMedHistoryList.setAdapter(historyAdapter);
		historyAdapter.notifyDataSetChanged();
	}
	public void setCellId(String cellId) {
		mCid = cellId;
	}
}
