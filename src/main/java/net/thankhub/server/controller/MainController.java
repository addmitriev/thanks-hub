package net.thankhub.server.controller;

import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import net.thankhub.server.service.NameResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    private NameResolver nameResolver;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) throws IOException {
        model.addAttribute("error", null);
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String link(@RequestParam("github") String github, @RequestParam("yandex") String yandex, Model model)
            throws IOException, InsufficientScopeException, InvalidTokenException, InvalidRequestException {
        if (StringUtils.isEmpty(github) || StringUtils.isEmpty(yandex)) {
            model.addAttribute("error", "Both accounts are required");
            model.addAttribute("github", github);
            model.addAttribute("yandex", yandex);
            return "index";
        }
        try {
            nameResolver.addPair(github, yandex);
        } catch (IllegalArgumentException e) {
            model.addAttribute("github", null);
            model.addAttribute("yandex", null);
            model.addAttribute("error", e.getMessage());
            return "index";
        }
        return "linked";
    }
}
