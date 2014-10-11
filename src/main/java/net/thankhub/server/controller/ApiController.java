package net.thankhub.server.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.Map;

@Controller
public class ApiController {

    @RequestMapping(value = {"/api/auth", "/api/auth/"}, method = RequestMethod.GET)
    public String auth(WebRequest request, Model model) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("string1", objectMapper.writeValueAsString(parameterMap));
        return "auth";
    }

    @RequestMapping(value = {"/api/start", "/api/start/"}, method = RequestMethod.POST)
    public String start(@RequestParam("amount") int amount, @RequestParam("user") String user, Model model) throws IOException {
        model.addAttribute("status", "OK");
        model.addAttribute("user", user);
        model.addAttribute("amount", amount);
        return "success";
    }
}
