public class Book extends Item implements Vat6 {

 public static final double BOUND_RATE = 0.25;
    private final String author;
    private double price;
    private boolean bound;

   

    public Book(String name, String author, double price, boolean bound) {
        super(name);
        this.price = price;
        this.author = author;
        this.bound = bound;
    }

    @Override
    public double getPrice() {
        if (bound)
        {
            return  price + (price * BOUND_RATE);
        }
        else
            return price;
    }

    private double getPriceAfterVAT() {
        return getPrice() + (getPrice() * getVAT());
    }
 @Override
    public String toString() {
        return "name= " + name + " author= " + author + " type= " + this.getClass().getTypeName() +  " bound= " + bound + " price= " + getPrice() + " price+VAT= " + getPriceAfterVAT();
    }
}
