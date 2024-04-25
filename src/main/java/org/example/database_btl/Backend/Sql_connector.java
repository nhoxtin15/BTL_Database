package org.example.database_btl.Backend;

import java.sql.*;

public class Sql_connector {

    ////////////////////////////////
    //                            //
    //        Mysql's Url         //
    //                            //
    ////////////////////////////////

    private static final String url = "jdbc:mysql://0.0.0.0:3306/hotpot";

    ////////////////////////////////
    //                            //
    //   SqlConnector's stuffs    //
    //                            //
    ////////////////////////////////

    private final String user ;
    private final String password ;

    private  Connection Sql_server;
    private  Statement statement;

    private ResultSet resultSet;

    ////////////////////////////////
    //                            //
    //      ResultSet's Lock      //
    //                            //
    ////////////////////////////////
    public static final Object lock = new Object();

    private Sql_connector() {
        user = "root";
        password = "nhoxtin1";

    }

    ////////////////////////////////
    //  SqlConnector's SingleTon  //
    //           Stuffs           //
    ////////////////////////////////

    private static  Sql_connector instance;
    public static synchronized Sql_connector getInstance() {
        if(instance == null) {
            instance = new Sql_connector();
            connect();
        }
        return instance;
    }


    //////////////////////////////////
    //                              //
    //Connector and queries executor//
    //                              //
    //////////////////////////////////


    public static synchronized void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            getInstance().Sql_server = DriverManager.getConnection(url, getInstance().user, getInstance().password);
            getInstance().statement = getInstance().Sql_server.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static synchronized ResultSet executeQuery(String query) {
        try {
            getInstance().resultSet = getInstance().statement.executeQuery(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getInstance().resultSet;
    }

    public static synchronized void executeUpdate(String query) {
        try {
            getInstance().statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }





}
