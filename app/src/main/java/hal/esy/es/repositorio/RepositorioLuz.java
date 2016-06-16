package hal.esy.es.repositorio;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class RepositorioLuz {
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;
	public static final int YELLOW = 3;

	private ArrayList<Integer> sequencia = new ArrayList<Integer>();
	private Context context;

	public RepositorioLuz(Context context){
		this.context = context;
	}

	public int getQuantidadeDeElementos() {
		return sequencia.size();
	}

	public int[] getSequenciaArray() {
		return  convertToIntArray(sequencia);
	}

    public ArrayList<Integer> getSequencia() {
		return  sequencia;
	}

	public void newElementoSequencia(){
        Random rand = new Random();
		sequencia.add(rand.nextInt(YELLOW+1));
	}
	public int getSequenciaPosition(int position){
		if (position<=getQuantidadeDeElementos()){
			return sequencia.get(position);
		}
		else{
			Toast.makeText(context, "Erro interno", Toast.LENGTH_SHORT).show();
			return 0;
		}
	}

	private int[] convertToIntArray(ArrayList<Integer> array){
		int[] arrayLocal = new int[array.size()];
		for (int i = 0; i < array.size(); i++){
			arrayLocal[i] = array.get(i);
		}
		return arrayLocal;
	}

    public void setSequencia(ArrayList<Integer> sequencia) {
        this.sequencia = sequencia;
    }

    public void addCor(int cor){
        sequencia.add(cor);
    }

    public boolean comparar(RepositorioLuz repoLuz){
        ArrayList<Integer> sequeincia1 = getSequencia();
        ArrayList<Integer> sequeincia2 = repoLuz.getSequencia();

        boolean aux = true;

        for (int i = 0; i < sequeincia2.size(); i++){
            if (sequeincia1.get(i) != sequeincia2.get(i))
                aux = false;
        }

        return aux;
    }

    public void clear(){
        sequencia.clear();
    }
}
