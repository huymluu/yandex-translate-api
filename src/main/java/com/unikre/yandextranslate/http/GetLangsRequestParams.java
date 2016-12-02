package com.unikre.yandextranslate.http;

import com.unikre.yandextranslate.params.ApiVersion;
import com.unikre.yandextranslate.params.Language;
import com.unikre.yandextranslate.params.RequestType;
import lombok.Builder;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class GetLangsRequestParams extends RequestParams {

    private static final String PARAM_UI = "ui";
    private static final String PARAM_CALLBACK = "callback"; // Optional

    private Language ui;

    @Builder
    public GetLangsRequestParams(RequestType requestType,
                                 ApiVersion apiVersion,
                                 String encoding,
                                 String apiKey,
                                 Language ui) {
        super(requestType, apiVersion, encoding, apiKey);
        this.ui = ui;
    }

    @Override
    public HttpPost buildHttpPost() throws Exception {
        HttpPost httpPost = new HttpPost(buildUrl());

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        // API key
        params.add(new BasicNameValuePair(PARAM_KEY, getApiKey()));

        params.add(new BasicNameValuePair(PARAM_UI, ui.toString()));

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        return httpPost;
    }

    @Override
    protected String buildUrl() throws Exception {
        // Validate mandatory params
        if (getApiKey() == null || ui == null) {
            throw new IllegalArgumentException("Missing mandatory params");
        }

        return getApiVersion().getUrl() + "/" + getRequestType() + "/getLangs?";
    }

}
