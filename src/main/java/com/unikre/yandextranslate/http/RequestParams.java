package com.unikre.yandextranslate.http;

import com.unikre.yandextranslate.params.ApiVersion;
import com.unikre.yandextranslate.params.RequestType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.http.client.methods.HttpPost;

@AllArgsConstructor
@Getter
public abstract class RequestParams {
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final RequestType DEFAULT_REQUEST_TYPE = RequestType.JSON;
    public static final ApiVersion DEFAULT_API_VERSION = ApiVersion.LATEST;

    protected static final String PARAM_KEY = "key";

    private RequestType requestType; // Optional
    private ApiVersion apiVersion; // Optional
    private String encoding; // Optional
    @NonNull
    private String apiKey;

    // Lombok workaround default values, can improve it?
    public RequestType getRequestType() {
        if (requestType == null) requestType = DEFAULT_REQUEST_TYPE;
        return requestType;
    }

    public ApiVersion getApiVersion() {
        if (apiVersion == null) apiVersion = DEFAULT_API_VERSION;
        return apiVersion;
    }

    public String getEncoding() {
        if (encoding == null) encoding = DEFAULT_ENCODING;
        return encoding;
    }

    public abstract HttpPost buildHttpPost() throws Exception;

    protected abstract String buildUrl() throws Exception;
}
