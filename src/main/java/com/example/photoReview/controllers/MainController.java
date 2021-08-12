package com.example.photoReview.controllers;
import com.example.photoReview.models.FirebaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    FirebaseRepository db;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Main page");
        model.addAttribute("tours", db.getTourList());

        return "home";
    }

}
