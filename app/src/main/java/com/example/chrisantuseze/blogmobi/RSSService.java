package com.example.chrisantuseze.blogmobi;

import com.example.chrisantuseze.blogmobi.Library.RSSFeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by CHRISANTUS EZE on 3/24/2018.
 */

public interface RSSService {
    /**
     * No baseUrl defined. Each RSS Feed will be consumed by it's Url
     * @param url RSS Feed Url
     * @return Retrofit Call
     */
    @GET
    Call<RSSFeed> getRss(@Url String url);
}
