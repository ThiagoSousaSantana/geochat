package br.com.geochat.internal;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import br.com.geochat.models.User;

public class UserEncoder implements Encoder.Text<User> {

    private static Gson gson = new Gson();

	@Override
	public void init(EndpointConfig config) {
		// no-op
		
	}

	@Override
	public void destroy() {
		// no-op
		
	}

	@Override
	public String encode(User object) throws EncodeException {
		return gson.toJson(object);
	}
    
}