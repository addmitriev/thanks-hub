package net.thankhub;

import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.Token;
import com.yandex.money.api.net.DefaultApiClient;
import com.yandex.money.api.net.OAuth2Session;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author Slava Yasevich (vyasevich@yamoney.ru)
 */
@Test(enabled = false)
public class TokenTest implements ApiTest2 {

    private static final String CODE = "976E9238783778BD76AFA4F20B8716BE107A326C16209EBAAC9510D6717AAD1A7E2202E0FF27EE87963CF97A4C2DB9F27766EE0D79E25D2356B02EF9EAD5063F9AD391E0AE81483D8DBDAE0715A3104C0474146DF476B0807D9D68172953326CF4D6C74F680DE721EFAE805F35C7286F31F5A5DC27FB59B85B7B9B6EE945A35D";

    @Test(enabled = false)
    public void testToken() throws InvalidTokenException, InsufficientScopeException,
            InvalidRequestException, IOException {

        OAuth2Session session = new OAuth2Session(new DefaultApiClient(CLIENT_ID, true));
        session.setDebugLogging(true);

        Token token = session.execute(new Token.Request(CODE, CLIENT_ID, REDIRECT_URI, null));

        Assert.assertNotNull(token);
        Assert.assertNotNull(token.getAccessToken());
    }

}
