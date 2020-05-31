package br.com.geochat.models;

public class Message {
    private String from;
    private String to;
	private String content;
	private boolean privateMessage;
	
	
	
	public String getFrom() {
		return from;
	}
	public boolean isPrivateMessage() {
		return privateMessage;
	}
	public void setPrivateMessage(boolean privateMessage) {
		this.privateMessage = privateMessage;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public void setFrom(String from) {
		this.from = from;
	}

	public Message(String from, String to, String content, boolean privateMessage) {
		this.from = from;
		this.to = to;
		this.content = content;
		this.privateMessage = privateMessage;
	}

	public Message() {
		
	}
    
}