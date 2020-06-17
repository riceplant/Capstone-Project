package com.riceplant.capstoneproject.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameInstance {

    private static Retrofit retrofit;
    public static final String BASE_URL = "https://api-v3.igdb.com/";

    public static Retrofit getGameInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
