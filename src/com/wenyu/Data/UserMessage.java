package com.wenyu.Data;

import android.graphics.Bitmap;

public class UserMessage {
	
	private String customUserID;        
	private String nickname;            
	private String lastMessageTime;        
	private String lastMessageContent;  
	private Bitmap bitmap;             
	private long unreadChatMessageCount; 
	
	public String getCustomUserID() {
		return customUserID;
	}
	public void setCustomUserID(String customUserID) {
		this.customUserID = customUserID;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLastMessageContent() {
		return lastMessageContent;
	}
	public void setLastMessageContent(String lastMessageContent) {
		this.lastMessageContent = lastMessageContent;
	}
	public long getUnreadChatMessageCount() {
		return unreadChatMessageCount;
	}
	public void setUnreadChatMessageCount(long unreadChatMessageCount) {
		this.unreadChatMessageCount = unreadChatMessageCount;
	}
	public String getLastMessageTime() {
		return lastMessageTime;
	}
	public void setLastMessageTime(String lastMessageTime) {
		this.lastMessageTime = lastMessageTime;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
}
