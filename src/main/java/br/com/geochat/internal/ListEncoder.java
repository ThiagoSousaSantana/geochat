package br.com.geochat.internal;

import java.util.LinkedList;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import br.com.geochat.models.User;

public class ListEncoder implements Encoder.Text<LinkedList<User>>{

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
	public String encode(LinkedList<User> object) throws EncodeException {
		return gson.toJson(object);
	}
    
}