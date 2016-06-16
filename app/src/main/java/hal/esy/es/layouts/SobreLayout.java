package hal.esy.es.layouts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hal.esy.es.R;
import hal.esy.es.activities.SobreActivity;


public class SobreLayout extends LinearLayout {
	private Context context;
	private TextView titulo;
	private ImageView imagem;
	private TextView texto;
	private TextView easterEggText;

	public SobreLayout(SobreActivity context) {
		super(context);
		context.setTitle("Sobre");
		this.context = context;
		
		titulo = new TextView(context);
		texto = new TextView(context);
		imagem = new ImageView(context);

		titulo.setText("HAL PROJECT");
		titulo.setTextSize(50);
		titulo.setGravity(Gravity.CENTER_HORIZONTAL);
		
		Bitmap imagemBitmap = Bitmap.createScaledBitmap(BitmapFactory
				.decodeResource(getResources(), R.mipmap.sobre_image), 200,
				200, true);
		imagem.setImageBitmap(imagemBitmap);
		imagem.setPadding(0, 15, 0, 0);

		easterEggText = new TextView(context);
		easterEggText.setPadding(5, 5, 5, 5);

		texto.setText("Esse aplicativo foi desenvolvido no IFRS - Campus Osório por uma iniciativa "
				+ "do Prof.º Terrimar Ignácio Pascualetto, na disciplina de física, de pôr em prática "+
				"os conhecimentos obtidos na mesma disciplina aplicada com os conceitos de Programação "+
				"em um trabalho utilizando o Arduino.\n\nHAL foi desenvolvido por:\nWellington Espíndula"
				+ "\nGabriel Amoretti\nJoão Pedro Laureano\nNicole Rita Perera Ribeiro\nLarissa Giacomelli");
		texto.setGravity(Gravity.CENTER);
		texto.setTextSize(20);

		texto.setOnClickListener(new OnClickListener() {
			private int counter = 0;

			@Override
			public void onClick(View v) {
				counter++;
				if (counter > 5) {
					easterEggText
							.setText("4e 61 20 76 65 72 64 61 64 65 2c 20 71 75 65 6d 20 64 65 73 65 6e 76 6f 6c 76 65 75 20 6f 20 6a 6f 67 6f 20 70 75 72 61 6d 65 6e 74 6f 20 66 6f 69 20 65 75 20 28 57 65 6c 6c 69 6e 67 74 6f 6e 20 45 73 70 c3 ad 6e 64 75 6c 61 29 2c 20 63 6f 6d 20 75 6d 20 67 72 61 6e 64 65 20 61 75 78 c3 ad 6c 69 6f 20 6e 6f 20 68 61 72 64 77 61 72 65 20 70 6f 72 20 6d 65 75 20 6d 65 6c 68 6f 72 20 61 6d 69 67 6f 20 47 61 62 72 69 65 6c 20 41 6d 6f 72 65 74 74 69 2e 0d 0a 41 63 6f 6e 74 65 63 65 20 71 75 65 20 6d 65 20 61 70 61 69 78 6f 6e 65 69 20 70 6f 72 20 4c 61 72 69 73 73 61 20 47 69 61 63 6f 6d 65 6c 6c 69 2c 20 65 20 6a c3 a1 20 66 61 7a 69 61 20 31 20 61 6e 6f 20 71 75 65 20 6d 61 6e 74 69 61 20 65 73 73 65 20 73 65 6e 74 69 6d 65 6e 74 6f 2c 20 65 2c 20 65 6c 61 20 69 6e 69 63 69 61 6c 6d 65 6e 74 65 20 67 6f 73 74 61 76 61 20 64 65 20 6d 69 6d 2c 20 69 73 74 6f 20 70 6f 72 71 75 65 20 73 75 61 20 6d 65 6c 68 6f 72 20 61 6d 69 67 61 2c 20 4e 69 63 6f 6c 65 20 52 69 62 65 69 72 6f 20 6d 65 20 63 6f 6e 74 6f 75 2c 20 6d 61 73 20 65 75 20 63 6f 6d 65 63 65 69 20 61 20 64 65 6d 6f 73 74 72 61 72 20 71 75 65 20 67 6f 73 74 61 76 61 20 64 65 6c 61 20 65 20 65 6c 61 20 63 6f 6d 65 c3 a7 6f 75 20 61 20 73 65 72 20 61 72 72 6f 67 61 6e 74 65 20 63 6f 6d 69 67 6f 2e 20 41 74 c3 a9 20 71 75 65 2c 20 65 6e 74 c3 a3 6f 2c 20 65 75 20 74 65 6e 74 65 69 20 70 65 72 64 6f 61 72 20 74 6f 64 61 20 61 20 61 72 72 6f 67 c3 a2 6e 63 69 61 20 64 65 6c 61 20 65 2c 20 74 65 6e 74 61 72 20 76 6f 6c 74 61 73 20 61 74 72 c3 a1 73 20 63 6f 6d 20 65 6c 61 2e 0d 0a 4e 6f 20 66 69 6d 2c 20 65 6c 61 20 6d 65 20 6c 61 72 67 6f 75 20 65 2c 20 66 69 63 6f 75 20 63 6f 6d 20 75 6d 20 63 61 72 61 20 70 6f 72 20 31 20 73 65 6d 61 6e 61 20 65 2c 20 65 6e 74 c3 a3 6f 2c 20 74 65 72 6d 69 6e 6f 75 20 63 6f 6d 20 65 6c 65 20 70 6f 72 71 75 65 20 65 6c 65 20 22 6e c3 a3 6f 20 73 61 62 69 61 20 71 75 65 20 65 6c 61 20 71 75 65 72 69 61 20 75 6d 20 72 65 6c 61 63 69 6f 6e 61 6d 65 6e 74 6f 20 73 c3 a9 72 69 6f 22 20 65 20 66 69 63 6f 75 20 63 6f 6d 20 6f 75 74 72 61 73 20 64 75 72 61 6e 74 65 20 65 73 73 65 20 70 65 72 c3 ad 6f 64 6f 2e 0d 0a 41 74 75 61 6c 6d 65 6e 74 65 2c 20 65 6c 61 20 65 73 74 c3 a1 20 66 69 63 61 6e 64 6f 20 63 6f 6d 20 75 6d 61 20 70 65 73 73 6f 61 20 71 75 65 20 63 6f 6e 73 6f 6c 6f 75 20 65 6c 61 20 6e 61 20 66 65 73 74 61 20 65 6d 20 71 75 65 20 65 6c 61 20 74 65 72 6d 69 6e 6f 75 2e 20 4d 61 73 2c 20 6f 20 6d 61 69 73 20 74 72 69 73 74 65 20 c3 a9 20 71 75 65 20 73 65 69 2c 20 71 75 65 20 73 65 20 65 6c 65 20 6c 65 76 61 72 20 65 73 73 65 20 72 65 6c 61 63 69 6f 6e 61 6d 65 6e 74 6f 20 61 64 69 61 6e 74 65 2c 20 65 6c 61 20 69 72 c3 a1 20 73 65 20 6d 61 67 6f 61 72 20 6d 75 69 74 6f 20 6d 61 69 73 2e 0d 0a 4f 20 47 61 62 72 69 65 6c 20 65 20 6f 20 4a 6f c3 a3 6f 20 6a c3 a1 20 67 6f 73 74 61 72 61 6d 20 64 61 20 4e 69 63 6f 6c 65 2c 20 65 2c 20 6f 20 47 61 62 72 69 65 6c 20 6c 65 76 6f 75 20 75 6d 20 66 6f 72 61 20 64 61 20 4e 69 63 6f 6c 65 20 6a c3 a1 2c 20 6d 61 73 20 6f 73 20 64 6f 69 73 20 65 73 74 c3 a3 6f 20 62 65 6d 2e 20 4f 20 4a 6f c3 a3 6f 20 61 69 6e 64 61 20 67 6f 73 74 61 20 64 61 20 4e 69 63 6f 6c 65 2c 20 6d 61 73 20 6e c3 a3 6f 20 73 65 69 20 6d 75 69 74 6f 20 62 65 6d 20 64 6f 20 71 75 65 20 65 73 74 c3 a1 20 61 63 6f 6e 74 65 63 65 6e 64 6f 2e 0d 0a 41 6c c3 a9 6d 20 64 65 20 6d 69 6d 2c 20 6e 69 6e 67 75 c3 a9 6d 20 6e 6f 20 67 72 75 70 6f 20 73 61 62 65 20 71 75 65 20 65 78 69 73 74 65 20 6f 20 45 61 73 74 65 72 20 45 67 67 2c 20 70 6f 72 71 75 65 20 6f 20 61 70 70 20 66 6f 69 20 64 65 73 65 6e 76 6f 6c 76 69 64 6f 20 70 75 72 61 6d 65 6e 74 65 20 70 6f 72 20 6d 69 6d 2c 20 65 6e 74 c3 a3 6f 20 73 69 6e 74 61 2d 73 65 20 68 6f 6d 65 6e 61 67 65 61 64 6f 20 70 6f 72 20 73 61 62 65 72 20 6e 6f 73 73 61 20 68 69 73 74 c3 b3 72 69 61 2e 2e 2e 2e 20 53 61 62 65 72 20 63 6f 6d 6f 20 61 20 4c 61 72 69 73 73 61 20 6d 65 20 6d 61 67 6f 6f 75 2e 2e 2e 0d 0a 4f 62 72 69 67 61 64 6f 20 61 20 74 6f 64 6f 73 2e 2e 2e");
				}
			}
		});
		easterEggText.setTextSize(10);
		
		this.setOrientation(LinearLayout.VERTICAL);
		
		this.addView(titulo);
		this.addView(imagem);
		this.addView(texto);
		this.addView(easterEggText);
	}
}
