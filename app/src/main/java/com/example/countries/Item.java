package com.example.countries;

public class Item {
    private String name;

    public static final Item[] items = {
            new Item("Country Population"),
            new Item("Country Currency")
    };

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
