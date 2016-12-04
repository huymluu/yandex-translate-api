package com.unikre.yandex.params;

public enum RequestInterface {
    JSON(".json"),
    XML("");

    private final String postFix;

    private RequestInterface(String postFix) {
        this.postFix = postFix;
    }

    @Override
    public String toString() {
        return postFix;
    }
}
