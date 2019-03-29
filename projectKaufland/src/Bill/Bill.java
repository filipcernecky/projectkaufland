package Bill;

import Exception.BillException;
import java.io.IOException;
import java.sql.SQLException;

import Database.Database;
import Items.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Items.Piece;
import Items.Fruit;
import Main.Globals;
import Main.Internet;
import Items.DraftInterface;

public class Bill {
    private List<Item> list;
    private int count;
    private  double sum;
    private boolean open;
    private  Date date;

    public Bill() {
        this.list = new ArrayList<>();
        count = 0;
        open=true;
    }

    public List<Item> getList() {
        return list;
    }

    public Date getDate() {
        return date;
    }

    public void end() throws SQLException {
        if(open){
            Database db = Database.getInstanceDB();
            db.insertNewBill(this);

        }
        else {
            System.out.println("Bill is open!");
        }

        open=false;

    }

    public void addItem(Item item) throws BillException {
        if (item != null) {
            if (!open){
                String message = "Bill is closed. no more items!";
                throw new BillException(message);
            }
            if (count == Globals.MAXITEMS) {
                String message = "Bill is full, max is " + Globals.MAXITEMS + " items.";
                throw new BillException(message);
            }
            list.add(item);
            count++;

        }
    }

    public void removeItem(Item item) {
        if (list.contains(item)){
            list.remove(item);
            count--;
        }
    }

    public double getFinalPrice(){
        sum=0;
        for(Item item: list){
            sum+=item.getTotalPrice();
        }

        return sum;

    }

    public int getCount(){
        return count;
    }

    public void print(){
        if (count==0){
            System.out.println("Bill is empty");
        } else{
            for (Item item:list) {
                if(item instanceof DraftInterface) {
                    System.out.println("Name: "+item.getName() + " Count " + ((DraftInterface) item).getVolume()+" Price: "+item.getPrice() + " TotalPrice: " + item.getTotalPrice());
                }
                else if(item instanceof Fruit){
                    System.out.println("Name: "+item.getName() + " Count " + ((Fruit) item).getWeight()+" Price: "+item.getPrice() + " TotalPrice: " + item.getTotalPrice());
                }
                else if(item instanceof Piece){
                    System.out.println("Name: "+item.getName() + " Count " + ((Piece) item).getAmount() +" Price: "+item.getPrice() + " TotalPrice: " + item.getTotalPrice());
                }
            }
            date = new Date();
            System.out.println( date);
        }
    }

    public double getTotalPriceUSD() throws IOException {
        double totalPrice = getFinalPrice();
        double sum = totalPrice * Internet.getUSDrate();
        return sum;
    }

}