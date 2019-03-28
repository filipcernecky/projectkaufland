package Bill;

import Exception.BillException;
import Database.Database;
import Items.Item;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Items.DraftInterface;
import Items.PcsInterface;

import Items.Piece;
import Items.Fruit;
import Main.Globals;
import Main.Internet;

public class Bill {
    private int count;
    private boolean open;

    private List<Item>  list;

    public Bill() {
        this.list = new ArrayList<>();
        count =0;
        open=true;
    }

    public void end(){
        open=false;
    }

    public void addItem(Item item) throws BillException{
        if(item!=null) {
            if(open==false){
                String message = "Bill is closed. No more items!";
                throw new BillException(message);
            }
            if(count==Globals.MAXITEMS) {
                String message = "Bill is full, maximum is "+Globals.MAXITEMS+" items";
                throw new BillException(message);
            }
            list.add(item);
            count++;
        }
    }
    public void removeItem(Item item){
        if(list.contains(item)) {
            list.remove(item);
            count--;
        }
    }
    public double getFinalPrice(){
        throw new UnsupportedOperationException("Method does not exists");
    }

    public void print(){
        if(count==0)
            System.out.println("Bill is epmty !");
        else{
            for(Item item:list){
                if(item instanceof DraftInterface){
                    System.out.print(item.getName()+" "+((DraftInterface) item).getVolume()+" ");
                    System.out.println(item.getPrice()+" "+item.getTotalPrice());
                }
                else if(item instanceof Fruit){
                    System.out.print(item.getName()+" "+((Fruit)item).getWeight()+" ");
                    System.out.println(item.getPrice()+" "+item.getTotalPrice());
                }
                else if(item instanceof PcsInterface){
                    System.out.print(item.getName()+" "+((PcsInterface)item).getAmount()+" ");
                    System.out.println(item.getPrice()+" "+item.getTotalPrice());
                } {

                }

            }
        }
    }
    public int getCount(){
        return count;
    }

    /*public double getTotalPriceUSD() throws IOException {
        double totalPrice = getFinalPrice();
        double sum = totalPrice * Internet.getUSDrate();
        return sum;
    }*/

}