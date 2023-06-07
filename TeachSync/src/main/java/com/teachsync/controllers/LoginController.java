package com.teachsync.controllers;

import com.teachsync.dtos.user.UserCreateDTO;
import com.teachsync.dtos.user.UserReadDTO;
import com.teachsync.services.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;



    @GetMapping("/login")
    public String login(Model model) {
        Object objUser = model.getAttribute("user");

        /* TODO: use DTO instead */
        if (objUser instanceof UserReadDTO) {
            /* Already login */
            return "redirect:/index";
        }

        return "login";
    }


    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {
        try {
            UserReadDTO user = userService.loginDTO(username, password);

            if (user == null) {
                model.addAttribute("msg", "Incorrect username or password");
                return "login";
            }

            session.setAttribute("loginUser", user);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", "Server error, please try again later");
            return "login";
        }

        return "redirect:/index";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String fullName,
            @RequestParam String email,
            Model model) {
        try {
            UserCreateDTO createDTO = new UserCreateDTO(username, password, email, fullName);

            UserReadDTO readDTO = userService.signupDTO(createDTO);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            model.addAttribute("errorIllegalMsg", e.getMessage());
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", "Server error, please try again later");
            return "signup";
        }

        return "login";
    }

}
