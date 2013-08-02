import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lgsi.auctionsniper.Auction;
import com.lgsi.auctionsniper.AuctionSniper;
import com.lgsi.auctionsniper.AuctionSniperListener;

import static org.mockito.Mockito.*;

public class AuctionSniperTest {

	public AuctionSniperTest() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void whenAuctionClosedCalledSniperLostShouldBeInvoked() {
		AuctionSniperListener listener = mock(AuctionSniperListener.class);
		AuctionSniper sniper = new AuctionSniper(listener, null);

		sniper.auctionClosed();
		verify(listener).sniperLost();
	}

	@Test
	public void whenCurrentPriceCalledSniperBiddingAndAuctionBidShouldBeInvoked() {
		Auction auction = mock(Auction.class);
		AuctionSniperListener listener = mock(AuctionSniperListener.class);
		AuctionSniper sniper = new AuctionSniper(listener, auction);

		sniper.currentPrice(1000, 100, "other");
		verify(listener).sniperBidding();
		verify(auction).bid(1100);
	}

}
