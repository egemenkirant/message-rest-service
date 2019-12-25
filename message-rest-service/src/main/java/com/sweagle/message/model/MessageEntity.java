package com.sweagle.message.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class MessageEntity {

	@Id
	private String id;
	private String sender;
	private String receiver;
	private String subject;
	private String content;
	private Date sentDate;
	
	public MessageEntity() {
		super();
	}

	public MessageEntity(String id, String sender, String receiver, String subject, String content, Date sentDate) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.content = content;
		this.sentDate = sentDate;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	
	@Override
	public String toString() {
		return "MessageEntity [id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", subject=" + subject
				+ ", content=" + content + ", sentDate=" + sentDate + "]";
	}
	
	
	
}
