package com.yandex.money.api.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.yandex.money.api.methods.JsonUtils;

import java.lang.reflect.Type;

/**
 * Describes digital item, that user can obtain when paying for them.
 */
public class Good {

    private final String serial;
    private final String secret;
    private final String merchantArticleId;

    /**
     * Constructor.
     *
     * @param serial serial number
     * @param secret secret
     * @param merchantArticleId merchant article id
     */
    public Good(String serial, String secret, String merchantArticleId) {
        if (serial == null) {
            throw new NullPointerException("serial is null");
        }
        this.serial = serial;
        if (secret == null) {
            throw new NullPointerException("secret is null");
        }
        this.secret = secret;
        this.merchantArticleId = merchantArticleId;
    }

    /**
     * Creates {@link com.yandex.money.api.model.Good} from JSON.
     */
    public static Good createFromJson(JsonElement json) {
        return buildGson().fromJson(json, Good.class);
    }

    @Override
    public String toString() {
        return "Good{" +
                "serial='" + serial + '\'' +
                ", secret='" + secret + '\'' +
                ", merchantArticleId='" + merchantArticleId + '\'' +
                '}';
    }

    /**
     * @return serial number
     */
    public String getSerial() {
        return serial;
    }

    /**
     * @return secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @return merchant article id
     */
    public String getMerchantArticleId() {
        return merchantArticleId;
    }

    private static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Good.class, new Deserializer())
                .create();
    }

    private static final class Deserializer implements JsonDeserializer<Good> {
        @Override
        public Good deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new Good(JsonUtils.getMandatoryString(object, "serial"),
                    JsonUtils.getMandatoryString(object, "secret"),
                    JsonUtils.getString(object, "merchantArticleId"));
        }
    }
}
