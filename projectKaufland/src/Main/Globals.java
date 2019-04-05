package Main;

public class Globals {
    public static final int MAXITEMS;

    static {
        MAXITEMS = 7; 
    }
    
    public static final String userName = "customer";
    public static final String password = "buy";
    public static final String url = "jdbc:mysql://localhost:3308/klaufland";

    public static final String port = "27017";
    public static final String uri = "mongodb://localhost:"+port;
    public static final String mongoUser = "custom";
    public static final String mongoPass = "vpr";
    public static final String nameDatabase = "klaufland";
    
    /*public void print( String message){
    	System.out.println(message);
    }

    public void print(String message){
    	System.out.print(message);
    }*/
}
