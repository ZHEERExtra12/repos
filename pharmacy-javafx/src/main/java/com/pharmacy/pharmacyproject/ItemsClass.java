package com.pharmacy.pharmacyproject;

import javafx.beans.property.*;

public class ItemsClass {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty barcode;
    private final IntegerProperty quantity;
    private final DoubleProperty price;
    private final DoubleProperty profit;
    private final StringProperty expiray;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemsClass that = (ItemsClass) o;
        return barcode.get().equals(that.barcode.get());
    }

    @Override
    public int hashCode() {
        return barcode.get().hashCode();
    }

    public ItemsClass(int id, String name, String barcode, int quantity, double price, double profit, String expiray) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.barcode = new SimpleStringProperty(barcode);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.profit = new SimpleDoubleProperty(profit);
        this.expiray = new SimpleStringProperty(expiray);
    }

    // Getters for all properties
    public IntegerProperty getId() {
        return id;
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getBarcode() {
        return barcode;
    }

    public IntegerProperty getQuantity() {
        return quantity;
    }

    public DoubleProperty getPrice() {
        return price;
    }

    public DoubleProperty getProfit() {
        return profit;
    }

    public StringProperty getDateOfExpiry() {
        return expiray;
    }
}
