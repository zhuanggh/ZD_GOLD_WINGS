package com.eoe.tampletfragment;

import com.zxing.activity.CaptureActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class addContactsWay extends FragmentActivity implements OnClickListener {
	private Button addContactByHands;
	private Button addContactByQRcode;

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.activity_addcontacts);
		super.onCreate(arg0);
		addContactByHands = (Button) findViewById(R.id.addcontacts_byHands);
		addContactByQRcode = (Button) findViewById(R.id.addcontacts_byQRcode);
		addContactByHands.setOnClickListener(this);
		addContactByQRcode.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addcontacts_byHands:
			Intent intent = new Intent();
			intent.setClass(this, addActivity.class);
			startActivity(intent);
			break;
		case R.id.addcontacts_byQRcode:

			Intent intent2 = new Intent();
			intent2.setClass(this, addContactsByQRcode.class);
			startActivity(intent2);
			break;
		default:
			break;

		}
	}
	
}