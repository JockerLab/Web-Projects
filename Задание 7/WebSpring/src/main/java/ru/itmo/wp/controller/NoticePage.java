package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String notice(Model model, HttpSession httpSession) {
        if (!noticeService.isUserAuthorized(getUser(httpSession))) {
            putMessage(httpSession, "You should be authorized.");
            return "redirect:/";
        }
        model.addAttribute("noticeForm", new Notice());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String notice(@Valid @ModelAttribute("noticeForm") Notice noticeForm,
                           BindingResult bindingResult,
                           HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        if (!noticeService.isUserAuthorized(getUser(httpSession))) {
            putMessage(httpSession, "You should be authorized.");
            return "redirect:/";
        }

        noticeService.save(noticeForm.getContent());
        putMessage(httpSession, "You successfully post notice");

        return "redirect:/";
    }
}
