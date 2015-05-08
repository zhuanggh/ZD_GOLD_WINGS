package com.eoe.tampletfragment;

import com.eoe.application.application;
import com.eoe.store.ContactsInfo;
import com.eoe.store.DatabaseOperation;
import com.eoe.tampletfragment.addActivity.SaveOnClickListener;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
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
 * @author yangyu ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Activityï¿½ï¿½ï¿½ï¿½
 */
public class contactEditActivity extends FragmentActivity {
	private DatabaseOperation dbOpera = null;
	private SQLiteDatabase db = null;
	private EditText name;
	private EditText phonenum;
	private EditText address;
	private EditText remarks;
	private Context context;
	private ContactsInfo user;
	private Button save=null;
	private int index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit);
		context = this;
		dbOpera = ((application) getApplication()).getDatabaseOperation();
		db = dbOpera.getDatabase();
		name = (EditText) findViewById(R.id.query_name);
		phonenum = (EditText) findViewById(R.id.query_phonenum);
		address = (EditText) findViewById(R.id.query_address);
		remarks = (EditText) findViewById(R.id.query_remarks);
		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new SaveOnClickListener());
		Intent intent=getIntent();
		index=intent.getIntExtra("id", -1);
		if(index!=-1){
			user=dbOpera.query(index);
			init();
		}
	}


	public void init(){
		name.setText(user.getName());
		phonenum.setText(user.getPhoneNum(0));
		address.setText(user.getAddress());
		remarks.setText(user.getRemark());
	}
	
	public class SaveOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ContactsInfo user = new ContactsInfo();
			if (name.getText().length() == 0) {
				Builder builder = new Builder(context); // thisÊÇµ±Ç°Context
				builder.setTitle("ÏûÏ¢");
				builder.setMessage("²éÑ¯ÁªÏµÈËÊ§°Ü£¬ÇëÈ·¶¨ÊÇ·ñÊäÈëÐÕÃû£¡ \n");
				builder.show();
			} else {
				user.setName(name.getText().toString());
				user.setPhoneNum(phonenum.getText().toString());
				user.setAddress(address.getText().toString());
				user.setRemark(remarks.getText().toString());
				dbOpera.modify(index,user);
				contactEditActivity.this.finish();
			}

		}
	}
}
