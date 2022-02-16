public abstract class Item implements Vat {

    protected final String name;

    protected Item(String name) {
        this.name = name;
    }

    public abstract double getPrice();

    public final double getPricePlusVAT () {
        return getPrice() + (getPrice() * getVAT());
    }
}