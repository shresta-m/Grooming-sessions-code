package com.example.demo.week3;

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
    public String toString(){
        return "Item{ id: "+id+" name: "+name+" warranty: "+warranty+" category: "+category;
    }

}

public class StreamsExample {

    public static void main(String args[]) {

        Item item1 = new Item(1, "Sony tv", true, Category.TV);
        Item item2 = new Item(2, "Sony fridge", false, Category.REFRIGERATOR);
        Item item3 = new Item(3, "Sony computer", false, Category.LAPTOP);
        Item item4 = new Item(4, "Sony mobile", true, Category.MOBILE);
        Item item5 = new Item(5, "Samsung fridge", true, Category.REFRIGERATOR);

        // This code is demonstrating the use of the `partitioningBy` method in Java
        // streams.
        // List<String> input = new ArrayList<>(Arrays.asList("one", "two", "three", "four", "five"));
        // Map<Boolean, List<String>> map = input.stream()
        //         .collect(Collectors.partitioningBy(s -> s.toString().length() == 3));
        // List<List<String>> result = new ArrayList<>();
        List<Item> inputItems = new ArrayList<>();
        inputItems.add(item1);
        inputItems.add(item2);
        inputItems.add(item3);
        inputItems.add(item4);
        inputItems.add(item5);
        Predicate<Item> condition = s -> s.getWarranty()==true;
        Map<Boolean, List<Item>> map = inputItems.stream()
                .collect(Collectors.partitioningBy(condition));
        List<List<Item>> result = new ArrayList<>();
        result.add(map.get(true));
        result.add(map.get(false));
        System.out.println("With warranty :\n"+ result.get(0));
        System.out.println("Without warranty :\n"+ result.get(1));
       

    }

}

// String[] arr = new String[] { "a", "b", "c" };
// Arrays.stream(arr).forEach(s->System.out.println(s));
// Stream.of("a", "b", "c").forEach(s->System.out.println(s));
// System.out.println(Arrays.stream(arr).distinct().count());
// List<String> arr1 = List.of("a","b","c","d");
// boolean isExist = arr1.stream().anyMatch(element -> element.contains("a"));
// System.out.println(isExist);
// List<String> filteredList = arr1.stream().filter(element ->
// element.contains("d")).collect(Collectors.toList());
// System.out.println(filteredList.toString());

// List<Integer> numberList = List.of(1,6,3,9,4,2,10,5,15,7,0,8,14,13,12);
// List<Integer> filtered = numberList.stream().filter(e->
// e>6).sorted().toList();
// System.out.println(filtered);

// int result = numberList.stream().filter(e->e>6).reduce(0,(a,b)-> (a+b));
// System.out.println(result);

// List<String> arr1 = List.of("ani","nib","cni","d");
// long c = arr1.stream().filter(element -> element.contains("ni")).count();
// System.out.println(c);

// System.out.println(arr1.stream().filter(element ->
// element.length()>4).count());
