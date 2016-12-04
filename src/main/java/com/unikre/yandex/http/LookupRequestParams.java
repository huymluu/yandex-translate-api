package com.unikre.yandex.http;

import com.unikre.yandex.params.ApiVersion;
import com.unikre.yandex.params.Language;
import com.unikre.yandex.params.LookupFlag;
import com.unikre.yandex.params.RequestInterface;
import com.unikre.yandex.params.YandexService;
import lombok.Builder;
import lombok.Singular;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LookupRequestParams extends RequestParams {

    private static final String PARAM_LANG = "lang";
    private static final String PARAM_TEXT = "text";
    private static final String PARAM_UI = "ui"; // Optional
    private static final String PARAM_FLAGS = "flags"; // Optional
    private static final String PARAM_CALLBACK = "callback"; // Optional

    private String text;
    private Language from;
    private Language to;
    private Language ui; // Optional
    private List<LookupFlag> flags; // Optional

    @Builder
    public LookupRequestParams(RequestInterface requestInterface,
                               ApiVersion apiVersion,
                               String encoding,
                               String apiKey,
                               String text,
                               Language from,
                               Language to,
                               Language ui,
                               @Singular List<LookupFlag> flags) {
        super(requestInterface, apiVersion, encoding, apiKey);

        this.text = text;
        this.from = from;
        this.to = to;
        this.ui = ui;
        this.flags = flags;
    }

    @Override
    public HttpPost buildHttpPost() throws Exception {
        HttpPost httpPost = new HttpPost(buildUrl());

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        // API key
        params.add(new BasicNameValuePair(PARAM_KEY, getApiKey()));

        // Lang
        params.add(new BasicNameValuePair(PARAM_LANG, from + "-" + to));

        // Text
        params.add(new BasicNameValuePair(PARAM_TEXT, text));

        if (ui != null && !ui.equals(Language.AUTODETECT)) {
            params.add(new BasicNameValuePair(PARAM_UI, ui.toString()));
        }

        if (flags != null && flags.size() > 0) {
            int bitmask = 0;
            for (LookupFlag flag : flags) {
                bitmask |= flag.getBitmask();
            }
            params.add(new BasicNameValuePair(PARAM_FLAGS, String.valueOf(bitmask)));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        return httpPost;
    }

    @Override
    protected String buildUrl() throws Exception {
        // Validate mandatory params
        if (getApiKey() == null || text == null || text.length() == 0 || from == null || to == null) {
            throw new IllegalArgumentException("Missing mandatory params");
        }

        return YandexService.buildUrl(YandexService.DICTIONARY, getApiVersion(), getRequestType()) + "/lookup?";
    }

}
