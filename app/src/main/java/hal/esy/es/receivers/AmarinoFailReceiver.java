package hal.esy.es.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import at.abraxas.amarino.AmarinoIntent;
import hal.esy.es.interfaces.GameFailReceiverListener;

/**
 * Created by wellington on 18/10/14.
 */
public class AmarinoFailReceiver extends BroadcastReceiver {
    private GameFailReceiverListener gameActivity;

    public AmarinoFailReceiver(GameFailReceiverListener gameActivity){
        this.gameActivity = gameActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (AmarinoIntent.ACTION_CONNECTION_FAILED.equals(action)){
            Toast.makeText(context, "Erro ao conectar com dispositivo...\nDispositivo não fora encontrado, verifique se o mesmo encontra-se ligado e tente novamente", Toast.LENGTH_LONG).show();
            gameActivity.finish();
        }
    }
}
