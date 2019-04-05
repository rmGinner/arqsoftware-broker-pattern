package br.rmginner;

public class Vendedor {

    private String nome;

    private String nomeItem;

    public Vendedor() {
    }

    public Vendedor(String nome, String nomeItem) {
        this.nome = nome;
        this.nomeItem = nomeItem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "nome='" + nome + '\'' +
                ", nomeItem='" + nomeItem + '\'' +
                '}';
    }
}
