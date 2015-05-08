package com.eoe.tampletfragment.fragment;

import java.util.jar.Attributes.Name;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.eoe.tampletfragment.R;
import com.eoe.tampletfragment.view.TitleView;
import com.eoe.tampletfragment.view.TitleView.OnLeftButtonClickListener;
import com.eoe.tampletfragment.view.TitleView.OnRightButtonClickListener;
import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;

/**
 * @author yangyu ��������������fragmentҳ��
 */
public class SettingsFragment extends Fragment {

	private View mParent;

	private FragmentActivity mActivity;

	private TitleView mTitle;

	private ImageButton mImageButton;

	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static SettingsFragment newInstance(int index) {
		SettingsFragment f = new SettingsFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view;
		int ncount = 0;
		// ��ȡ���ݿ������Ϣ�������ݿ�������ݣ�����ò���fragment_settings,������ò���fragment_myinfo_input.

		if (ncount == 0) {
			view = inflater.inflate(R.layout.fragment_settings, container,
					false);

		} else {
			view = inflater.inflate(R.layout.fragment_settings, container,
					false);
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();

		mTitle = (TitleView) mParent.findViewById(R.id.title);
		mTitle.setTitle(R.string.tab_personal);
		mTitle.setLeftButton(R.string.back, new OnLeftButtonClickListener() {

			@Override
			public void onClick(View button) {
			}
		});
		mTitle.hiddenLeftButton();
		mTitle.setRightButton(R.string.help, new OnRightButtonClickListener() {

			@Override
			public void onClick(View button) {
			}
		});
		mImageButton = (ImageButton) mParent.findViewById(R.id.qrcode);
		mImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goQRcode();
			}
		});

	}

	private void goQRcode() {
		String nameString="陈生水";
		try {
			Bitmap qrcode = EncodingHandler.createQRCode(nameString, 500);
			ImageView img = new ImageView(mActivity);
			img.setImageBitmap(qrcode);
			new AlertDialog.Builder(mActivity)
			.setTitle(nameString+"的二维码")
			.setView(img)
			.setPositiveButton("退出", null)
			.show();
			
		} catch (WriterException e) {
		
			e.printStackTrace();
		}
		
		
	}

}
