package com.eoe.tampletfragment;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.eoe.application.application;
import com.eoe.store.ContactsInfo;
import com.eoe.store.DatabaseOperation;
import com.eoe.tampletfragment.view.TitleView;
import com.eoe.tampletfragment.view.TitleView.OnRightButtonClickListener;

/**
 * @author yangyu ��������������Activity����
 */
public class QueryActivity extends FragmentActivity {
	private DatabaseOperation dbOpera = null;
	private SQLiteDatabase db = null;
	private TextView name;
	private TextView phonenum;
	private TextView address;
	private TextView remarks;
	private Context context;
	private ContactsInfo user;
	private Button dial;
	private TitleView title;
	private int index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_query);
		context = this;
		dbOpera = ((application) getApplication()).getDatabaseOperation();
		db = dbOpera.getDatabase();
		name = (TextView) findViewById(R.id.query_name);
		phonenum = (TextView) findViewById(R.id.query_phonenum);
		address = (TextView) findViewById(R.id.query_address);
		remarks = (TextView) findViewById(R.id.query_remarks);
		title = (TitleView) findViewById(R.id.query_title);
		title.setTitle("联系人");
		title.setRightButton("编辑", new OnRightButtonClickListener() {
			@Override
			public void onClick(View button) {
				goEdit();
			}
		});
		
		dial = (Button)findViewById(R.id.dial_button);
		dial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("tel:"+ phonenum.getText().toString());
				Intent it = new Intent(Intent.ACTION_CALL, uri);
				startActivity(it);	
			}
		});
		Intent intent=getIntent();
		index=intent.getIntExtra("key", -1);
		if(index!=-1){
			user=dbOpera.query(index);
			init();
		}
	}

	private void goEdit() {
		Intent intent = new Intent(this, contactEditActivity.class);
		intent.putExtra("id", index);
		startActivity(intent);
	}
	public void init(){
		name.setText(user.getName());
		phonenum.setText(user.getPhoneNum(0));
		address.setText(user.getAddress());
		remarks.setText(user.getRemark());
	}
}
