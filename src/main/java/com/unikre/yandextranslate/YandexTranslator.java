package com.unikre.yandextranslate;

import com.unikre.yandextranslate.http.RequestParams;
import com.unikre.yandextranslate.http.ResponseCode;
import com.unikre.yandextranslate.params.ApiVersion;
import com.unikre.yandextranslate.params.Language;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class YandexTranslator {

    private String apiKey;
    private ApiVersion apiVersion;
    private CloseableHttpClient httpClient;

    public YandexTranslator(String apiKey) {
        setApiKey(apiKey);
        setApiVersion(ApiVersion.LATEST);
        httpClient = HttpClients.createDefault();
    }

    public String translate(String text, Language from, Language to) throws Exception {

        List<String> input = new ArrayList<>();
        input.add(text);
        List<String> result = translate(input, from, to);

        return result.get(0);
    }

    public List<String> translate(List<String> texts, Language from, Language to) throws Exception {
        RequestParams requestParams = RequestParams.builder()
                .apiVersion(apiVersion)
                .apiKey(apiKey)
                .texts(texts)
                .from(from)
                .to(to)
                .build();

        HttpPost httpPost = requestParams.buildHttpPost();
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // Response - status code
        ResponseCode responseCode = ResponseCode.byCode(response.getStatusLine().getStatusCode());
        if (responseCode == null) {
            throw new Exception("API call error: " + response.getStatusLine().getStatusCode() + " - " + response.getStatusLine().getReasonPhrase());
        } else if (responseCode.compareTo(ResponseCode.OK) != 0) {
            throw new Exception("API call error: " + responseCode.code + " - " + responseCode.description);
        }

        // Response - body
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity == null) {
            throw new Exception("API call error: Empty response body");
        }

        JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
        JSONArray textArray = jsonObject.getJSONArray("text");

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < textArray.length(); i++) {
            list.add(textArray.getString(i));
        }

        return list;
    }

    public String translate(String text, Language to) throws Exception {
        return translate(text, Language.AUTODETECT, to);
    }

    public List<String> translate(List<String> texts, Language to) throws Exception {
        return translate(texts, Language.AUTODETECT, to);
    }

}
