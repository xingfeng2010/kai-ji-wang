package com.wenyu.kaijiw;



import imsdk.data.IMMyself;
import imsdk.data.IMMyself.OnActionListener;
import imsdk.data.customuserinfo.IMSDKCustomUserInfo;
import imsdk.data.mainphoto.IMSDKMainPhoto;
import imsdk.data.mainphoto.IMSDKMainPhoto.OnBitmapRequestProgressListener;
import imsdk.data.relations.IMMyselfRelations;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class ProfileActivity extends Activity implements OnClickListener {
	// data
	private final static int TIMEOUT = 5;
	private String mCustomUserID;

	// ui
	private ImageView mMainPhotoImageView;

	private TextView mNickNameTextView;
	private TextView mGenderTextView;
	private TextView mRegionTextView;
	private TextView mSignTextView;

	private Button mKickToBlacklistBtn;
	private Button mRemoveFromBlacklistBtn;
	private Button mAddFriendBtn;
	private Button mRemoveFromFriendsListBtn;
	private Button mSendMessageBtn;

	private final static int FRIEND = 1;
	private final static int BLACKLIST = 2;
	private final static int STRANGER = 3;
	private final static int SELF = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 使得音量键控制媒体声�?
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_profile);

		mCustomUserID = getIntent().getStringExtra("CustomUserID");

		mMainPhotoImageView = (ImageView) findViewById(R.id.profile_mainphoto_imageview);

		mNickNameTextView = (TextView) findViewById(R.id.profile_nickname_textview);
		mGenderTextView = (TextView) findViewById(R.id.profile_gender_textview);
		mRegionTextView = (TextView) findViewById(R.id.profile_region_textview);
		mSignTextView = (TextView) findViewById(R.id.profile_sign_textview);

		// 获取本地头像数据
		Bitmap bitmap = IMSDKMainPhoto.get(mCustomUserID);

		if (bitmap != null) {
			mMainPhotoImageView.setImageBitmap(bitmap);
		} else {
			mMainPhotoImageView.setImageResource(R.drawable.ic_launcher);
		}

		mKickToBlacklistBtn = (Button) findViewById(R.id.profile_kicktoblack_btn);
		mRemoveFromBlacklistBtn = (Button) findViewById(R.id.profile_removefromblack_btn);
		mAddFriendBtn = (Button) findViewById(R.id.profile_addfriend_btn);
		mRemoveFromFriendsListBtn = (Button) findViewById(R.id.profile_removefriend_btn);
		mSendMessageBtn = (Button) findViewById(R.id.profile_sendmsg_btn);

		((TextView) findViewById(R.id.imbasetitlebar_title))
				.setText("��ϸ����");
		((ImageButton) findViewById(R.id.imbasetitlebar_back))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});

		mKickToBlacklistBtn.setOnClickListener(this);
		mRemoveFromBlacklistBtn.setOnClickListener(this);
		mAddFriendBtn.setOnClickListener(this);
		mRemoveFromFriendsListBtn.setOnClickListener(this);
		mSendMessageBtn.setOnClickListener(this);

		mNickNameTextView.setText(mCustomUserID);

		String customUserInfo = IMSDKCustomUserInfo.get(mCustomUserID);
		String[] array = customUserInfo.split("\n");

		if (array.length == 3) {
			mGenderTextView.setText(array[0]);
			mRegionTextView.setText(array[1]);
			mSignTextView.setText(array[2]);
		}

		int relation = 0;

		if (IMMyselfRelations.isMyBlacklistUser(mCustomUserID)) {
			relation = BLACKLIST;
		} else if (IMMyselfRelations.isMyFriend(mCustomUserID)) {
			relation = FRIEND;
		} else {
			relation = STRANGER;
		}

		if (mCustomUserID.equals(IMMyself.getCustomUserID())) {
			relation = SELF;
		}

		// 根据用户关系呈现界面内容
		showUIByRelation(relation);

		// 更新头像数据
		IMSDKMainPhoto.request(mCustomUserID, 20,
				new OnBitmapRequestProgressListener() {
					@Override
					public void onSuccess(Bitmap bitmap, byte[] buffer) {
						if (bitmap != null) {
							mMainPhotoImageView.setImageBitmap(bitmap);
						} else {
							mMainPhotoImageView
									.setImageResource(R.drawable.ic_launcher);
						}
					}

					@Override
					public void onProgress(double progress) {
					}

					@Override
					public void onFailure(String error) {
					}
				});

		// 更新CustomUserInfo
		IMSDKCustomUserInfo.request(mCustomUserID, new OnActionListener() {
			@Override
			public void onSuccess() {
				String customUserInfo = IMSDKCustomUserInfo.get(mCustomUserID);
				String[] array = customUserInfo.split("\n");

				if (array.length == 3) {
					mGenderTextView.setText(array[0]);
					mRegionTextView.setText(array[1]);
					mSignTextView.setText(array[2]);
				}
			}

			@Override
			public void onFailure(String error) {
			}
		});
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();

		switch (viewId) {
		case R.id.profile_kicktoblack_btn:
			make_black();
			break;
		case R.id.profile_removefromblack_btn:
			remove_black();
			break;
		case R.id.profile_addfriend_btn:
			add_friend();
			break;
		case R.id.profile_removefriend_btn:
			remove_friend();
			break;
		case R.id.profile_sendmsg_btn:
			Intent intent = new Intent(ProfileActivity.this, IMChatActivity.class);

			intent.putExtra("CustomUserID", mCustomUserID);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	public void remove_black() {
		String blackTitle = "ȷ��Ҫ���������?";

		new AlertDialog.Builder(this).setMessage(blackTitle)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						IMMyselfRelations.removeUserFromBlacklist(mCustomUserID,
								TIMEOUT, new OnActionListener() {
									@Override
									public void onSuccess() {
										showUIByRelation(STRANGER);
										Toast.makeText(ProfileActivity.this, "�Ƴ��������ɹ���",
												Toast.LENGTH_SHORT);
										refresh();
									}

									@Override
									public void onFailure(String error) {
										Toast.makeText(ProfileActivity.this, "�Ƴ�������ʧ�ܣ�",
												Toast.LENGTH_SHORT);
									}
								});
					}
				}).setNegativeButton("ȡ��", null).create().show();
	}

	public void make_black() {
		String blackTitle = "ȷ��Ҫ������������?";

		new AlertDialog.Builder(this).setMessage(blackTitle)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						IMMyselfRelations.moveUserToBlacklist(mCustomUserID, TIMEOUT,
								new OnActionListener() {

									@Override
									public void onSuccess() {
										showUIByRelation(BLACKLIST);
										Toast.makeText(ProfileActivity.this, "���ڳɹ���",
												Toast.LENGTH_SHORT);
										refresh();
									}

									@Override
									public void onFailure(String error) {
										Toast.makeText(ProfileActivity.this, "����ʧ�ܣ�"+ error,
												Toast.LENGTH_SHORT);

									}
								});

					}
				}).setNegativeButton("ȡ��", null).create().show();
	}

	public void add_friend() {
		String addTitle = "ȷ��Ҫ�����Ϊ����?";

		new AlertDialog.Builder(this).setMessage(addTitle)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						IMMyselfRelations.sendFriendRequest("", mCustomUserID, TIMEOUT,
								new OnActionListener() {

									@Override
									public void onSuccess() {
										Toast.makeText(ProfileActivity.this, "���������ѷ��ͣ�",
												Toast.LENGTH_SHORT);
									}

									@Override
									public void onFailure(String error) {
										Toast.makeText(ProfileActivity.this, "����������ʧ�ܣ�" + error,
												Toast.LENGTH_SHORT);
									}
								});

					}
				}).setNegativeButton("ȡ��", null).create().show();

	}

	public void remove_friend() {
		String addTitle = "ȷ��Ҫ������ѹ�ϵ?";

		new AlertDialog.Builder(this).setMessage(addTitle)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

						IMMyselfRelations.removeUserFromFriendsList(mCustomUserID,
								TIMEOUT, new OnActionListener() {
									@Override
									public void onSuccess() {
										showUIByRelation(STRANGER);
										Toast.makeText(ProfileActivity.this, "�Ƴ����ѳɹ���" ,
												Toast.LENGTH_SHORT);
										refresh();
									}

									@Override
									public void onFailure(String error) {
										Toast.makeText(ProfileActivity.this, "�Ƴ�����ʧ�ܣ�" + error ,
												Toast.LENGTH_SHORT);

									}
								});

					}
				}).setNegativeButton("ȡ��", null).create().show();

	}

	public void refresh() {
		// Intent intent = getIntent();
		// intent.putExtra("User", mUser);
		// finish();
		// startActivity(intent);
	}

	private void showUIByRelation(int relation) {
		switch (relation) {
		case FRIEND:
			mKickToBlacklistBtn.setVisibility(View.GONE);
			mRemoveFromBlacklistBtn.setVisibility(View.GONE);
			mAddFriendBtn.setVisibility(View.GONE);
			mRemoveFromFriendsListBtn.setVisibility(View.VISIBLE);
			mSendMessageBtn.setVisibility(View.VISIBLE);
			break;
		case BLACKLIST:
			mKickToBlacklistBtn.setVisibility(View.GONE);
			mRemoveFromBlacklistBtn.setVisibility(View.VISIBLE);
			mAddFriendBtn.setVisibility(View.GONE);
			mRemoveFromFriendsListBtn.setVisibility(View.GONE);
			mSendMessageBtn.setVisibility(View.GONE);
			break;
		case STRANGER:
			mKickToBlacklistBtn.setVisibility(View.VISIBLE);
			mRemoveFromBlacklistBtn.setVisibility(View.GONE);
			mAddFriendBtn.setVisibility(View.VISIBLE);
			mRemoveFromFriendsListBtn.setVisibility(View.GONE);
			mSendMessageBtn.setVisibility(View.GONE);
			break;
		case SELF:
			mSendMessageBtn.setVisibility(View.VISIBLE);
			mKickToBlacklistBtn.setVisibility(View.GONE);
			mRemoveFromBlacklistBtn.setVisibility(View.GONE);
			mAddFriendBtn.setVisibility(View.GONE);
			mRemoveFromFriendsListBtn.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
}
