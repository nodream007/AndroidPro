package com.ceres.jailmon.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * ���ܣ�������ݿ�+3�ű?���ڴ洢��Ʒ��Ϣ
 */
public class DBOpenHelper extends SQLiteOpenHelper {
	
	private static final String DBNAME = "commodity.db";  //��Ʒ��ݿ⣬���������ű�
	private static final int VERSION = 1;
	private static final String TAG = "DBOpenHelper";
	
	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub  //�½�һ����洢�ֶ�count,����ֶ�count1,count2,count3,��Զֻ��һ����ݣ�ÿ�ζ��Ǹ������
		//�����ֶΣ�Id����ţ���Name����ƣ���Description��˵������ Price��ͼƬ�������ƣ��� Price���۸�
		String sqlDailys = "create table if not exists daily_t (id integer primary key ,name varchar(20),description varchar(64),price double,pic blob,picUrlStr varchar(64));";
		String sqlLabors = "create table if not exists labor_t (id integer primary key ,name varchar(20) not null,description varchar(64),price double not null,pic blob,picUrlStr varchar(64));";
		String sqlFoods  = "create table if not exists food_t (id integer primary key ,name varchar(20) not null,description varchar(64),price double not null,pic blob,picUrlStr varchar(64));";
		//sqlCounts��ṹ˵����id����ţ�dailyCount��laborCount��foodCount:�ֱ��ʶ��Ʒxml���ı仯��flag��������ʱ��������Ŀ��content,��ʱ���������ݣ�#�Ÿ�����colorNum,��ʱ������ɫ�仯����#�Ÿ�������countNum,ֵ��ѭ���Ĵ���;cardNum,Ҫ���͵�ֵ�����Ա���
		String sqlCounts = "create table if not exists count_t (id integer primary key ,dailyCount int,laborCount int,foodCount int,flag int,content text,colorNum text,countNum int,cardNum text,tsflag int);";  //tsflag:����֪ͨ����Ŀ
		db.execSQL(sqlDailys);
		db.execSQL(sqlLabors);
		db.execSQL(sqlFoods);
		db.execSQL(sqlCounts);
		db.execSQL("insert into count_t (id,dailyCount,laborCount,foodCount,flag,colorNum,countNum,tsflag) values (1,0,0,0,0,'',0,0);");//isfirst:1�״Σ�2���״�
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if(newVersion > oldVersion){
			db.execSQL("Drop table if existis daily_t");
			db.execSQL("Drop table if existis labor_t");
			db.execSQL("Drop table if existis food_t");
		}else{
			return;
		}
		onCreate(db);
	}
	
	
	
}













