package com.example.Marketplace.services;

import com.example.Marketplace.models.Post;
import com.example.Marketplace.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPostsByCategoryId(Long id) {
        return postRepository.findAllByCategory_Id(id);
    }
}
