package hal.esy.es.model;


public class Usuario {

    private int id;
    private String nome;
    private int pontuacao;
    private String id_device;

    public Usuario() {
    }

    public Usuario(int id, String nome, int pontuacao, String id_device) {
        this.id = id;
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.id_device = id_device;
    }

    public String getId_device() {
        return id_device;
    }

    public void setId_device(String id_device) {
        this.id_device = id_device;
    }

    public Usuario(int id, String nome) {
        this.setId(id);
        this.setNome(nome);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    @Override
    public String toString() {
        return  nome;
    }
}
