package com.unikre.yandex;

import com.unikre.yandex.definition.TranslatedWord;
import com.unikre.yandex.definition.Word;

public class YandexUtil {
    public static String humanize(TranslatedWord translatedWord) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TranslatedWord.Definition definition : translatedWord.getDefinitions()) {
            stringBuilder.append(definition.getSrcWord().getPos() + "\n");

            for (TranslatedWord.Definition.Translation translation : definition.getTranslations()) {
                stringBuilder.append(humanize(translation));
            }
        }

        return stringBuilder.toString();
    }

    public static String humanize(Word word) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word.getText());
        if (word.getGen() != null) {
            stringBuilder.append("(");
            stringBuilder.append(word.getGen());
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    public static String humanize(TranslatedWord.Definition.Translation translation) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("    " + humanize(translation.getTranslatedWord()));

        if (translation.getSynonyms() != null) {
            stringBuilder.append(" ~ ");
            for (Word syn : translation.getSynonyms()) {
                stringBuilder.append(humanize(syn) + " ");
            }
        }
        stringBuilder.append("\n");

        if (translation.getSrcMeans() != null) {
            stringBuilder.append("    [");
            String means = "";
            for (String mean : translation.getSrcMeans()) {
                means += " " + mean;
            }
            stringBuilder.append(means.substring(1));
            stringBuilder.append("]\n");
        }

        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}
