package net.thankhub.server.controller;

import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.RequestPayment;
import net.thankhub.server.service.NameResolver;
import net.thankhub.server.service.YaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    private NameResolver nameResolver;

    @Autowired
    private YaService yaService;

    @RequestMapping(value = {"/payment", "/payment/"}, method = RequestMethod.POST)
    public String payment(@RequestParam("code") String code, @RequestParam("amount") String amount,
                          @RequestParam("gitHubUser") String gitHubUser, Model model)
            throws IOException, InsufficientScopeException, InvalidTokenException, InvalidRequestException {
        String to = nameResolver.fromGitHub(gitHubUser);
        RequestPayment requestPayment = yaService.send(code, to, amount,
                "Thanks for commit to" + gitHubUser,
                "Thanks for commit on GitHub");
        model.addAttribute("status", requestPayment.getStatus());
        model.addAttribute("error", requestPayment.getError());
        return "success";
    }

}
