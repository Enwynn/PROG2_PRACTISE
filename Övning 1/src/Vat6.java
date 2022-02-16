public interface Vat6 extends Vat {

    default double getVAT() {
        return 0.06;
    }
}

