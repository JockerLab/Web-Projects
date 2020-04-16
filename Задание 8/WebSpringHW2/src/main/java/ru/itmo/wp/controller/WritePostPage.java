package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.form.TagsCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.form.validator.TagValidator;
import ru.itmo.wp.form.validator.UserCredentialsRegisterValidator;
import ru.itmo.wp.repository.TagRepository;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.TagService;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;
    private final TagValidator tagValidator;
    private final TagService tagService;

    public WritePostPage(UserService userService, TagService tagService, TagValidator tagValidator) {
        this.userService = userService;
        this.tagValidator = tagValidator;
        this.tagService = tagService;
    }

    @InitBinder("tagsList")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(tagValidator);
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("tagsList", new TagsCredentials());
        return "WritePostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("post") Post post,
                                BindingResult bindingResult,
                                @Valid @ModelAttribute("tagsList") TagsCredentials tagsList,
                                BindingResult bindingResult2,
                                Model model,
                                HttpSession httpSession) {
        if (bindingResult.hasErrors() || bindingResult2.hasErrors()) {
            return "WritePostPage";
        }
        String[] tags = tagsList.getName().split("[\\s]");
        Set<Tag> tagsSet = new HashSet<>();
        for (String name : tags) {
            Tag currentTag = tagService.findByName(name);
            if (currentTag == null) {
                Tag tag = new Tag();
                tag.setName(name);
                tagService.save(tag);
            }
            tagsSet.add(tagService.findByName(name));
        }

        post.setTags(tagsSet);

        userService.writePost(getUser(httpSession), post);
        putMessage(httpSession, "You published new post");

        return "redirect:/posts";
    }
}
