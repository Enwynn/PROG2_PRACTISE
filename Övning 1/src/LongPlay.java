import java.util.Calendar;

public class LongPlay extends Recording {

    private static final String TYPE = "LP";
    private static final int YEARLY_RARITY_PRICE_INCREASE = 5;

    public LongPlay(String name, String artist, int year, int condition, double price) {
        super(name, TYPE, year, artist,  condition, price);
    }

    @Override
    public double getPrice() {
        double conTemp = MAX_CONDITION - condition;
        double conditionCalculator = conTemp / MAX_CONDITION;
        double priceAfterCondition = price - (price * conditionCalculator);
        priceAfterCondition = priceAfterCondition + ((Calendar.getInstance().get(Calendar.YEAR) - year) * YEARLY_RARITY_PRICE_INCREASE);

        if (priceAfterCondition < 10){
            return MAX_CONDITION;
        }
        else {
            return priceAfterCondition;
        }
    }
}