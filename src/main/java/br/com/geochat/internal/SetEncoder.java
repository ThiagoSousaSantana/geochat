package br.com.geochat.internal;

import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class SetEncoder implements Encoder.Text<Set<String>>{

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
	public String encode(Set<String> object) throws EncodeException {
		return gson.toJson(object);
	}
    
}