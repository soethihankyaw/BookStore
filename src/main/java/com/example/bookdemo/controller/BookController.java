package com.example.bookdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    @GetMapping({"/","/home"})
    public String index(Model model) {
        return  "index";
    }


}
