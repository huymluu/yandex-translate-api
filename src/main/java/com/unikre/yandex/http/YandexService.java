package com.unikre.yandex.http;

import com.unikre.yandex.params.ApiVersion;
import com.unikre.yandex.params.Language;
import com.unikre.yandex.params.RequestInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YandexService {
    @GET("/api/{version}/{requestInterface}/getLangs")
    Call<ResponseBody> getSupportedLanguages(@Path("version") ApiVersion apiVersion,
                                             @Path("requestInterface") RequestInterface requestInterface,
                                             @Query("key") String key,
                                             @Query("ui") Language ui);

    @GET("/api/{version}/{requestInterface}/translate")
    Call<ResponseBody> translate(@Path("version") ApiVersion apiVersion,
                                 @Path("requestInterface") RequestInterface requestInterface,
                                 @Query("key") String key,
                                 @Query("text") String text,
                                 @Query("lang") String lang);

    @GET("/api/{version}/{requestInterface}/detect")
    Call<ResponseBody> detectLanguage(@Path("version") ApiVersion apiVersion,
                                      @Path("requestInterface") RequestInterface requestInterface,
                                      @Query("key") String key,
                                      @Query("text") String text,
                                      @Query("hint") String hints);


    @GET("/api/{version}/{requestInterface}/getLangs")
    Call<ResponseBody> getSupportedTranslateDirections(@Path("version") ApiVersion apiVersion,
                                                       @Path("requestInterface") RequestInterface requestInterface,
                                                       @Query("key") String key);

    @GET("/api/{version}/{requestInterface}/lookup")
    Call<ResponseBody> lookup(@Path("version") ApiVersion apiVersion,
                              @Path("requestInterface") RequestInterface requestInterface,
                              @Query("key") String key,
                              @Query("lang") String lang,
                              @Query("text") String text,
                              @Query("flags") Long flags);
}
