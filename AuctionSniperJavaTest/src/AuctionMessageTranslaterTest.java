import static org.junit.Assert.*;

import org.jivesoftware.smack.packet.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lgsi.auctionsniper.AuctionEventListener;
import com.lgsi.auctionsniper.AuctionMessageTranslater;

import static org.mockito.Mockito.*;

public class AuctionMessageTranslaterTest {

	public AuctionMessageTranslaterTest() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void whenCloseMessageComesAuctionClosedShouldBeCalled() {
		AuctionEventListener listener = mock(AuctionEventListener.class);
		AuctionMessageTranslater translater = new AuctionMessageTranslater(listener);

		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: CLOSE;");
		translater.processMessage(null, message);
		
		verify(listener).auctionClosed();
	}

	@Test
	public void whenPriceMessageComesCurrentPriceShouldBeCalled() {
		AuctionEventListener listener = mock(AuctionEventListener.class);
		AuctionMessageTranslater translater = new AuctionMessageTranslater(listener);

		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 1000; Increment: 100; Bidder: other");
		translater.processMessage(null, message);
		
		verify(listener).currentPrice(1000, 100, "other");
	}

}
