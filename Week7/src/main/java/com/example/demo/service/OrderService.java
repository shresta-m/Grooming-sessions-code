package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.OrdersDTO;
import com.example.demo.exception.ResourceException;
import com.example.demo.model.Item;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order placeorder(OrdersDTO ordersDTO) {
        Order order = new Order();
        List<Integer> ids = ordersDTO.getProduct_ids();
        List<Integer> quantity = ordersDTO.getQuantities();
        Set<Item> items = new HashSet<>();
        Double totalPrice = 0.0;
        List<Product> allProducts = productRepository
                .findAllById(ordersDTO.getProduct_ids().stream().map(Integer::longValue)
                        .collect(Collectors.toList()));
        log.info(allProducts.toString());
        if (allProducts.size() != ids.size()) {
            throw new ResourceException("Enter valid Product ids for placing the order");
        }
        for (int i = 0; i < ids.size(); i++) {
            Product product = allProducts.get(i);
            Item item = new Item();
            item.setItemIdFk((Long.valueOf(ids.get(i))));
            item.setPrice(Double.valueOf(product.getPrice()));
            if (quantity.get(i) <= product.getQuantity()) {
                totalPrice = totalPrice + product.getPrice() * quantity.get(i);
                product.setQuantity(product.getQuantity() - quantity.get(i));
                productRepository.save(product);
                item.setItemQuantity(quantity.get(i));
            } else {
                item.setItemQuantity(0);
            }
            items.add(itemRepository.save(item));
        }
        order.setItems(items);
        order.setPrice(totalPrice);
        order.setUserId(Long.valueOf(ordersDTO.getUser_id()));
        order.setQuantity(quantity.stream().mapToLong(i -> i).sum());
        log.info(order.toString());
        return orderRepository.save(order);
    }

}


