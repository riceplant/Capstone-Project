package com.riceplant.capstoneproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.capstoneproject.R;
import com.riceplant.capstoneproject.adapter.VideoAdapter;
import com.riceplant.capstoneproject.data.Cover;
import com.riceplant.capstoneproject.data.Game;
import com.riceplant.capstoneproject.data.Genre;
import com.riceplant.capstoneproject.data.Platform;
import com.riceplant.capstoneproject.data.ReleaseDate;
import com.riceplant.capstoneproject.data.Video;
import com.riceplant.capstoneproject.room.AppExecutors;
import com.riceplant.capstoneproject.room.GameRoomDatabase;
import com.riceplant.capstoneproject.room.MyGame;
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class GameDetailsActivity extends AppCompatActivity {

    final String IMAGE_URL = "https://images.igdb.com/igdb/image/upload/t_720p/";
    final String IMAGE_FORMAT = ".jpg";

    private String coverUrl;
    private String gameName;
    private List<ReleaseDate> gameRelease;
    private Double gameRating;
    private List<Genre> gameGenre;
    private List<Platform> gamePlatform;
    private String gameSummary;
    private List<Video> gameVideo;

    public static Game mGame;
    private TextView title;
    private TextView releaseDate;
    private TextView rating;
    private TextView genre;
    private TextView platform;
    private TextView summary;
    private ImageView gameCover;
    private Button addToLibraryButton;

    private GameRoomDatabase mDb;
    private Boolean mIsInLibrary = false;

    private VideoAdapter mVideoAdapter;
    private RecyclerView mVideoRecyclerView;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        gameName = mGame.getName();
        gameRelease = mGame.getReleaseDates();
        gameRating = mGame.getRating();
        gameGenre = mGame.getGenres();
        gamePlatform = mGame.getPlatforms();
        gameSummary = mGame.getSummary();
        gameVideo = mGame.getVideos();

        Toolbar mToolbar = findViewById(R.id.toolbar_details);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(gameName);


        title = findViewById(R.id.game_title_details);
        releaseDate = findViewById(R.id.release_date_details);
        rating = findViewById(R.id.rating_details);
        genre = findViewById(R.id.genre_details);
        platform = findViewById(R.id.platform_details);
        summary = findViewById(R.id.synopsis_details);
        addToLibraryButton = findViewById(R.id.favourite_button_details);

        mDb = GameRoomDatabase.getInstance(getApplicationContext());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final MyGame myGame = mDb.gameDao().loadGameById(Integer.parseInt(String.valueOf(mGame.getId())));
                addGameToLibrary((myGame != null) ? true : false);
            }
        });

        addToLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyGame game = new MyGame(
                        mGame.getId(),
                        mGame.getCover(),
                        mGame.getName(),
                        mGame.getPopularity(),
                        mGame.getSummary(),
                        mGame.getGenres(),
                        mGame.getPlatforms(),
                        mGame.getRating(),
                        mGame.getReleaseDates(),
                        mGame.getVideos()
                );
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (mIsInLibrary) {
                            mDb.gameDao().deleteGame(game);
                        } else {
                            mDb.gameDao().insertGame(game);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addGameToLibrary(!mIsInLibrary);
                            }
                        });
                    }
                });
            }
        });

        title.setText(gameName);
        if (gameSummary != null) {
            summary.setText(gameSummary);
        } else {
            summary.setText("No Summary Available");
        }

        if (gameRelease != null) {
            releaseDate.setText(gameRelease.get(0).getHuman());
        } else {
            releaseDate.setText("No Release Date available");
        }

        if (gameGenre != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < gameGenre.size(); i++) {
                sb.append(gameGenre.get(i).getName());
                if (i < gameGenre.size() - 1) {
                    sb.append(", ");
                }
                genre.setText(sb.toString());
            }
        } else {
            genre.setText("No Genre Available");
        }

        if (gamePlatform != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < gamePlatform.size(); i++) {
                sb.append(gamePlatform.get(i).getName());
                if (i < gamePlatform.size() - 1) {
                    sb.append(", ");
                }
            }
            platform.setText(sb.toString());
        } else {
            platform.setText("No Platform Available");
        }

        if (gameRating != null) {
            DecimalFormat df = new DecimalFormat("#");
            String formattedRating = df.format(gameRating);
            rating.setText(formattedRating);
        } else {
            rating.setText("No Rating available");
        }

        gameCover = findViewById(R.id.game_cover_image_details);
        Cover cover = mGame.getCover();
        String coverId = null;
        if (cover != null)
            coverId = mGame.getCover().getImageId();
        coverUrl = IMAGE_URL + coverId + IMAGE_FORMAT;

        Picasso.get()
                .load(coverUrl)
                .error(R.drawable.image_not_found)
                .into(gameCover);

        loadVideos(gameVideo);
    }

    private void loadVideos(List<Video> videoList) {
        mVideoAdapter = new VideoAdapter(videoList, GameDetailsActivity.this);
        mVideoRecyclerView = findViewById(R.id.recycler_view_videos);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mVideoRecyclerView.setLayoutManager(layoutManager);
        mVideoRecyclerView.setHasFixedSize(true);
        mVideoRecyclerView.setAdapter(mVideoAdapter);
    }

    private void addGameToLibrary(Boolean isInLibrary) {
        if (isInLibrary) {
            mIsInLibrary = true;
            addToLibraryButton.setText(R.string.remove_library);
            addToLibraryButton.setContentDescription(getString(R.string.remove_library));
        } else {
            mIsInLibrary = false;
            addToLibraryButton.setText(R.string.add_library);
            addToLibraryButton.setContentDescription(getString(R.string.add_library));
        }
    }
}
