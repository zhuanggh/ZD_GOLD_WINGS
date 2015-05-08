package com.eoe.tampletfragment;

import com.eoe.application.application;
import com.eoe.store.ContactsInfo;
import com.eoe.store.DatabaseOperation;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author yangyu ��������������Activity����
 */
public class addActivity extends FragmentActivity {
	private DatabaseOperation dbOpera = null;
	private SQLiteDatabase db = null;
	private Button save = null;
	private EditText name;
	private EditText phonenum;
	private EditText address;
	private EditText remarks;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addcontacts_byhands);
		context = this;
		dbOpera = ((application) getApplication()).getDatabaseOperation();
		db = dbOpera.getDatabase();
		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new SaveOnClickListener());
		name = (EditText) findViewById(R.id.name);
		phonenum = (EditText) findViewById(R.id.telephone);
		address = (EditText) findViewById(R.id.address);
		remarks = (EditText) findViewById(R.id.more);
	}

	public class SaveOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ContactsInfo user = new ContactsInfo();
			if (name.getText().length() == 0) {
				Builder builder = new Builder(context); // this是当前Context
				builder.setTitle("消息");
				builder.setMessage("查询联系人失败，请确定是否输入姓名！ \n");
				builder.show();
			} else {
				user.setName(name.getText().toString());
				user.setPhoneNum(phonenum.getText().toString());
				user.setAddress(address.getText().toString());
				user.setRemark(remarks.getText().toString());
				dbOpera.insert(user);
				addActivity.this.finish();
			}

		}
	}

}
