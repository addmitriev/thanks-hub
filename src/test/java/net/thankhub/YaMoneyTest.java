package net.thankhub;

import com.yandex.money.api.YandexMoney;
import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.*;
import net.thankhub.server.service.YaService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

public class YaMoneyTest implements ApiTest2 {

    @Test
    public void test() throws InvalidTokenException, InsufficientScopeException, InvalidRequestException, IOException {

        String to = "dmalexeydm@yandex.ru";
        String amount = "1.00";
        String messageFrom = "Комментарий к переводу, отображается в истории отправителя.";
        String messageTo = "Комментарий к переводу, отображается получателю.";

        RequestPayment response = new YaService(CLIENT_ID).send(ACCESS_TOKEN, to, amount, messageFrom, messageTo);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), BaseRequestPayment.Status.SUCCESS);
        Assert.assertNull(response.getError());
        Assert.assertNotNull(response.getRequestId());
        Assert.assertTrue(response.getRequestId().length() > 0);
        Assert.assertEquals(response.getContractAmount(), new BigDecimal("1.00"));

    }

}
