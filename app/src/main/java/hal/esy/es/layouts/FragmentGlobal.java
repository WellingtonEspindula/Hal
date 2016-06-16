package hal.esy.es.layouts;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import hal.esy.es.R;
import hal.esy.es.adapter.PontucaoGlobalAdapter;
import hal.esy.es.model.Usuario;

public class FragmentGlobal extends Fragment {
    private ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewRoot = inflater.inflate(R.layout.fragment_fragment_global, container, false);
        lv = (ListView) viewRoot.findViewById(R.id.listView2);

        ReadData task1 = new ReadData();
        task1.execute(new String[]{"http://hal.esy.es/json/readjson.php"});

        return viewRoot;
    }

    private class ReadData extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Reading Data...");
            dialog.show();
        }

        String text = "";

        ArrayList<Usuario> list1;

        @Override
        protected Boolean doInBackground(String... urls) {


            InputStream is1;
            for (String url1 : urls) {
                //Read from web to InputStream
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url1);
                    HttpResponse response = client.execute(post);
                    is1 = response.getEntity().getContent();

                } catch (ClientProtocolException e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    return false;
                } catch (IOException e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    return false;
                }
                //end of Read from web to InputStream

                //Convert from InputStream to String Text
                BufferedReader reader;
                try {
                    reader = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"), 8);
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        text += line + "\n";
                    }
                    is1.close();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //end of Convert from InputStream to String Text

                //Convert from Text to JSON and add to ArrayList list1
                list1 = new ArrayList<Usuario>();
                try {
                    JSONArray jArray = new JSONArray(text);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jsonData = jArray.getJSONObject(i);
                        Usuario user = new Usuario();
                        user.setNome(jsonData.getString("nome"));
                        user.setPontuacao(jsonData.getInt("pontuacao"));
                        user.setId_device(jsonData.getString("idDevice"));
                        list1.add(user);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //end of Convert from Text to JSON and add to ArrayList list1
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result == true) {
                lv.setAdapter(new PontucaoGlobalAdapter(getContext(), list1));

            } else {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
    }


}
