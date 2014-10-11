package net.thankhub.service;

import com.yandex.money.api.exceptions.InsufficientScopeException;
import com.yandex.money.api.exceptions.InvalidRequestException;
import com.yandex.money.api.exceptions.InvalidTokenException;
import com.yandex.money.api.methods.RequestPayment;
import net.thankhub.server.service.NameResolver;
import net.thankhub.server.service.YaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Евгений on 11.10.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class YaServiceTest {

    @Autowired
    private YaService yaService;

    @Autowired
    private NameResolver nameResolver;

    @Test(expected = InvalidRequestException.class)
    public void test() throws InvalidTokenException, InsufficientScopeException, InvalidRequestException, IOException {

        assertNotNull(yaService);

        RequestPayment result = yaService.send("1", nameResolver.fromGitHub("zamonier"), "1.00",
                "Thanks for commit to" + "zamonier",
                "Thanks for commit on GitHub");
        assertNotNull(result);
    }
}
