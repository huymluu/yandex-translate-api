package com.unikre.yandex;

import com.unikre.yandex.params.Language;
import com.unikre.yandex.params.RequestInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TranslatorTest {

    private static final String SAMPLE_KEY = "trnsl.1.1.20161127T135107Z.1de8c96e60cdc4ab.f67b9a245a8646dcf6b3f2638580def896ba1750";
    private static final YandexTranslator yandexTranslator = new YandexTranslator(SAMPLE_KEY);

    @Before
    public void init() {
        yandexTranslator.setRequestInterface(RequestInterface.JSON);
    }

    @Test
    public void testTranslateSingleText() throws Exception {
        // Explicit
        Assert.assertEquals(yandexTranslator.translate("Hello world", Language.ENGLISH, Language.GERMAN), "Hallo Welt");

        // Auto detect 'from' lang
        Assert.assertEquals(yandexTranslator.translate("Hello world", Language.GERMAN), "Hallo Welt");
    }

    @Test
    public void testTranslateMultiText() throws Exception {
        List<String> input = new ArrayList<String>();
        input.add("Hello");
        input.add("World");

        List<String> output = yandexTranslator.translate(input, Language.ENGLISH, Language.GERMAN);
        Assert.assertEquals(output.size(), 2);
        Assert.assertEquals(output.get(0), "Hallo");
        Assert.assertEquals(output.get(1), "Welt");
    }

    @Test
    public void testGetLangs() throws Exception {
        List<Language> languages = yandexTranslator.getSupportedLanguages(Language.GERMAN);
        Assert.assertTrue(languages.size() > 0);

        languages = yandexTranslator.getSupportedLanguages();
        Assert.assertTrue(languages.size() > 0);
    }

    @Test
    public void testDetectLanguage() throws Exception {
        Assert.assertTrue(yandexTranslator.detectLanguage("Hello") == Language.ENGLISH);

        Assert.assertTrue(yandexTranslator.detectLanguage("Guten") == Language.GERMAN);

        Assert.assertTrue(yandexTranslator.detectLanguage("Hallo", Language.GERMAN) == Language.GERMAN);

        Assert.assertTrue(yandexTranslator.detectLanguage("Hallo", Language.GERMAN, Language.ENGLISH) == Language.GERMAN);
    }
}