package com.teachsync.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContactController {
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
