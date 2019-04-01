package Items;

import Items.PcsInterface;
import Items.Piece;

public class Bottle extends Drink implements Piece {
    private int amount;

    public Bottle(String name, double price, boolean sweet, int amount) {
        super(name, price, sweet);
        this.amount=amount;
    }

    public Bottle(String name, double price, int amount){
         this(name, price, false,amount);
    }

    public Bottle(String name, double price, boolean sweet){
        this(name, price, sweet, 1);
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {

    }

    @Override
    public double getTotalPrice() {
        return amount*getPrice();
    }

}