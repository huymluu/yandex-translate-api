package com.unikre.yandex;

import com.unikre.yandex.http.DetectRequestParams;
import com.unikre.yandex.http.GetLangsRequestParams;
import com.unikre.yandex.http.TranslateRequestParams;
import com.unikre.yandex.params.ApiVersion;
import com.unikre.yandex.params.Language;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class YandexTranslator extends YandexExecutor {

    public YandexTranslator(String apiKey) {
        super(apiKey);

        setApiVersion(ApiVersion.TRANSLATE_LATEST);
    }

    public List<Language> getSupportedLanguages() throws Exception {
        return getSupportedLanguages(Language.ENGLISH);
    }

    public List<Language> getSupportedLanguages(Language ui) throws Exception {
        GetLangsRequestParams requestParams = GetLangsRequestParams.builder()
                .apiVersion(apiVersion)
                .requestInterface(requestInterface)
                .apiKey(apiKey)
                .ui(ui)
                .build();

        HttpPost httpPost = requestParams.buildHttpPost();
        CloseableHttpResponse response = httpClient.execute(httpPost);

        validateResponse(response);

        JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
        JSONObject jsonLangsObject = jsonObject.getJSONObject("langs");

        List<Language> ret = new ArrayList<>();
        for (String code : jsonLangsObject.keySet()) {
            ret.add(Language.byCode(code));
        }
        return ret;
    }

    public String translate(String text, Language from, Language to) throws Exception {

        List<String> input = new ArrayList<>();
        input.add(text);
        List<String> result = translate(input, from, to);

        return result.get(0);
    }

    public List<String> translate(List<String> texts, Language from, Language to) throws Exception {
        TranslateRequestParams requestParams = TranslateRequestParams.builder()
                .apiVersion(apiVersion)
                .requestInterface(requestInterface)
                .apiKey(apiKey)
                .texts(texts)
                .from(from)
                .to(to)
                .build();

        HttpPost httpPost = requestParams.buildHttpPost();
        CloseableHttpResponse response = httpClient.execute(httpPost);

        validateResponse(response);

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

    public Language detectLanguage(String text, Language... hints) throws Exception {
        List<Language> hintList = new ArrayList<>();
        for (Language hint : hints) {
            if (hint != null) {
                hintList.add(hint);
            }
        }

        DetectRequestParams requestParams = DetectRequestParams.builder()
                .apiVersion(apiVersion)
                .requestType(requestInterface)
                .apiKey(apiKey)
                .text(text)
                .hints(hintList)
                .build();

        HttpPost httpPost = requestParams.buildHttpPost();
        CloseableHttpResponse response = httpClient.execute(httpPost);

        validateResponse(response);

        JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
        String langCode = jsonObject.getString("lang");

        return Language.byCode(langCode);
    }

    public Language detectLanguage(String text) throws Exception {
        return detectLanguage(text, (Language) null);
    }

}
