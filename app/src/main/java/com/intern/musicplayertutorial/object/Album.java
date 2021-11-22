package com.intern.musicplayertutorial.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("title")
    String name;
    @SerializedName("cover")
    String pictureUrl;

    public String getId() {
        return id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getName() {
        return name;
    }
    public Album(String id,String title,String cover){
        this.id = id;
        this.name = title;
        this.pictureUrl = cover;
    }

}
