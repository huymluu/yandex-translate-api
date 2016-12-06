package com.unikre.yandex;

public interface YandexCallback<T> {
    void onResponse(T result);

    void onFailure(Throwable t);
}
