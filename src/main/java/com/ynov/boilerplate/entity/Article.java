package com.ynov.boilerplate.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "article")
public class Article {

    private int id;
    private String name;
    private int price;

    public Article(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
