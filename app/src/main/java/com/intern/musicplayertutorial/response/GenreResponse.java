package com.intern.musicplayertutorial.response;

import com.google.gson.annotations.SerializedName;
import com.intern.musicplayertutorial.object.Genre;

import java.util.List;

public class GenreResponse {
    @SerializedName("data")
    List<Genre> genreList;

    public List<Genre> getGenreList() {
        return genreList;
    }
}
