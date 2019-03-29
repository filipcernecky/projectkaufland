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

    private static Database dtbs = new Database();

    private Database() {
    }

    public static Database getInstanceDB(){
        return dtbs;
    }

    private Connection getConnection(){
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connection Complete");
            connection = DriverManager.getConnection(url,userName,password);
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public void insertNewBill(Bill bill) throws SQLException {
        Connection con = getConnection();
        String query1 = "insert into bill(totalPrice,date, time) values(?,?,?)";
        String query2 = "insert into item(orderId,name,price,count,unit) values(?,?,?,?,?)";
        try {
            con.setAutoCommit(false);
            PreparedStatement statement = con.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, bill.getFinalPrice());
            statement.setDate(2, new java.sql.Date(bill.getDate().getTime()));
            statement.setTime(3, new java.sql.Time(bill.getDate().getTime()));

            int uptodate = statement.executeUpdate();

            if (uptodate == 0) {
                throw new SQLException("Error, Bill is not Up to Date!");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()){
                    for (Item item : bill.getList()) {
                        PreparedStatement statement2 = con.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                        statement2.setDouble(1, generatedKeys.getLong(1));
                        statement2.setString(2, item.getName());
                        statement2.setDouble(3, item.getPrice());

                        if (item instanceof DraftInterface) {
                            statement2.setDouble(4, ((DraftInterface) item).getVolume());
                            statement2.setString(5, "l");
                        } else if (item instanceof Fruit) {
                            statement2.setDouble(4, ((Fruit) item).getWeight());
                            statement2.setString(5, "kg");
                        } else if (item instanceof Piece) {
                            statement2.setDouble(4, ((Piece) item).getAmount());
                            statement2.setString(5, "pcs");
                        }

                        statement2.executeUpdate();

                    }
                }
                else {
                    throw new SQLException("Error. Bill was not created!");
                }

            }
            con.commit();
            con.close();
        }
            catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        }

    }

}