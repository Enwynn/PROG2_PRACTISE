public interface Vat25 extends Vat {
    
    default double getVAT() {
        return 0.25;
    }
}
