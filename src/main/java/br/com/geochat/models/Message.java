package br.com.geochat.models;

public class Message {
    private User from;
    private User to;
	private String content;
	private boolean privateMessage;
	
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isPrivateMessage() {
		return privateMessage;
	}
	public void setPrivateMessage(boolean privateMessage) {
		this.privateMessage = privateMessage;
	}

	public Message(User from, User to, String content, boolean privateMessage) {
		this.from = from;
		this.to = to;
		this.content = content;
		this.privateMessage = privateMessage;
	}

	public Message() {
	}
	   
}