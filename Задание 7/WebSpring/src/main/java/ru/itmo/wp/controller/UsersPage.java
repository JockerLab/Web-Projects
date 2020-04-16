package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.DisableForm;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model, HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            putMessage(httpSession, "You should be authorized.");
            return "redirect:/";
        }
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String users(@Valid @ModelAttribute("disableForm") DisableForm disableForm,
                           BindingResult bindingResult,
                           HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "IndexPage";
        }

        if (getUser(httpSession) == null) {
            putMessage(httpSession, "You should be authorized.");
            return "redirect:/";
        }

        if (userService.hasUserById(disableForm.getUserId())) {
            userService.updateDisabled(disableForm.getUserId(), "Disable".equals(disableForm.getDisable()));
        }

        return "redirect:/users/all";
    }
}
