package com.unikre.yandex;

import com.unikre.yandex.http.GetSupportedTranslateDirectionsRequestParams;
import com.unikre.yandex.params.ApiVersion;
import com.unikre.yandex.params.Language;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YandexDictionary extends YandexExecutor {

    public YandexDictionary(String apiKey) {
        super(apiKey);

        setApiVersion(ApiVersion.DICTIONARY_LATEST);
    }

    public Map<Language, List<Language>> getSupportedTranslateDirections() throws Exception {
        GetSupportedTranslateDirectionsRequestParams requestParams = GetSupportedTranslateDirectionsRequestParams.builder()
                .apiVersion(apiVersion)
                .requestInterface(requestInterface)
                .apiKey(apiKey)
                .build();

        HttpPost httpPost = requestParams.buildHttpPost();
        CloseableHttpResponse response = httpClient.execute(httpPost);

        validateResponse(response);

        JSONArray jsonArray = new JSONArray(EntityUtils.toString(response.getEntity()));

        Map<Language, List<Language>> map = new HashMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            String[] pairs = jsonArray.getString(i).split("-");

            Language from = Language.byCode(pairs[0]);
            Language to = Language.byCode(pairs[1]);

            List<Language> toLanguages = map.get(from);
            if (toLanguages == null) {
                toLanguages = new ArrayList<>();
            }
            toLanguages.add(to);

            map.put(from, toLanguages);
        }
        return map;
    }

}
