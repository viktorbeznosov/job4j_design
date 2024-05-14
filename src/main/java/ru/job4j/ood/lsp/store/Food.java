package ru.job4j.ood.lsp.store;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Food {
    private String name;

    private LocalDate expiryDate;

    private LocalDate createDate;

    private float price;

    private int discount;

    public Food() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public float getPrice() {
        return price - price / 100 * discount;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Food(String name, LocalDate createDate, LocalDate expiryDate, float price) {
        this.name = name;
        this.createDate = createDate;
        this.expiryDate = expiryDate;
        this.price = price;
        this.discount = 0;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Food food = (Food) o;
        return Float.compare(food.price, price) == 0 && Objects.equals(name, food.name) && Objects.equals(expiryDate, food.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, expiryDate, price);
    }

    public float getExpirationPercent() {
        int daysSinceCreation = (int) ChronoUnit.DAYS.between(getCreateDate(), LocalDate.now());
        int daysLeftExpirationDate = (int) ChronoUnit.DAYS.between(LocalDate.now(), getExpiryDate());
        float expirationPercent = daysSinceCreation * 100 / (daysSinceCreation + daysLeftExpirationDate);
        return expirationPercent;
    }
}
