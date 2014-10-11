package net.thankhub.server.service;

import com.yandex.money.api.YandexMoney;
import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.RequestPayment;

import java.io.IOException;
import java.util.HashMap;

public class YaService {

    public static final String PATTERN_ID = "p2p";
    public static final String TO = "to";
    public static final String AMOUNT = "amount";
    public static final String MESSAGE_FROM = "comment";
    public static final String MESSAGE_TO = "message";

    private YandexMoney ym;

    public YaService(String clientId) {
        ym = new YandexMoney(clientId);
        ym.setDebugLogging(true);
    }

    public RequestPayment send(String accessToken, String to, String amount, String messageFrom, String messageTo)
            throws IOException, InsufficientScopeException, InvalidTokenException, InvalidRequestException {

        ym.setAccessToken(accessToken);

        HashMap<String, String> params = new HashMap<>();

        params.put(TO, to);
        params.put(AMOUNT, amount);
        params.put(MESSAGE_FROM, messageFrom);
        params.put(MESSAGE_TO, messageTo);

        params.put("test_payment", "true");
        params.put("test_result", "success");

        return ym.execute(new RequestPayment.Request(PATTERN_ID, params));
    }
}
