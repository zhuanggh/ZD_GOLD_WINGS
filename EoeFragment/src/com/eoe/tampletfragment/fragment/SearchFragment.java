package com.eoe.tampletfragment.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.eoe.application.application;
import com.eoe.store.ContactsInfo;
import com.eoe.store.DatabaseOperation;
import com.eoe.tampletfragment.MainActivity;
import com.eoe.tampletfragment.QueryActivity;
import com.eoe.tampletfragment.R;
import com.eoe.tampletfragment.addActivity;
import com.eoe.tampletfragment.contactEditActivity;
import com.eoe.tampletfragment.adapter.ListAdapter;
import com.eoe.tampletfragment.view.TitleView;
import com.eoe.tampletfragment.view.TitleView.OnLeftButtonClickListener;
import com.eoe.tampletfragment.view.TitleView.OnRightButtonClickListener;

/**
 * @author yangyu 功能描述：搜索fragment页面
 */
public class SearchFragment extends Fragment {
	private DatabaseOperation dbOpera = null;
	private SQLiteDatabase db = null;
	private ArrayList<ContactsInfo> list = new ArrayList<ContactsInfo>();
	private ArrayList<ContactsInfo> list_show = new ArrayList<ContactsInfo>();
	private ArrayList<String> type = new ArrayList<String>();
	private View mParent;
	private FragmentActivity mActivity;
	private EditText etSearch;
	private ImageView ivClearText;

	private TitleView mTitle;

	private ListAdapter adapter = null;
	private ListView mListView = null;

	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dbOpera = ((application) getActivity().getApplication())
				.getDatabaseOperation();
		db = dbOpera.getDatabase();
		list.clear();
		list = dbOpera.getAllUser();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_search, container, false);
		ivClearText = (ImageView) view.findViewById(R.id.ivClearText);
		etSearch = (EditText) view.findViewById(R.id.et_search);
		mListView = (ListView) view.findViewById(R.id.list);
		adapter = new ListAdapter(getActivity());
		mListView.setAdapter(adapter);
		mListView.setFocusable(true);
		mListView.setFocusableInTouchMode(true);
		mListView.requestFocus();
		mListView
				.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
					@Override
					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo) {
						menu.setHeaderTitle("菜单");
						menu.add(0, 0, 0, "拨号");
						menu.add(0, 1, 0, "编辑联系人");
						menu.add(0, 2, 0, "删除联系人");
						// TODO Auto-generated method stub
					}
				});

		// 实现单击事件
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mActivity, QueryActivity.class);
				intent.putExtra("key", list_show.get(position).getId());
				mActivity.startActivity(intent);
			}
		});
		initListener();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();

		mTitle = (TitleView) mParent.findViewById(R.id.title);
		mTitle.setTitle(R.string.tab_contact);
		mTitle.setLeftButton(R.string.back, new OnLeftButtonClickListener() {

			@Override
			public void onClick(View button) {
				backHomeFragment();
			}
		});
		mTitle.setRightButton(R.string.add, new OnRightButtonClickListener() {

			@Override
			public void onClick(View button) {
				goHelp();
			}
		});
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		RefreshListView();
		// RefreshListView();
	}

	// 选中菜单Item后触发
	public boolean onContextItemSelected(MenuItem item) {
		// 关键代码在这里 menuInfo.position
		AdapterView.AdapterContextMenuInfo menuInfo;
		menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case 0:
			// 拨号操作
			Uri uri = Uri.parse("tel:"
					+ list_show.get(menuInfo.position).getPhoneNum(0));
			Intent it = new Intent(Intent.ACTION_CALL, uri);
			startActivity(it);
			break;

		case 1:
			// 编辑操作
			Intent intent = new Intent(mActivity, contactEditActivity.class);
			intent.putExtra("id", list_show.get(menuInfo.position).getId());
			mActivity.startActivity(intent);
			break;

		case 2:
			// 删除操作
			dbOpera.delete((list_show.get(menuInfo.position)).getId());
			etSearch.setText(etSearch.getText().toString());
			break;

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}

	private void initListener() {

		/** 娓呴櫎杈撳叆瀛楃 **/
		ivClearText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				etSearch.setText("");
			}
		});
		etSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable e) {

				String content = etSearch.getText().toString();
				list_show.clear();
				type.clear();
				if ("".equals(content)) {
					ivClearText.setVisibility(View.INVISIBLE);
				} else {
					ivClearText.setVisibility(View.VISIBLE);
				}

				if ("".equals(content)) {
					list_show=(ArrayList<ContactsInfo>) list.clone();
					type.clear();
				} else if (content.matches("[0-9]+")) {
					Cursor cursor = db.rawQuery(
							"select * from phone where phonenum like'%"
									+ content + "%'", null);
					for (int i = 0; i < cursor.getCount(); i++) {
						cursor.moveToNext();
						int id = cursor.getInt(0);
						list_show.add(dbOpera.query(id));
						type.add("phonenum" + cursor.getString(1));
					}
					cursor.close();
				}
				// 短拼音和全拼音搜索
				else if (content.matches("[a-zA-Z]+")) {
					Cursor cursor = db.rawQuery(
							"select id from contacts where FirstpinYin like'%"
									+ content + "%'", null);
					for (int i = 0; i < cursor.getCount(); i++) {
						cursor.moveToNext();
						int id = cursor.getInt(0);
						list_show.add(dbOpera.query(id));
						type.add("FirstpinYin");
					}
					cursor.close();
					Cursor cursor1 = db.rawQuery(
							"select id from contacts where pinYin like'%"
									+ content + "%'", null);
					for (int i = 0; i < cursor1.getCount(); i++) {
						cursor1.moveToNext();
						int id = cursor1.getInt(0);
						list_show.add(dbOpera.query(id));
						type.add("pinYin");
					}

					cursor1.close();
				}
				// 搜索名字
				else {
					Cursor cursor = db.rawQuery(
							"select id from contacts where name like'%"
									+ content + "%'", null);// 此时得到的是一个数，而不是表，所以用cursor.getColumnIndex("id"))是错误的
					for (int i = 0; i < cursor.getCount(); i++) {
						cursor.moveToNext();
						int id = cursor.getInt(0);
						list_show.add(dbOpera.query(id));
						type.add("name");
					}
					cursor.close();
				}
				if (!"".equals(content)) {
					search_address();
					search_remarks();
				}
				adapter.setList(list_show);
				adapter.setType(type);
				adapter.setSearchKeys(content);
				adapter.notifyDataSetChanged();
			}
		});

	}

	public static SearchFragment newInstance(int index) {
		SearchFragment f = new SearchFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	public void search_address() {
		String text = etSearch.getText().toString();
		// 查询地址
		Cursor cursor = db.rawQuery(
				"select sourceid from vir_address where value match'" + text
						+ "'", null);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToNext();
			int id = cursor.getInt(0);
			list_show.add(dbOpera.query(id));
			type.add("address");
		}
		cursor.close();
	}

	public void search_remarks() {
		String text = etSearch.getText().toString();
		// 查询地址
		Cursor cursor = db.rawQuery(
				"select sourceid from vir_remarks where value match'" + text
						+ "'", null);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToNext();
			int id = cursor.getInt(0);
			list_show.add(dbOpera.query(id));
			type.add("remarks");
		}
		cursor.close();
	}

	/**
	 * 返回到首页
	 */
	private void backHomeFragment() {
		getFragmentManager().beginTransaction()
				.hide(MainActivity.mFragments[1])
				.show(MainActivity.mFragments[0]).commit();
		FragmentIndicator.setIndicator(0);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
		}
	}

	private void goHelp() {
		Intent intent = new Intent(mActivity, addActivity.class);
		startActivity(intent);
	}

	// refresh ListView列表
	public void RefreshListView() {
		//ArrayList<E>作为一个类容器，list_show=list并不会创建新对象给list_show，而是给list的地址给它
		list_show = (ArrayList<ContactsInfo>) list.clone();
		type.clear();
		adapter.setList(list_show);
		adapter.setType(type);
		adapter.setSearchKeys(null);
		adapter.notifyDataSetChanged();
	}
}
