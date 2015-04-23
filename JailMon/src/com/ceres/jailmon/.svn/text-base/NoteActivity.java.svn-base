package com.ceres.jailmon;

import com.ceres.jailmon.data.CommonResult;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends BaseActivity {

	TextView mTvTitle;
	EditText mEditNote;
	Button mButtonOK;
	Spinner mSpinPreset;
	String mPoliceID;
	boolean mDoctorFlag;
	String[] mPreset;
	String[] mPresetPolice = { "快速留言", "平安无事", "一切顺利", "注意防火" };
	String[] mPresetDoctor = { "快速留言", "记得吃药", "及时打针", "不要乱动" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note);

		mTvTitle = (TextView) findViewById(R.id.textTitle);

		mEditNote = (EditText) findViewById(R.id.editNote);

		mButtonOK = (Button) findViewById(R.id.buttonOK);

		mSpinPreset = (Spinner) findViewById(R.id.spinner);

		Bundle bundle = this.getIntent().getExtras();
		
		mPoliceID = bundle.getString("ID");
		
		mDoctorFlag = bundle.getString("Role").equals("Doctor");
		mPreset = mDoctorFlag? mPresetDoctor:mPresetPolice;
		
		String name = bundle.getString("Name");
		mTvTitle.setText("留言给：" + name);

		mButtonOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendNote();
			}
		});

		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		//		android.R.layout.simple_spinner_item, mPreset);
		
		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner_item, R.id.TextView01, mPreset );
		
		
		mSpinPreset.setAdapter(adapter);

		mSpinPreset.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (position != 0) {
					mEditNote.setText(mPreset[position]);
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	
	@Override
	protected void onReceivePoliceNoteResult(CommonResult ret) {
		if( ret.m_bOK )
		{
			Toast.makeText(this, "留言成功！", Toast.LENGTH_LONG).show();
			finish();
		}
		else
			Toast.makeText(this, "留言失败！", Toast.LENGTH_LONG).show();
	}
	
	
	@Override
	protected void onReceiveDoctorNoteResult(CommonResult ret) {
		if( ret.m_bOK )
		{
			Toast.makeText(this, "留言成功！", Toast.LENGTH_LONG).show();
			finish();
		}
		else
			Toast.makeText(this, "留言失败！", Toast.LENGTH_LONG).show();
	}

	private void sendNote()
	{
		String note = mEditNote.getText().toString();
		if( note == null || note.isEmpty() )
		{
			Toast.makeText(this, "请填写留言内容", Toast.LENGTH_LONG).show();
			return;
		}
		
		if( mDoctorFlag )
			postDoctorNote(m_basehandler, mPoliceID, note);
		else
			postPoliceNote(m_basehandler, mPoliceID, note);
	}
}
