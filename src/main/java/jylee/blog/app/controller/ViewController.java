package jylee.blog.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("title", "About Me");
        model.addAttribute("view", "base/home");
        model.addAttribute("fragment", "home");
        return "base/main";
    }

}
