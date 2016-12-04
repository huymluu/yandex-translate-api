package com.unikre.yandex.params;

public enum ApiVersion {
    VERSION_1_5("v1.5"),
    LATEST("v1.5");

    private final String version;

    private ApiVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return version;
    }
}
