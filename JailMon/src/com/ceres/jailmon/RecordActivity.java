package com.ceres.jailmon;

import java.io.IOException;

import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.data.Account;
import com.ceres.jailmon.data.Cell;
import com.ceres.jailmon.data.CellList;
import com.ceres.jailmon.data.Prisoner;
import com.ceres.jailmon.data.PrisonerList;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
/**
 * 监所谈话录音功能-主Activity
 * @author zhounan1
 *
 */
public class RecordActivity extends BaseActivity {

	private static final String LOG_TAG = "MediaRecord";
	private LinearLayout m_layoutRooms, m_layoutPrisoners, m_layoutRecordContent;
	private ListView m_listViewRoom, m_listViewPrisoner;
	private CellList m_celllist = null;
	private PrisonerList m_prisonerlist = null;
	private Prisoner m_prisoner = null;
	private CustomAdapter_MainList mAdapter;
	// 语音文件保存路径
	private String FileName = null;
	// 界面控件
	private TextView startRecord;
	private TextView startPlay;
	private TextView stopRecord;
	private Button stopPlay;
	// 语音操作对象
	private MediaPlayer mPlayer = null;
	private MediaRecorder mRecorder = null;
	
	private AnimationUtil m_aniset;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
		// 开始录音
		startRecord = (TextView) findViewById(R.id.startRecord);
		// 绑定监听器
		startRecord.setOnClickListener(new startRecordListener());

		// 结束录音
		stopRecord = (TextView) findViewById(R.id.stopRecord);
		stopRecord.setOnClickListener(new stopRecordListener());

		// 开始播放
		startPlay = (TextView) findViewById(R.id.startPlay);
		// 绑定监听器
		startPlay.setOnClickListener(new startPlayListener());

		// 结束播放
//		stopPlay = (Button) findViewById(R.id.stopPlay);
//		stopPlay.setOnClickListener(new stopPlayListener());

		// 设置sdcard的路径
		FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		FileName += "/audiorecordtest.3gp";
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

				if (m_prisoner != null) {
					m_layoutRecordContent.setVisibility(View.VISIBLE);
				}
			}

		});
	}
	/**
	 * 向云端获取人员列表
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
