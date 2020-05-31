package br.com.geochat.internal;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import br.com.geochat.models.AbstractMessage;

public class AbstractMessageEncoder implements Encoder.Text<AbstractMessage>{

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
	public String encode(AbstractMessage object) throws EncodeException {
		return gson.toJson(object);
	}
    
}