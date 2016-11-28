package com.unikre.yandextranslate.http;

import com.unikre.yandextranslate.params.ApiVersion;
import com.unikre.yandextranslate.params.Format;
import com.unikre.yandextranslate.params.Language;
import com.unikre.yandextranslate.params.RequestType;
import lombok.Builder;

import java.net.URLEncoder;

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
    private String text;
    private Language from; // Optional
    private Language to;
    private Format format; // Optional

    // Lombok workaround default values
    public static class RequestParamsBuilder {
        private RequestType requestType = DEFAULT_REQUEST_TYPE;
        private ApiVersion apiVersion = ApiVersion.LATEST;
        private String encoding = DEFAULT_ENCODING;
    }

    public String buildUrl() throws Exception {

        // Validate mandatory params
        if (apiKey == null || text == null || to == null) {
            throw new IllegalArgumentException("Missing mandatory params");
        }

        String url = apiVersion.getUrl() + "/" + requestType + "/translate?";

        // API key
        url += PARAM_KEY + "=" + URLEncoder.encode(apiKey, this.encoding);

        // Lang
        String langPair = "";
        if (from != null && !from.equals(Language.AUTODETECT)) {
            langPair = from + "-";
        }
        langPair += to;
        url += "&" + PARAM_LANG + "=" + URLEncoder.encode(langPair, this.encoding);

        // Text
        url += "&" + PARAM_TEXT + "=" + URLEncoder.encode(text, this.encoding);

        if (format != null) {
            url += "&" + PARAM_FORMAT + "=" + URLEncoder.encode(format.toString(), this.encoding);
        }

        return url;
    }

}
