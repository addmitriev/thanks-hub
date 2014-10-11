package net.thankhub.server.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {

    @RequestMapping(value = {"/form", "/form/"}, method = RequestMethod.GET)
    public String start(@RequestParam("user") String user, Model model) throws IOException {
        model.addAttribute("user", user);
        return "form";
    }
}
