import java.util.ArrayList;
import java.util.Arrays;

public class Order {

    private int orderCount;

    private double totalPrice;
    private double totalPriceVAT;

    private ArrayList<Item> items = new ArrayList<Item>();

    public Order(Item ...o) {
        addToList(o);
        orderCount++;
    }

    private void addToList(Item [] o) {
        items.addAll(Arrays.asList(o));
        totalPrice = getTotalValue();
        totalPriceVAT = getTotalValuePlusVAT();
    }

    public double getTotalValue() {
        double total = 0;
        for (Item o: items) {
            total += o.getPrice();
        }
        return total;
    }

     public double getTotalValuePlusVAT() {

        double totalPlusVat = 0;

        for (Item o: items) {
            totalPlusVat += o.getPricePlusVAT();
        }
     
        return totalPlusVat;
    }

    public String getReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Receipt for order #" + orderCount + "\n");
        for (Item o: items) {
            receipt.append(o.toString() + "\n");
        }
        receipt.append("Total excl. VAT: ").append(totalPrice).append("\n");
        receipt.append("Total incl. VAT: ").append(totalPriceVAT).append("\n");
        return receipt.toString();
    }


}
