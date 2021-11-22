package com.intern.musicplayertutorial;

import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Artist;
import com.intern.musicplayertutorial.object.Genre;
import com.intern.musicplayertutorial.object.Song;

import java.util.List;

public class DataHolder {
    private static List<Song> songList;
    private static List<Artist> artistList;
    private static List<Album> albumList;
    private static Album album;
    private static List<Genre> genreList;

    public static void setSongList(List<Song> songList) {
        DataHolder.songList = songList;
    }

    public static void setAlbumList(List<Album> albumList) {
        DataHolder.albumList = albumList;
    }

    public static void setArtistList(List<Artist> artistList) {
        DataHolder.artistList = artistList;
    }

    public static List<Song> getSongList() {
        return songList;
    }

    public static List<Artist> getArtistList() {
        return artistList;
    }

    public static List<Album> getAlbumList() {
        return albumList;
    }

    public static Album getAlbum() {
        return album;
    }

    public static void setAlbum(Album album) {
        DataHolder.album = album;
    }

    public static List<Genre> getGenreList() {
        return genreList;
    }

    public static void setGenreList(List<Genre> genreList) {
        DataHolder.genreList = genreList;
    }
}

