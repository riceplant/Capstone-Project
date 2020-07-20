package com.riceplant.capstoneproject.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.riceplant.capstoneproject.data.Cover;
import com.riceplant.capstoneproject.data.Genre;
import com.riceplant.capstoneproject.data.Platform;
import com.riceplant.capstoneproject.data.ReleaseDate;
import com.riceplant.capstoneproject.data.Video;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static Cover toCover(String value) {
        String imgID = value.substring(value.lastIndexOf("/")+1, value.lastIndexOf("."));
        return value == null ? null : new Cover(imgID);
    }

    @TypeConverter
    public static String toString(Cover value) {
        return value == null ? null : value.getUrl();
    }

    @TypeConverter
    public static List<Genre> toGenre(String value) {
        Type listType = new TypeToken<List<Genre>>() {}.getType();
        List<Genre> genres = new Gson().fromJson(value, listType);
        return genres;
    }

    @TypeConverter
    public static String toGenreString(List<Genre> value) {
        Gson gson = new Gson();
        return gson.toJson(value);

    }

    @TypeConverter
    public static List<Platform> toPlatform(String value) {
        Type listType = new TypeToken<List<Platform>>() {}.getType();
        List<Platform> platforms = new Gson().fromJson(value, listType);
        return platforms;
    }

    @TypeConverter
    public static String toPlatformString(List<Platform> value) {
        Gson gson = new Gson();
        return gson.toJson(value);
    }

    @TypeConverter
    public static List<Video> toVideo(String value) {
        Type listType = new TypeToken<List<Video>>() {}.getType();
        List<Video> videos = new Gson().fromJson(value, listType);
        return videos;
    }

    @TypeConverter
    public static String toVideoString(List<Video> value) {
        Gson gson = new Gson();
        return gson.toJson(value);
    }

    @TypeConverter
    public static List<ReleaseDate> toReleaseDate(String value) {
        Type listType = new TypeToken<List<ReleaseDate>>() {}.getType();
        List<ReleaseDate> releaseDates = new Gson().fromJson(value, listType);
        return releaseDates;
    }

    @TypeConverter
    public static String toReleaseDateString(List<ReleaseDate> value) {
        Gson gson = new Gson();
        return gson.toJson(value);
    }
}
