package com.eoe.tampletfragment;


import java.io.UnsupportedEncodingException;

import com.eoe.application.application;
import com.eoe.store.ContactsInfo;
import com.eoe.store.DatabaseOperation;
import com.eoe.tampletfragment.addActivity.SaveOnClickListener;
import com.zxing.activity.CaptureActivity;

import android.R.integer;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class addContactsByQRcode extends FragmentActivity {

	private DatabaseOperation dbOpera = null;
	private SQLiteDatabase db = null;
	private Button save = null;
	private EditText name;
	private EditText phonenum;
	private EditText address;
	private EditText remarks;
	private Context  context;
	String myname ="",myphonenum ="",myaddress ="",myremarks = "";
	String Encodingresult;
	@Override
	protected void onCreate(Bundle arg0) {
		Toast.makeText(this, "ɨ���ά�룬���Ҽ���ͨѶ¼", Toast.LENGTH_SHORT).show();
		Intent  startScan = new Intent(addContactsByQRcode.this, CaptureActivity.class);
		startActivityForResult(startScan, 0);		
		super.onCreate(arg0);
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
	   @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	// TODO Auto-generated method stub
	    	super.onActivityResult(requestCode, resultCode, data);
	    	if(resultCode==RESULT_OK){
	    		String result=data.getExtras().getString("result");
	    		//EncodingProcess(result);
	    		QRcodeProcess(result);
	    		name.setText(myname);
	    		phonenum.setText(myphonenum);
	    		address.setText(myaddress);
	    		remarks.setText(myremarks);
	    	}
	    }
	   //���ñ����ʽ
	  /* private void EncodingProcess(String result) {
		   
				try {
					//Encodingresult=new String(result.getBytes("ISO-8859-1"),"UTF-8");
					Encodingresult=new String(result.getBytes("ISO-8859-1"),"GB2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					
	   }*/
	//��ɨ�������Ĵ���
		private void QRcodeProcess(String result) {
			 String path = result;
			 String strs[]=path.split("\\|");
			 myname=strs[0];
			 myphonenum=strs[1];
			 myaddress=strs[2];
			 myremarks=strs[3];
			
	}
		public class SaveOnClickListener implements OnClickListener {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContactsInfo user = new ContactsInfo();
				if (name.getText().length() == 0) {
					Builder builder = new Builder(context); // this�ǵ�ǰContext
					builder.setTitle("��Ϣ");
					builder.setMessage("��ѯ��ϵ��ʧ�ܣ���ȷ���Ƿ����������� \n");
					builder.show();
				} else {
					user.setName(name.getText().toString());
					user.setPhoneNum(phonenum.getText().toString());
					user.setAddress(address.getText().toString());
					user.setRemark(remarks.getText().toString());
					dbOpera.insert(user);
					addContactsByQRcode.this.finish();
				}

			}
		}

}
