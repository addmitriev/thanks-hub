package net.thankhub;

import com.yandex.money.api.YandexMoney;
import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

public class YaMoneyTest implements ApiTest2 {

    @Test
    public void test() throws InvalidTokenException, InsufficientScopeException, InvalidRequestException, IOException {

        YandexMoney ym = new YandexMoney(CLIENT_ID);
        ym.setDebugLogging(true);

        ym.setAccessToken(ACCESS_TOKEN);
        HashMap<String, String> params1 = new HashMap<>();
        params1.put("to", "dmalexeydm@yandex.ru");
        params1.put("amount", "1.00");
        params1.put("comment", "Комментарий к переводу, отображается в истории отправителя.");
        params1.put("message", "Комментарий к переводу, отображается получателю.");
        params1.put("test_payment", "true");
        params1.put("test_result", "success");

        RequestPayment response = ym.execute(new RequestPayment.Request("p2p", params1));

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), BaseRequestPayment.Status.SUCCESS);
        Assert.assertNull(response.getError());
        Assert.assertNotNull(response.getRequestId());
        Assert.assertTrue(response.getRequestId().length() > 0);
        Assert.assertEquals(response.getContractAmount(), new BigDecimal("1.00"));

        ym.setAccessToken(null);
    }

}
