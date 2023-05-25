package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", unique = true, nullable = false)
    private Long itemId;

    @Column(name = "item_id_fk", nullable = false)
    private Long itemIdFk;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer itemQuantity;

    public Item(Long itemIdFk,Double price,Integer itemQuantity){
        this.itemIdFk=itemIdFk;
        this.price=price;
        this.itemQuantity=itemQuantity;
    }

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "order_id")
    // private Order order;
}
