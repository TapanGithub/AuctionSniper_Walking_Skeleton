package com.lgsi.auctionsniper;
import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;


public class AuctionMessageTranslater implements MessageListener {

	private AuctionEventListener listener;

	public AuctionMessageTranslater(AuctionEventListener listener) {
		this.listener = listener;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		HashMap<String, String> events = packEventFrom(message);
		if ("CLOSE".equals(events.get("Event")))
			listener.auctionClosed();
		else {
			int currentPrice = Integer.valueOf(events.get("CurrentPrice"));
			int increment    = Integer.valueOf(events.get("Increment"));
			listener.currentPrice(currentPrice, increment, events.get("Bidder"));
		}
	}

	private HashMap<String, String> packEventFrom(Message message) {
		HashMap<String, String> events = new HashMap<String, String>();
		String body = message.getBody();
		for (String s: body.split(";")) {
			String key   = s.split(":")[0].trim();
			String value = s.split(":")[1].trim();
			events.put(key, value);
		}
		return events;
	}

}
