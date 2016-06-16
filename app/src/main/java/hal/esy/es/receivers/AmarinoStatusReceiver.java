package hal.esy.es.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import at.abraxas.amarino.AmarinoIntent;
import hal.esy.es.interfaces.GameStatusReceiverListener;

public class AmarinoStatusReceiver extends BroadcastReceiver {
	private GameStatusReceiverListener game;
	
	public AmarinoStatusReceiver(GameStatusReceiverListener game){
		this.game = game;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();
		
		if (AmarinoIntent.ACTION_CONNECTED.equals(action)){
			game.dismissWaitingConctedDialog();
		}
	}
}