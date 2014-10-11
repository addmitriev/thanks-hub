package net.thankhub.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {

    @RequestMapping(value = "/api/user/{user}/commit/{commit}")
    @ResponseBody
    public String getWallet(@PathVariable("user") String user, @PathVariable("commit") String commit) {
        return "{clientId: 12321312}";
    }
}
