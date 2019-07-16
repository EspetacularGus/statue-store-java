package com.statuestore.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {

    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    public void newCon() {
        setConnection(ConnectionPool.newCon());
    }

    public void quit() {
        ConnectionPool.releaseCon(getConnection());
    }

    //Setters
    public void setStatement(String query) throws SQLException {
        stmt = con.prepareStatement(query);
    }

    public void setResultSet(PreparedStatement stmt) throws SQLException {
        rs = stmt.executeQuery();
    }

    private void setConnection(Connection con) {
        this.con = con;
    }

    //Getters
    public PreparedStatement getStatement() {
        return stmt;
    }
    public ResultSet getResultSet() {
        return rs;
    }
    public Connection getConnection() { return con; }
}
