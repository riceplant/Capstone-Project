package com.riceplant.capstoneproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.capstoneproject.R;
import com.riceplant.capstoneproject.activities.GameDetailsActivity;
import com.riceplant.capstoneproject.adapter.GameAdapter;
import com.riceplant.capstoneproject.data.Game;
import com.riceplant.capstoneproject.room.GameViewModel;
import com.riceplant.capstoneproject.room.MyGame;

import java.util.ArrayList;
import java.util.List;

public class MyLibraryFragment extends Fragment implements GameAdapter.GameAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;

    private GameAdapter mGameAdapter;
    private ArrayList<MyGame> mGamesLibrary;
    private ArrayList<Game> mGames;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_games, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mErrorMessage = view.findViewById(R.id.popular_games_error_message);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        mGamesLibrary = new ArrayList<>();
        mGames = new ArrayList<>();
        loadGamesLibrary();
        setUpViewModel();
        return view;
    }

    private void loadGamesLibrary() {
        mGames.clear();
        for (int i = 0; i < mGamesLibrary.size(); i++) {
            Game game = new Game(
                    mGamesLibrary.get(i).getId(),
                    mGamesLibrary.get(i).getCover(),
                    mGamesLibrary.get(i).getName(),
                    mGamesLibrary.get(i).getPopularity(),
                    mGamesLibrary.get(i).getSummary(),
                    mGamesLibrary.get(i).getGenres(),
                    mGamesLibrary.get(i).getPlatform(),
                    mGamesLibrary.get(i).getRating(),
                    mGamesLibrary.get(i).getReleaseDate(),
                    mGamesLibrary.get(i).getVideos()
            );
            mGames.add(game);
        }
        mProgressBar.setVisibility(View.INVISIBLE);
        mGameAdapter = new GameAdapter(mGames, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mGameAdapter);
    }


    private void setUpViewModel() {
        GameViewModel viewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        viewModel.getGames().observe(getViewLifecycleOwner(), new Observer<List<MyGame>>() {
            @Override
            public void onChanged(List<MyGame> myGames) {
                if (myGames.size() > 0) {
                    mErrorMessage.setVisibility(View.INVISIBLE);
                    mGamesLibrary.clear();
                    mGamesLibrary = (ArrayList<MyGame>) myGames;
                } else if (myGames.size() == 0) {
                    mGamesLibrary.clear();
                    mGames.clear();
                    mErrorMessage.setVisibility(View.VISIBLE);
                    mErrorMessage.setText(R.string.add_games);
                }
                loadGamesLibrary();
            }
        });
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
