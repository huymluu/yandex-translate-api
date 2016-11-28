package com.unikre.yandextranslate.http;

public enum ResponseCode {
    OK(200, "Operation completed successfully"),
    INVALID_API_KEY(401, "Invalid API key"),
    BLOCKED_API_KEY(402, "Blocked API key"),
    DAILY_LIMIT(404, "Exceeded the daily limit on the amount of translated text"),
    TEXT_LIMIT(413, "Exceeded the maximum text size"),
    FAILED_TO_TRANSLATE(422, "The text cannot be translated"),
    NOT_SUPPORTED(501, "The specified translation direction is not supported");

    public final int code;
    public final String description;

    private ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ResponseCode byCode(int code) {
        for (ResponseCode responseCode : values()) {
            if (responseCode.code == code) {
                return responseCode;
            }
        }
        return null;
    }

}
