package br.com.geochat.models;

import java.io.Serializable;

import javax.websocket.Session;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
    private String avatar;
    private Session session;
    
	public String getUsername() {
		return username;
	}
    
    public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getAvatar() {
		return avatar;
	}
    
    public void setAvatar(String avatar) {
		this.avatar = avatar;
    }
    
    public void setUsername(String username) {
		this.username = username;
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
    }
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
    }
    
	public User() {
	}

    public User(String username) {
		this.username = username;
	}

	public User(String username, String avatar, Session session) {
        this.username = username;
		this.avatar = avatar;
		this.session = session;
	}

        
}