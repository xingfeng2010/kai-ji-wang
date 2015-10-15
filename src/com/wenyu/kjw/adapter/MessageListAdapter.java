package com.wenyu.kjw.adapter;
import imsdk.data.mainphoto.IMSDKMainPhoto;
import imsdk.data.mainphoto.IMSDKMainPhoto.OnBitmapRequestProgressListener;
import imsdk.data.nickname.IMSDKNickname;
import imsdk.views.IMEmotionTextView;

import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenyu.Data.UserMessage;
import com.wenyu.Utils.BadgeView;
import com.wenyu.Utils.CommonUtil;
import com.wenyu.kaijiw.MainActivity;
import com.wenyu.kaijiw.R;

public	class MessageListAdapter extends BaseAdapter {
	List<UserMessage> userMessages;
	
	public MessageListAdapter(List<UserMessage> userMessages) {
		super();
		this.userMessages = userMessages;
	}

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
		}else{
			itemViewHolder.mContactNameTextView.setText(userMessage.getNickname());
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

	private  final class ItemViewHolder {
		ImageView mContactImageView;
		TextView mContactNameTextView;
		IMEmotionTextView mContactInfoEmotionTextView;
		TextView mContactTimeTextView;
		BadgeView mBadgeView;
	}
}