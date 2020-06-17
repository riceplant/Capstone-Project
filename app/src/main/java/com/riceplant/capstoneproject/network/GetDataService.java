package com.riceplant.capstoneproject.network;

import com.riceplant.capstoneproject.data.Game;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetDataService {

    @Headers("user-key: 0d22adeb6cf3650dbefe7f8214537075")
    @POST("games")
    Call<ArrayList<Game>> getAllGames(@Query("fields") String fields);
}
