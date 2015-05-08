package com.eoe.application;

import java.io.IOException;

import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.dic.Dictionary;

import com.eoe.store.ContactsInfo;
import com.eoe.store.DatabaseOperation;
import com.eoe.util.DataBaseUtil;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class application extends Application {
	private DatabaseOperation dbOpera = null;
	private SQLiteDatabase db = null;
	private mThread mthread = null;
	private static final int MSG_SUCCESS = 0;
	private static final int MSG_FAILURE = 1;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {// 此方法在ui线程运行
			switch (msg.what) {
			case MSG_SUCCESS:
				dbOpera.setInfo();
				break;

			case MSG_FAILURE:
				break;
			}
		}
	};

	public void onCreate() {
		copyDataBaseToPhone();
		mthread = new mThread();
		mthread.start();

	}

	public DatabaseOperation getDatabaseOperation() {
		dbOpera = new DatabaseOperation(this);
		db = dbOpera.getDatabase();
		return dbOpera;
	}

	public SQLiteDatabase getSQLiteDatabase() {
		dbOpera = new DatabaseOperation(this);
		db = dbOpera.getDatabase();
		return db;
	}

	public class mThread extends Thread {
		public mThread() {
			// TODO Auto-generated constructor stub

		}

		public void run() {
			Configuration cfg = new Configuration();
			cfg.setUseSmart(true);
			Dictionary.getInstance(cfg);
			mHandler.obtainMessage(MSG_SUCCESS).sendToTarget();
		}
	}
	//将res里面的数据库拷到手机上
	private void copyDataBaseToPhone() {  
        DataBaseUtil util = new DataBaseUtil(this);  
        // 判断数据库是否存在  
        boolean dbExist = util.checkDataBase();  
  
        if (dbExist) {  
            Log.i("tag", "The database is exist.");  
        } else {// 不存在就把raw里的数据库写入手机  
            try {  
                util.copyDataBase();  
            } catch (IOException e) {  
                throw new Error("Error copying database");  
            }  
        }  
    }  
}
