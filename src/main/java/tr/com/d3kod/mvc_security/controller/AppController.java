package tr.com.d3kod.mvc_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/")
    public String showHome() {
        return "home";
    }

    @GetMapping("/leaders")
    public String showLeaders() {
        return "leaders";
    }

    @GetMapping("/systems")
    public String showSystems() {
        return "systems";
    }
}
