package br.com.geochat;

import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import io.vertx.core.json.Json;

public class SetEncoder implements Encoder.Text<Set>{

	@Override
	public void init(EndpointConfig config) {
		// no-op
		
	}

	@Override
	public void destroy() {
		// no-op
		
	}

	@Override
	public String encode(Set object) throws EncodeException {
		return Json.encode(object);
	}
    
}