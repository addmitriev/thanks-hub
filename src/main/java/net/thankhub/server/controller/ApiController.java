package net.thankhub.server.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.Map;

@Controller
public class ApiController {

    @ResponseBody
    @RequestMapping(value = "/api/user/{user}/commit/{commit}")
    public String getWallet(@PathVariable("user") String user, @PathVariable("commit") String commit) {
        return "{clientId: 12321312}";
    }
    @ResponseBody
    @RequestMapping(value = "/api/payment/user/{user}/token/{token}")
    public String payment(@PathVariable("user") String user, @PathVariable("commit") String commit) {
        return "{OK}";
    }

    @RequestMapping(value = {"/api/auth", "/api/auth/"}, method = RequestMethod.GET)
    public String auth(WebRequest request, Model model) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("string1", objectMapper.writeValueAsString(parameterMap));
        return "auth";
    }
}
