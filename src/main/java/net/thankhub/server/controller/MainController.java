package net.thankhub.server.controller;

import net.thankhub.server.service.YaService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() throws IOException {
        return "index";
    }

    @RequestMapping(value = {"/form", "/form/"}, method = RequestMethod.GET)
    public String start(@RequestParam("user") String gitHubUser, Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("client_id", YaService.CLIENT_ID);
        model.addAttribute("response_type", "code");
        model.addAttribute("redirect_uri", YaService.REDIRECT_URI);
        model.addAttribute("scope", "account-info operation-details payment-p2p");
        model.addAttribute("gitHubUser", gitHubUser);

        request.getSession().setAttribute("gitHubUser", gitHubUser);

        return "allow-form";
    }

}
