package com.unikre.yandextranslate.params;

public enum Language {

    AUTODETECT(""),
    ENGLISH("en"),
    FRENCH("fr"),
    GERMAN("de"),
    ITALIAN("it"),
    PORTUGUESE("pt"),
    RUSSIAN("ru"),
    SPANISH("es"),
    VIETNAMESE("vi");

    private final String code;

    private Language(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
