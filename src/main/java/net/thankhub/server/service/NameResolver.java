package net.thankhub.server.service;

import org.springframework.stereotype.Component;

@Component
public class NameResolver {

    public String fromGitHub(String gitHubName) {
        return "thankshub@yandex.ru";
    }

    public String fromYaMoney(String yaName) {
        return "zamonier";
    }
}
