package com.statuestore.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.jtds.jdbc.Driver;

class ConnectionPool {

    private static List<Connection> listCon = new ArrayList<>();
    private static List<Connection> usingCon = new ArrayList<>();

    private static final String URL = "jdbc:jtds:sqlserver://DESKTOP-I7EC2HA/StatueStore";
    private static final String USR = "sa";
    private static final String PSWRD = "gustavo123";

    //Initializer
    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

     static Connection newCon() {

        Connection aCon;

        try {

            if (listCon.isEmpty()) {
                listCon.add(DriverManager.getConnection(URL, USR, PSWRD));
            }

            aCon = listCon.get(0);
            listCon.remove(aCon);
            usingCon.add(aCon);
            return aCon;

        } catch (SQLException e) {

            e.printStackTrace();
            return aCon = null;
        }
    }

    static void releaseCon(Connection con) {
        listCon.add(con);
        usingCon.remove(con);
    }
}
