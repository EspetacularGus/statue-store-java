package com.statuestore.modelo;

import com.statuestore.bd.DataSource;
import java.sql.SQLException;

public class Funcionario {

    private DataSource ds = new DataSource();

    private static int id;
    private static String cpf;
    private static String nome;
    private static String sobrenome;
    private static String funcao;
    private static String nivelAcesso;

    public Funcionario(int id) throws SQLException {

        ds.newCon();
        ds.setStatement("SELECT * FROM Funcionario WHERE idFuncionario = ?;");
        ds.getStatement().setInt(1, id);

        ds.setResultSet(ds.getStatement());
        ds.getResultSet().next();

        setId(id);
        setCpf(ds.getResultSet().getString("cpf"));
        setNome(ds.getResultSet().getString("nome"));
        setSobrenome(ds.getResultSet().getString("sobrenome"));
        setFuncao(ds.getResultSet().getString("funcao"));
        setNivelAcesso(ds.getResultSet().getInt("idNivelAcesso"));

        ds.quit();
    }

    //Getters & Setters

    public static int getId() {
        return id;
    }

    public static void setId(int idFunc) {
        Funcionario.id = idFunc;
    }

    public static String getCpf() {
        return cpf;
    }

    public static void setCpf(String cpfFunc) {
        Funcionario.cpf = cpfFunc;
    }

    public static String getNome() {
        return nome;
    }

    public static void setNome(String nomeFunc) {
        Funcionario.nome = nomeFunc;
    }

    public static String getSobrenome() {
        return sobrenome;
    }

    public static void setSobrenome(String sobrenomeFunc) {
        Funcionario.sobrenome = sobrenomeFunc;
    }

    public static String getFuncao() {
        return funcao;
    }

    public static void setFuncao(String funcaoFunc) {
        Funcionario.funcao = funcaoFunc;
    }

    public static String getNivelAcesso() {
        return nivelAcesso;
    }

    public static void setNivelAcesso(int nivel) {

        switch(nivel) {
            case 2:
                nivelAcesso = "Básico";
                break;
            case 3:
                nivelAcesso = "Administrador";
                break;
            case 4:
                nivelAcesso = "Gerente";
                break;
        }
    }
}
