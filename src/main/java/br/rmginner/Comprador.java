package br.rmginner;

public class Comprador {

    private String nome;

    private String nomeVendedor;

    private String itemDesejado;

    private Double valor;

    public Comprador() {
    }

    public Comprador(String nome, String nomeVendedor, String itemDesejado, Double valor) {
        this.nome = nome;
        this.nomeVendedor = nomeVendedor;
        this.itemDesejado = itemDesejado;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getItemDesejado() {
        return itemDesejado;
    }

    public void setItemDesejado(String itemDesejado) {
        this.itemDesejado = itemDesejado;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getNomeVendedor() {
        return nomeVendedor;
    }

    public void setNomeVendedor(String nomeVendedor) {
        this.nomeVendedor = nomeVendedor;
    }

    @Override
    public String toString() {
        return "Comprador{" +
                "nome='" + nome + '\'' +
                ", nomeVendedor='" + nomeVendedor + '\'' +
                ", itemDesejado='" + itemDesejado + '\'' +
                ", valor=" + valor +
                '}';
    }
}
