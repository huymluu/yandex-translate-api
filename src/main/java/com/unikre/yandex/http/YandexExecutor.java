package com.unikre.yandex.http;

import com.unikre.yandex.params.ApiVersion;
import com.unikre.yandex.params.RequestInterface;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Response;

public abstract class YandexExecutor {

    @Getter
    @Setter
    protected String apiKey;

    @Getter
    @Setter
    protected ApiVersion apiVersion;

    @Getter
    @Setter
    protected RequestInterface requestInterface;

    protected YandexService yandexService;

    public YandexExecutor(String apiKey) {
        setApiKey(apiKey);
    }

    protected void validateResponse(Response response) throws Exception {
        // Result - status code
        ResponseCode responseCode = ResponseCode.byCode(response.code());
        if (responseCode == null) {
            throw new Exception("API call error: " + response.code() + " - " + response.message());
        } else if (responseCode.compareTo(ResponseCode.OK) != 0) {
            throw new Exception("API call error: " + responseCode.code + " - " + responseCode.description);
        }

        // Result - body
        if (response.body() == null) {
            throw new Exception("API call error: Empty response body");
        }
    }

}
