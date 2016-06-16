package hal.esy.es.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hal.esy.es.R;
import hal.esy.es.model.Pontuacao;

/**
 * Created by Wellington on 14/12/2015.
 */
public class PontuacaoLocalAdapter extends ArrayAdapter<Pontuacao> {
    // View lookup cache
    private static class ViewHolder {
        TextView colocacao;
        TextView name;
        TextView pontuacao;
    }

    public PontuacaoLocalAdapter(Context context, ArrayList<Pontuacao> users) {
        super(context, R.layout.item_recorde, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Pontuacao pontucao_usuario = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_recorde, parent, false);
            viewHolder.colocacao = (TextView) convertView.findViewById(R.id.colocacao);
            viewHolder.name = (TextView) convertView.findViewById(R.id.nome_recordes);
            viewHolder.pontuacao = (TextView) convertView.findViewById(R.id.pontuacao_recordes);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.colocacao.setText((position+1)+"º ");
        viewHolder.name.setText(pontucao_usuario.getUsuario().getNome());
        viewHolder.pontuacao.setText(pontucao_usuario.getPontos()+"");
        // Return the completed view to render on screen
        return convertView;
    }
}
