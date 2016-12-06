package com.unikre.yandex;

import com.unikre.yandex.params.Language;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TranslatorTest {

    private static final String SAMPLE_KEY = "trnsl.1.1.20161127T135107Z.1de8c96e60cdc4ab.f67b9a245a8646dcf6b3f2638580def896ba1750";
    private static final YandexTranslator yandexTranslator = new YandexTranslator(SAMPLE_KEY);

    @Test
    public void testTranslate() throws Exception {
        // Explicit
        Assert.assertEquals(yandexTranslator.translate("Hello world", Language.ENGLISH, Language.GERMAN), "Hallo Welt");

        // Auto detect 'from' lang
        Assert.assertEquals(yandexTranslator.translate("Hello world", Language.GERMAN), "Hallo Welt");
    }

    @Test
    public void testTranslateAsync() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);
        yandexTranslator.translate("Hello world", Language.ENGLISH, Language.GERMAN, new YandexCallback<String>() {
            @Override
            public void onResponse(String result) {
                Assert.assertEquals(result, "Hallo Welt");
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
    public void testGetLangs() throws Exception {
        List<Language> languages = yandexTranslator.getSupportedLanguages();
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
