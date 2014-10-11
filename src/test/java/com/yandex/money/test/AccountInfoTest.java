package com.yandex.money.test;

import com.yandex.money.api.YandexMoney;
import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.AccountInfo;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author Slava Yasevich (vyasevich@yamoney.ru)
 */
@Test(enabled=false)
public class AccountInfoTest implements ApiTest {

    private YandexMoney yandexMoney;

    @BeforeTest
    public void beforeTest() {
        yandexMoney = new YandexMoney(CLIENT_ID);
        yandexMoney.setDebugLogging(true);
        yandexMoney.setAccessToken(ACCESS_TOKEN);
    }

    @Test(enabled=false)
    public void testAccountInfo() throws InvalidTokenException, InsufficientScopeException,
            InvalidRequestException, IOException {

        AccountInfo.Request request = new AccountInfo.Request();
        AccountInfo accountInfo = yandexMoney.execute(request);
        Assert.assertNotNull(accountInfo);
    }
}
