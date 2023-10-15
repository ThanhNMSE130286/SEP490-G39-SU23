package com.teachsync.controllers;

import com.teachsync.dtos.user.UserReadDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home(
            Model model,
            @SessionAttribute(value = "user", required = false) UserReadDTO userDTO,
            @ModelAttribute("mess") String mess) {
        model.addAttribute("mess", mess);

//        if (Objects.isNull(userDTO)) {
//
//        }

        return "index";
    }

    @GetMapping("/home")
    public String home1(
            @ModelAttribute("mess") String mess,
            RedirectAttributes redirect) {
        redirect.addFlashAttribute("mess", mess);
        return "redirect:/";
    }
    @GetMapping("/index")
    public String home2(
            @ModelAttribute("mess") String mess,
            RedirectAttributes redirect) {
        redirect.addFlashAttribute("mess", mess);
        return "redirect:/";
    }
    @GetMapping("/trang-chu")
    public String home3(
            @ModelAttribute("mess") String mess,
            RedirectAttributes redirect) {
        redirect.addFlashAttribute("mess", mess);
        return "redirect:/";
    }
}
