package com.ceres.jailmon;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RecordActivity extends BaseActivity {

	private static final String LOG_TAG = "MediaRecord";
	// 语音文件保存路径
	private String FileName = null;
	// 界面控件
	private Button startRecord;
	private Button startPlay;
	private Button stopRecord;
	private Button stopPlay;
	// 语音操作对象
	private MediaPlayer mPlayer = null;
	private MediaRecorder mRecorder = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_recorder);
		// 开始录音
		startRecord = (Button) findViewById(R.id.startRecord);
		// 绑定监听器
		startRecord.setOnClickListener(new startRecordListener());

		// 结束录音
		stopRecord = (Button) findViewById(R.id.stopRecord);
		stopRecord.setOnClickListener(new stopRecordListener());

		// 开始播放
		startPlay = (Button) findViewById(R.id.startPlay);
		// 绑定监听器
		startPlay.setOnClickListener(new startPlayListener());

		// 结束播放
		stopPlay = (Button) findViewById(R.id.stopPlay);
		stopPlay.setOnClickListener(new stopPlayListener());

		// 设置sdcard的路径
		FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		FileName += "/audiorecordtest.3gp";
	}

	// 开始录音
	class startRecordListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mRecorder = new MediaRecorder();
			// 第1步：设置音频来源（MIC表示麦克风）
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			//第2步：设置音频输出格式（默认的输出格式） 
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			//第3步：设置音频编码方式（默认的编码方式） 
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); 
			//第4步：指定音频输出文件
			mRecorder.setOutputFile(FileName);
			
			try {
				//第5步：调用prepare方法   
				mRecorder.prepare();
			} catch (IOException e) {
				Log.e(LOG_TAG, "prepare() failed");
			}
			//第6步：调用start方法开始录音   
			mRecorder.start();
		}

	}

	// 停止录音
	class stopRecordListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
		}

	}

	// 播放录音
	class startPlayListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mPlayer = new MediaPlayer();
			try {
				mPlayer.setDataSource(FileName);
				mPlayer.prepare();
				mPlayer.start();
			} catch (IOException e) {
				Log.e(LOG_TAG, "播放失败");
			}
		}

	}

	// 停止播放录音
	class stopPlayListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mPlayer.release();
			mPlayer = null;
		}

	}
}
