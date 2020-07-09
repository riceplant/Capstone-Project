package com.riceplant.capstoneproject.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.capstoneproject.R;
import com.riceplant.capstoneproject.adapter.GameAdapter;
import com.riceplant.capstoneproject.data.Game;
import com.riceplant.capstoneproject.network.GameInstance;
import com.riceplant.capstoneproject.network.GetDataService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity implements GameAdapter.GameAdapterOnClickHandler{
    private RecyclerView mRecyclerView;
    private TextView mErrorTextMessage;
    private ProgressBar mProgressBar;

    private GameAdapter mGameAdapter;
    private ArrayList<Game> mGames;

    public static final String FIELDS = "name, platforms.name, cover, cover.url, cover.image_id, rating, release_dates.human, genres.name, summary, popularity, time_to_beat, videos.name, videos.video_id;";
    public static final String SEARCH = "search ";
    public static final String LIMIT = "limit 20;";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar_search_results);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        handleIntent(getIntent());

        mProgressBar = findViewById(R.id.search_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            GetDataService service = GameInstance.getGameInstance().create(GetDataService.class);
            Call<ArrayList<Game>> call = service.getAllGames(FIELDS + LIMIT + SEARCH + "\"" + query + "\"");
            call.enqueue(new Callback<ArrayList<Game>>() {
                @Override
                public void onResponse(Call<ArrayList<Game>> call, Response<ArrayList<Game>> response) {
                    if (response.isSuccessful()) {
                        mGames = response.body();
                        getSupportActionBar().setTitle(query);
                        generateDataList(mGames);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Game>> call, Throwable t) {
                    mErrorTextMessage = findViewById(R.id.popular_games_error_message);
                    mErrorTextMessage.setText("Something went wrong. Try again!");
                }
            });
        }
    }

    private void generateDataList(ArrayList<Game> gameList) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mGameAdapter = new GameAdapter(gameList, SearchResultsActivity.this);

        mRecyclerView = findViewById(R.id.search_results_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchResultsActivity.this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mGameAdapter);
    }

    @Override
    public void onClick(int adapterPosition) {
        Context context = SearchResultsActivity.this;
        Class detailClass = GameDetailsActivity.class;
        GameDetailsActivity.mGame = mGames.get(adapterPosition);

        Intent detailIntent = new Intent(context, detailClass);
        startActivity(detailIntent);
    }
}
