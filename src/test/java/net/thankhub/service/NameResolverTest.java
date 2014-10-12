package net.thankhub.service;

import net.thankhub.server.service.NameResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Евгений on 11.10.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class NameResolverTest {

    @Autowired
    private NameResolver nameResolver;

    @Test
    public void test() {

        assertNotNull(nameResolver);

        String result = nameResolver.fromGitHub("zamonier");
        assertEquals("thankshub@yandex.ru", result);
    }

    @Test
    public void testAdd() {

        assertNotNull(nameResolver);

        nameResolver.addPair("ya-east", "thankshub@yandex.ru");
        assertEquals("thankshub@yandex.ru", nameResolver.fromGitHub("ya-east"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoubleAdd() {

        assertNotNull(nameResolver);

        nameResolver.addPair("ya-east", "thankshub@yandex.ru");
        nameResolver.addPair("ya-east", "thankshub@yandex.ru");
    }
}
