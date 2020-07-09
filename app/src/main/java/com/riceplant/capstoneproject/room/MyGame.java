package com.riceplant.capstoneproject.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.riceplant.capstoneproject.data.Cover;
import com.riceplant.capstoneproject.data.Genre;
import com.riceplant.capstoneproject.data.Platform;
import com.riceplant.capstoneproject.data.ReleaseDate;
import com.riceplant.capstoneproject.data.Video;

import java.util.List;

@Entity(tableName = "game")
public class MyGame {

    @PrimaryKey(autoGenerate = true)
    private Integer mId;
    private Cover mCover;
    private String mName;
    private Double mPopularity;
    private String mSummary;
    private List<Genre> mGenres;
    private List<Platform> mPlatform;
    private Double mRating;
    private List<ReleaseDate> mReleaseDate;
    private List<Video> mVideos;

    @Ignore
    public MyGame() {
    }

    public MyGame(Integer id,
                  Cover cover,
                  String name,
                  Double popularity,
                  String summary,
                  List<Genre> genres,
                  List<Platform> platform,
                  Double rating,
                  List<ReleaseDate> releaseDate,
                  List<Video> videos) {
        mId = id;
        mCover = cover;
        mName = name;
        mPopularity = popularity;
        mSummary = summary;
        mGenres = genres;
        mPlatform = platform;
        mRating = rating;
        mReleaseDate = releaseDate;
        mVideos = videos;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        id = mId;
    }

    public Cover getCover() {
        return mCover;
    }

    public void setCover(Cover cover) {
        cover = mCover;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        name = mName;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double popularity) {
        popularity = mPopularity;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        summary = mSummary;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        genres = mGenres;
    }

    public List<Platform> getPlatform() {
        return mPlatform;
    }

    public void setPlatform(List<Platform> platform) {
        platform = mPlatform;
    }

    public Double getRating() {
        return mRating;
    }

    public void setRating(Double rating) {
        rating = mRating;
    }

    public List<ReleaseDate> getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(List<ReleaseDate> releaseDate) {
        releaseDate = mReleaseDate;
    }

    public List<Video> getVideos() {
        return mVideos;
    }

    public void setVideos(List<Video> videos) {
        videos = mVideos;
    }
}
