package com.ceres.jailmon.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ceres.jailmon.R;
import com.ceres.jailmon.data.Medicine;
import com.ceres.jailmon.data.SendMedicineData;

public class CustomAdapter_MedSendMedicine extends BaseAdapter {

	protected LayoutInflater listContainer;
	protected Context m_context;
	// protected List<Medicine> m_infolist;
	protected List<String> mUnitList;
	private int mPosition = -1;
	private List<SendMedicineData> mMedicineList = new ArrayList<SendMedicineData>();

	static class ListItemView {
		LinearLayout linear;
		CheckBox isChecked;
		TextView medicineNameBtn;
		Spinner unitSpinner;
		LinearLayout subBtn;
		LinearLayout addBtn;
		EditText medNumEdit;
		LinearLayout subLinear;
		Button infoBut;
	};

	public CustomAdapter_MedSendMedicine(Context ctx, List<Medicine> infolist,
			List<String> unitList, String medicineType) {
		listContainer = LayoutInflater.from(ctx);
		m_context = ctx;
		// m_infolist = infolist;

		for (Medicine medicine : infolist) {
			SendMedicineData sendMedicineData = new SendMedicineData();
			sendMedicineData.setMedId(medicine.getId());
			sendMedicineData.setMedNum("0");
			sendMedicineData.setSelected(false);
			sendMedicineData.setMedName(medicine.getName());
			sendMedicineData.setMedType(medicineType);
			Log.d("jiayy", "medicine.getName() = " + medicine.getName());
			mMedicineList.add(sendMedicineData);
		}
		mUnitList = unitList;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final ListItemView listItemView;
		SendMedicineData sendMedicineData = mMedicineList.get(position);
		
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = listContainer.inflate(
					R.layout.med_send_medicine_list, null);
			listItemView.linear = (LinearLayout) convertView
					.findViewById(R.id.medicine_name_id);
			listItemView.medicineNameBtn = (TextView) convertView
					.findViewById(R.id.medicine_name);
			listItemView.unitSpinner = (Spinner) convertView
					.findViewById(R.id.medicine_type_spinner);
			listItemView.subBtn = (LinearLayout) convertView
					.findViewById(R.id.medicine_num_sub_id);
			listItemView.addBtn = (LinearLayout) convertView
					.findViewById(R.id.medicine_num_add_id);
			listItemView.medNumEdit = (EditText) convertView
					.findViewById(R.id.medicine_num_edit);
			listItemView.isChecked = (CheckBox) convertView
					.findViewById(R.id.medicine_checked);
			listItemView.infoBut = (Button)convertView
					.findViewById(R.id.medicine_info_btn);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		
		String medicineName = sendMedicineData.getMedName();
		listItemView.medicineNameBtn.setText(medicineName);
		listItemView.isChecked.setTag(position);
		boolean selectedFlag = sendMedicineData.getSelected();
		// 设置checkbox的点击状态
		listItemView.isChecked.setOnCheckedChangeListener(null);
		listItemView.isChecked.setChecked(selectedFlag);
		listItemView.isChecked
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						int index = (Integer) buttonView.getTag();
						SendMedicineData sendMedicineData = mMedicineList
								.get(index);
						if (isChecked) {
							sendMedicineData.setSelected(true);
						} else {
							sendMedicineData.setSelected(false);
						}

					}
				});
		// .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		// {
		//
		// // @Override
		// // public void onCheckedChanged(CompoundButton buttonView,
		// // boolean isChecked) {
		// // int index = (Integer) buttonView.getTag();
		// // SendMedicineData sendMedicineData = mMedicineList
		// // .get(index);
		// // if (isChecked) {
		// // sendMedicineData.setSelected(true);
		// // } else {
		// // sendMedicineData.setSelected(false);
		// // //sendMedicineData.setMedNum("0");
		// // //sendMedicineData.setMedUnit("粒");
		// // }
		// // notifyDataSetChanged();
		// // }
		//
		// @Override
		// public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		// listItemView.linear.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View view) {
		// int index = (Integer) view.getTag();
		// SendMedicineData sendMedicineData = mMedicineList.get(index);
		// boolean medicineSelected = sendMedicineData.getSelected();
		// if (!medicineSelected) {
		// /*
		// * listItemView.medicineNameBtn
		// * .setBackgroundResource(R.drawable.kx_selected);
		// */
		// sendMedicineData.setSelected(true);
		// } else {
		// /*
		// * listItemView.medicineNameBtn
		// * .setBackgroundResource(R.drawable.send_med_name_bg);
		// */
		// sendMedicineData.setSelected(false);
		// sendMedicineData.setMedNum("0");
		// sendMedicineData.setMedUnit("粒");
		// }
		// notifyDataSetChanged();
		// }
		// });
		listItemView.medNumEdit.setTag(position);
		String medNumStr = sendMedicineData.getMedNum();
		listItemView.medNumEdit.setText(medNumStr);
		listItemView.subBtn.setTag(position);
		// listItemView.subBtn.setTag(R.id.med_num_sub, medNum);
		listItemView.subBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("jiayy", "sub Onclick begin");
				int index = (Integer) view.getTag();
				SendMedicineData sendMedicineData = mMedicineList.get(index);
				String numStr = sendMedicineData.getMedNum();
				int num = Integer.valueOf(numStr);
				boolean selected = sendMedicineData.getSelected();
				if (selected) {
					if (num != 0) {
						num = num - 1;
						sendMedicineData.setMedNum(String.valueOf(num));
						listItemView.medNumEdit.setText(String.valueOf(num));
					}
				} else {
					Toast.makeText(m_context, "请先选中药品后再进行数量增减",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		listItemView.addBtn.setTag(position);
		// listItemView.addBtn.setTag(R.id.med_num_add, medNum);
		listItemView.addBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("jiayy", "add Onclick begin");
				int index = (Integer) view.getTag();
				SendMedicineData sendMedicineData = mMedicineList.get(index);
				boolean selected = sendMedicineData.getSelected();
				if (selected) {
					// int num = (Integer) view.getTag(R.id.med_num_add);
					String numStr = sendMedicineData.getMedNum();
					int num = Integer.valueOf(numStr);
					num = num + 1;
					sendMedicineData.setMedNum(String.valueOf(num));
					Log.d("jiayy", "num = " + num);
					listItemView.medNumEdit.setText(String.valueOf(num));
				} else {
					Toast.makeText(m_context, "请先选中药品后再进行数量增减",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		listItemView.medNumEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {

				int index = (Integer) listItemView.medNumEdit.getTag();
				SendMedicineData sendMedicineData = mMedicineList.get(index);
				boolean selected = sendMedicineData.getSelected();
				if (selected) {
					String num = listItemView.medNumEdit.getText().toString();
					sendMedicineData.setMedNum(num);
					Log.d("jiayy", "notifyDataSetChanged in medicine");
				} /*
				 * else { Log.d("jiayy","afterTextChanged");
				 * listItemView.medNumEdit.setText("0");
				 * Toast.makeText(m_context, "请先点击药品名称选中后再进行数量增减",
				 * Toast.LENGTH_SHORT).show(); }
				 */
			}
		});
		listItemView.infoBut.setTag(position);
		listItemView.infoBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int index = (Integer) listItemView.medNumEdit.getTag();
				SendMedicineData sendMedicineData = mMedicineList.get(index);
				boolean selected = sendMedicineData.getSelected();
				if (selected) {
					showMedicineInstruction(index);
				}else {
					Toast.makeText(m_context, "请先选中药品后再进行用药说明添加",
							Toast.LENGTH_SHORT).show();
				}
				
			}
			
		});
		ArrayAdapter<String> medicineUnitAdapter = new ArrayAdapter<String>(
				m_context, android.R.layout.simple_spinner_dropdown_item,
				mUnitList);
		listItemView.unitSpinner.setAdapter(medicineUnitAdapter);
		listItemView.unitSpinner.setTag(position);
		String unit = sendMedicineData.getMedUnit();
		Log.d("jiayy", "position = " + position + "unit = " + unit);
		for (int i = 0; i < mUnitList.size(); i++) {
			if (mUnitList.get(i).equals(unit)) {
				listItemView.unitSpinner.setSelection(i);
			}
		}

		listItemView.unitSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View view,
							int position, long arg3) {

						if (!mUnitList.isEmpty()) {
							String unit = mUnitList.get(position);
							int index = (Integer) arg0.getTag();
							// listItemView.unitSpinner.setSelection(position,true);
							SendMedicineData sendMedicineData = mMedicineList
									.get(index);
							sendMedicineData.setMedUnit(unit);
							listItemView.unitSpinner.setSelection(position);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});
		return convertView;
	}

	private void expandBtn(View view) {
		int top = 5;
		int bottom = 10;
		int left = 0;
		int right = 0;
		expandViewTouchDelegate(view, top, bottom, left, right);
	}
	/**
	 * 用药说明dialog
	 * @param position
	 */
	public void showMedicineInstruction(final int position) {
		final Dialog dialog = new Dialog(m_context, R.style.dialog);
		dialog.setContentView(R.layout.medicine_instruction_dialog);
		final SendMedicineData sendMedicineData = mMedicineList.get(position);
		if(sendMedicineData != null && sendMedicineData.getMedInstruction() != "") {
			((EditText)dialog.findViewById(R.id.send_med_instruction)).setText(sendMedicineData.getMedInstruction());
		}
		if(sendMedicineData != null && sendMedicineData.getMedRemark() != "") {
			((EditText)dialog.findViewById(R.id.send_med_remark)).setText(sendMedicineData.getMedRemark());
		}
		Window dialogWindow = dialog.getWindow();
		LayoutParams lp = new LayoutParams();
		// dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
//		lp.x = 0; // 新位置X坐标
//		lp.y = 0; // 新位置Y坐标
		lp.width = 600; // 宽度
		lp.height = 400; // 高度
		lp.alpha = 0.97f; // 透明度
		dialogWindow.setAttributes(lp);
		dialog.show();
		Button ok = (Button) dialog.findViewById(R.id.send_med_ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				sendMedicineData.setMedInstruction(
						((EditText)dialog.findViewById(R.id.send_med_instruction)).getText().toString());
				sendMedicineData.setMedRemark(
						((EditText)dialog.findViewById(R.id.send_med_remark)).getText().toString());
				dialog.dismiss();
			}
		});
	}

	/**
	 * 扩大View的触摸和点击响应范围,最大不超过其父View范围
	 * 
	 * @param view
	 * @param top
	 * @param bottom
	 * @param left
	 * @param right
	 */
	public static void expandViewTouchDelegate(final View view, final int top,
			final int bottom, final int left, final int right) {

		((View) view.getParent()).post(new Runnable() {
			@Override
			public void run() {
				Rect bounds = new Rect();
				view.setEnabled(true);
				view.getHitRect(bounds);

				bounds.top -= top;
				bounds.bottom += bottom;
				bounds.left -= left;
				bounds.right += right;

				TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

				if (View.class.isInstance(view.getParent())) {
					((View) view.getParent()).setTouchDelegate(touchDelegate);
				}
			}
		});
	}

	public void setPostion(int position) {
		mPosition = position;
	}

	public final int getCount() {
		return mMedicineList.size();
	}

	public final Object getItem(int position) {
		return mMedicineList.get(position);
	}

	public final long getItemId(int position) {
		return position;
	}

	public List<SendMedicineData> getSendMedicine() {
		return mMedicineList;
	}
}
