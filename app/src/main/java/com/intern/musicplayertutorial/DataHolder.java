package com.intern.musicplayertutorial;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Artist;
import com.intern.musicplayertutorial.object.Genre;
import com.intern.musicplayertutorial.object.Song;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DataHolder {

    public static SharedPreferences sharedPreferences;

    public static void init(Context context){
        sharedPreferences = context.getSharedPreferences("DataHolder",MODE_PRIVATE);
    }

    public static String listToString(List<?> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


    public static void setSongList(List<Song> songList){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Song list",listToString(songList));
        editor.commit();
    }


    public static List<Song> getSongList(){
        List<Song> songList = new ArrayList<>();
        String serializedObject = sharedPreferences.getString("Song list",null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Song>>(){}.getType();
            songList = gson.fromJson(serializedObject, type);
        }
        return songList;
    }


    public static void setAlbum(Album album){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Album",new Gson().toJson(album));
        editor.commit();
    }
    public static Album getAlbum(){
        Album album = new Album("","","");
        String serializedObject = sharedPreferences.getString("Album",null);
        if(serializedObject!=null){
            album = new Gson().fromJson(serializedObject,Album.class);
        }
        return album;
    }


    public static void setGenreList(List<Genre> genreList){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Genre list",listToString(genreList));
        editor.commit();
    }
    public static List<Genre> getGenreList(){
        List<Genre> genreList = new ArrayList<>();
        String serializedObject = sharedPreferences.getString("Genre list",null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Genre>>(){}.getType();
            genreList = gson.fromJson(serializedObject, type);
        }
        return genreList;
    }
    public static void setAlbumList(List<Album> list){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Album list",listToString(list));
        editor.commit();
    }
    public static List<Album> getAlbumList(){
        List<Album> list = new ArrayList<>();
        String serializedObject = sharedPreferences.getString("Album list",null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Album>>(){}.getType();
            list = gson.fromJson(serializedObject, type);
        }
        return list;
    }

    public static void setArtistList(List<Artist> list){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Artist list",listToString(list));
        editor.commit();
    }
    public static List<Artist> getArtistList(){
        List<Artist> list = new ArrayList<>();
        String serializedObject = sharedPreferences.getString("Artist list",null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Artist>>(){}.getType();
            list = gson.fromJson(serializedObject, type);
        }
        return list;
    }

}

