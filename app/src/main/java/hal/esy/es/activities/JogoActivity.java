package hal.esy.es.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import at.abraxas.amarino.Amarino;
import at.abraxas.amarino.AmarinoIntent;
import hal.esy.es.R;
import hal.esy.es.utils.CalculadoraPontuacao;
import hal.esy.es.utils.ManipulacaoDeString;
import hal.esy.es.interfaces.GameArduinoReceiverListener;
import hal.esy.es.interfaces.GameFailReceiverListener;
import hal.esy.es.interfaces.GameStatusReceiverListener;
import hal.esy.es.receivers.AmarinoFailReceiver;
import hal.esy.es.receivers.AmarinoStatusReceiver;
import hal.esy.es.receivers.ArduinoReceiver;
import hal.esy.es.repositorio.RepositorioLuz;

public class JogoActivity extends Activity implements GameArduinoReceiverListener, GameFailReceiverListener, GameStatusReceiverListener {

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
        unregisterReceiver(statusReceiver);
        Amarino.disconnect(this, macAdress);

    }

    //Componentes de Layout
    TextView contadorPontsTv;

    //Repositorio do jogo
    private RepositorioLuz repositorioLuz;

    //Diálogos
    private ProgressDialog waitConection;
    private AlertDialog.Builder arduinoMessager;
    private AlertDialog alertaArduinoMessager;

    //Receivers de eventos
    private ArduinoReceiver receiver;
    private AmarinoStatusReceiver statusReceiver;
    private AmarinoFailReceiver failReceiver;

    private int pontuacao;
    private RepositorioLuz repositorioDigitado;

    //Constantes
    private static String macAdress = "20:14:03:27:37:48";

    //Botoes
    private Button vermelho;
    private Button verde;
    private Button azul;
    private Button amarelo;

    //Sound
    SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    int soundVermelhoId;
    int soundVerdeId;
    int soundAzulId;
    int soundAmareloId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        contadorPontsTv = (TextView) findViewById(R.id.pontuacaoText);

        repositorioLuz = new RepositorioLuz(this);
        repositorioDigitado = new RepositorioLuz(this);

        soundVermelhoId = sp.load(this, R.raw.vermelho, 1);
        soundVerdeId = sp.load(this, R.raw.verde, 1);
        soundAzulId = sp.load(this, R.raw.azul, 1);
        soundAmareloId = sp.load(this, R.raw.amarelo, 1);

        statusReceiver = new AmarinoStatusReceiver(this);
        receiver = new ArduinoReceiver(this);
        failReceiver = new AmarinoFailReceiver(this);

        vermelho = (Button) findViewById(R.id.vermelho);
        verde = (Button) findViewById(R.id.verde);
        azul = (Button) findViewById(R.id.azul);
        amarelo = (Button) findViewById(R.id.amarelo);

        vermelho.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onVermelhoTouch(v, event);
            }
        });

        verde.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onVerdeTouch(v, event);
            }
        });

        azul.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onAzulTouch(v, event);
            }
        });

        amarelo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onAmareloTouch(v, event);
            }
        });

        Amarino.disconnect(this, macAdress);
        registerReceiver(statusReceiver, new IntentFilter(
                AmarinoIntent.ACTION_CONNECTED));

        registerReceiver(receiver, new IntentFilter(
                AmarinoIntent.ACTION_RECEIVED));


        Amarino.connect(this, macAdress);

        waitConection = new ProgressDialog(this);
        waitConection.setMessage("Esperando conexão");
        waitConection.setCancelable(false);
        waitConection.show();
    }

    private boolean onVermelhoTouch(View v, MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                sp.play(soundVermelhoId, 1, 1, 0, 0, 1);

            case MotionEvent.ACTION_UP:
                sp.stop(soundVermelhoId);
        }

        return false;
    }

    private boolean onVerdeTouch(View v, MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                sp.play(soundVerdeId, 1, 1, 0, 0, 1);
                break;

            case MotionEvent.ACTION_UP:
                sp.stop(soundVerdeId);
                break;
        }

        return false;
    }

    private boolean onAzulTouch(View v, MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                sp.play(soundAzulId, 1, 1, 0, 0, 1);
                break;

            case MotionEvent.ACTION_UP:
                sp.stop(soundAzulId);
                break;
        }

        return false;
    }

    private boolean onAmareloTouch(View v, MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                sp.play(soundAmareloId, 1, 1, 0, 0, 1);
                break;

            case MotionEvent.ACTION_UP:
                sp.stop(soundAmareloId);
                break;
        }

        return false;
    }

    public void dismissWaitingConctedDialog() {
        if (waitConection.isShowing()) {
            waitConection.dismiss();
            actionConected();
        }
    }

    public void actionConected() {
        LayoutInflater li = getLayoutInflater();
        View view = li.inflate(R.layout.waiting_cube_response_dialog, null);
        arduinoMessager = new AlertDialog.Builder(this);
        arduinoMessager.setView(view);

        alertaArduinoMessager = arduinoMessager.create();

        loop();
    }

    private void loop() {
        repositorioDigitado.clear();
        repositorioLuz.newElementoSequencia();
        pontuacao = CalculadoraPontuacao.receberPontuacao(repositorioLuz.getQuantidadeDeElementos());
        contadorPontsTv.setText("Pontuação: " + pontuacao);
        Amarino.sendDataToArduino(this, macAdress, 'i',
                ManipulacaoDeString.passarArrayParaTexto(repositorioLuz.getSequenciaArray()));
        showWaitingDialog();
    }

    public void closeWaitingDialog() {
        if (alertaArduinoMessager.isShowing())
            alertaArduinoMessager.dismiss();
    }

    public void showWaitingDialog() {
        alertaArduinoMessager.show();
    }


    public void cancelarEntrada() {
        Toast.makeText(this, "Você perdeu!! ", Toast.LENGTH_SHORT).show();
        Intent pontuacaoIntent = new Intent(this, PontuacaoActivity.class);
        pontuacaoIntent.putExtra(PontuacaoActivity.EXTRA_PONTUACAO, pontuacao);
        this.startActivity(pontuacaoIntent);
    }

    public void cliqueVermelho(View view) {
        repositorioDigitado.addCor(RepositorioLuz.RED);
        if (repositorioLuz.comparar(repositorioDigitado)) {
            if (repositorioLuz.getQuantidadeDeElementos() == (repositorioDigitado.getQuantidadeDeElementos())) {
                Toast.makeText(this, "Você acertou!!", Toast.LENGTH_SHORT).show();
                loop();
            }
        } else {
            cancelarEntrada();
        }
    }

    public void cliqueVerde(View view) {
        repositorioDigitado.addCor(RepositorioLuz.GREEN);
        if (repositorioLuz.comparar(repositorioDigitado)) {
            if (repositorioLuz.getQuantidadeDeElementos() == (repositorioDigitado.getQuantidadeDeElementos())) {
                Toast.makeText(this, "Você acertou!!", Toast.LENGTH_SHORT).show();
                loop();
            }
        } else {
            cancelarEntrada();
        }

    }

    public void cliqueAzul(View view) {
        repositorioDigitado.addCor(RepositorioLuz.BLUE);
        if (repositorioLuz.comparar(repositorioDigitado)) {
            if (repositorioLuz.getQuantidadeDeElementos() == (repositorioDigitado.getQuantidadeDeElementos())) {
                Toast.makeText(this, "Você acertou!!", Toast.LENGTH_SHORT).show();
                loop();
            }
        } else {
            cancelarEntrada();
        }

    }

    public void cliqueAmarelo(View view) {
        repositorioDigitado.addCor(RepositorioLuz.YELLOW);
        if (repositorioLuz.comparar(repositorioDigitado)) {
            if (repositorioLuz.getQuantidadeDeElementos() == (repositorioDigitado.getQuantidadeDeElementos())) {
                Toast.makeText(this, "Você acertou!!", Toast.LENGTH_SHORT).show();
                loop();
            }
        } else {
            cancelarEntrada();
        }
    }
}