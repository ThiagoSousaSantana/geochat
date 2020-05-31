package br.com.geochat.internal;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import br.com.geochat.models.Message;


public class MessageDecoder implements Decoder.Text<Message>{

    private static Gson gson = new Gson();

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Message decode(String json) throws DecodeException {
		return gson.fromJson(json, Message.class);
	}

	@Override
	public boolean willDecode(String s) {
		return s != null;
	}
    
}