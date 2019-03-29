package Main;

import Exception.BillException;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

//import java.io.IOException;
//import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws BillException, IOException, SQLException, TransformerException, ParserConfigurationException {

        Aplication app =  Aplication.getInstance();
        app.example();

    }
}