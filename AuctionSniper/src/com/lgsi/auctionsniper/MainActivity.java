package com.lgsi.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements AuctionSniperListener {

    private Button joinButton;
	private TextView sniperStatus;
	private Chat chat;
	public static final String AUCTION_COMMAND_JOIN_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String AUCTION_COMMAND_BID_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        joinButton = (Button) findViewById(R.id.join);
        sniperStatus = (TextView) findViewById(R.id.sniper_status);
        joinButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				sniperStatus.setText(R.string.status_joining);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							join();
						} catch (XMPPException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}

		});
    }

    private void join() throws XMPPException {
		ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration("10.168.148.72", 5222);
		XMPPConnection connection = new XMPPConnection(connectionConfiguration);
		connection.connect();
		connection.login("sniper", "sniper", "Auction");

		chat = connection.getChatManager().createChat("auction-item-54321@localhost", null);
		XMPPAuction auction = new XMPPAuction(chat);
		chat.addMessageListener(new AuctionMessageTranslater(new AuctionSniper(this, auction)));
		auction.join();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void sniperLost() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sniperStatus.setText(R.string.status_lost);		
			}
		});
	}

	@Override
	public void sniperBidding() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sniperStatus.setText(R.string.status_bidding);
			}
		});
	}
    
}
