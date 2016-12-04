package com.unikre.yandex;

import com.unikre.yandex.params.Language;
import com.unikre.yandex.params.RequestInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class DictionaryTest {

    private static final String SAMPLE_KEY = "dict.1.1.20161204T031851Z.2550bd8d1d3461c6.9ca71217641bdab8a71f5f3b474cc4dbc5e53f3e";
    private static final YandexDictionary yandexDictionary = new YandexDictionary(SAMPLE_KEY);

    @Before
    public void init() {
        yandexDictionary.setRequestInterface(RequestInterface.JSON);
    }

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

}
