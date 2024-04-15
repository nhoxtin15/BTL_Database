package org.example.database_btl.Backend;

import java.sql.*;

public class Sql_connector {
    private static final String url = "jdbc:mysql://localhost:3306/company";
    private static final String user = "root";
    private static final String password = "nhoxtin1";

    private static Connection Sql_server;
    private static Statement statement;
    private static ResultSet resultSet;



    public static synchronized void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Sql_server = DriverManager.getConnection(url, user, password);
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
