package com.unikre.yandextranslate.params;

public enum ApiVersion {
    VERSION_1_5("1.5", "https://translate.yandex.net/api/v1.5"),
    LATEST("1.5", "https://translate.yandex.net/api/v1.5");

    private final String version;
    private final String url;

    private ApiVersion(String version, String url) {
        this.version = version;
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
