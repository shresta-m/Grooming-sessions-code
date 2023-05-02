package com.example.demo.week4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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

    public Category getCategory() {
        return category;
    }


    @Override
    public String toString() {
        return "Item{ id: " + id + ", name: " + name + ", warranty: " + warranty + ", category: " + category;
    }

}

public class Streamsq1 {

    public static Category checkCategory(String ip) throws CustomException {
        Category[] categories = Category.values();
        for (Category c: categories) {
            if (c.toString().equalsIgnoreCase(ip)) {
                return c;
            }
        }
        throw new CustomException("Given Category does not exist!!. Enter valid one");
    }

    public static void main(String args[]) {

        Item item1 = new Item(1, "Sony tv", true, Category.TV);
        Item item2 = new Item(2, "Sony fridge", false, Category.REFRIGERATOR);
        Item item3 = new Item(3, "Sony computer", false, Category.LAPTOP);
        Item item4 = new Item(4, "Sony mobile", true, Category.MOBILE);
        Item item5 = new Item(5, "Samsung fridge", true, Category.REFRIGERATOR);
        Item item6 = new Item(3, "Sony computer", true, Category.LAPTOP);
        Item item7 = new Item(3, "Sony computer", true, Category.LAPTOP);

        List<Item> inputItems = new ArrayList<>();
        inputItems.add(item1);
        inputItems.add(item2);
        inputItems.add(item3);
        inputItems.add(item4);
        inputItems.add(item5);
        inputItems.add(item6);
        inputItems.add(item7);
        String cIn ;
        try (Scanner in = new Scanner(System.in)) {
            try {
                cIn = in.next();
                Category c = checkCategory(cIn);
                Predicate<Item> condition = s -> s.getWarranty() == true;
                Predicate<Item> categoryCondition = s-> s.getCategory().equals(c);
                Map<Boolean, List<Item>> map = inputItems.stream().filter(categoryCondition)
                        .collect(Collectors.partitioningBy(condition));
                System.out.println(map.toString());
                List<List<Item>> result = new ArrayList<>();
                result.add(map.get(true));
                result.add(map.get(false));
                System.out.println("With warranty :\n" + result.get(0));
                System.out.println("Without warranty :\n" + result.get(1));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
