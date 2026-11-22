
package br.com.omniroute.model;

public class Encomenda {

    private int id;
    private String cliente;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;
    private String uf;
    private String status;

    public Encomenda(String cliente, String endereco, String numero,
                     String bairro, String cidade, String cep,
                     String uf, String status) {

        this.cliente = cliente;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
        this.uf = uf;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getCep() {
        return cep;
    }

    public String getUf() {
        return uf;
    }

    public String getStatus() {
        return status;
    }
}
