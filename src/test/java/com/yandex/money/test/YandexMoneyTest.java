package com.yandex.money.test;

import com.yandex.money.api.YandexMoney;
import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.BaseProcessPayment;
import com.yandex.money.api.methods.BaseRequestPayment;
import com.yandex.money.api.methods.InstanceId;
import com.yandex.money.api.methods.ProcessExternalPayment;
import com.yandex.money.api.methods.ProcessPayment;
import com.yandex.money.api.methods.RequestExternalPayment;
import com.yandex.money.api.methods.RequestPayment;
import com.yandex.money.api.methods.params.PhoneParams;
import com.yandex.money.api.model.Error;
import com.yandex.money.api.net.MethodRequest;

import org.junit.Ignore;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 *
 */
@Test(enabled=false)
public class YandexMoneyTest implements ApiTest {

    private static final String PATTERN_ID_PHONE_TOPUP = PhoneParams.PATTERN_ID;
    private static final String AMOUNT = "1.00";

    private YandexMoney ym;

    private InstanceId respInstanceId;
    private InstanceId.Request reqInstanceId;

    private RequestExternalPayment respRequestExternalPayment;
    private RequestExternalPayment.Request reqRequestExternalPayment;
    private ProcessExternalPayment.Request reqProcessExternalPayment;

    @BeforeClass
    private void setUp() {
        ym = new YandexMoney(CLIENT_ID);
        ym.setDebugLogging(true);
    }

    @BeforeTest
    private void beforeTest() {
        reqInstanceId = null;
        respInstanceId = null;

        reqRequestExternalPayment = null;
        respRequestExternalPayment = null;

        reqProcessExternalPayment = null;
    }

    @Test(enabled=false)
    public void testInstanceIdSuccess() throws IOException, InsufficientScopeException,
            InvalidTokenException, InvalidRequestException {

        reqInstanceId = new InstanceId.Request(CLIENT_ID);
        respInstanceId = ym.execute(reqInstanceId);

        Assert.assertEquals(respInstanceId.getStatus(), InstanceId.Status.SUCCESS);
        Assert.assertNotNull(respInstanceId.getInstanceId());
        Assert.assertNull(respInstanceId.getError());
    }

    @Test(enabled=false)
    public void testInstanceIdFail() throws IOException, InsufficientScopeException,
            InvalidTokenException, InvalidRequestException {

        reqInstanceId = new InstanceId.Request(" ");
        respInstanceId = ym.execute(reqInstanceId);

        Assert.assertEquals(respInstanceId.getStatus(), InstanceId.Status.REFUSED);
        Assert.assertNotNull(respInstanceId.getError());
        Assert.assertEquals(respInstanceId.getError(), Error.ILLEGAL_PARAM_CLIENT_ID);
        Assert.assertNull(respInstanceId.getInstanceId());
    }

    @Test(enabled=false)
    public void testRequestExternalPayment() throws IOException, InsufficientScopeException,
            InvalidTokenException, InvalidRequestException {

        reqRequestExternalPayment = createRequestExternalPayment();
        respRequestExternalPayment = testRequestPayment(reqRequestExternalPayment);
    }

    @Test(enabled=false)
    public void testRequestPayment() throws InvalidTokenException, InsufficientScopeException,
            InvalidRequestException, IOException {

        ym.setAccessToken(ACCESS_TOKEN);
        testRequestPayment(createRequestPayment());
        ym.setAccessToken(null);
    }

    private <T extends BaseRequestPayment> T testRequestPayment(MethodRequest<T> request)
            throws InvalidTokenException, InsufficientScopeException, InvalidRequestException,
            IOException {

        T response = ym.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), BaseRequestPayment.Status.SUCCESS);
        Assert.assertNull(response.getError());
        Assert.assertNotNull(response.getRequestId());
        Assert.assertTrue(response.getRequestId().length() > 0);
        Assert.assertEquals(response.getContractAmount(), new BigDecimal(AMOUNT));

        return response;
    }

    private HashMap<String, String> successRequestParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("amount", AMOUNT);
        params.put("phone-number", "79112611383");
        return params;
    }

    @Test(enabled=false)
    public void testRequestExternalFail() throws IOException, InsufficientScopeException,
            InvalidTokenException, InvalidRequestException {

        reqInstanceId = new InstanceId.Request(CLIENT_ID);
        respInstanceId = ym.execute(reqInstanceId);

        HashMap<String, String> params = successRequestParams();
        reqRequestExternalPayment = RequestExternalPayment.Request.newInstance(
                respInstanceId.getInstanceId(), " ", params);
        respRequestExternalPayment = testRequestPaymentFail(reqRequestExternalPayment);

        params = successRequestParams();
        params.remove("amount");
        reqRequestExternalPayment = RequestExternalPayment.Request.newInstance(
                respInstanceId.getInstanceId(), PATTERN_ID_PHONE_TOPUP, params);
        respRequestExternalPayment = testRequestPaymentFail(reqRequestExternalPayment);

        params = successRequestParams();
        params.remove("phone-number");
        reqRequestExternalPayment = RequestExternalPayment.Request.newInstance(
                respInstanceId.getInstanceId(), PATTERN_ID_PHONE_TOPUP, params);
        respRequestExternalPayment = testRequestPaymentFail(reqRequestExternalPayment);
    }

    private <T extends BaseRequestPayment> T testRequestPaymentFail(MethodRequest<T> request)
            throws InvalidTokenException, InsufficientScopeException, InvalidRequestException,
            IOException {

        T response = ym.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), RequestExternalPayment.Status.REFUSED);
        Assert.assertNotNull(response.getError());
        Assert.assertNotEquals(response.getError(), Error.UNKNOWN);
        Assert.assertNull(response.getRequestId());

        return response;
    }

    @Test(enabled=false)
    public void testProcessExternalPayment() throws IOException, InsufficientScopeException,
            InvalidTokenException, InvalidRequestException {

        reqRequestExternalPayment = createRequestExternalPayment();
        respRequestExternalPayment = ym.execute(reqRequestExternalPayment);

        String successUri = "https://elbrus.yandex.ru/success";
        String failUri = "https://elbrus.yandex.ru/fail";
        reqProcessExternalPayment = new ProcessExternalPayment.Request(
                respInstanceId.getInstanceId(), respRequestExternalPayment.getRequestId(),
                successUri, failUri, false);
        testProcessPayment(reqProcessExternalPayment);
    }

    @Test(enabled=false)
    public void testProcessPayment() throws InvalidTokenException, InsufficientScopeException,
            InvalidRequestException, IOException {

        ym.setAccessToken(ACCESS_TOKEN);
        RequestPayment requestPayment = ym.execute(createRequestPayment());
        ProcessPayment processPayment = ym.execute(new ProcessPayment.Request(
                requestPayment.getRequestId()));
        Assert.assertNotNull(processPayment);
        ym.setAccessToken(null);
    }

    private <T extends BaseProcessPayment> void testProcessPayment(MethodRequest<T> request)
            throws InvalidTokenException, InsufficientScopeException, InvalidRequestException,
            IOException {

        T response = ym.execute(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), ProcessExternalPayment.Status.EXT_AUTH_REQUIRED);
    }

    private RequestExternalPayment.Request createRequestExternalPayment()
            throws InvalidTokenException, InsufficientScopeException, InvalidRequestException,
            IOException {

        reqInstanceId = new InstanceId.Request(CLIENT_ID);
        respInstanceId = ym.execute(reqInstanceId);

        HashMap<String, String> params = successRequestParams();
        return RequestExternalPayment.Request.newInstance(
                respInstanceId.getInstanceId(), PATTERN_ID_PHONE_TOPUP, params);
    }

    private RequestPayment.Request createRequestPayment() {
        HashMap<String, String> params = successRequestParams();
        return new RequestPayment.Request(params.get("phone-number"), new BigDecimal(AMOUNT));
    }
}
