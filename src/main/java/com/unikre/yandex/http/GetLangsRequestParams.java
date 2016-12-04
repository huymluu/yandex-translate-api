package com.unikre.yandex.http;

import com.unikre.yandex.params.ApiVersion;
import com.unikre.yandex.params.Language;
import com.unikre.yandex.params.RequestInterface;
import com.unikre.yandex.params.YandexService;
import lombok.Builder;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class GetLangsRequestParams extends RequestParams {

    private static final String PARAM_UI = "ui"; // Optional
    private static final String PARAM_CALLBACK = "callback"; // Optional

    private Language ui; // optional

    @Builder
    public GetLangsRequestParams(RequestInterface requestInterface,
                                 ApiVersion apiVersion,
                                 String encoding,
                                 String apiKey,
                                 Language ui) {
        super(requestInterface, apiVersion, encoding, apiKey);
        this.ui = ui;
    }

    @Override
    public HttpPost buildHttpPost() throws Exception {
        HttpPost httpPost = new HttpPost(buildUrl());

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        // API key
        params.add(new BasicNameValuePair(PARAM_KEY, getApiKey()));

        if (ui != null && !ui.equals(Language.AUTODETECT)) {
            params.add(new BasicNameValuePair(PARAM_UI, ui.toString()));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        return httpPost;
    }

    @Override
    protected String buildUrl() throws Exception {
        // Validate mandatory params
        if (getApiKey() == null || ui == null) {
            throw new IllegalArgumentException("Missing mandatory params");
        }

        return YandexService.buildUrl(YandexService.TRANSLATE, getApiVersion(), getRequestType()) + "/getLangs?";
    }

}
