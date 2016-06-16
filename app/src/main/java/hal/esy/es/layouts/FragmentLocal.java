package hal.esy.es.layouts;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import hal.esy.es.dao.DBHelper;
import hal.esy.es.R;
import hal.esy.es.adapter.PontuacaoLocalAdapter;

public class FragmentLocal extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_local, container, false);

        ListView lv = (ListView) v.findViewById(R.id.listView);
        lv.setAdapter(new PontuacaoLocalAdapter(getContext(),
                new DBHelper(getContext()).getTodosPontuacao()));

        return v;
    }


}
