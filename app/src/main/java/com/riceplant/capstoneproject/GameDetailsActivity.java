package com.riceplant.capstoneproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.riceplant.capstoneproject.data.Cover;
import com.riceplant.capstoneproject.data.Game;
import com.riceplant.capstoneproject.data.Genre;
import com.riceplant.capstoneproject.data.Platform;
import com.riceplant.capstoneproject.data.ReleaseDate;
import com.squareup.picasso.Picasso;

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

    public static Game mGame;
    private TextView title;
    private TextView releaseDate;
    private TextView rating;
    private TextView genre;
    private TextView platform;
    private TextView summary;
    private ImageView gameCover;

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
            for (int i = 0; i < gameGenre.size(); i++) {
                genre.setText(gameGenre.get(i).getName());
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
            rating.setText(gameRating.toString());
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

    }
}
