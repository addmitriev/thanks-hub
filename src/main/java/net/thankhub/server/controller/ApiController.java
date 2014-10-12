package net.thankhub.server.controller;

import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.ProcessPayment;
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

    @RequestMapping(value = {"/auth", "/auth/"}, method = RequestMethod.GET)
    public String auth(@RequestParam("code") String code, Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("code", code);
        String gitHubUser = (String) request.getSession().getAttribute("gitHubUser");
        model.addAttribute("gitHubUser", gitHubUser);
        return "payment-form";
    }

    @RequestMapping(value = {"/payment", "/payment/"}, method = RequestMethod.POST)
    public String payment(@RequestParam("code") String code, @RequestParam("amount") String amount,
                          @RequestParam("gitHubUser") String gitHubUser,
                          @RequestParam("commitId") String commitId, Model model)
            throws IOException, InsufficientScopeException, InvalidTokenException, InvalidRequestException {
        String to = nameResolver.fromGitHub(gitHubUser);
        if (to == null) {
            model.addAttribute("status", "fail");
            model.addAttribute("error", "User does not exists yet.");
            return "success";
        }
        ProcessPayment payment = yaService.send(code, to, amount,
                "Thanks for commit to " + gitHubUser,
                "Thanks for commit on GitHub for commit " + commitId);
        model.addAttribute("status", payment.getStatus().toString().toLowerCase());
        model.addAttribute("error", payment.getError());
        return "success";
    }

}
