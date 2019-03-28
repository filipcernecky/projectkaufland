package Main;

public class Globals {
    public static final int MAXITEMS;

    static {
        MAXITEMS = 7; 
    }
    
    public static final String userName = "customer";
    public static final String password = "buy";
    public static final String url = "jdbc:mysql://localhost:3308/klaufland";

    public void println( String message){
    	System.out.println(message);
    }

    public void print(String message){
    	System.out.print(message);
    }
}