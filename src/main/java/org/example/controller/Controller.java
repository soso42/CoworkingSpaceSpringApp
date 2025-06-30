package org.example.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public String test(Model model) {
        model.addAttribute("name", "Soso");
        return "main";
    }

}
