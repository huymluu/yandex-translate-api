package com.unikre.yandex.http;

import com.unikre.yandex.params.ApiVersion;
import com.unikre.yandex.params.RequestInterface;
import com.unikre.yandex.params.YandexService;
import lombok.Builder;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class GetSupportedTranslateDirectionsRequestParams extends RequestParams {

    private static final String PARAM_CALLBACK = "callback"; // Optional

    @Builder
    public GetSupportedTranslateDirectionsRequestParams(RequestInterface requestInterface,
                                                        ApiVersion apiVersion,
                                                        String encoding,
                                                        String apiKey) {
        super(requestInterface, apiVersion, encoding, apiKey);
    }

    @Override
    public HttpPost buildHttpPost() throws Exception {
        HttpPost httpPost = new HttpPost(buildUrl());

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        // API key
        params.add(new BasicNameValuePair(PARAM_KEY, getApiKey()));

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        return httpPost;
    }

    @Override
    protected String buildUrl() throws Exception {
        // Validate mandatory params
        if (getApiKey() == null) {
            throw new IllegalArgumentException("Missing mandatory params");
        }

        return YandexService.buildUrl(YandexService.DICTIONARY, getApiVersion(), getRequestType()) + "/getLangs?";
    }

}
