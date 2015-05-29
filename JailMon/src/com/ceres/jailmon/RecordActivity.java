package com.ceres.jailmon;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.data.Cell;
import com.ceres.jailmon.data.CellList;
import com.ceres.jailmon.data.Prisoner;
import com.ceres.jailmon.data.PrisonerList;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * 监所谈话录音功能-主Activity
 * 
 * @author zhounan1
 * 
 */
public class RecordActivity extends BaseActivity implements OnClickListener,
		OnCompletionListener, OnErrorListener {

	private static final String LOG_TAG = "MediaRecord";
	private LinearLayout m_layoutRooms, m_layoutPrisoners,
			m_layoutRecordContent;
	private ListView m_listViewRoom, m_listViewPrisoner, m_listViewRecord;
	private CellList m_celllist = null;
	private PrisonerList m_prisonerlist = null;
	private Prisoner m_prisoner = null;
	private List<String> recordFileitems = new ArrayList<String>();// 存放名称
	private CustomAdapter_MainList mAdapter;
	private ArrayAdapter<String> mRecordadapter;
	// 语音文件保存路径
	private String mFilename = null;
	private static final String SAMPLE_DEFAULT_DIR = "/sound_recorder";
	// 界面控件
	private TextView newRecord;
	private TextView startRecord;
	private TextView startPlay;
	private TextView stopRecord;
	private TextView pausePlay;
	private TextView delRecord;
	private TextView recordAni;
	private Chronometer chronometer = null;
	// 语音操作对象
	private MediaPlayer mPlayer = null;
	private MediaRecorder mRecorder = null;
	private long mLastClickTime;
	private int mLastButtonId;
	// operation started

	private File mSampleDir = null;
	private AnimationUtil m_aniset;

	public static final int IDLE_STATE = 0;

	public static final int RECORDING_STATE = 1;

	public static final int PLAYING_STATE = 2;

	public static final int PLAYING_PAUSED_STATE = 3;

	private int mState = IDLE_STATE;
	// 保存当前点击的人员id
	private String currentPersonId;
	
	private boolean ifChanged = false;


	private final Handler mHandler = new Handler();
	// 记录中间图片变换的位置
	int recordAniBgPos = 0;

	private View oldView;

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (recordAniBgPos <= 4) {
				// recordAni.setBackgroundResource(recordAniBg[recordAniBgPos]);
				recordAni.setBackgroundResource(getResId("record"
						+ recordAniBgPos));

				recordAniBgPos++;
			} else {
				recordAniBgPos = 0;
			}
			// 要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
			mHandler.postDelayed(this, 500);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		m_aniset = new AnimationUtil(this);
		m_layoutRooms = (LinearLayout) findViewById(R.id.layoutPurRooms);
		m_layoutPrisoners = (LinearLayout) findViewById(R.id.layoutPurPrisoners);
		m_layoutRecordContent = (LinearLayout) findViewById(R.id.record_content);

		initRoomListView();
		// 读取监室列表
		loadCellList();
		initPrisonerListView();
		initBackButton(R.id.buttonBack);
		initRecordView();
		File sampleDir = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + SAMPLE_DEFAULT_DIR);
		if (!sampleDir.exists()) {
			sampleDir.mkdirs();
		}
		mSampleDir = sampleDir;

		// 设置sdcard的路径
		// FileName =
		// Environment.getExternalStorageDirectory().getAbsolutePath();
		// FileName += "/audiorecordtest.3gp";
	}

	private void initRecordView() {
		// 新增录音
		newRecord = (TextView) findViewById(R.id.newRecord);
		newRecord.setOnClickListener(this);
		// 开始录音
		startRecord = (TextView) findViewById(R.id.startRecord);
		startRecord.setOnClickListener(this);
		// 结束录音
		stopRecord = (TextView) findViewById(R.id.stopRecord);
		stopRecord.setOnClickListener(this);
		// 开始播放
		startPlay = (TextView) findViewById(R.id.startPlay);
		startPlay.setOnClickListener(this);
		// 暂停播放
		pausePlay = (TextView) findViewById(R.id.pausePlay);
		pausePlay.setOnClickListener(this);
		// 删除播放
		delRecord = (TextView) findViewById(R.id.deleteRecord);
		delRecord.setOnClickListener(this);

		chronometer = (Chronometer) findViewById(R.id.chronometer);
		recordAni = (TextView) findViewById(R.id.record_animation);

		mLastClickTime = 0;
		mLastButtonId = 0;

	}

	/**
	 * 初始化监室列表界面
	 */
	public void initRoomListView() {
		m_listViewRoom = (ListView) findViewById(R.id.lstRooms);

		m_listViewRoom.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				if (m_celllist != null) {
					loadPrisoners(m_celllist.getCell(arg2));
				}
				mAdapter.setPosition(arg2);
			}

		});
	}

	/**
	 * 初始化人员界面
	 */
	private void initPrisonerListView() {
		m_listViewPrisoner = (ListView) findViewById(R.id.lstPrisoners);

		m_listViewPrisoner.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				m_prisoner = m_prisonerlist.m_list.get((int) arg2);
				currentPersonId = m_prisoner.getID();
				if (m_prisoner != null) {
					m_layoutRecordContent.setVisibility(View.VISIBLE);
					initRecordHistory();
				}
			}

		});
	}

	/**
	 * 初始化录音历史文件
	 */
	private void initRecordHistory() {
		m_listViewRecord = (ListView) findViewById(R.id.list_record);
		// recordFileitems
		if (mSampleDir != null) {
			subRecordName();
			mRecordadapter = new ArrayAdapter<String>(RecordActivity.this,
					R.layout.record_list_item, R.id.record_history_text,
					recordFileitems);
			m_listViewRecord.setAdapter(mRecordadapter);
			m_listViewRecord.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int arg2, long arg3) {
					view.setSelected(true);

					if (oldView == null) {
						view.findViewById(R.id.record_history_play)
								.setBackgroundResource(R.drawable.audition_4);
						oldView = view;
					} else if (oldView != null && oldView == view) {

						return;
					} else if (oldView != view) {
						oldView.findViewById(R.id.record_history_play)
								.setBackgroundResource(R.drawable.audition);
						view.findViewById(R.id.record_history_play)
								.setBackgroundResource(R.drawable.audition_4);
						oldView = view;
					}
					stopPlay();
					mPlayer = new MediaPlayer();
					ifChanged = true;
					try {
						mPlayer.setDataSource(mSampleDir.getAbsolutePath()
								+ "/" + recordFileitems.get(arg2) + "_"
								+ currentPersonId + ".3gp");
						mPlayer.setOnCompletionListener(RecordActivity.this);
						mPlayer.prepare();
						mPlayer.start();
						mState = PLAYING_STATE;
						mHandler.postDelayed(runnable, 500);
						updataUI();
					} catch (IOException e) {
						Log.e(LOG_TAG, "播放失败");
					}
				}
			});
		}
	}

	/**
	 * 截取录音文件名
	 */
	private void subRecordName() {
		File[] files = mSampleDir.listFiles();
		if (files != null) {
			int count = files.length;// 文件个数
			recordFileitems.clear();
			for (int i = count - 1; i >= 0; i--) {
				File file = files[i];
				if (currentPersonId != null
						&& currentPersonId != ""
						&& file.getName().split("_")[1]
								.contains(currentPersonId)) {
					recordFileitems.add(file.getName().split("_")[0]);
				}
			}
		}
	}

	/**
	 * 向云端获取人员列表
	 * 
	 * @param cell
	 */
	private void loadPrisoners(Cell cell) {
		if (cell != null && cell.getID() != null) {
			m_layoutPrisoners.setVisibility(View.GONE);
			showProcessDialog();
			getPrisonerList(m_basehandler, cell.getID());
		}
	}

	/**
	 * 向云端获取监室列表
	 */
	private void loadCellList() {
		m_layoutRooms.setVisibility(View.GONE);
		m_layoutPrisoners.setVisibility(View.GONE);
		showProcessDialog();
		getCellList(m_basehandler);
	}

	/**
	 * 接收云端返回的监室列表
	 */
	protected void onReceiveCellList(CellList infolist) {
		closeProcessDialog();

		m_celllist = infolist;

		if (m_celllist == null)
			return;

		mAdapter = new CustomAdapter_MainList(RecordActivity.this,
				m_celllist.getList(), R.drawable.list_icon_home);

		m_listViewRoom.setAdapter(mAdapter);

		m_layoutRooms.setVisibility(View.VISIBLE);
		m_layoutRooms.startAnimation(m_aniset.m_aniFadeIn);

	}

	/**
	 * 接收云端返回的人员列表
	 */
	protected void onReceivePrisonerList(final PrisonerList infolist) {

		closeProcessDialog();

		m_prisonerlist = infolist;

		if (infolist == null)
			return;

		ArrayAdapter adapter = new ArrayAdapter(RecordActivity.this,
				R.layout.custom_list_item_sub, R.id.list_item_1_text,
				infolist.m_list);

		m_listViewPrisoner.setAdapter(adapter);
		m_layoutPrisoners.setVisibility(View.VISIBLE);
		m_layoutPrisoners.startAnimation(m_aniset.m_AniSlideIn);

	}

	@SuppressLint("SimpleDateFormat")
	public String getCurrentTime() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}

	public int getResId(String name) {
		int resID = getResources().getIdentifier(name, "drawable",
				"com.ceres.jailmon");
		return resID;
	}

	/**
	 * 更新视图
	 */
	public void updataUI() {
		switch (mState) {
		case IDLE_STATE:
			// 将计时器清零
			chronometer.setBase(SystemClock.elapsedRealtime());
			chronometer.stop();
			startRecord.setVisibility(View.VISIBLE);
			stopRecord.setVisibility(View.GONE);
			startPlay.setVisibility(View.GONE);
			pausePlay.setVisibility(View.GONE);
			newRecord.setEnabled(false);
			delRecord.setEnabled(false);
			break;
		case RECORDING_STATE:
			chronometer.setBase(SystemClock.elapsedRealtime());
			// 开始计时
			chronometer.start();
			startRecord.setVisibility(View.GONE);
			stopRecord.setVisibility(View.VISIBLE);
			startPlay.setVisibility(View.GONE);
			pausePlay.setVisibility(View.GONE);
			newRecord.setEnabled(false);
			delRecord.setEnabled(false);
			break;
		case PLAYING_STATE:
			if (ifChanged) {
				chronometer.setBase(SystemClock.elapsedRealtime());
			}
			chronometer.start();
			startRecord.setVisibility(View.GONE);
			stopRecord.setVisibility(View.GONE);
			startPlay.setVisibility(View.GONE);
			pausePlay.setVisibility(View.VISIBLE);
			newRecord.setEnabled(true);
			delRecord.setEnabled(false);
			break;
		case PLAYING_PAUSED_STATE:
			chronometer.stop();
			startRecord.setVisibility(View.GONE);
			stopRecord.setVisibility(View.GONE);
			startPlay.setVisibility(View.VISIBLE);
			pausePlay.setVisibility(View.GONE);
			newRecord.setEnabled(true);
			delRecord.setEnabled(true);
			break;
		}
	}

	/*
	 * Make sure we're not recording music playing in the background, ask the
	 * MediaPlaybackService to pause playback.
	 */
	private void stopAudioPlayback() {
		// Shamelessly copied from MediaPlaybackService.java, which
		// should be public, but isn't.
		Intent i = new Intent("com.android.music.musicservicecommand");
		i.putExtra("command", "pause");

		sendBroadcast(i);
	}

	@Override
	public void onClick(View button) {
		if (System.currentTimeMillis() - mLastClickTime < 300) {
			// in order to avoid user click bottom too quickly
			return;
		}

		if (!button.isEnabled())
			return;

		if (button.getId() == mLastButtonId && button.getId() != R.id.newRecord) {
			// as the recorder state is async with the UI
			// we need to avoid launching the duplicated action
			return;
		}

		if (button.getId() == R.id.stopRecord
				&& System.currentTimeMillis() - mLastClickTime < 1500) {
			// it seems that the media recorder is not robust enough
			// sometime it crashes when stop recording right after starting
			return;
		}

		mLastClickTime = System.currentTimeMillis();
		mLastButtonId = button.getId();

		switch (button.getId()) {
		case R.id.newRecord:
			if (mRecorder != null) {
				mRecorder.reset();
			}
			if (oldView != null) {
				oldView.findViewById(R.id.record_history_play)
				.setBackgroundResource(R.drawable.audition);
				stopPlay();
			}
			mState = IDLE_STATE;
			break;
		case R.id.startRecord:
			startRecording();
			break;
		case R.id.stopRecord:
			stopRecording();
			break;
		case R.id.startPlay:
			startPlay(playProgress(), mFilename);
			break;
		case R.id.pausePlay:
			pausePlay();
			break;
		case R.id.deleteRecord:

			break;
		}
		updataUI();
		// case R.id.deleteButton:
		// showDeleteConfirmDialog();
		// break;

	}

	private void startRecording() {
		stopAudioPlayback();
		mRecorder = new MediaRecorder();
		// 第1步：设置音频来源（MIC表示麦克风）
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// 第2步：设置音频输出格式（默认的输出格式）
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		// 第3步：设置音频编码方式（默认的编码方式）
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mFilename = mSampleDir.getAbsolutePath() + "/" + getCurrentTime() + "_"
				+ currentPersonId + ".3gp";
		// 第4步：指定音频输出文件
		mRecorder.setOutputFile(mFilename);

		try {
			// 第5步：调用prepare方法
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
		// 第6步：调用start方法开始录音
		mRecorder.start();
		mState = RECORDING_STATE;
		mHandler.postDelayed(runnable, 500);
	}

	private void stopRecording() {
		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
		}
		mState = IDLE_STATE;
		subRecordName();
		mRecordadapter.notifyDataSetChanged();
		// initRecordHistory();
		if (mHandler != null) {
			mHandler.removeCallbacks(runnable);
			recordAni.setBackgroundResource(R.drawable.record0);
		}
	}

	private void startPlay(float percentage, String path) {
		if (mState == PLAYING_PAUSED_STATE && mPlayer != null) {
//			mSampleStart = System.currentTimeMillis()
//					- mPlayer.getCurrentPosition();
			mPlayer.seekTo((int) (percentage * mPlayer.getDuration()));
			mPlayer.start();
			mState = PLAYING_STATE;
		} else {
			stopRecording();
			stopPlay();
			ifChanged = true;
			mPlayer = new MediaPlayer();
			try {
				mPlayer.setDataSource(path);
				mPlayer.setOnCompletionListener(this);
				mPlayer.setOnErrorListener(this);
				mPlayer.prepare();
				mPlayer.seekTo((int) (percentage * mPlayer.getDuration()));
				mPlayer.start();
			} catch (IllegalArgumentException e) {
				mPlayer = null;
				return;
			} catch (IOException e) {
				mPlayer = null;
				return;
			}
			if (mHandler != null) {
				mHandler.postDelayed(runnable, 500);
			}
//			mSampleStart = System.currentTimeMillis();
			mState = PLAYING_STATE;
		}
	}

	private void pausePlay() {
		if (mPlayer == null)
			return;
		mPlayer.pause();
		mState = PLAYING_PAUSED_STATE;
		if (mHandler != null) {
			mHandler.removeCallbacks(runnable);
			recordAni.setBackgroundResource(R.drawable.record0);
		}
	}

	public void stopPlay() {
		if (mPlayer == null)
			return;
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
		mState = IDLE_STATE;
		if (mHandler != null) {
			mHandler.removeCallbacks(runnable);
			recordAni.setBackgroundResource(R.drawable.record0);
		}
	}

	public float playProgress() {
		if (mPlayer != null) {
			return ((float) mPlayer.getCurrentPosition())
					/ mPlayer.getDuration();
		}
		return 0.0f;
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		stopPlay();
		mState = PLAYING_PAUSED_STATE;
		updataUI();
		if (mHandler != null) {
			mHandler.removeCallbacks(runnable);
			recordAni.setBackgroundResource(R.drawable.record0);
		}
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}
}
