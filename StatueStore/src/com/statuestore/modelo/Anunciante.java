package com.statuestore.modelo;

import com.statuestore.bd.DataSource;

import java.sql.SQLException;

public class Anunciante {

    private static DataSource ds = new DataSource();

    private int id;
    private String cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone1;
    private String telefone2;
    private String cidade;
    private String endereco;
    private int numAnuncios;

    public Anunciante(int id) {
        try {
            int idEndereco;

            ds.newCon();
            ds.setStatement("SELECT * FROM Anunciante WHERE idAnunciante = ?");
            ds.getStatement().setInt(1, id);
            ds.setResultSet(ds.getStatement());
            ds.getResultSet().next();
            ds.quit();

            setId(id);
            setNome(ds.getResultSet().getString("nome"));
            setSobrenome(ds.getResultSet().getString("sobrenome"));
            setCpf(ds.getResultSet().getString("cpf"));
            setTelefone1(ds.getResultSet().getString("telefone1"));
            setTelefone2(ds.getResultSet().getString("telefone2"));
            setNumAnuncios(Anuncio.totalAnuncios(getId()));
            setEmail(ds.getResultSet().getString("email"));
            idEndereco = ds.getResultSet().getInt("idEndereco");


            ds.newCon();
            ds.setStatement("SELECT * FROM Endereco WHERE idEndereco = ?");
            ds.getStatement().setInt(1, idEndereco);
            ds.setResultSet(ds.getStatement());
            ds.getResultSet().next();
            ds.quit();

            setEndereco(ds.getResultSet().getString("logradouro"));
            setCidade(ds.getResultSet().getString("cidade"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDs() {
        return ds;
    }

    public static void setDs(DataSource ds) {
        Anunciante.ds = ds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumAnuncios() {
        return numAnuncios;
    }

    public void setNumAnuncios(int numAnuncios) {
        this.numAnuncios = numAnuncios;
    }
}
