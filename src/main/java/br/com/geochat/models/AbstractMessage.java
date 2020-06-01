package br.com.geochat.models;

public class AbstractMessage {
    private transient MessageType messageType;
    private transient Object content;

	public Object getContent() {
		return content;
    }
    
	public MessageType getMessageType() {
		return messageType;
    }
    
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
    }
    
	public void setContent(Object content) {
		this.content = content;
	}

	public AbstractMessage(MessageType messageType, Object content) {
		this.messageType = messageType;
		this.content = content;
	}

	public AbstractMessage() {
	}
}