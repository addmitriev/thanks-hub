import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.BaseRequestPayment;
import com.yandex.money.api.methods.ProcessPayment;
import com.yandex.money.api.methods.RequestPayment;
import net.thankhub.server.service.YaService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class YaMoneyTest implements ApiTest {

    @Test(enabled = false)
    public void test() throws InvalidTokenException, InsufficientScopeException, InvalidRequestException, IOException {

        String to = "dmalexeydm@yandex.ru";
        String amount = "1.00";
        String messageFrom = "Комментарий к переводу, отображается в истории отправителя.";
        String messageTo = "Комментарий к переводу, отображается получателю.";

        ProcessPayment response = new YaService().send(ACCESS_TOKEN, to, amount, messageFrom, messageTo);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), BaseRequestPayment.Status.SUCCESS);
        Assert.assertNull(response.getError());
    }

}
