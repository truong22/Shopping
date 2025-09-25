package com.dailycodework.dreamshops.dto;

import com.dailycodework.dreamshops.entity.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class ProductDto {
        private Long id;
        private String name;
        private String brand;
        private BigDecimal price;
        private int inventory;
        private String desciption;
        private Category category;
        private List<ImageDto> images;
}
