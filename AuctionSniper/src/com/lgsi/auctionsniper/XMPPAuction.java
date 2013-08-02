package com.lgsi.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import static com.lgsi.auctionsniper.MainActivity.AUCTION_COMMAND_BID_FORMAT;
import static com.lgsi.auctionsniper.MainActivity.AUCTION_COMMAND_JOIN_FORMAT;

public class XMPPAuction implements Auction {
	
	final private Chat chat;

	public XMPPAuction(Chat chat) {
		this.chat = chat;
	}
	
	@Override
	public void bid(int price) {
		try {
			chat.sendMessage(String.format(AUCTION_COMMAND_BID_FORMAT, price));
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void join() {
		try {
			chat.sendMessage(AUCTION_COMMAND_JOIN_FORMAT);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
}