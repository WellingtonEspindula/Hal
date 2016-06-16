package hal.esy.es.utils;

/**
 * Created by wellington on 19/01/15.
 */
public class ManipulacaoDeString {
    public static String passarArrayParaTexto(int[] mensagem) {
        String texto = "";
        for (int i = 0; i < mensagem.length; i++) {
            texto += (mensagem[i]+"");
        }
        return texto;
    }
}
