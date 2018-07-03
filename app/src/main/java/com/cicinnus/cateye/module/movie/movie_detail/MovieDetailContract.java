package com.cicinnus.cateye.module.movie.movie_detail;

import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieAwardsBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieBasicDataBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieCommentTagBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieLongCommentBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieMoneyBoxBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieProCommentBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieRelatedInformationBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieResourceBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieShortCommentBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieStarBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieTipsBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieTopicBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.RelatedMovieBean;
import com.cicinnus.retrofitlib.base.ICoreLoadingView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */

public class MovieDetailContract {
    public interface IMovieDetailView extends ICoreLoadingView {
        void addMovieBasicData(MovieBasicDataBean.DataBean.MovieBean movie);

        void addMovieTips(MovieTipsBean.DataBean tips);

        void addMovieStarList(MovieStarBean movieStarBean);

        void addMovieMoneyBox(MovieMoneyBoxBean moneyBoxBean);

        void addMovieAwards(List<MovieAwardsBean.DataBean> movieAwards);

        void addMovieResource(List<MovieResourceBean.DataBean> movieResources);

        void addMovieCommentTag(List<MovieCommentTagBean.DataBean> commentTags);

        void addMovieShortComment(MovieShortCommentBean movieShortCommentBean);

        void addMovieLongComment(MovieLongCommentBean.DataBean movieLongComments);

        void addMovieRelatedInformation(List<MovieRelatedInformationBean.DataBean.NewsListBean> newsListBean);

        void addRelatedMovie(List<RelatedMovieBean.DataBean> relatedMovies);

        void addMovieTopic(MovieTopicBean.DataBean movieTopicBean);

        void addMovieProComment(MovieProCommentBean data);

    }

    public interface IMoviePresenter {
        void getMovieData(int movieId);

        void getMovieSecondData(int movieId);

        void getMovieMoreData(int movieId);
    }
}
