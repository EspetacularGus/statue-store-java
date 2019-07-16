package com.statuestore.modelo;

import com.statuestore.bd.DataSource;
import java.sql.SQLException;

public class LoggedAnunciante {

    private static DataSource ds = new DataSource();

    private static int id;
    private static String cpf;
    private static String nome;
    private static String sobrenome;
    private static String email;
    private static int numAnuncios;

    public LoggedAnunciante(int id) {
        try {
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
            setNumAnuncios(Anuncio.totalAnuncios(getId()));
            setEmail(ds.getResultSet().getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Setters

    private static void setId(int id) {
        LoggedAnunciante.id = id;
    }

    private static void setCpf(String cpf) {
        LoggedAnunciante.cpf = cpf;
    }

    private static void setNome(String nome) {
        LoggedAnunciante.nome = nome;
    }

    private static void setSobrenome(String sobrenome) {
        LoggedAnunciante.sobrenome = sobrenome;
    }

    private static void setNumAnuncios(int total) {
        LoggedAnunciante.numAnuncios = total;
    }

    //Getters

    public static int getId() {
        return id;
    }

    public static String getCpf() {
        return cpf;
    }

    public static String getNome() {
        return nome;
    }

    public static String getSobrenome() {
        return sobrenome;
    }

    public static int getNumAnuncios() {
        return numAnuncios;
    }

    public static int getNumAnunciosWhere(int tipo) {
        try {
            return Anuncio.totalAnuncios(getId(), tipo);
        } catch (SQLException e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        LoggedAnunciante.email = email;
    }
}
