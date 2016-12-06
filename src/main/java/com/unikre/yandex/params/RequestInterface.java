package com.unikre.yandex.params;

public enum RequestInterface {
    TRANSLATE_JSON("tr.json"),
    TRANSLATE_XML("tr"),
    DICTIONARY_JSON("dicservice.json"),
    DICTIONARY_XML("dicservice"),;

    private final String code;

    private RequestInterface(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
