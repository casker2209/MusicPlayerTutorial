package com.intern.musicplayertutorial.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Artist implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("picture")
    String pictureUrl;
    public Artist(String id,String name){
        this.id = id;
        this.name = name;
    }
    public Artist(String id,String name,String picture){
        this.id = id;
        this.name = name;
        this.pictureUrl = picture;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPictureUrl() {
        return pictureUrl;
    }
}
