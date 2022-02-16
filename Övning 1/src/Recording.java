abstract class Recording extends Item implements Vat25 {

protected static final double MAX_CONDITION = 10.0;
    protected final int year;
    protected final String artist;
    protected final double price;
    protected final int condition;
    private final String type;
    

    

     Recording(String name, String type, int year, String artist,
                     int condition , double price) {
        super(name);
        this.year = year;
        this.artist = artist;
        this.price = price;
        this.type = type;
        this.condition = condition;
    }



      @Override
    public String toString() {
        return "name= " + name + " artist= " + artist + " year= " + year + " type= " + type + "(" + getClass().getSuperclass().getTypeName() + ")"+
                " condition= " + condition + "Price= " + price + "Original price= " + getPrice() + " price+VAT= " + getPricePlusVAT();

    }
}
