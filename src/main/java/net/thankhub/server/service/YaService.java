package net.thankhub.server.service;

import com.yandex.money.api.YandexMoney;
import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.RequestPayment;
import com.yandex.money.api.methods.Token;
import com.yandex.money.api.net.DefaultApiClient;
import com.yandex.money.api.net.OAuth2Session;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

@Component
public class YaService {

    public static final String CLIENT_ID = "6C5764252E3AFFAFD29B1023327C8AA75F8D95B1CE9C4DD457132DD7BF31D6C0";
    public static final String REDIRECT_URI = "http://thanks-hub.herokuapp.com/api/auth";

    public static final String PATTERN_ID = "p2p";
    public static final String TO = "to";
    public static final String AMOUNT = "amount";
    public static final String MESSAGE_FROM = "comment";
    public static final String MESSAGE_TO = "message";

    private YandexMoney ym;

    public YaService() {
        ym = new YandexMoney(CLIENT_ID);
        ym.setDebugLogging(true);
    }

    public RequestPayment send(String code, String to, String amount, String messageFrom, String messageTo)
            throws IOException, InsufficientScopeException, InvalidTokenException, InvalidRequestException {

        System.out.println("Code: " + code + ", To: " + to + ", Amount: " + amount);

        OAuth2Session session = new OAuth2Session(new DefaultApiClient(CLIENT_ID, true));
        session.setDebugLogging(true);
        Token token = session.execute(new Token.Request(code, CLIENT_ID, REDIRECT_URI, null));
        System.out.println("Token: " + token.getAccessToken());
        ym.setAccessToken(token.getAccessToken());

        HashMap<String, String> params = new HashMap<>();

        params.put(TO, to);
        params.put(AMOUNT, amount);
        params.put(MESSAGE_FROM, messageFrom);
        params.put(MESSAGE_TO, messageTo);

        return ym.execute(new RequestPayment.Request(PATTERN_ID, params));
    }
}
