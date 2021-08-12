package com.example.photoReview.controllers;

import com.example.photoReview.models.FirebaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {
    @Autowired
    FirebaseRepository db;

    @GetMapping("/contacts")
    public String home(Model model) {

        return "contacts";
    }
}
