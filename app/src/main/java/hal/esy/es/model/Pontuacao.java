package hal.esy.es.model;

/**
 * Created by wellington on 29/11/15.
 */
public class Pontuacao {

    private int id;
    private int pontos;
    private Usuario usuario;

    public Pontuacao() {
    }

    public Pontuacao(int id, int pontos, Usuario usuario) {

        this.id = id;
        this.pontos = pontos;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
