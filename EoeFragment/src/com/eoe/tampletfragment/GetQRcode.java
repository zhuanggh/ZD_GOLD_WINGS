package com.eoe.tampletfragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.eoe.tampletfragment.R;
import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;

public class GetQRcode extends FragmentActivity {

	private ImageView qrcodeImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_qrcode);

		qrcodeImg = (ImageView) findViewById(R.id.img_qrcode);

		String nameString = "�ҽг���ˮ";
		if (nameString.equals("")) {
			Toast.makeText(this, "���ݿ�û���ҵ����ݣ�������д�ҵ���Ϣ������ٲ鿴�ҵĶ�ά��",
					Toast.LENGTH_LONG).show();
		} else {
			try {
				Bitmap qrcode = EncodingHandler.createQRCode(nameString, 400);
				qrcodeImg.setImageBitmap(qrcode);
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
