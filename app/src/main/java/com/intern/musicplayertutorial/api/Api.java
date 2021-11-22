package com.intern.musicplayertutorial.api;


import com.intern.musicplayertutorial.module.artist.ArtistListAdapter;
import com.intern.musicplayertutorial.module.songlist.SongListAdapter;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Artist;
import com.intern.musicplayertutorial.object.Song;
import com.intern.musicplayertutorial.response.AlbumListResponse;
import com.intern.musicplayertutorial.response.AlbumResponse;
import com.intern.musicplayertutorial.response.ArtistListResponse;
import com.intern.musicplayertutorial.response.ArtistResponse;
import com.intern.musicplayertutorial.response.GenreResponse;
import com.intern.musicplayertutorial.response.SongEntityResponse;
import com.intern.musicplayertutorial.response.SongListResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {
    @GET("genre")
    Observable<GenreResponse> getAllGenres();
    @GET("genre/{id}/artists")
    Observable<ArtistResponse>  getArtistbyGenreID(@Path("id") String id);
    @GET("artist/{id}/albums")
    Observable<AlbumResponse> getAlbumbyArtistID(@Path("id") String id);
    @GET("album/{id}/tracks")
    Observable<SongEntityResponse> getSongbyAlbumID(@Path("id") String id);
    @GET("track/{id}")
    Observable<Song> getSongbyEntityId(@Path("id") String id);
    @GET("album/{id}")
    Observable<Album> getAlbumById(@Path("id") String id);
    @GET("artist/{id}")
    Observable<Artist> getArtistById(@Path("id") String id);
    @GET("search/track?limit=5")
    Observable<ResponseBody> getSongbyQuery(@Query("q") String query);
    @GET("search/album?limit=5")
    Observable<AlbumResponse> getAlbumbyQuery(@Query("q") String query);
    @GET("search/artist?limit=5")
    Observable<ArtistResponse> getArtistbyQuery(@Query("q") String query);

}
