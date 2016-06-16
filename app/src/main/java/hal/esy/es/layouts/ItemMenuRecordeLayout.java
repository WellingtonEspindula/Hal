package hal.esy.es.layouts;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemMenuRecordeLayout extends LinearLayout{
    private TextView playerName;
	private TextView pontuacao;
	
	public ItemMenuRecordeLayout(Context context){
		super(context);
		
		playerName = new TextView(context);
		pontuacao = new TextView(context);
		
		playerName.setTextSize(15);
		pontuacao.setTextSize(12);
		pontuacao.setTextColor(Color.GRAY);
		
		this.addView(playerName);
		this.addView(pontuacao);
		
		this.setOrientation(LinearLayout.VERTICAL);
	}
}
