public class CompactDisc extends Recording {

    private static final String TYPE = "CD";

    public CompactDisc(String name,  String artist, int year, int condition , double price) {
        super(name, TYPE,  year, artist, condition, price);
    }

    @Override
    public double getPrice() {
        double conTemp = MAX_CONDITION - condition;
        double conditionCalculator = conTemp / MAX_CONDITION;
        double priceAfterCondition = price - (price * conditionCalculator);
        return checkIfMinPrice(priceAfterCondition);
    }

    private double checkIfMinPrice(double p) {
        if (p < 10){
            return MAX_CONDITION;
        }

        else
            return p;
    }
}
