package com.lgsi.auctionsniper;

public class AuctionSniper implements AuctionEventListener {

	private AuctionSniperListener listener;
	private Auction auction;

	public AuctionSniper(AuctionSniperListener listener, Auction auction) {
		this.listener = listener;
		this.auction = auction;
	}

	@Override
	public void auctionClosed() {
		listener.sniperLost();
	}

	@Override
	public void currentPrice(int currentPrice, int increment, String bidder) {
		listener.sniperBidding();
		auction.bid(currentPrice + increment);
	}

}
