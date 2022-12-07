package com.example.Marketplace.repos;

import com.example.Marketplace.models.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findByTitle(String title);
}
