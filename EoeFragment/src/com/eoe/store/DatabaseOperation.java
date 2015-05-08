package com.eoe.store;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class DatabaseOperation {
	private static boolean isload = false; // 静态数据，用于判断分词词典是否加载好了
	private static Stack<ContactsInfo> stack = new Stack();// 存储待插入数据
	private Context context;
	private SQLiteDatabase db;
	private DatabaseHelper database_helper;
	private String name = "contacts.db";

	public DatabaseOperation(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		database_helper = new DatabaseHelper(context, name);
		db = database_helper.getWritableDatabase();// 这里是获得可写的数据库
	}

	public void setInfo() {
		while (!stack.empty()) {
			insert2(stack.pop());
		}
		isload = true;
	}

	public boolean getInfo() {
		return isload;
	}

	public SQLiteDatabase getDatabase() {
		return db;
	}

	public void deleteDatabase() {
		database_helper.deleteDatabase(context, name);
	}

	public void insert(ContactsInfo user) {
		if (isload)
			insert2(user);
		else
			stack.push(user);
	}

	/**
	 * 前提：id和name必须有，才能进行modify操作,id相同保证的和其他表的一致性 作业：输入一个ContactsInfo
	 * user，进行数据库的插入，id不给的话会自增长
	 */
	public void insert2(ContactsInfo user) {
		ContentValues values = new ContentValues();
		// values.put("id", user.getId());
		values.put("name", user.getName());
		if (user.getId() != 0) {
			values.put("id", user.getId());
		}
		if (user.getAddress() != null) {
			values.put("address", user.getAddress());
		}
		if (user.getRemark() != null) {
			values.put("remarks", user.getRemark());
		}
		if (user.getPinYin() != null) {
			values.put("pinYin", user.getPinYin());
		}
		if (user.getFirstPinYin() != null) {
			values.put("FirstpinYin", user.getFirstPinYin());
		}
		db.insert("contacts", null, values); // 好像不能有contentValue的对的值是null
		values.clear();
		if (user.getId() == 0) {// 因为id是由数据库产生，所以插入后再获取最大的id即为刚刚插入user的id
			Cursor cursor = db.rawQuery("select max(id) from contacts", null);// 此时得到的是一个数，而不是表，所以用cursor.getColumnIndex("id"))是错误的
			cursor.moveToFirst();
			user.setId(cursor.getInt(0));// 从0开始计算
			cursor.close();
		}
		// 电话表的插入
		for (int i = 0; i < user.sizeOfPhone(); i++) {
			values.put("id", user.getId());
			values.put("phoneNum", user.getPhoneNum(i));
			db.insert("phone", null, values);
			values.clear();
		}
		// 虚表的插入操作
		if (user.getAddress() != null) {
			String text = user.getAddress();
			// String text="我们的未来我做主，千金难买青葱岁月";
			StringReader reader = new StringReader(text);
			IKSegmenter ik = new IKSegmenter(reader, false);
			Lexeme lexeme = null;
			try {
				while ((lexeme = ik.next()) != null) {
					db.execSQL("insert into vir_address(value,sourceid) values('"
							+ lexeme.getLexemeText()
							+ "',"
							+ user.getId()
							+ ")");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}
		}
		// db.execSQL("insert into vir_name(name,sourceid) values
		// ('"+user.getName()+"',"+user.getId()+")");
		if (user.getRemark() != null) {
			String text = user.getRemark();
			// String text="我们的未来我做主，千金难买青葱岁月";
			StringReader reader = new StringReader(text);
			IKSegmenter ik = new IKSegmenter(reader, false);
			Lexeme lexeme = null;
			try {
				while ((lexeme = ik.next()) != null) {
					db.execSQL("insert into vir_remarks(value,sourceid) values('"
							+ lexeme.getLexemeText()
							+ "',"
							+ user.getId()
							+ ")");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}
		}
	}

	public ContactsInfo query(int id) {
		ContactsInfo user = new ContactsInfo();
		Cursor cursor = db.rawQuery("select * from contacts where id ='" + id
				+ "'", null);
		if (cursor.getCount() != 0) {
			cursor.moveToNext();
			user.setId(id);
			user.setPinYin(cursor.getString(2));
			user.setFirstPinYin(cursor.getString(5));
			user.setName(cursor.getString(1));
			user.setAddress(cursor.getString(3));
			user.setRemark(cursor.getString(4));
			cursor.close();
			Cursor cursor1 = db.rawQuery("select * from phone where id='" + id
					+ "'", null);
			for (int i = 0; i < cursor1.getCount(); i++) {
				cursor1.moveToFirst();
				user.setPhoneNum(cursor1.getString(1));
			}
			cursor1.close();
		}
		return user;
	}

	public void delete(int id) {
		db.execSQL("delete from contacts where id=" + id);
		db.execSQL("delete from phone where id=" + id);
		db.execSQL("delete from label where id=" + id);
		db.execSQL("delete from vir_address where sourceid=" + id);
		db.execSQL("delete from vir_remarks where sourceid=" + id);
	}

	/**
	 * 前提：id和name必须有，才能进行modify操作,id相同保证的和其他表的一致性 输入一个ContactsInfo user，进行数据库的更新
	 */
	public void modify(int id, ContactsInfo user) {
		// 删除旧的数据
		delete(id);
		// id和name必须有，才能进行modify操作,id相同保证的和其他表的一致性
		insert(user);
	}

	/**
	 * 查询id=？的用户，只有id是唯一的主键
	 * 
	 * @param id号
	 * @return ContactsInfo
	 */
	/*
	 * public ArrayList<ContactsInfo> getAllUser() {}
	 */

	/**
	 * 通过一个词查询用户，这个词可以是备注、地址、姓名....
	 * 
	 * @param String
	 * @return id
	 */
	/*
	 * public ArrayList<int> search(String str){}
	 */

	/**
	 * 获得数据库中所有的用户，将每一个用户放到一个map中去，然后再将map放到list里面去返回
	 * 
	 * @param privacy
	 * @return list
	 */
	public ArrayList<ContactsInfo> getAllUser() {
		ArrayList<ContactsInfo> list = new ArrayList<ContactsInfo>();
		// order by pinYin COLLATE LOCALIZED
		//new String[] { "id", "name","pinYin", "address", "remarks", "FirstpinYin" }
		Cursor cursor = db.query("contacts",null , null, null,
				null, null, "pinYin ASC", null);
		while (cursor.moveToNext()) {
			ContactsInfo user = new ContactsInfo();
			user.setId(cursor.getInt(cursor.getColumnIndex("id")));
			user.setPinYin(cursor.getString(cursor.getColumnIndex("pinYin")));
			user.setFirstPinYin(cursor.getString(cursor
					.getColumnIndex("FirstpinYin")));
			user.setName(cursor.getString(cursor.getColumnIndex("name")));
			user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
			user.setRemark(cursor.getString(cursor.getColumnIndex("remarks")));
			Cursor cursor1 = db.query("phone",
					new String[] { "id", "phoneNum" }, "id=?",
					new String[] { String.valueOf(user.getId()) }, null, null,
					null);
			while (cursor1.moveToNext()) {
				user.setPhoneNum(cursor1.getString(cursor1
						.getColumnIndex("phoneNum")));
			}
			cursor1.close();
			list.add(user);
		}
		cursor.close();
		return list;
	}
}
