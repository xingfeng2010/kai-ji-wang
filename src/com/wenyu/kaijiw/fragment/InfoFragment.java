package com.wenyu.kaijiw.fragment;

import imsdk.data.IMSDK.OnDataChangedListener;
import imsdk.data.localchatmessagehistory.IMChatMessage;
import imsdk.data.localchatmessagehistory.IMMyselfLocalChatMessageHistory;
import imsdk.data.mainphoto.IMSDKMainPhoto;
import imsdk.data.mainphoto.IMSDKMainPhoto.OnBitmapRequestProgressListener;
import imsdk.data.nickname.IMSDKNickname;
import imsdk.data.recentcontacts.IMMyselfRecentContacts;
import imsdk.views.IMEmotionTextView;

import java.util.ArrayList;
import java.util.List;

import com.wenyu.Data.UserMessage;
import com.wenyu.Utils.BadgeView;
import com.wenyu.Utils.CommonUtil;
import com.wenyu.Utils.DateUtil;
import com.wenyu.kaijiw.IMChatActivitys;
import com.wenyu.kaijiw.MainActivity;
import com.wenyu.kaijiw.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class InfoFragment extends  Fragment {
	// data
	public boolean mShowingGroupMessage;
	private ListView mListView;
	private View mEmptyView;
	private TextView frendlist;
	private MessageListAdapter mAdapter;
	private List<UserMessage> userMessages;
    
	/**
	 * 初始化未读信息
	 */
	public void initData(){
		userMessages = new ArrayList<UserMessage>();
	    List<String> userLists = IMMyselfRecentContacts.getUsersList();
	    UserMessage userMessage = null;
		for(int i = 0 ; i < userLists.size(); i++){
			userMessage = new UserMessage();
			userMessage.setCustomUserID(String.valueOf(userLists.get(i)));
			
			IMChatMessage chatMessage = IMMyselfLocalChatMessageHistory
					.getLastChatMessage(String.valueOf(userLists.get(i)));
			if(!"".equals(chatMessage)){
			userMessage.setLastMessageContent(chatMessage.getText());
			}
			userMessage.setLastMessageTime(DateUtil
					.getTimeBylong(chatMessage.getServerSendTime() * 1000));
			userMessage.setUnreadChatMessageCount(IMMyselfRecentContacts
					.getUnreadChatMessageCount(String.valueOf(userLists.get(i))));
			userMessage.setNickname(IMSDKNickname.get(String.valueOf(userLists.get(i))));
			userMessage.setBitmap(IMSDKMainPhoto.get(String.valueOf(userLists.get(i))));
			userMessages.add(userMessage);
			
//			System.out.println("唯独的信息"+userMessages);
		 }
	    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_messages, container, false);
		initData();
		mAdapter = new MessageListAdapter();
		mListView = (ListView) view.findViewById(R.id.messages_listview);
		mEmptyView = view.findViewById(R.id.messages_empty_textview);
//		frendlist = (TextView) view.findViewById(R.id.message_right);
		mListView.setEmptyView(mEmptyView);
//		frendlist.setOnClickListener(ol);
		mListView.setAdapter(mAdapter);
        
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
//			Toast.makeText(getActivity(), position, 1000).show();
				String customUserID = IMMyselfRecentContacts.getUser(position);
				if("".equals(customUserID)){
					   Toast.makeText(getActivity(), "没有聊天记录", Toast.LENGTH_SHORT).show();
				      return;
				}
				Intent intent = new Intent(getActivity(), IMChatActivitys.class);
				intent.putExtra("CustomUserID", customUserID);
				startActivity(intent);

				IMMyselfRecentContacts.clearUnreadChatMessage(customUserID);

        		int unreadMessageCount = (int) IMMyselfRecentContacts
						.getUnreadChatMessageCount();

				
				initData();
				mAdapter.notifyDataSetChanged();
			}
		});
		IMMyselfRecentContacts.setOnDataChangedListener(new OnDataChangedListener() {
			@Override
			public void onDataChanged() {
				initData();
				mAdapter.notifyDataSetChanged();

			}
		});
	
		return view;
	}
	OnClickListener ol = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		 switch (v.getId()) {
//		case R.id.message_right:
//			 Intent it = new Intent(getActivity(), ContactsActivity.class);
//			 startActivity(it);
//			break;

		default:
			break;
		}	
		}
	};
	private class MessageListAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return userMessages.size();
		}

		@Override
		public Object getItem(int position) {
			return userMessages.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final UserMessage userMessage = userMessages.get(position);
			ItemViewHolder itemViewHolder = null;

			if (convertView == null) {
				itemViewHolder = new ItemViewHolder();
				convertView = MainActivity.sSingleton.mInflater.inflate(
						R.layout.item_user, parent, false);
				itemViewHolder.mContactNameTextView = (TextView) convertView
						.findViewById(R.id.item_user_name_textview);
				itemViewHolder.mContactImageView = (ImageView) convertView
						.findViewById(R.id.item_user_mainphoto_imageview);
				itemViewHolder.mContactInfoEmotionTextView = (IMEmotionTextView) convertView
						.findViewById(R.id.item_user_otherinfo_textview);
				itemViewHolder.mContactTimeTextView = (TextView) convertView
						.findViewById(R.id.item_user_time_textview);
				itemViewHolder.mBadgeView = (BadgeView) convertView
						.findViewById(R.id.item_user_badgeview);

				convertView.setTag(itemViewHolder);
			} else {
				itemViewHolder = (ItemViewHolder) convertView.getTag();
			}

			// 如果存在新的消息，则设置BadgeView
			if (userMessage.getUnreadChatMessageCount() > 0) {
				itemViewHolder.mBadgeView.setVisibility(View.VISIBLE);
				itemViewHolder.mBadgeView.setBadgeCount((int) userMessage.getUnreadChatMessageCount());
			} else {
				if (itemViewHolder.mBadgeView != null) {
					itemViewHolder.mBadgeView.setVisibility(View.INVISIBLE);
				}
			}
			if(CommonUtil.isNull(userMessage.getNickname())){
				itemViewHolder.mContactNameTextView.setText(userMessage.getCustomUserID());	
				itemViewHolder.mContactNameTextView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
				itemViewHolder.mContactNameTextView.setMaxEms(7);
				itemViewHolder.mContactNameTextView.setSingleLine(true);
			}else{
				itemViewHolder.mContactNameTextView.setText(userMessage.getNickname());
				itemViewHolder.mContactNameTextView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
				itemViewHolder.mContactNameTextView.setMaxEms(7);
				itemViewHolder.mContactNameTextView.setSingleLine(true);
			}

			itemViewHolder.mContactTimeTextView.setText(userMessage.getLastMessageTime());
			itemViewHolder.mContactInfoEmotionTextView.setStaticEmotionText(userMessage.getLastMessageContent());

			if (userMessage.getBitmap() != null) {
				itemViewHolder.mContactImageView.setImageBitmap(userMessage.getBitmap());
			} else {
				itemViewHolder.mContactImageView
						.setImageResource(R.drawable.filmonimg);
			}

			final ImageView contactImageView = itemViewHolder.mContactImageView;
			final TextView contactNameTextView = itemViewHolder.mContactNameTextView;

			IMSDKMainPhoto.request(userMessage.getCustomUserID(), 20,
					new OnBitmapRequestProgressListener() {
						@Override
						public void onSuccess(Bitmap bitmap, byte[] buffer) {
							if (bitmap != null) {
								contactImageView.setImageBitmap(bitmap);
							} else {
								contactImageView
										.setImageResource(R.drawable.filmonimg);
							}
							//头像更新后，昵称也会同步更新
							String nickname_new =  IMSDKNickname.get(userMessage.getCustomUserID());
							if(!CommonUtil.isNull(nickname_new)){
								contactNameTextView.setText(nickname_new);
							}
						}

						@Override
						public void onProgress(double arg0) {
						}

						@Override
						public void onFailure(String arg0) {
						}
					});

			return convertView;
		}

		private final class ItemViewHolder {
			ImageView mContactImageView;
			TextView mContactNameTextView;
			IMEmotionTextView mContactInfoEmotionTextView;
			TextView mContactTimeTextView;
			BadgeView mBadgeView;
		}
	}
}
