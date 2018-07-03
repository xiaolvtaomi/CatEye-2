package com.cicinnus.cateye.module.movie.movie_star;

import com.cicinnus.cateye.module.movie.movie_star.bean.MovieStarHonor;
import com.cicinnus.cateye.module.movie.movie_star.bean.MovieStarInfoBean;
import com.cicinnus.cateye.module.movie.movie_star.bean.RelatedInformationBean;
import com.cicinnus.cateye.module.movie.movie_star.bean.StarMoviesBean;
import com.cicinnus.cateye.module.movie.movie_star.bean.StarRelatedPeople;
import com.cicinnus.cateye.net.RetrofitClient;

import io.reactivex.ObservableSource;


/**
 * Created by Cicinnus on 2017/2/18.
 */

public class MovieStarManage {

    /**
     * 获取影人资料
     *
     * @param starId
     * @return
     */
    public ObservableSource<MovieStarInfoBean> getMovieStarInfo(int starId) {
        return RetrofitClient
                .getInstance()
                .api()
                .getMovieStarInfoBean(starId);
    }

    /**
     * 参演电影
     *
     * @param starId
     * @return
     */
    public ObservableSource<StarMoviesBean> getStarMovies(int starId) {
        return RetrofitClient
                .getInstance()
                .api()
                .getStarMovies(starId, 20, 0);
    }

    /**
     * 影人荣誉
     *
     * @param starId
     * @return
     */
    public ObservableSource<MovieStarHonor> getMovieStarHonor(int starId) {
        return RetrofitClient
                .getInstance()
                .api()
                .getMovieStarHonors(starId);
    }

    /**
     * 相关资讯
     * @param starId
     * @return
     */
    public ObservableSource<RelatedInformationBean> getRelatedInformation(int starId){
        return RetrofitClient
                .getInstance()
                .api()
                .getRelatedInformation(starId);
    }

    public ObservableSource<StarRelatedPeople> getStarRelatedPeople(int starId){
        return RetrofitClient
                .getInstance()
                .api()
                .getStarRelatedPeople(starId);
    }

}
