package com.ceres.jailmon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.res.Resources;

/*
 * ˵���������࣬����ݵ���ɾ�Ĳ�
 */
public class GoodsDao 
{
	private static final String DBNAME = "commodity.db";   //��ݿ���ע�Ᵽ��һ��
	private static final int VERSION = 1;
	private static final String TAG = "DBOpenHelper";
	private DBOpenHelper helper;
	private SQLiteDatabase db;
	private Cursor cursor;
	private static final String DAILY_TABLE = "daily_t";
	private static final String LABOR_TABLE = "labor_t";
	private static final String FOOD_TABLE = "food_t";
	private static final String COUNT_TABLE = "count_t";
	
	public GoodsDao(Context context)
	{
		helper = new DBOpenHelper(context, DBNAME, null, VERSION);
		db = helper.getWritableDatabase();
	}
	
	//������� insert into pic_t (name,description,price,num,pic) values ('ssm','very good',12,1,os);
	public void addData(String tablename,Goods data)//�������ͼƬ
	{
	    db.execSQL("insert into "+tablename+"(id,name,description,price,pic,picUrlStr) values(?,?,?,?,?,?)", 
			new Object[]{data.getId(),data.getName(),data.getDescrip(),data.getPrice(),data.getPic(),data.getPicStrUrl()});  
	}
	public void addDailyPic(Goods data)
	{
		/*	db.execSQL("insert into "+DAILY_TABLE+"(pic) values(?)",
		    new Object[]{data.getPic()});    */
		db.execSQL("insert into "+DAILY_TABLE+"(id,name,description,price,pic) values(?,?,?,?,?)",
		    new Object[]{data.getId(),data.getName(),data.getDescrip(),data.getPrice(),data.getPic()});
	}
	
	//��ȡĳ��������ݵ���Ŀ
	public int getTableCount(String tablename)  //Long���� --> int����
	{
	    cursor = db.rawQuery("select count(*) from "+tablename,null);
	    cursor.moveToFirst();
	    int count = cursor.getInt(0);
	    cursor.close();
	    return count;
	}
	
	//���ĳ�����ԣ���ѯ���
	public Cursor queryTableAndShow(String tablename)
	{
		cursor = db.query(DAILY_TABLE, new String[]{"name","description","price","pic"}, null, null, null, null, null);
		return cursor;
	}

	//���ĳ�����ԣ���ѯ���
	public Cursor idQueryTable(String id,String tablename)
	{
		cursor = db.query(tablename, new String[]{"name","description","price","pic"}, "id = ?", new String[]{id}, null, null, null);//idQuery(String _id),���id��ѯ
	    return cursor;
	}
	
	public void addLaborPic(Goods data)
	{
		db.execSQL("insert into "+LABOR_TABLE+"(id,name,description,price,pic) values(?,?,?,?,?)",
		    new Object[]{data.getId(),data.getName(),data.getDescrip(),data.getPrice(),data.getPic()});
	}
	//���ĳ�����ԣ���ѯ���
	public Cursor idQueryLabor(String id)
	{
		cursor = db.query(LABOR_TABLE, new String[]{"name","description","price","pic"}, "id = ?", new String[]{id}, null, null, null);//idQuery(String _id),���id��ѯ
	    return cursor;
	}
	//��ѯ��ݿ�����ݣ���ʾ��������
	public Cursor queryTable(String tableName)
	{
		cursor = db.query(tableName, new String[]{"id","name","description","price","pic","picUrlStr"}, null, null, null, null, null,null);
		return cursor;
	}
	public void addFoodPic(Goods data)
	{
		db.execSQL("insert into "+FOOD_TABLE+"(id,name,description,price,pic) values(?,?,?,?,?)",
		    new Object[]{data.getId(),data.getName(),data.getDescrip(),data.getPrice(),data.getPic()});
	}
	//���ĳ�����ԣ���ѯ���
	public Cursor idQueryFood(String id)
	{
		cursor = db.query(FOOD_TABLE, new String[]{"name","description","price","pic"}, "id = ?", new String[]{id}, null, null, null);//idQuery(String _id),���id��ѯ
		return cursor;
	}

	//������ݡ� update pic_t set name='name' where id = id;
	public void update(String name,int id)
	{
		ContentValues values = new ContentValues();
		values.put("name", name);
		db.update(DAILY_TABLE, values, "id="+id, null);
	}
	
	//update count_t set key = 'value' where id = 1;
	public void updateCount(String key,String value)
	{
		ContentValues values = new ContentValues();
		values.put(key, value);
		db.update(COUNT_TABLE, values, null, null);
	}
	
	//��ȡcount_t���е�ĳ���ֶ�key��ֵ
	public Cursor idQueryCount(String key)
	{
		cursor = db.query(COUNT_TABLE, new String[]{key}, "id = 1", null, null, null, null);
		return cursor;
	}
	
	//���id������ͼƬ
	public void updateDataPic(byte[] pic,int id,String tablename)
	{
		ContentValues values = new ContentValues();
		values.put("pic", pic);
		db.update(tablename, values, "id="+id, null);
	}
	
	//ɾ�����   delete from pic_t where _id = id;
	public void delete(int id)
	{
		db.execSQL("delete from "+DAILY_TABLE+" where id= "+id);
	}
	
	public void close()
	{
		if(!cursor.isClosed()){
			cursor.close();
		}
		if(db != null)
		{
			db.close();
			db = null;
		}
		if(helper != null)
		{
			helper.close();
			helper = null;
		}
	}
	
	public void deleteTable(String tablename)
	{
		db.execSQL("Drop table "+tablename);
	}
	
	public void createTable(String tablename)
	{
		db.execSQL("create table if not exists "+tablename+"(id integer primary key ,name varchar(20),description varchar(64),price double,pic blob,picUrlStr varchar(64));");
	}
}
