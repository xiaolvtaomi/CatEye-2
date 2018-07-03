package com.cicinnus.cateye.module.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cicinnus.cateye.R;
import com.cicinnus.cateye.base.BaseActivity;
import com.cicinnus.cateye.base.BaseWebViewActivity;
import com.cicinnus.cateye.module.movie.movie_detail.MovieDetailContract;
import com.cicinnus.cateye.module.movie.movie_detail.MovieDetailPresenter;
import com.cicinnus.cateye.module.movie.movie_detail.adapter.MovieAwardsAdapter;
import com.cicinnus.cateye.module.movie.movie_detail.adapter.MoviePhotosAdapter;
import com.cicinnus.cateye.module.movie.movie_detail.adapter.MovieShortCommentAdapter;
import com.cicinnus.cateye.module.movie.movie_detail.adapter.MovieStarListAdapter;
import com.cicinnus.cateye.module.movie.movie_detail.adapter.MovieTipsAdapter;
import com.cicinnus.cateye.module.movie.movie_detail.adapter.RelatedMovieAdapter;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieAwardsBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieBasicDataBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieCommentTagBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieLongCommentBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieMoneyBoxBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MoviePhotosBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieProCommentBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieRelatedInformationBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieResourceBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieShortCommentBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieStarBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieTipsBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.MovieTopicBean;
import com.cicinnus.cateye.module.movie.movie_detail.bean.RelatedMovieBean;
import com.cicinnus.cateye.module.movie.movie_detail.movie_information.MovieInformationActivity;
import com.cicinnus.cateye.module.movie.movie_detail.movie_long_comment.MovieLongCommentActivity;
import com.cicinnus.cateye.module.movie.movie_detail.movie_long_comment.MovieLongCommentAdapter;
import com.cicinnus.cateye.module.movie.movie_detail.movie_pro_comment.MovieProCommentActivity;
import com.cicinnus.cateye.module.movie.movie_detail.movie_pro_comment.MovieProCommentAdapter;
import com.cicinnus.cateye.module.movie.movie_detail.movie_resource.MovieResourceActivity;
import com.cicinnus.cateye.module.movie.movie_detail.movie_resource.adapter.MovieResourceAdapter;
import com.cicinnus.cateye.module.movie.movie_detail.movie_short_comment.MovieShortCommentActivity;
import com.cicinnus.cateye.module.movie.movie_detail.movie_short_comment.movie_short_comment_detail.MovieShortCommentDetailActivity;
import com.cicinnus.cateye.module.movie.movie_detail.movie_soundtrack.MovieSoundTrackActivity;
import com.cicinnus.cateye.module.movie.movie_detail.movie_topic.MovieTopicActivity;
import com.cicinnus.cateye.module.movie.movie_video.MovieVideoActivity;
import com.cicinnus.cateye.tools.GlideManager;
import com.cicinnus.cateye.tools.ImgSizeUtil;
import com.cicinnus.cateye.tools.StringUtil;
import com.cicinnus.cateye.tools.ToastUtil;
import com.cicinnus.cateye.tools.UiUtils;
import com.cicinnus.cateye.view.ExpandTextView;
import com.cicinnus.cateye.view.ProgressLayout;
import com.cicinnus.retrofitlib.rx.SchedulersCompat;
import com.cicinnus.retrofitlib.utils.BlurUtils;
import com.orhanobut.logger.Logger;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * 电影详情页
 */

public class XueyuanDetailActivity extends BaseActivity<MovieDetailPresenter> implements MovieDetailContract.IMovieDetailView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.expandText_movie_Desc)
    ExpandTextView expandText_movie_Desc;
    @BindView(R.id.ll_tips)
    LinearLayout llTips;
    @BindView(R.id.rv_movie_tips)
    RecyclerView rvMovieTips;
    /**************
     * 导演,演员
     *****************/
    @BindView(R.id.rv_movie_star)
    RecyclerView rvMovieStar;

    public static final String MOVIE_ID = "movie_id";
    private int movieId;//电影Id
    private String mMovieName;
    private String mTitle;
    private MovieBasicDataBean.DataBean.MovieBean mMovieBean;

    public static void start(Context context, int movieId) {
        Intent starter = new Intent(context, XueyuanDetailActivity.class);
        starter.putExtra(MOVIE_ID, movieId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_xueyuan_detail;
    }

    @Override
    protected MovieDetailPresenter getPresenter() {
        return new MovieDetailPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        movieId = 346641;

        mPresenter.getMovieData(movieId);
        mPresenter.getMovieSecondData(movieId);
        mPresenter.getMovieMoreData(movieId);
    }

    @OnClick({R.id.ib_back})
        void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_back:
                    finish();
                    break;
            }
    }



    @Override
    public void addMovieBasicData(MovieBasicDataBean.DataBean.MovieBean movie) {
        //标题栏
        setTitle(movie);
        //影片描述
//        expandText_movie_Desc.setText(movie.getDra());
        expandText_movie_Desc.setText("计算机学院1958年设立计算机专业，1981年建立计算机科学系，1998年设立计算机学院，2005年5月，为了进一步整合教学和科研资源，学校决定，计算机学院和软件学院行政班子合并统一运作、实行教学和学生管理独立运行的模式。 学院下设三个系：计算机科学与技术系、物联网工程系、计算金融系；两个研究所：图象图形研究所、网络空间安全研究院（2015年成立）；三个教学实验中心：计算机基础教学实验中心、IBM技术中心和计算机专业实验中心。");

    }

    /**
     * 观影小贴士
     *
     * @param
     */
    @Override
    public void addMovieTips(final MovieTipsBean.DataBean tips) {
        Observable.just(tips.getTips())
                .flatMap(new Function<List<MovieTipsBean.DataBean.TipsBean>, ObservableSource<MovieTipsBean.DataBean.TipsBean>>() {
                    @Override
                    public ObservableSource<MovieTipsBean.DataBean.TipsBean> apply(@NonNull List<MovieTipsBean.DataBean.TipsBean> tipsBeen) throws Exception {
                        if (tipsBeen != null && tipsBeen.size() > 0) {
                            return Observable.fromIterable(tipsBeen);
                        }
                        return Observable.error(new Exception("empty data"));
                    }
                })
                .compose(SchedulersCompat.<MovieTipsBean.DataBean.TipsBean>applyIoSchedulers())
                .toList()
                .subscribe(new Consumer<List<MovieTipsBean.DataBean.TipsBean>>() {
                    @Override
                    public void accept(@NonNull List<MovieTipsBean.DataBean.TipsBean> tipsBeen) throws Exception {
                        MovieTipsAdapter movieTipsAdapter = new MovieTipsAdapter();
                        rvMovieTips.setLayoutManager(new LinearLayoutManager(mContext));
                        rvMovieTips.setNestedScrollingEnabled(false);
                        rvMovieTips.setAdapter(movieTipsAdapter);
                        movieTipsAdapter.setNewData(tipsBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        progressLayout.removeView(llTips);
                        llTips.setVisibility(View.GONE);
                    }
                });
    }

    

    /**
     * 导演和演员
     *
     * @param movieStarBean
     */
    @Override
    public void addMovieStarList(MovieStarBean movieStarBean) {
        Observable.just(movieStarBean.getData())
                .flatMap(new Function<List<List<MovieStarBean.DataBean>>, Observable<List<MovieStarBean.DataBean>>>() {
                    @Override
                    public Observable<List<MovieStarBean.DataBean>> apply(List<List<MovieStarBean.DataBean>> lists) {
                        return Observable.fromIterable(lists);
                    }
                })
                //取前两组数据,如果只有1组就取1组
                .take(2)
                .flatMap(new Function<List<MovieStarBean.DataBean>, Observable<MovieStarBean.DataBean>>() {
                    @Override
                    public Observable<MovieStarBean.DataBean> apply(List<MovieStarBean.DataBean> dataBeen) {
                        return Observable.fromIterable(dataBeen);
                    }
                })
                .compose(SchedulersCompat.<MovieStarBean.DataBean>applyIoSchedulers())
                .toList()
                .subscribe(new Consumer<List<MovieStarBean.DataBean>>() {
                    @Override
                    public void accept(@NonNull List<MovieStarBean.DataBean> dataBeen) throws Exception {
                        MovieStarListAdapter movieStarListAdapter = new MovieStarListAdapter();
                        rvMovieStar.setAdapter(movieStarListAdapter);
                        rvMovieStar.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        movieStarListAdapter.setNewData(dataBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());

                    }
                });

    }
    
    @Override
    public void addMovieMoneyBox(MovieMoneyBoxBean moneyBoxBean) {
    
    }
    
    @Override
    public void addMovieAwards(List<MovieAwardsBean.DataBean> movieAwards) {
    
    }
    
    @Override
    public void addMovieResource(List<MovieResourceBean.DataBean> movieResources) {
    
    }
    
    @Override
    public void addMovieCommentTag(List<MovieCommentTagBean.DataBean> commentTags) {
    
    }
    
    @Override
    public void addMovieShortComment(MovieShortCommentBean movieShortCommentBean) {
    
    }
    
    @Override
    public void addMovieLongComment(MovieLongCommentBean.DataBean movieLongComments) {
    
    }
    
    @Override
    public void addMovieRelatedInformation(List<MovieRelatedInformationBean.DataBean.NewsListBean> newsListBean) {
    
    }
    
    @Override
    public void addRelatedMovie(List<RelatedMovieBean.DataBean> relatedMovies) {
    
    }
    
    @Override
    public void addMovieTopic(MovieTopicBean.DataBean movieTopicBean) {
    
    }
    
    @Override
    public void addMovieProComment(MovieProCommentBean data) {
    
    }
    
    
    /**
     * 标题栏
     *
     * @param movie
     */
    private void setTitle(MovieBasicDataBean.DataBean.MovieBean movie) {
        tvTitle.setTextColor(mContext.getResources().getColor(android.R.color.transparent));
        mTitle = movie.getNm();
        tvTitle.setText("学院名称");//中文主标题
        mMovieName = movie.getNm();
    }

    
    @Override
    public void showLoading() {
    }

    @Override
    public void showContent() {
    }

    @Override
    public void showError(String errorMsg) {
        Logger.e(errorMsg);
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }
}
