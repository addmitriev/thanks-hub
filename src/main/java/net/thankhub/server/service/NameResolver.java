package net.thankhub.server.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NameResolver {

    private BiMap<String, String> gitHubToYaMoneyNames = HashBiMap.create();

    @PostConstruct
    public void init() {
        gitHubToYaMoneyNames.put("zamonier", "thankshub@yandex.ru");
    }

    public String fromGitHub(String gitHubName) {
        return gitHubToYaMoneyNames.get(gitHubName);
    }

    public String fromYaMoney(String yaName) {
        return gitHubToYaMoneyNames.inverse().get(yaName);
    }

    public void addPair(String gitHub, String yaMoney) {
        gitHubToYaMoneyNames.put(gitHub, yaMoney);
    }
}
