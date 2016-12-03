package com.unikre.yandextranslate;

import com.unikre.yandextranslate.params.Language;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TranslatorTest {

    private static final String SAMPLE_KEY = "trnsl.1.1.20161127T135107Z.1de8c96e60cdc4ab.f67b9a245a8646dcf6b3f2638580def896ba1750";

    @Test
    public void test1() throws Exception {
        YandexTranslator yandexTranslator = new YandexTranslator(SAMPLE_KEY);
        String output = yandexTranslator.translate("Hello world", Language.ENGLISH, Language.GERMAN);
        Assert.assertEquals(output, "Hallo Welt");
    }

    @Test
    public void testMultiText() throws Exception {
        YandexTranslator yandexTranslator = new YandexTranslator(SAMPLE_KEY);

        List<String> input = new ArrayList<String>();
        input.add("Hello");
        input.add("World");

        List<String> output = yandexTranslator.translate(input, Language.ENGLISH, Language.GERMAN);
        Assert.assertEquals(output.size(), 2);
        Assert.assertEquals(output.get(0), "Hallo");
        Assert.assertEquals(output.get(1), "Welt");
    }

    @Test
    public void testAutoDetectFrom() throws Exception {
        YandexTranslator yandexTranslator = new YandexTranslator(SAMPLE_KEY);

        // Single
        String singleOutput = yandexTranslator.translate("Hallo Welt", Language.ENGLISH);
        Assert.assertEquals(singleOutput, "Hello World");

        // Multi
        List<String> input = new ArrayList<String>();
        input.add("Hello");
        input.add("World");

        List<String> output = yandexTranslator.translate(input, Language.GERMAN);
        Assert.assertEquals(output.size(), 2);
        Assert.assertEquals(output.get(0), "Hallo");
        Assert.assertEquals(output.get(1), "Welt");
    }

    @Test
    public void testGetLangs() throws Exception {
        YandexTranslator yandexTranslator = new YandexTranslator(SAMPLE_KEY);

        List<Language> languages = yandexTranslator.getSupportedLanguages(Language.GERMAN);
        Assert.assertTrue(languages.size() > 0);

        languages = yandexTranslator.getSupportedLanguages();
        Assert.assertTrue(languages.size() > 0);
    }

    @Test
    public void testDetectLanguage() throws Exception {
        YandexTranslator yandexTranslator = new YandexTranslator(SAMPLE_KEY);

        Assert.assertTrue(yandexTranslator.detectLanguage("Hello") == Language.ENGLISH);

        Assert.assertTrue(yandexTranslator.detectLanguage("Guten") == Language.GERMAN);

        Assert.assertTrue(yandexTranslator.detectLanguage("Hallo", Language.GERMAN) == Language.GERMAN);

        Assert.assertTrue(yandexTranslator.detectLanguage("Hallo", Language.GERMAN, Language.ENGLISH) == Language.GERMAN);
    }
}
