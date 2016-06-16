package hal.esy.es.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;

import hal.esy.es.dao.DBHelper;
import hal.esy.es.R;
import hal.esy.es.model.Pontuacao;
import hal.esy.es.model.Usuario;


public class PontuacaoActivity extends Activity {

    public static final String EXTRA_PONTUACAO = "intent.EXTRA_PONTUACAO";

    private TextView pontosTV;
    private Spinner usuariosSpinner;
    private ArrayList<Usuario> usuarios;
    private int pontos;


    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuacao);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        Intent intent = getIntent();
        pontos = (int) intent.getSerializableExtra(EXTRA_PONTUACAO);

        pontosTV = (TextView) findViewById(R.id.pontos);
        pontosTV.setText(pontos + " pontos");

        atualizaSpinner();

    }

    public void criarUsuario(View view) {
        final Dialog dialogoCriarUsuario = new Dialog(this);
        dialogoCriarUsuario.setTitle("Cadastrar novo usuário");
        dialogoCriarUsuario.setContentView(R.layout.criar_usuario_dialog);

        Button dialogButton = (Button) dialogoCriarUsuario.findViewById(R.id.dialogButtonOK);
        final EditText nome = (EditText) dialogoCriarUsuario.findViewById(R.id.nome_usuario);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();
                usuario.setNome((String) nome.getText().toString());

                new DBHelper(PontuacaoActivity.this).inserirUsuario(usuario);
                atualizaSpinner();
                dialogoCriarUsuario.dismiss();
            }
        });

        dialogoCriarUsuario.show();
    }

    private void atualizaSpinner() {
        usuarios = new DBHelper(this).getTodosUsuario();
        usuariosSpinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<Usuario> dataAdapter = new ArrayAdapter<Usuario>(this,
                android.R.layout.simple_spinner_item, usuarios);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usuariosSpinner.setAdapter(dataAdapter);
    }

    public void compartilhar(View view){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Eu fiz "+pontos+" pontos no HAL, tente você também!! Baixe o app no link: www.hal.esy.es/download";
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void jogarNovamente(View view) {
        Intent intent = new Intent(this, JogoActivity.class);
        salvaBD(intent);
    }

    public void menu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        salvaBD(intent);
    }

    private void salvaBD(Intent intent) {
        Pontuacao pont = new Pontuacao();
        pont.setPontos(pontos);
        pont.setUsuario((Usuario) usuariosSpinner.getSelectedItem());
        new DBHelper(this).inserirPontuacao(pont);

        Usuario newUser = new Usuario();
        newUser.setNome(((Usuario) usuariosSpinner.getSelectedItem()).getNome());
        newUser.setPontuacao(pontos);
        newUser.setId_device(Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));

        Log.i("i", Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));

        InsertData task1 = new InsertData(newUser, intent);
        task1.execute(new String[]{"http://hal.esy.es/json/insert.php"});
    }

    private class InsertData extends AsyncTask<String, Void, Boolean> {
        ProgressDialog dialog = new ProgressDialog(PontuacaoActivity.this);

        private Usuario usuario;
        private Intent intent;

        public InsertData(Usuario usuario, Intent intent) {
            this.usuario = usuario;
            this.intent = intent;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Sending Data...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            for (String url1 : urls) {

                try {
                    ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("nome", usuario.getNome()));
                    pairs.add(new BasicNameValuePair("pontuacao", usuario.getPontuacao() + ""));
                    pairs.add(new BasicNameValuePair("idDevice", usuario.getId_device()));

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url1);
                    post.setEntity(new UrlEncodedFormEntity(pairs));

                    HttpResponse response = client.execute(post);
                } catch (ClientProtocolException e) {
                    Toast.makeText(PontuacaoActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    return false;
                } catch (IOException e) {
                    Toast.makeText(PontuacaoActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result == true) {
                Toast.makeText(PontuacaoActivity.this, "Insert Success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PontuacaoActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
            PontuacaoActivity.this.startActivity(intent);
        }

    }
}
