package com.example.Marketplace.models;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Заполните поле")
    private String title;
    @NotEmpty(message = "Заполните поле")
    @Size(min = 1, max = 250, message = "Количество символов должно быть в\n" +
            "диапозоне от 1 до 250")
    private String description;
    @NotNull(message = "Заполните поле")
    @DecimalMin(value = "0.0", message = "Число не может быть отрицательным", inclusive = false)
    @DecimalMax(value = "99999.99", message = "Некорректный ввод", inclusive = false)
    private Double price;
    @NotEmpty(message = "Заполните поле")
    @Size(min = 1, max = 100, message = "Количество символов должно быть в\n" +
            "диапозоне от 1 до 100")
    private String address;
    private String author;
    private String filename;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Category category;

    public Post() {

    }

    public Post(String title, String description, Double price, String address, String author, String filename, Category category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.address = address;
        this.author = author;
        this.filename = filename;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
