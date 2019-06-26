package jp.alhinc.ishiguro_marina.beans;

import java.util.Date;

public class UserComment  {

    private int id;
    private String text;
    private int userId;
    private int messageId;
    private String userName;
    private Date createdDate;

	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int postId) {
		this.messageId = postId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}



}