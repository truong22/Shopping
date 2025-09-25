package com.dailycodework.dreamshops.Request;

import com.dailycodework.dreamshops.entity.Category;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
