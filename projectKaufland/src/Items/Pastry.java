package Items;

import Items.PcsInterface;
import Items.Piece;

public class Pastry extends Food implements Piece {
    private int amount;

    public Pastry(String name, double price, int callories, int amount){
        super(name,price,callories);
        this.amount=amount;
    }

    public Pastry(String name, double price, int amount){
        this(name,price,-1,amount);
    }

    @Override
    public int getAmount() {
       return amount;
    }

    @Override
    public void setAmount(double amount) {

    }

    @Override
    public double getTotalPrice(){
        return amount*getPrice();
    }

}