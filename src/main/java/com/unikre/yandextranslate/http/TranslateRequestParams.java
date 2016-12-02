package com.unikre.yandextranslate.http;

import com.unikre.yandextranslate.params.ApiVersion;
import com.unikre.yandextranslate.params.Format;
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

public class TranslateRequestParams extends RequestParams {

    private static final String PARAM_TEXT = "text";
    private static final String PARAM_LANG = "lang";
    private static final String PARAM_FORMAT = "format"; // Optional
    private static final String PARAM_OPTIONS = "options"; // Optional
    private static final String PARAM_CALLBACK = "callback"; // Optional

    private List<String> texts;
    private Language from; // Optional
    private Language to;
    private Format format; // Optional

    @Builder
    public TranslateRequestParams(RequestType requestType,
                                  ApiVersion apiVersion,
                                  String encoding,
                                  String apiKey,
                                  @Singular List<String> texts,
                                  Language from,
                                  Language to,
                                  Format format) {
        super(requestType, apiVersion, encoding, apiKey);
        this.texts = texts;
        this.from = from;
        this.to = to;
        this.format = format;
    }

    @Override
    public HttpPost buildHttpPost() throws Exception {
        HttpPost httpPost = new HttpPost(buildUrl());

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        // API key
        params.add(new BasicNameValuePair(PARAM_KEY, getApiKey()));

        // Lang
        String langPair = "";
        if (from != null && !from.equals(Language.AUTODETECT)) {
            langPair = from + "-";
        }
        langPair += to;
        params.add(new BasicNameValuePair(PARAM_LANG, langPair));

        // Text
        for (String text : texts) {
            params.add(new BasicNameValuePair(PARAM_TEXT, text));
        }

        // Format
        if (format != null) {
            params.add(new BasicNameValuePair(PARAM_FORMAT, format.toString()));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        return httpPost;
    }

    @Override
    protected String buildUrl() throws Exception {
        // Validate mandatory params
        if (getApiKey() == null || texts.size() == 0 || to == null) {
            throw new IllegalArgumentException("Missing mandatory params");
        }

        return getApiVersion().getUrl() + "/" + getRequestType() + "/translate?";
    }

}
