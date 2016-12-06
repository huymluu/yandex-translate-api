package com.unikre.yandex;

import com.unikre.yandex.http.YandexExecutor;
import com.unikre.yandex.http.YandexService;
import com.unikre.yandex.params.ApiVersion;
import com.unikre.yandex.params.Language;
import com.unikre.yandex.params.RequestInterface;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;

public class YandexTranslator extends YandexExecutor {

    public YandexTranslator(String apiKey) {
        super(apiKey);
        setApiVersion(ApiVersion.TRANSLATE_LATEST);
        setRequestInterface(RequestInterface.TRANSLATE_JSON);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://translate.yandex.net")
                .build();
        yandexService = retrofit.create(YandexService.class);
    }

    private List<Language> parseSupportedLanguages(Response<ResponseBody> response) throws Exception {
        validateResponse(response);

        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONObject jsonLangsObject = jsonObject.getJSONObject("langs");

        List<Language> ret = new ArrayList<>();
        for (String code : jsonLangsObject.keySet()) {
            ret.add(Language.byCode(code));
        }
        return ret;
    }

    public List<Language> getSupportedLanguages() throws Exception {
        Call<ResponseBody> call = yandexService.getSupportedLanguages(getApiVersion(),
                getRequestInterface(),
                getApiKey(),
                Language.ENGLISH);
        return parseSupportedLanguages(call.execute());
    }

    public void getSupportedLanguages(final YandexCallback<List<Language>> callback) throws Exception {
        Call<ResponseBody> call = yandexService.getSupportedLanguages(getApiVersion(),
                getRequestInterface(),
                getApiKey(),
                Language.ENGLISH);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(parseSupportedLanguages(response));
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        };

        call.enqueue(genericCallback);
    }

    private Language parseDetectLanguage(Response<ResponseBody> response) throws Exception {
        validateResponse(response);

        JSONObject jsonObject = new JSONObject(response.body().string());
        String langCode = jsonObject.getString("lang");

        return Language.byCode(langCode);
    }

    public Language detectLanguage(String text, Language... hints) throws Exception {
        String hintList = "";
        for (Language hint : hints) {
            if (hint != null) {
                hintList += "," + hint;
            }
        }
        if (hintList.length() > 1) hintList = hintList.substring(1);

        Call<ResponseBody> call = yandexService.detectLanguage(getApiVersion(),
                getRequestInterface(),
                getApiKey(),
                text,
                hintList);

        return parseDetectLanguage(call.execute());
    }

    public void detectLanguage(String text, final YandexCallback<Language> callback, Language... hints) throws Exception {
        String hintList = "";
        for (Language hint : hints) {
            if (hint != null) {
                hintList += "," + hint;
            }
        }
        if (hintList.length() > 1) hintList = hintList.substring(1);

        Call<ResponseBody> call = yandexService.detectLanguage(getApiVersion(),
                getRequestInterface(),
                getApiKey(),
                text,
                hintList);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(parseDetectLanguage(response));
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        };

        call.enqueue(genericCallback);
    }

    private String parseTranslate(Response<ResponseBody> response) throws Exception {
        validateResponse(response);

        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONArray textArray = jsonObject.getJSONArray("text");

        return textArray.get(0).toString();
    }

    public void translate(String text, Language from, Language to, final YandexCallback<String> callback) throws Exception {
        Call<ResponseBody> call = yandexService.translate(getApiVersion(),
                getRequestInterface(),
                getApiKey(),
                text,
                from == null ? to.toString() : from + "-" + to);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(parseTranslate(response));
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        };

        call.enqueue(genericCallback);
    }

    public String translate(String text, Language from, Language to) throws Exception {
        Call<ResponseBody> call = yandexService.translate(getApiVersion(),
                getRequestInterface(),
                getApiKey(),
                text,
                from == null ? to.toString() : from + "-" + to);

        return parseTranslate(call.execute());
    }

    public String translate(String text, Language to) throws Exception {
        return translate(text, null, to);
    }


}
