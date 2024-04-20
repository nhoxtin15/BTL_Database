package org.example.database_btl.Backend;

import java.sql.*;

public class Sql_connector {
    private static final String url = "jdbc:mysql://0.0.0.0:3306/hotpot";

    private final String user ;
    private final String password ;

    private static Connection Sql_server;
    private static Statement statement;
    private static ResultSet resultSet;

    private Sql_connector() {
        user = "root";
        password = "nhoxtin1";
    }

    private  static  Sql_connector instance;
    public static synchronized Sql_connector getInstance() {
        if(instance == null) {
            instance = new Sql_connector();
        }
        return instance;
    }


    public static synchronized void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Sql_server = DriverManager.getConnection(url, getInstance().user, getInstance().password);
            statement = Sql_server.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static synchronized ResultSet executeQuery(String query) {
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public static synchronized void executeUpdate(String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }





}
