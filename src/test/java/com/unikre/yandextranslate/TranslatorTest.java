package com.unikre.yandextranslate;

import com.unikre.yandextranslate.params.Language;
import org.junit.Assert;
import org.junit.Test;

public class TranslatorTest {

    private static final String SAMPLE_KEY = "trnsl.1.1.20161127T135107Z.1de8c96e60cdc4ab.f67b9a245a8646dcf6b3f2638580def896ba1750";

    @Test
    public void test1() throws Exception {
        YandexTranslator yandexTranslator = new YandexTranslator(SAMPLE_KEY);
        String output = yandexTranslator.translate("Hello world", Language.ENGLISH, Language.GERMAN);
        Assert.assertEquals(output, "Hallo Welt");
    }
}
