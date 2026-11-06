package com.product_service.dto;

import com.product_service.entity.SubCategory;

import java.util.LinkedHashSet;
import java.util.Set;

public class CategoryDto {

    private Integer id;
    private String name;

    public Set<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    private Set<SubCategory> subCategories = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
