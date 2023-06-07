package com.teachsync.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home(Model model) {
        /* TODO: add news, promotion,... to model */
        return "index";
    }

    @GetMapping("/home")
    public String home1() {
        return "redirect:/";
    }
    @GetMapping("/index")
    public String home2() {
        return "redirect:/";
    }
    @GetMapping("/trang-chu")
    public String home3() {
        return "redirect:/";
    }
}
