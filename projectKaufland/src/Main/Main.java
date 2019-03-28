package Main;

import Exception.BillException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws BillException, IOException, SQLException {

        Aplication app =  Aplication.getInstance();
        app.example();

    }
}