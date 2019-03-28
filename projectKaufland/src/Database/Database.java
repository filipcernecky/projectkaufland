package Database;

import java.sql.*;

import static Main.Globals.userName;
import static Main.Globals.url;
import static Main.Globals.password;
import Bill.Bill;

import Items.Item;
import Items.Piece;
import Items.DraftInterface;
import Items.Fruit;

public class Database {

    private static Database db = new Database();

    private Database() {
    }

    public static Database getInstanceDB(){
        return db;
    }

    private Connection getConnection(){
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Is connection");
            connection = DriverManager.getConnection(url,userName,password);
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
    
    

}