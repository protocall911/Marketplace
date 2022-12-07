package com.example.Marketplace.repos;

import com.example.Marketplace.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    public List<Post> findByTitle(String title);
    public List<Post> findAllByCategory_Id(Long id);
}
