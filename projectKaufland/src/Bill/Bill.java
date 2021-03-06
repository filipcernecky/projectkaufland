package Bill;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Exception.BillException;

import Database.Database;
import Database.MongoDatabase;

import Items.Item;
import Items.Piece;
import Items.Fruit;
import Items.Goods;
import Items.Bottle;
import Items.Draft;
import Items.Pastry;
import Items.Sweet;
import Main.Globals;
import Main.Internet;
import Items.DraftInterface;


public class Bill {
    private List<Item> list;
    private int count;
    private  double sum;
    private boolean open;
    private  Date date;
    private static int counter=0;
    private int id;

    public int getId() {
        return id;
    }

    public Bill() {
        this.list = new ArrayList<>();
        count = 0;
        open=true;
        sum=0;
        counter++;
        id=counter;
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
            MongoDatabaseD mngDb = MongoDatabaseD.getInstanceMongoDB();
            mngDb.addBillToMongoDB(this);

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
            Item existujuci = checkingItem(item);
            if(existujuci == null){
                list.add(item);
            }
            else{
                updateItem(existujuci,item);
            }
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

    public void updateItem(Item toUpdate, Item oldItem){
        if(toUpdate instanceof DraftInterface) {
            double newVolume = ((DraftInterface) toUpdate).getVolume()+ ((DraftInterface) oldItem).getVolume();
            ((DraftInterface) toUpdate).setVolume(newVolume);
        }
        else if(toUpdate instanceof Fruit){
            double newWeight = ((Fruit) toUpdate).getWeight() + ((Fruit) oldItem).getWeight();
            ((Fruit) toUpdate).setWeight(newWeight);
        }
        else if(toUpdate instanceof Piece){
            double newAmount = ((Piece) toUpdate).getAmount() + ((Piece) oldItem).getAmount();
            ((Piece) toUpdate).setAmount(newAmount);
        }
    }

    public Item checkingItem(Item item){
        for (Item item2: list) {
            if (item.getName().toLowerCase().equals(item2.getName().toLowerCase()) && item.getClass().getName().equals(item2.getClass().getName())) {
                return item2;
            }
        }
        return null;
    }

}