package com.unikre.yandex;

import com.unikre.yandex.http.ResponseCode;
import com.unikre.yandex.params.ApiVersion;
import com.unikre.yandex.params.RequestInterface;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

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

    protected final CloseableHttpClient httpClient;

    public YandexExecutor(String apiKey) {

        // Default settings
        setApiKey(apiKey);
        setRequestInterface(RequestInterface.JSON);

        httpClient = HttpClients.createDefault();
    }

    protected void validateResponse(CloseableHttpResponse response) throws Exception {
        // Response - status code
        ResponseCode responseCode = ResponseCode.byCode(response.getStatusLine().getStatusCode());
        if (responseCode == null) {
            throw new Exception("API call error: " + response.getStatusLine().getStatusCode() + " - " + response.getStatusLine().getReasonPhrase());
        } else if (responseCode.compareTo(ResponseCode.OK) != 0) {
            throw new Exception("API call error: " + responseCode.code + " - " + responseCode.description);
        }

        // Response - body
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity == null) {
            throw new Exception("API call error: Empty response body");
        }
    }

}
