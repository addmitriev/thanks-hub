package net.thankhub.server.controller;

import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.RequestPayment;
import net.thankhub.server.service.YaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class ApiController {

    @RequestMapping(value = {"/api/auth", "/api/auth/"}, method = RequestMethod.GET)
    public String auth(@RequestParam("code") String code, Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("code", code);
        String gitHubUser = (String) request.getSession().getAttribute("gitHubUser");
        model.addAttribute("gitHubUser", gitHubUser);
        return "payment-form";
    }

    @RequestMapping(value = {"/api/payment", "/api/payment/"}, method = RequestMethod.POST)
    public String payment(@RequestParam("code") String code, @RequestParam("amount") String amount,
                          @RequestParam("gitHubUser") String gitHubUser, Model model)
            throws IOException, InsufficientScopeException, InvalidTokenException, InvalidRequestException {
        YaService yaService = new YaService();
        RequestPayment requestPayment = yaService.send(code, getLogin(gitHubUser), amount, "Комментарий к переводу, отображается в истории отправителя.", "Комментарий к переводу, отображается получателю.");
        model.addAttribute("result", requestPayment.toString());
        return "success";
    }

    private String getLogin(String gitHubUser) throws IOException {
        if (!StringUtils.isEmpty(gitHubUser)) {
            return "thankshub@yandex.ru";
        } else {
            throw new IOException("Wrong login");
        }
    }
}
