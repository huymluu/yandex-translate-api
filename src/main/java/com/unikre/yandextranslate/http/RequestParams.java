package com.unikre.yandextranslate.http;

import com.unikre.yandextranslate.params.ApiVersion;
import com.unikre.yandextranslate.params.RequestType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.http.client.methods.HttpPost;

@AllArgsConstructor
@Getter
abstract class RequestParams {
    public static final String DEFAULT_ENCODING = "UTF-8";

    protected static final String PARAM_KEY = "key";

    @NonNull
    private RequestType requestType;

    @NonNull
    private ApiVersion apiVersion;

    private String encoding; // Optional

    @NonNull
    private String apiKey;

    // Lombok workaround default values, can improve it?
    public String getEncoding() {
        if (encoding == null) encoding = DEFAULT_ENCODING;
        return encoding;
    }

    public abstract HttpPost buildHttpPost() throws Exception;

    protected abstract String buildUrl() throws Exception;
}
