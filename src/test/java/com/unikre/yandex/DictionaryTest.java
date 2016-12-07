package com.unikre.yandex;

import com.unikre.yandex.definition.TranslatedWord;
import com.unikre.yandex.params.Language;
import com.unikre.yandex.params.LookupFlag;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DictionaryTest {

    private static final String SAMPLE_KEY = "dict.1.1.20161204T031851Z.2550bd8d1d3461c6.9ca71217641bdab8a71f5f3b474cc4dbc5e53f3e";
    private static final YandexDictionary yandexDictionary = new YandexDictionary(SAMPLE_KEY);

    @Test
    public void testGetLangs() throws Exception {
        Map<Language, List<Language>> directions = yandexDictionary.getSupportedTranslateDirections();
        Assert.assertTrue(directions.size() > 0);

        for (Map.Entry entry : directions.entrySet()) {
            Language from = (Language) entry.getKey();
            List<Language> toLanguages = (List<Language>) entry.getValue();

            System.out.println("---- From: " + from);

            for (Language to : toLanguages) {
                System.out.println("To: " + to);
            }
        }
    }

    @Test
    public void testGetLangsAsync() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);
        yandexDictionary.getSupportedTranslateDirections(new YandexCallback<Map<Language, List<Language>>>() {
            @Override
            public void onResponse(Map<Language, List<Language>> result) {
                for (Map.Entry entry : result.entrySet()) {
                    Language from = (Language) entry.getKey();
                    List<Language> toLanguages = (List<Language>) entry.getValue();

                    System.out.println("---- From: " + from);

                    for (Language to : toLanguages) {
                        System.out.println("To: " + to);
                    }
                }
                lock.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        lock.await(5000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testLookup() throws Exception {
        TranslatedWord translatedWord = yandexDictionary.lookup("time", Language.ENGLISH, Language.GERMAN);
        Assert.assertTrue(translatedWord.getDefinitions().size() == 1);

        translatedWord = yandexDictionary.lookup("time", Language.ENGLISH, Language.ENGLISH);
        Assert.assertTrue(translatedWord.getDefinitions().size() > 0);

        translatedWord = yandexDictionary.lookup("time", Language.ENGLISH, Language.RUSSIAN);
        Assert.assertTrue(translatedWord.getDefinitions().size() == 3);
    }

    @Test
    public void testLookupWithFlags() throws Exception {
        TranslatedWord translatedWord = yandexDictionary.lookup("time", Language.ENGLISH, Language.GERMAN, LookupFlag.FAMILY, LookupFlag.MORPHO, LookupFlag.POS_FILTER);
        Assert.assertTrue(translatedWord.getDefinitions().size() == 1);
    }

    @Test
    public void testLookupAsync() throws InterruptedException {
        final CountDownLatch lock = new CountDownLatch(1);
        yandexDictionary.lookup("book", Language.ENGLISH, Language.GERMAN, new YandexCallback<TranslatedWord>() {
            @Override
            public void onResponse(TranslatedWord result) {
                Assert.assertTrue(result.getDefinitions().size() > 0);
                lock.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        lock.await(5000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testLookupHumanize() throws Exception {
        TranslatedWord translatedWord = yandexDictionary.lookup("time", Language.ENGLISH, Language.RUSSIAN);
        Assert.assertTrue(translatedWord.getDefinitions().size() == 3);
        System.out.println(YandexUtil.humanize(translatedWord));
    }
}
