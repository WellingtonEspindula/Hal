package hal.esy.es.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import hal.esy.es.interfaces.GameArduinoReceiverListener;

public class ArduinoReceiver extends BroadcastReceiver{
	
	private GameArduinoReceiverListener game;
	
	public ArduinoReceiver(GameArduinoReceiverListener game){
		this.game = game;
	}
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
			game.closeWaitingDialog();
	}

}
