package com.unikre.yandextranslate.params;

public enum RequestType {
    JSON("tr.json"),
    XML("tr");

    private final String code;

    private RequestType(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
