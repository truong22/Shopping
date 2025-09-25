package com.dailycodework.dreamshops.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private  String brand;
    private String Desciption;
    private int inventory;
    private BigDecimal price;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image>images;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(String name, String brand,BigDecimal price,  int inventory, String desciption, Category category) {
        this.name = name;
        this.brand = brand;
       this.Desciption = desciption;
        this.inventory = inventory;
        this.price = price;
        this.category = category;
    }


}
