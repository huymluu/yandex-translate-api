package com.unikre.yandextranslate.http;

import com.unikre.yandextranslate.params.ApiVersion;
import com.unikre.yandextranslate.params.Language;
import com.unikre.yandextranslate.params.RequestType;
import lombok.Builder;
import lombok.Singular;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class DetectRequestParams extends RequestParams {

    private static final String PARAM_TEXT = "text";
    private static final String PARAM_HINT = "hint"; // Optional
    private static final String PARAM_CALLBACK = "callback"; // Optional

    private String text;
    private List<Language> hints;

    @Builder
    public DetectRequestParams(RequestType requestType,
                               ApiVersion apiVersion,
                               String encoding,
                               String apiKey,
                               String text,
                               @Singular List<Language> hints) {
        super(requestType, apiVersion, encoding, apiKey);
        this.text = text;
        this.hints = hints;
    }

    @Override
    public HttpPost buildHttpPost() throws Exception {
        HttpPost httpPost = new HttpPost(buildUrl());

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        // API key
        params.add(new BasicNameValuePair(PARAM_KEY, getApiKey()));

        params.add(new BasicNameValuePair(PARAM_TEXT, text));

        if (hints != null && hints.size() > 0) {
            String hintParam = "";
            for (Language language : hints) {
                hintParam += "," + language.toString();
            }

            params.add(new BasicNameValuePair(PARAM_HINT, hintParam.substring(1)));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        return httpPost;
    }

    @Override
    protected String buildUrl() throws Exception {
        // Validate mandatory params
        if (getApiKey() == null || text == null || text.length() == 0) {
            throw new IllegalArgumentException("Missing mandatory params");
        }

        return getApiVersion().getUrl() + "/" + getRequestType() + "/detect?";
    }

}
