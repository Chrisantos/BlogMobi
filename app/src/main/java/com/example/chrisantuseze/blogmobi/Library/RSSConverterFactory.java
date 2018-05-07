package com.example.chrisantuseze.blogmobi.Library;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by CHRISANTUS EZE on 3/24/2018.
 */

public class RSSConverterFactory extends Converter.Factory {
    /**
     * Creates an instance
     *
     * @return instance
     */
    public static RSSConverterFactory create() {
        return new RSSConverterFactory();
    }

    /**
     * Constructor
     */
    private RSSConverterFactory() {
        super();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new RSSResponseBodyConverter<>();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.stringConverter(type, annotations, retrofit);
    }
}
