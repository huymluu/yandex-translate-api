package com.unikre.yandex.params;

public enum YandexService {
    TRANSLATE("https://translate.yandex.net/api/", "tr"),
    DICTIONARY("https://dictionary.yandex.net/api/", "dicservice");

    private final String url;
    private final String interfacePrefix;

    private YandexService(String url, String interfacePrefix) {
        this.url = url;
        this.interfacePrefix = interfacePrefix;
    }

    public static String buildUrl(YandexService yandexService, ApiVersion apiVersion, RequestInterface requestInterface) {
        return yandexService.url
                + apiVersion.toString() + "/"
                + yandexService.interfacePrefix + requestInterface.toString();
    }
}
