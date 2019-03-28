package Main;

import Bill.Bill;
import Exception.BillException;
import Items.Category;
import Items.Goods;
import Items.Item;
import Items.Bottle;
import Items.Draft;
//import Items.Drink;
import Items.Food;
import Items.Fruit;
import Items.Pastry;
import java.io.IOException;
import java.sql.SQLException;

public class Aplication {
	
	private static Aplication app= new Aplication();

    private Aplication(){

    }

    public static Aplication getInstance(){
        return app;
    }

    public void example () throws BillException, IOException, SQLException {
        Bill bill = new Bill();

        bill.print();
        Bottle milk=new Bottle("milk 1,5", 1, 3);
        bill.addItem(milk);
        Item pizza = new Pastry("Chawaj", 2, 150, 8);
        bill.addItem(pizza);
        Food frut = new Fruit("Tarberry", 0.3, 1);
        bill.addItem(frut);
        Goods rad = new Goods("Dosimeter", 4, 8, Category.SCHOOL);
        bill.addItem(rad);
        Draft inj= new Draft("Tetanovka", 1.5, true, 2);
        bill.addItem(inj);
        bill.addItem(null);
        Draft beer= new Draft("Tesco beer", 1, true, 0.5);
        bill.addItem(beer);
        bill.removeItem(beer);
        bill.print();
        System.out.println(bill.getFinalPrice());
        Internet.getUSDrate();
        bill.end();
    }
}