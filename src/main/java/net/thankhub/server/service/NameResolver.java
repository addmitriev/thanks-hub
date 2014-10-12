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

    public void addPair(String gitHub, String yaMoney) {
        if (gitHubToYaMoneyNames.containsKey(gitHub)) {
            throw new IllegalArgumentException("User already exists!");
        }
        gitHubToYaMoneyNames.put(gitHub, yaMoney);
    }
}
