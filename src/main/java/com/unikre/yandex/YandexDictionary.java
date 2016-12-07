package com.unikre.yandex;

import com.unikre.yandex.definition.TranslatedWord;
import com.unikre.yandex.definition.Word;
import com.unikre.yandex.http.YandexExecutor;
import com.unikre.yandex.http.YandexService;
import com.unikre.yandex.params.ApiVersion;
import com.unikre.yandex.params.Language;
import com.unikre.yandex.params.LookupFlag;
import com.unikre.yandex.params.RequestInterface;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YandexDictionary extends YandexExecutor {

    public YandexDictionary(String apiKey) {
        super(apiKey);
        setApiVersion(ApiVersion.DICTIONARY_LATEST);
        setRequestInterface(RequestInterface.DICTIONARY_JSON);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dictionary.yandex.net")
                .build();
        yandexService = retrofit.create(YandexService.class);
    }

    private Map<Language, List<Language>> parseSupportedTranslateDirections(Response<ResponseBody> response) throws Exception {
        validateResponse(response);

        JSONArray jsonArray = new JSONArray(response.body().string());

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

    public Map<Language, List<Language>> getSupportedTranslateDirections() throws Exception {
        Call<ResponseBody> call = yandexService.getSupportedTranslateDirections(getApiVersion(),
                getRequestInterface(),
                getApiKey());

        return parseSupportedTranslateDirections(call.execute());
    }

    public void getSupportedTranslateDirections(final YandexCallback<Map<Language, List<Language>>> callback) {
        Call<ResponseBody> call = yandexService.getSupportedTranslateDirections(getApiVersion(),
                getRequestInterface(),
                getApiKey());

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Map<Language, List<Language>> result = parseSupportedTranslateDirections(response);
                    callback.onResponse(result);
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

    private TranslatedWord parseLookup(Response<ResponseBody> response) throws Exception {
        validateResponse(response);

        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONArray defsArray = jsonObject.getJSONArray("def");
        List<TranslatedWord.Definition> definitions = parseDefinitions(defsArray);

        if (definitions.size() == 0) {
            throw new Exception("API call error: Definition not found");
        }

        TranslatedWord translatedWord = new TranslatedWord();
        Word srcWord = definitions.get(0).getSrcWord();
        translatedWord.setGen(srcWord.getGen());
        translatedWord.setTs(srcWord.getTs());
        translatedWord.setPos(srcWord.getPos());
        translatedWord.setText(srcWord.getText());
        translatedWord.setDefinitions(parseDefinitions(defsArray));

        return translatedWord;
    }

    public TranslatedWord lookup(String text, Language from, Language to, LookupFlag... flags) throws Exception {
        long flagsParam = 0;
        for (LookupFlag flag : flags) {
            if (flag != null) {
                flagsParam |= flag.getBitmask();
            }
        }

        Call<ResponseBody> call = yandexService.lookup(getApiVersion(),
                getRequestInterface(),
                getApiKey(),
                from == null ? to.toString() : from + "-" + to,
                text,
                flagsParam);

        return parseLookup(call.execute());
    }

    public TranslatedWord lookup(String text, Language from, Language to) throws Exception {
        return lookup(text, from, to, (LookupFlag) null);
    }

    public void lookup(String text, Language from, Language to, final YandexCallback<TranslatedWord> callback) {
        Call<ResponseBody> call = yandexService.lookup(getApiVersion(),
                getRequestInterface(),
                getApiKey(),
                from == null ? to.toString() : from + "-" + to,
                text,
                null);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    TranslatedWord result = parseLookup(response);
                    callback.onResponse(result);
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

    /**********
     *        *
     **********/
    private List<TranslatedWord.Definition> parseDefinitions(JSONArray defArray) {
        List<TranslatedWord.Definition> definitions = new ArrayList<>();
        for (int i = 0; i < defArray.length(); i++) {
            JSONObject defObject = defArray.getJSONObject(i);

            TranslatedWord.Definition definition = new TranslatedWord.Definition();

            Word srcWord = new Word();
            srcWord.setPos(defObject.getString("pos"));
            srcWord.setText(defObject.getString("text"));
            srcWord.setTs(defObject.getString("ts"));
            definition.setSrcWord(srcWord);

            definition.setTranslations(parseTranslations(defObject.getJSONArray("tr")));

            definitions.add(definition);
        }
        return definitions;
    }

    private List<TranslatedWord.Definition.Translation> parseTranslations(JSONArray trArray) {
        List<TranslatedWord.Definition.Translation> translations = new ArrayList<>();
        for (int i = 0; i < trArray.length(); i++) {
            JSONObject trObject = trArray.getJSONObject(i);

            TranslatedWord.Definition.Translation translation = new TranslatedWord.Definition.Translation();

            Word translatedWord = new Word();
            translatedWord.setPos(trObject.getString("pos"));
            translatedWord.setText(trObject.getString("text"));

            if (trObject.has("gen")) {
                translatedWord.setGen(trObject.getString("gen"));
            }
            translation.setTranslatedWord(translatedWord);

            if (trObject.has("ex")) {
                translation.setExamples(parseExamples(trObject.getJSONArray("ex")));
            }

            if (trObject.has("mean")) {
                translation.setSrcMeans(parseMeans(trObject.getJSONArray("mean")));
            }

            if (trObject.has("syn")) {
                translation.setSynonyms(parseSyns(trObject.getJSONArray("syn")));
            }

            translations.add(translation);
        }
        return translations;
    }

    private List<TranslatedWord.Definition.Translation.Example> parseExamples(JSONArray exArray) {
        List<TranslatedWord.Definition.Translation.Example> examples = new ArrayList<>();
        for (int i = 0; i < exArray.length(); i++) {
            JSONObject exObject = exArray.getJSONObject(i);

            TranslatedWord.Definition.Translation.Example example = new TranslatedWord.Definition.Translation.Example();
            example.setSrc(exObject.getString("text"));
            example.setTranslation(exObject.getJSONArray("tr").getJSONObject(0).getString("text"));

            examples.add(example);
        }

        return examples;
    }

    private List<String> parseMeans(JSONArray meanArray) {
        List<String> means = new ArrayList<>();
        for (int i = 0; i < meanArray.length(); i++) {
            JSONObject meanObject = meanArray.getJSONObject(i);
            means.add(meanObject.getString("text"));
        }

        return means;
    }

    private List<Word> parseSyns(JSONArray synArray) {
        List<Word> syns = new ArrayList<>();
        for (int i = 0; i < synArray.length(); i++) {
            JSONObject synObject = synArray.getJSONObject(i);

            Word syn = new Word();
            syn.setText(synObject.getString("text"));
            syn.setPos(synObject.getString("pos"));
            if (synObject.has("gen")) syn.setGen(synObject.getString("gen"));

            syns.add(syn);
        }

        return syns;
    }

}
