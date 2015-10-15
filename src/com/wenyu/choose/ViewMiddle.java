package com.wenyu.choose;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.wenyu.kaijiw.R;
import com.wenyu.kjw.adapter.TextAdapter;

public class ViewMiddle extends LinearLayout implements ViewBaseAction {
	private ListView regionListView;
	private ListView plateListView; 
	private  String[] items;
	private  String[] loction ;
	private ArrayList<String> groups = new ArrayList<String>();
	private ArrayList<String> childrenItem = new ArrayList<String>();
	private SparseArray<ArrayList<String>> children = new SparseArray<ArrayList<String>>();
	private TextAdapter plateListViewAdapter;
	private TextAdapter earaListViewAdapter;
	private OnSelectListener mOnSelectListener;
	private int tEaraPosition = 0;// i
	private int tBlockPosition = 0;// j
	private String showString = "不限";
	private Map<List<String>,ArrayList<String>> mas;
	private ArrayList<String> childrens;
	private ListView mListView;
	private TextAdapter adapter;
	private String mDistance;
	private String showText = "item1";
	public ViewMiddle(Context context) {
		super(context);
		init(context);
	}

	public ViewMiddle(Context context,String[] items) {
		super(context);
		this.items = items;
		inits(context);
	}

	public ListView getPlateListView() {
		return plateListView;
	}

	public void setPlateListView(ListView plateListView) {
		this.plateListView = plateListView;
	}

	public void setRegionListView(ListView regionListView) {
		this.regionListView = regionListView;
	}

	public TextAdapter getPlateListViewAdapter() {
		return plateListViewAdapter;
	}

	public void setPlateListViewAdapter(TextAdapter plateListViewAdapter) {
		this.plateListViewAdapter = plateListViewAdapter;
	}

	public TextAdapter getEaraListViewAdapter() {
		return earaListViewAdapter;
	}

	public void setEaraListViewAdapter(TextAdapter earaListViewAdapter) {
		this.earaListViewAdapter = earaListViewAdapter;
	}

	public ViewMiddle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public ViewMiddle(Context context,Map<List<String>,ArrayList<String>> mas) {
		super(context);
		this.mas = mas;
		init(context);

	}
	public ViewMiddle(Context context, String[] area,String[] loction) {
		super(context);
		this.loction = loction;
		items = area;
		init(context, items);

	}

	private void inits(Context context) {
		//		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_distance, this, true);
		setBackgroundDrawable(getResources().getDrawable(
				R.drawable.choosearea_bg_mid));
		mListView = (ListView) findViewById(R.id.listView);
		adapter = new TextAdapter(context, items, R.drawable.choose_item_right,
				R.drawable.choose_eara_item_selector);
		adapter.setTextSize(17);
		if (mDistance != null) {
			for (int i = 0; i < items.length; i++) {
				if (items[i].equals(mDistance)) {
					adapter.setSelectedPositionNoNotify(i);
					showText = items[i];
					break;
				}
			}
		}
		mListView.setAdapter(adapter);
		adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {

				if (mOnSelectListener != null) {
					showText = items[position];
					//					mOnSelectListener.getValue(showText);
					mOnSelectListener.getValue(items[position],
							items[position]);
				}
			}

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
		});
	}
	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_region, this, true);
		regionListView =((ListView) findViewById(R.id.listView));
		plateListView = (ListView) findViewById(R.id.listView2);
		setBackgroundDrawable(getResources().getDrawable(
				R.drawable.choosearea_bg_left));

		for (Map.Entry<List<String>, ArrayList<String>> mas1:mas.entrySet()) {
			int i = 0;
			groups.addAll(mas1.getKey());
			children.put(tBlockPosition, mas1.getValue());
//						tBlockPosition++;
		}

		earaListViewAdapter = new TextAdapter(context, groups,
				R.drawable.choose_item_selected,
				R.drawable.choose_eara_item_selector);
		earaListViewAdapter.setTextSize(17);
		earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
		getRegionListView().setAdapter(earaListViewAdapter);
		//对左侧listview 数据 的监听
		earaListViewAdapter
		.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				if (position <groups.size()) {
					childrenItem.clear();
					plateListView.setVisibility(view.VISIBLE);
					childrenItem.addAll(children.get(position));
					plateListViewAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			}
		});
		if (tEaraPosition <children.size()) {
			childrenItem.addAll(children.get(tEaraPosition));
			plateListViewAdapter = new TextAdapter(context, childrenItem,
					R.drawable.choose_item_right,
					R.drawable.choose_plate_item_selector);
			plateListViewAdapter.setTextSize(15);
			plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
			plateListView.setAdapter(plateListViewAdapter);
			//对右侧listview 的监听
			plateListViewAdapter
			.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

				@Override
				public void onItemClick(View view, final int position) {

					showString = childrenItem.get(position);
					if (mOnSelectListener != null) {
						mOnSelectListener.getValue(showString);
					}

				}

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

				} 
			});
			if (tBlockPosition < childrenItem.size())
				showString = childrenItem.get(tBlockPosition);
		}
		setDefaultSelect();

	}

	public void updateShowText(String showArea, String showBlock) {
		if (showArea == null || showBlock == null) {
			return;
		}
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(showArea)) {
				earaListViewAdapter.setSelectedPosition(i);
				childrenItem.clear();
				if (i < children.size()) {
					childrenItem.addAll(children.get(i));
				}
				tEaraPosition = i;
				break;
			}
		}
		for (int j = 0; j < childrenItem.size(); j++) {
			if (childrenItem.get(j).replace("涓嶉檺", "").equals(showBlock.trim())) {
				plateListViewAdapter.setSelectedPosition(j);
				tBlockPosition = j;
				break;
			}
		}
		setDefaultSelect();
	}
	private void init(Context context, String[] area) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_region, this, true);
		regionListView=((ListView) findViewById(R.id.listView));
		plateListView = (ListView) findViewById(R.id.listView2);
		setBackgroundDrawable(getResources().getDrawable(
				R.drawable.choosearea_bg_left));

		for (int i = 0; i < area.length; i++) {
			int p =0;
			groups.add(area[i]);
			ArrayList<String> titem = new ArrayList<String>();
			for (int j = 0; j < loction.length; j++) {
				{
					titem.add(loction[j]);
				}
				children.put(j, titem);
			}
		}

		earaListViewAdapter = new TextAdapter(context, groups,
				R.drawable.choose_item_selected,
				R.drawable.choose_eara_item_selector);
		earaListViewAdapter.setTextSize(17);
		earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
		getRegionListView().setAdapter(earaListViewAdapter);
		earaListViewAdapter
		.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				showString = groups.get(position);
				if (mOnSelectListener != null) {
					plateListView.setVisibility(view.INVISIBLE);
					mOnSelectListener.getValue(showString);
				}
				if (position == children.indexOfKey(position)) {
					childrenItem.clear();
					plateListView.setVisibility(view.VISIBLE);
					childrenItem.addAll(children.get(position));
					plateListViewAdapter.notifyDataSetChanged();

				}

			}

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

			}
		});
		if (tEaraPosition == children.indexOfKey(tEaraPosition)) {
			childrenItem.addAll(children.get(tEaraPosition));
			plateListViewAdapter = new TextAdapter(context, childrenItem,
					R.drawable.choose_item_right,
					R.drawable.choose_plate_item_selector);
			plateListViewAdapter.setTextSize(15);
			plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
			plateListView.setAdapter(plateListViewAdapter);
			plateListViewAdapter
			.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

				@Override
				public void onItemClick(View view, final int position) {

					showString = childrenItem.get(position);
					if (mOnSelectListener != null) {
						mOnSelectListener.getValue(showString);
					}

				}

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

				}
			});
			if (tBlockPosition < childrenItem.size())
				showString = childrenItem.get(tBlockPosition);
			if (showString.contains("不限")) {
				showString = showString.replace("不限", "");
			}
		}
		setDefaultSelect();

	}

	public void setDefaultSelect() {
		regionListView.setSelection(tEaraPosition);
		plateListView.setSelection(tBlockPosition);
	}

	public String getShowText(List<String> area,int i) {
		if(area.size()<mas.size()){
			showString=area.get(i);
		}else{
			showString ="0";
		}
		return showString;
	}
	public String getShowText(String[] area,int i) {
		return showString=area[i];
	}
	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String showText);
		public void getValue(String distance, String showText);
	}

	public void setOnSelectListeners(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	@Override
	public void hide() {

	}

	@Override
	public void show() {

	}

	public ListView getRegionListView() {
		return regionListView;
	}


}
