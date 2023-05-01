package com.example.demo.week4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

enum Category {
    MOBILE,
    LAPTOP,
    TV,
    REFRIGERATOR
}

class Item {
    private int id;
    private String name;
    private Boolean warranty;
    private Category category;

    public Item(int id, String name, Boolean warranty, Category category) {
        this.id = id;
        this.name = name;
        this.warranty = warranty;
        this.category = category;
    }

    public Boolean getWarranty() {
        return warranty;
    }

    @Override
    public String toString() {
        return "Item{ id: " + id + ", name: " + name + ", warranty: " + warranty + ", category: " + category;
    }

}

public class Streamsq1 {

    public static void main(String args[]) {

        Item item1 = new Item(1, "Sony tv", true, Category.TV);
        Item item2 = new Item(2, "Sony fridge", false, Category.REFRIGERATOR);
        Item item3 = new Item(3, "Sony computer", false, Category.LAPTOP);
        Item item4 = new Item(4, "Sony mobile", true, Category.MOBILE);
        Item item5 = new Item(5, "Samsung fridge", true, Category.REFRIGERATOR);

        List<Item> inputItems = new ArrayList<>();
        inputItems.add(item1);
        inputItems.add(item2);
        inputItems.add(item3);
        inputItems.add(item4);
        inputItems.add(item5);
        Predicate<Item> condition = s -> s.getWarranty() == true;
        Map<Boolean, List<Item>> map = inputItems.stream()
                .collect(Collectors.partitioningBy(condition));
        List<List<Item>> result = new ArrayList<>();
        result.add(map.get(true));
        result.add(map.get(false));
        System.out.println("With warranty :\n" + result.get(0));
        System.out.println("Without warranty :\n" + result.get(1));

    }

}
