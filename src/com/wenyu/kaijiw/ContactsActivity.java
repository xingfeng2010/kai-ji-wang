package com.wenyu.kaijiw;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wenyu.kjw.adapter.ClearEditText;
import com.wenyu.kjw.adapter.PinyinComparator;
import com.wenyu.kjw.adapter.SideBar;
import com.wenyu.kjw.adapter.SortAdapter;

public class ContactsActivity extends Activity {
	// data
	private List<String> mSortCustomUserIDsList;

	// 根据拼音来排列ListView里面的数据类
	private PinyinComparator mPinyinComparator;

	// ui
	private SortAdapter mAdapter;
	private ListView mListView;
	public ClearEditText mClearEditText;
	private SideBar mSideBar;
	private TextView mDialog;
	private TextView mEmptyTextView;
	private LinearLayout mLoadingLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.fragment_contacts);
	}
	
}
