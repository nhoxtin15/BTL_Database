package org.example.database_btl.Backend;

import org.example.database_btl.Exception.SqlException;

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

    private String user ;
    private String password ;

    private  Connection Sql_server;
    private  Statement statement;

    private ResultSet resultSet;

    ////////////////////////////////
    //                            //
    //      ResultSet's Lock      //
    //                            //
    ////////////////////////////////
    public static final Object lock = new Object();

    private Sql_connector(String user, String password) {
        this.user = user;
        this.password = password;
    }


    ////////////////////////////////
    //  SqlConnector's SingleTon  //
    //           Stuffs           //
    ////////////////////////////////

    private static  Sql_connector instance;
    public static synchronized Sql_connector getInstance(){
        return instance;
    }

    public static synchronized void setInstance(String user, String password) throws Exception{
        instance = new Sql_connector(user, password);
        connect();
    }


    //////////////////////////////////
    //                              //
    //Connector and queries executor//
    //                              //
    //////////////////////////////////


    public static synchronized void connect() throws  Exception{

        Class.forName("com.mysql.cj.jdbc.Driver");
        getInstance().Sql_server = DriverManager.getConnection(url, getInstance().user, getInstance().password);
        getInstance().statement = getInstance().Sql_server.createStatement();


    }


    public static synchronized ResultSet executeQuery(String query) throws Exception{
        try {
            getInstance().resultSet = getInstance().statement.executeQuery(query);

        } catch (SQLException throwables) {
            throw new SqlException(query);
        }
        return getInstance().resultSet;
    }

    public static synchronized void executeUpdate(String query) throws Exception {
        try {
            System.out.println(getInstance().statement.executeUpdate(query));
        } catch (SQLException throwables) {
            throw new SqlException(query);
        }
    }






}
