package com.lgsi.auctionsniper.test;


import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.lgsi.auctionsniper.MainActivity;
import com.lgsi.auctionsniper.R;

public class ApplicationRunner {
	public static final String SNIPER_ID = "sniper";
	public static final String SNIPER_PASSWORD = "sniper";
	
	//public static final String BIDDER_IPAdress = "10.168.148.72";
	public static final String BIDDER_ID = "sniper";
	public static final String BIDDER_PASSWORD = "sniper";
	
	private AuctionSniperDriver driver;
	private ActivityInstrumentationTestCase2<MainActivity> inst;

	public ApplicationRunner(ActivityInstrumentationTestCase2<MainActivity> inst) {
		this.inst = inst;
	}

	public void startBiddingIn(FakeAuctionServer auction) {
		Solo solo = new Solo(inst.getInstrumentation(),	inst.getActivity());
		solo.clickOnButton(0);
		driver = new AuctionSniperDriver(solo);
		driver.showsSniperStatus(R.string.status_joining);
	}

	public void showsSniperHasLostAuction() {
		driver.showsSniperStatus(R.string.status_lost);
	}
	
	public void showsSniperIsBidding() {
		driver.showsSniperStatus(R.string.status_bidding);
		
	}

	public void stop() {
	}

	
	
}