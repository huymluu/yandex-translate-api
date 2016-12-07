package com.unikre.yandex.definition;

import lombok.Data;

import java.util.List;

@Data
public class TranslatedWord extends Word {
    @Data
    public static class Definition {
        @Data
        public static class Translation {
            @Data
            public static class Example {
                private String src;
                private String translation;
            }

            private Word translatedWord;
            private List<String> srcMeans;
            private List<Example> examples;
            private List<Word> synonyms;
        }

        private Word srcWord;
        private List<Translation> translations;
    }

    private List<Definition> definitions;
}
