package com.example.Marketplace.controllers;

import com.example.Marketplace.models.Category;
import com.example.Marketplace.models.Post;
import com.example.Marketplace.repos.CategoryRepository;
import com.example.Marketplace.repos.PostRepository;
import com.example.Marketplace.repos.UserRepository;
import com.example.Marketplace.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class MainController {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public PostRepository postRepository;
    @Autowired
    public CategoryRepository categoryRepository;
    @Autowired
    public PostService postService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user_list", userRepository.findAll());
        model.addAttribute("post_list", postRepository.findAll());
        return "index";
    }

    @GetMapping("/category/{id}")
    public String postByCategory(Model model, @PathVariable Long id) {
        model.addAttribute("user_list", userRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("post_list", postService.getAllPostsByCategoryId(id));
        return "index";
    }
}
