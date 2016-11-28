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

@Builder
public class RequestParams {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final RequestType DEFAULT_REQUEST_TYPE = RequestType.JSON;

    private static final String PARAM_KEY = "key";
    private static final String PARAM_TEXT = "text";
    private static final String PARAM_LANG = "lang";
    private static final String PARAM_FORMAT = "format"; // Optional
    private static final String PARAM_OPTIONS = "options"; // Optional
    private static final String PARAM_CALLBACK = "callback"; // Optional

    private RequestType requestType; // Optional
    private ApiVersion apiVersion; // Optional
    private String encoding; // Optional
    private String apiKey;
    @Singular
    private List<String> texts;
    private Language from; // Optional
    private Language to;
    private Format format; // Optional

    // Lombok workaround default values
    public static class RequestParamsBuilder {
        private RequestType requestType = DEFAULT_REQUEST_TYPE;
        private ApiVersion apiVersion = ApiVersion.LATEST;
        private String encoding = DEFAULT_ENCODING;
    }

    public HttpPost buildHttpPost() throws Exception {
        HttpPost httpPost = new HttpPost(buildUrl());

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        // API key
        params.add(new BasicNameValuePair(PARAM_KEY, apiKey));

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

    private String buildUrl() throws Exception {
        // Validate mandatory params
        if (apiKey == null || texts.size() == 0 || to == null) {
            throw new IllegalArgumentException("Missing mandatory params");
        }

        return apiVersion.getUrl() + "/" + requestType + "/translate?";
    }

}
