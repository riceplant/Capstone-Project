package com.riceplant.capstoneproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.capstoneproject.GameDetailsActivity;
import com.riceplant.capstoneproject.R;
import com.riceplant.capstoneproject.adapter.GameAdapter;
import com.riceplant.capstoneproject.data.Game;
import com.riceplant.capstoneproject.network.GameInstance;
import com.riceplant.capstoneproject.network.GetDataService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularGamesFragment extends Fragment implements GameAdapter.GameAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private TextView mErrorTextMessage;

    private GameAdapter mGameAdapter;
    private ArrayList<Game> mGames;

    public static final String FIELDS = "name, platforms.name, cover, cover.url, cover.image_id, rating, release_dates.human, genres.name, summary, popularity, time_to_beat, videos.name, videos.video_id;";
    public static final String POPULARITY_SORTING = "sort popularity desc;";
    public static final String LIMIT = "limit 100;";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_games, container, false);

        GetDataService service = GameInstance.getGameInstance().create(GetDataService.class);
        Call<ArrayList<Game>> call = service.getAllGames(FIELDS + POPULARITY_SORTING + LIMIT);
        call.enqueue(new Callback<ArrayList<Game>>() {
            @Override
            public void onResponse(Call<ArrayList<Game>> call, Response<ArrayList<Game>> response) {
                mRecyclerView = view.findViewById(R.id.recycler_view);
                mGames = response.body();
                generateDataList(mGames);
            }

            @Override
            public void onFailure(Call<ArrayList<Game>> call, Throwable t) {
                mErrorTextMessage = view.findViewById(R.id.popular_games_error_message);
                mErrorTextMessage.setText("Something went wrong. Try again!");
            }
        });

        return view;
    }

    private void generateDataList(ArrayList<Game> gameList) {
        mGameAdapter = new GameAdapter(gameList, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mGameAdapter);
    }

    @Override
    public void onClick(int adapterPosition) {
        Context context = getActivity();
        Class detailClass = GameDetailsActivity.class;
        GameDetailsActivity.mGame = mGames.get(adapterPosition);

        Intent detailIntent = new Intent(context, detailClass);
        startActivity(detailIntent);
    }
}
