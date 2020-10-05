package com.kangkan.omdbapi.ApiClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiclient{

        private static final String BASE_URL="http://www.omdbapi.com/";
        private static final String API_KEY="473ebeb0";
        private static Retrofit retrofit;

        public static Retrofit getApiClient()
        {
            if (retrofit==null){
                retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            }
            return retrofit;
        }
}
