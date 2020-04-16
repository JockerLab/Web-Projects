package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/{id}")
    public String post(HttpSession httpSession, Model model, @PathVariable String id) {
        if (putPost(httpSession, model, id)) return "redirect:/";
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String post(@Valid @ModelAttribute("comment") Comment comment,
                       BindingResult bindingResult,
                       HttpSession httpSession, Model model, @PathVariable String id) {
        if (bindingResult.hasErrors()) {
            if (putPost(httpSession, model, id)) return "redirect:/";
            return "PostPage";
        }
        try {
            long postId = Long.parseLong(id);
            if(!postService.checkPostById(postId)) {
                putMessage(httpSession, "This post doesn't exist.");
                return "redirect:/";
            }
            Post postById = postService.findById(postId);
            comment.setUser(getUser(httpSession));
            comment.setPost(postById);
            List<Comment> comments = postById.getComments();
            comments.add(comment);
            postById.setComments(comments);
            postService.save(postById);
        } catch (NumberFormatException e) {
            putMessage(httpSession, "Incorrect post id");
            return "redirect:/";
        }

        return "redirect:/post/{id}";
    }

    private boolean putPost(HttpSession httpSession, Model model, @PathVariable String id) {
        try {
            long postId = Long.parseLong(id);
            model.addAttribute("post", postService.findById(postId));
            if(!postService.checkPostById(postId)) {
                putMessage(httpSession, "This post doesn't exist.");
                return true;
            }
        } catch (NumberFormatException e) {
            model.addAttribute("post", null);
            putMessage(httpSession, "Incorrect post id");
            return true;
        }
        return false;
    }
}
