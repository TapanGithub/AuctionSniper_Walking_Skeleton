package com.lgsi.auctionsniper.test;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;


import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import org.jivesoftware.smack.packet.Message;

import com.lgsi.auctionsniper.MainActivity;
import static org.hamcrest.Matchers.equalTo;

public class FakeAuctionServer {
	private static final String AUCTION_EVENT_PRICE_FORMAT = "SOLVersion: 1.1; Event: PRICE; " + "CurrentPrice: %d; Increment: %d; Bidder: %s";
	private static final String AUCTION_EVENT_CLOSE_FORMAT = "SOLVersion: 1.1; Event: CLOSE;";

	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_RESOURCE = "auAuction";
	public static final String XMPP_HOSTNAME = "10.168.148.72";
	private static final String AUCTION_PASSWORD = "auction";

	private final String itemId;
	private final XMPPConnection connection;
	private Chat currentChat;
	private final SingleMessageListener messageListener = new SingleMessageListener();

	public FakeAuctionServer(String itemId) {
		this.itemId = itemId;

		ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(
				XMPP_HOSTNAME, 5222);
		this.connection = new XMPPConnection(connectionConfiguration);
	}

	public void startSellingItem() throws XMPPException {
		connection.connect();
		connection.login(format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD,
				AUCTION_RESOURCE);
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean locallyCreated) {
				currentChat = chat;
				chat.addMessageListener(messageListener);
			}
		});
	}

	public void announceClosed() throws XMPPException {
		currentChat.sendMessage(AUCTION_EVENT_CLOSE_FORMAT);
	}

	public void stop() {
		// connection.disconnect();
	}

	public String getItemId() {
		return itemId;
	}
	
	public void hasReceivedJoinRequestFromSniper(String SNIPPER_ID) throws InterruptedException {
		messageListener.receivesAMessage(is(MainActivity.AUCTION_COMMAND_JOIN_FORMAT));
	}
	
	
	public void reportPrice(int auctionPrice, int increment , String lastBidder) throws XMPPException	{
		currentChat.sendMessage(String.format(AUCTION_EVENT_PRICE_FORMAT, auctionPrice, increment, lastBidder));
	}

	public void hasReceivedBid(int biddingPrice, String bidderId) throws InterruptedException{
		messageListener.receivesAMessage(equalTo(String.format(MainActivity.AUCTION_COMMAND_BID_FORMAT, biddingPrice)));
	}
	
}

