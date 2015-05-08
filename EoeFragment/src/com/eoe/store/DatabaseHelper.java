package com.eoe.store;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int  VERSON = 1;
    public DatabaseHelper(Context context, String name, CursorFactory factory, int verson){
        super(context, name, factory, verson);
    }
    public DatabaseHelper(Context context, String name, int verson){
        this(context, name, null, verson);
    }
    public DatabaseHelper(Context context, String name){
        this(context, name, VERSON);
    }
    public boolean deleteDatabase(Context context,String name) {
    	return context.deleteDatabase(name);
    	}

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub
        System.out.println("create a sqlite database");
        //execSQL()Ϊִ�в��������SQL��䣬��˲����е������Ҫ����SQL�﷨,�����Ǵ���һ����
        arg0.execSQL("create table contacts(id integer primary key, name varchar(20),pinYin varchar(20),address varchar(40),remarks varchar(80),FirstpinYin varchar(10))");
        arg0.execSQL("create table phone(id integer, phoneNum varchar(20))");
        arg0.execSQL("create table label(id integer, labelName varchar(20),name varchar(20))");
        //fts4虚拟表
        arg0.execSQL("CREATE VIRTUAL TABLE vir_address USING fts4(value varchar(10),sourceid INTEGER)");
        arg0.execSQL("CREATE VIRTUAL TABLE vir_remarks USING fts4(value varchar(10),sourceid INTEGER)");
        //触发器
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        System.out.println("update a sqlite database");
    }
}