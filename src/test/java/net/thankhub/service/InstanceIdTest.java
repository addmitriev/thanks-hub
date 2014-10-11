package net.thankhub.service;

import com.yandex.money.api.methods.InstanceId;
import com.yandex.money.api.model.Error;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 */
@Test
public class InstanceIdTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRequestClientIdNull() {
        new InstanceId.Request(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRequestClientIdEmpty() {
        new InstanceId.Request("");
    }

    @Test()
    public void testRequestClient() {
        InstanceId.Request request = new InstanceId.Request(" ");
        Assert.assertNotNull(request);
    }

    @Test
    public void testIsSuccess() {
        InstanceId instanceId = new InstanceId(InstanceId.Status.SUCCESS, null, "id");
        Assert.assertTrue(instanceId.isSuccess());

        instanceId = new InstanceId(InstanceId.Status.REFUSED, Error.UNKNOWN, null);
        Assert.assertFalse(instanceId.isSuccess());
    }
}
