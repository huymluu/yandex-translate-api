package com.unikre.yandex.params;

public enum ApiVersion {
    TRANSLATE_1_5("v1.5"),
    TRANSLATE_LATEST("v1.5"),

    DICTIONARY_1("v1"),
    DICTIONARY_LATEST("v1");

    private final String version;

    private ApiVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return version;
    }
}
