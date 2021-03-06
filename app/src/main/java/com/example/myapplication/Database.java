package com.example.myapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database {

    private Connection connection;

    private final String host = "217.25.230.151";

    private final String database = "niggerfaggot";
    private final int port = 5432;
    private final String user = "postgres";
    private final String pass = "postgre";
    private String url = "jdbc:postgresql://%s:%d/%s";
    private boolean status;

    public Database()
    {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
        //this.disconnect();
        System.out.println("connection status:" + status);
    }

    private void connect()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    System.out.println("connected:" + status);
                }
                catch (Exception e)
                {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }
    }

    public Connection getExtraConnection()
    {
        Connection c = null;
        try
        {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return c;
    }

    /*public ResultSet GetTaskLDS()
    {
        ResultSet rs = null;

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();){

            String selectSql = "SELECT skill_task FROM lds_word WHERE skill_id = 1";
            rs = statement.executeQuery(selectSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }*/

}
