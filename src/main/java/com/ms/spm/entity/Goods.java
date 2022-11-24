package com.ms.spm.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "goods_details", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Long price;
    private String description;
    private Long totalStock;

    public Goods(long id, String name, Long price, String description, Long totalStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.totalStock = totalStock;
    }

    public Goods() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Long totalStock) {
        this.totalStock = totalStock;
    }

}
