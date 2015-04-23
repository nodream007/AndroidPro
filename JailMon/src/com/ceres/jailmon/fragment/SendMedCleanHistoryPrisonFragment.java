package com.ceres.jailmon.fragment;

import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ceres.jailmon.AppContext;
import com.ceres.jailmon.R;
import com.ceres.jailmon.SendMedicineActivity;
import com.ceres.jailmon.adapter.CustomAdapter_MedCleanHistory;
import com.ceres.jailmon.data.MedRoundSendMedicineList;
import com.ceres.jailmon.data.MedicineCleanHistory;
import com.ceres.jailmon.data.MedicineCleanInfo;

@SuppressLint({ "ValidFragment", "NewApi" })
public class SendMedCleanHistoryPrisonFragment extends BaseFragment {

	private View mContentView;
	private MedRoundSendMedicineList mMedRoundSendMedicineList = null;
	ListView mSendMedHistoryList;
	private TextView mBeginDateText;
	private TextView mEndDateText;
	private String mCid, mMedType;
	private String mStartDateStr;
	private String mEndDateStr;
	private int year, monthOfYear, dayOfMonth, hourOfDay, minute;
	private Button btn;

	public SendMedCleanHistoryPrisonFragment(String roomId, String medType) {
		mCid = roomId;
		mMedType = medType;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContentView = LayoutInflater.from(getActivity()).inflate(
				R.layout.med_clean_history_fragment, null);
		LinearLayout datePickerLinear = (LinearLayout) mContentView
				.findViewById(R.id.history_date_picker);
		LinearLayout historyLinear = (LinearLayout) mContentView
				.findViewById(R.id.histroy_linear);
		m_AppContext = (AppContext) this.getActivity().getApplication();
		initSendMedHistoryViews(mContentView);
		loadCleanHistoryList();
		return mContentView;
	}

	private void initSendMedHistoryViews(View mContentView) {
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		monthOfYear = calendar.get(Calendar.MONTH);
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		mSendMedHistoryList = (ListView) mContentView
				.findViewById(R.id.send_med_clean_history_list);
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
								loadCleanHistoryList();
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
								mBeginDateText.setText(year + "/"
										+ (monthOfYear + 1) + "/" + dayOfMonth);
								loadCleanHistoryList();
							}
						}, year, monthOfYear, dayOfMonth);

				datePickerDialog.show();
				loadCleanHistoryList();
			}
		});
		loadCleanHistoryList();
	}

	public void loadCleanHistoryList() {
		String startDateStr = mBeginDateText.getText().toString();
		String endDateStr = mEndDateText.getText().toString();
		getCleanHistoryList(m_basehandler, mCid, null, startDateStr,
				endDateStr, mMedType);
	}

	protected void onReceiveMedicineCleanHistoryList(
			MedicineCleanHistory medicineClean) {

		List<MedicineCleanInfo> cleanList = medicineClean.getCleanInfoList();
		CustomAdapter_MedCleanHistory cleanAdapter = new CustomAdapter_MedCleanHistory(
				getActivity(), cleanList);
		mSendMedHistoryList.setAdapter(cleanAdapter);
		cleanAdapter.notifyDataSetChanged();
	}

	public void setCellId(String cellId) {
		mCid = cellId;
	}
}
