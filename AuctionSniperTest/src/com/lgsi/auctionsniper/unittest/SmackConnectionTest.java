package com.lgsi.auctionsniper.unittest;

//import com.jayway.android.robotium.solo.Solo;
import junit.framework.TestCase;

import com.lgsi.auctionsniper.MainActivity;
import com.lgsi.auctionsniper.test.ApplicationRunner;
import com.lgsi.auctionsniper.test.FakeAuctionServer;

import android.test.ActivityInstrumentationTestCase2;

//import junit.framework.TestCase;

public class SmackConnectionTest extends TestCase {

	public void testServerConnection() throws Exception {
		final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
		auction.startSellingItem();
	}
}
