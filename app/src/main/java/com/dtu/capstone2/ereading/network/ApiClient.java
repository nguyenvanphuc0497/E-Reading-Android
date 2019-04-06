package com.dtu.capstone2.ereading.network;

import com.dtu.capstone2.ereading.BuildConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Create by Nguyen Van Phuc on 2/20/19
 */
public class ApiClient {
    private static final long API_TIMEOUT = 15000L;// Time out = 15s
    private static ApiClient sApiClient;
    //    private static String sBaseUrl = "https://e-reading.herokuapp.com/api/";
    private static String sBaseUrl = "http://rss.cnn.com/rss/";

    public static ApiClient getInstants() {
        if (sApiClient == null) {
            sApiClient = new ApiClient();
        }
        return sApiClient;
    }

    public ApiServer createServer() {
        //Pares data
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .serializeNulls()
                .create();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        //Show log request
        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        // Header for request
        httpClientBuilder.interceptors()
                .add(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = (original).newBuilder()
                                .method((original).method(), (original).body());

                        // Request customization: add request headers
//                        requestBuilder.addHeader("Authorization", "Token edc116fca3e3c5149b1d9f1842229b5419a8a83d");
                        requestBuilder.addHeader("app-version", BuildConfig.VERSION_NAME);
                        requestBuilder.addHeader("User-Agent", "Android");

                        return chain.proceed(requestBuilder.build());
                    }
                });

        // Set time out for request
        OkHttpClient client = httpClientBuilder.connectTimeout(API_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(API_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(API_TIMEOUT, TimeUnit.MILLISECONDS)
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sBaseUrl)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy())))
//                .addCallAdapterFactory(CustomCallAdapterFactory.Companion.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ApiServer.class);
    }
}
