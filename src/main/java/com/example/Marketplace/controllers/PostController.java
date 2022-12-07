package com.example.Marketplace.controllers;

import com.example.Marketplace.models.Category;
import com.example.Marketplace.models.Post;
import com.example.Marketplace.models.User;
import com.example.Marketplace.repos.CategoryRepository;
import com.example.Marketplace.repos.PostRepository;
import com.example.Marketplace.repos.UserRepository;
import com.example.Marketplace.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class PostController {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    public PostRepository postRepository;
    @Autowired
    public CategoryRepository categoryRepository;

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("post_list", postRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "posts/index";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/post/add")
    public String addView(Post post,
                          Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "posts/add-post";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/post/add")
    public String addPost(@ModelAttribute("post") @Valid Post post, BindingResult bindingResult,
                          Model model,
                          @RequestParam("file") MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "posts/add-post";
        }
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            post.setFilename(resultFileName);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setAuthor(auth.getName());
        postRepository.save(post);
        return "redirect:/board/";
    }

    @GetMapping("/info/{id}")
    public String infoPost(
            @PathVariable Long id,
            Model model
    ) {
        Post post = postRepository.findById(id).orElseThrow();
        model.addAttribute("one_post", post);

        return "posts/post-info";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(name = "title") String title,
                         Model model) {
        List<Post> postList = postRepository.findByTitle(title);
        model.addAttribute("post_list", postList);

        return "index";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MANAGER')")
    @GetMapping("/info/{id}/del")
    public String deletePost(@PathVariable Long id,
                             Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
//        starRepository.deleteById(id);
        return "redirect:/board/";
    }
    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/info/{id}/upd")
    public String updateView(
            @PathVariable Long id,
            Model model
    ) {
        Post res = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id: "+id));
        model.addAttribute("post", res);
        model.addAttribute("categories", categoryRepository.findAll());
        return "posts/update-post";
    }
    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/info/{id}/upd")
    public String updatePost(
            @ModelAttribute("post") @Valid Post post, BindingResult bindingResult,
            @PathVariable Long id,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {

        post.setId(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "posts/update-post";
        }
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            post.setFilename(resultFileName);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setAuthor(auth.getName());
        postRepository.save(post);
        return "redirect:/post/info/" + post.getId();
    }
}
