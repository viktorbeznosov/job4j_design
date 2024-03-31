package ru.job4j.ood.lsp;

public class DiscountCard {
    protected int purchaseAmount = 0;

    protected void bue(int sum) {
        purchaseAmount += sum;
    }

    protected int getDiscount() {
        if (purchaseAmount > 1000) {
            return 15;
        }

        return 0;
    }

    public int getPrice(int price) {
        return price - price / 100 * getDiscount();
    }
}
