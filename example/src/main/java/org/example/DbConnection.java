package org.example;

import java.sql.*;
public class DbConnection {

    String url = "jdbc:mysql://localhost:3306/mydatabase";
    String user = "root";
    String password = "mypassword";
    public DbConnection(){

    }

    public void Execute(){

        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM SOME_TABLE");
        }
        catch (Exception ex){



        }

    }

}
