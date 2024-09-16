package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
    private String user = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost/virtualstore";
    Connection connection = null;

    public Connection getConnection(){
        if(connection == null){
            try{
                connection = DriverManager.getConnection(this.url, this.user, this.password);
                System.out.println("Conectado!");
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return connection;
    }
}