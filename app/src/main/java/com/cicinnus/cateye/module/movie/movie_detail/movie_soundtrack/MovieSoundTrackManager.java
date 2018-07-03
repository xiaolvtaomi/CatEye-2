package com.cicinnus.cateye.module.movie.movie_detail.movie_soundtrack;

import com.cicinnus.cateye.module.movie.movie_detail.movie_soundtrack.bean.MovieAlbumBean;
import com.cicinnus.cateye.module.movie.movie_detail.movie_soundtrack.bean.MovieMusicBean;
import com.cicinnus.cateye.net.RetrofitClient;

import io.reactivex.ObservableSource;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MovieSoundTrackManager {


    /**
     * 影片专辑
     * @param movieId
     * @return
     */
    public ObservableSource<MovieAlbumBean> getMovieAlbum(int movieId){
        return RetrofitClient
                .getInstance()
                .api()
                .getMovieAlbum(movieId);
    }

    /**
     * 影片原声
     * @param movieId
     * @return
     */
    public ObservableSource<MovieMusicBean> getMovieMusic(int movieId){
        return RetrofitClient
                .getInstance()
                .api()
                .getMovieMusic(movieId);
    }


}
