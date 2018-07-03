package com.cicinnus.cateye.module.movie.movie_main;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cicinnus.cateye.R;
import com.cicinnus.cateye.base.Constants;
import com.cicinnus.cateye.module.movie.find_movie.FindMovieMVPFragment;
import com.cicinnus.cateye.module.movie.hot_movie.HotMovieListFragment;
import com.cicinnus.cateye.module.movie.pick_city.PickCityActivity;
import com.cicinnus.cateye.module.movie.wait_movie.WaitMovieFragment;
import com.cicinnus.retrofitlib.base.BaseMVPFragment;
import com.cicinnus.retrofitlib.utils.SPUtils;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 电影页
 */

public class MovieMainFragment extends BaseMVPFragment {


    private HotMovieListFragment hotMovieListFragment;

    public static MovieMainFragment newInstance() {
        return new MovieMainFragment();
    }


    @BindView(R.id.tv_hot_movie)
    TextView tvHotMovie;
    @BindView(R.id.tv_wait_movie)
    TextView tvWaitMovie;
    @BindView(R.id.tv_find_movie)
    TextView tvFindView;
    @BindView(R.id.view_indicator)
    View view;
    @BindView(R.id.vp_movie)
    ViewPager vp_movie;
    @BindView(R.id.tv_city)
    TextView tvCity;


    private FrameLayout.LayoutParams params;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie;
    }


    @Override
    protected void initEventAndData() {
        tvCity.setText(SPUtils.getInstance(mContext, Constants.SP_CITY).getString(Constants.CITY_NAME, "广州"));
        hotMovieListFragment = HotMovieListFragment.newInstance();
        WaitMovieFragment waitMovieFragment = WaitMovieFragment.newInstance();
        FindMovieMVPFragment findMovieFragment = FindMovieMVPFragment.newInstance();
        MovieMainAdapter movieMainAdapter = new MovieMainAdapter(getFragmentManager());
        Fragment[] fragments = new Fragment[]{hotMovieListFragment, waitMovieFragment, findMovieFragment};
        String[] titles = new String[]{"社团", "待映", "找片"};
        movieMainAdapter.addFragment(Arrays.asList(fragments), Arrays.asList(titles));
        vp_movie.setAdapter(movieMainAdapter);
        vp_movie.setOffscreenPageLimit(3);


        final int selectedColor = getResources().getColor(R.color.colorAccent);
        final int unselectedColor = getResources().getColor(R.color.white);

        vp_movie.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                boolean lessThanHalf = positionOffset < 0.5;//判断是否滑动的距离小于整体布局（fragment）的一半

                float alpha = (1 - positionOffset * 2) * 255;//变化率（从0-255表示从无到有）
                int alphaWhite = Color.argb((int) alpha, 255, 255, 255);//白渐变到透明
                int alphaRed = Color.argb((int) alpha, 212, 62, 55);//红渐变到透明

                int alphaWhiteReverse = Color.argb((int) -alpha, 255, 255, 255);//白渐变到透明
                int alphaRedReverse = Color.argb((int) -alpha, 212, 62, 55);//红渐变到透明

                //获取滑块的Layout
                params = (FrameLayout.LayoutParams) view.getLayoutParams();
                //判断滑动的距离
                if (positionOffset == 0) { // 停止滚动
                    params.setMargins(view.getWidth() * position, 0, 0, 0);
                } else {
                    params.setMargins((int) (view.getWidth() * (position + positionOffset)), 0, 0, 0);
                }
                //通过LayoutParams设置滑块的位置
                view.setLayoutParams(params);
                /**
                 * 滑动的时候需要观察position的变化才能判断滑动的位置
                 * 从左到右滑会一直是左边的position，直到切换了fragment
                 * 从右往左滑会一直是右边的position，直到切换了fragment
                 * 所以这边只需要对前两个position进行alpha的颜色变化即可
                 */
                switch (position) {
                    case 0:
                        if (lessThanHalf) {
                            tvHotMovie.setTextColor(alphaRed);
                            tvWaitMovie.setTextColor(alphaWhite);
                        } else {
                            tvHotMovie.setTextColor(alphaWhiteReverse);
                            tvWaitMovie.setTextColor(alphaRedReverse);
                        }
                        break;
                    case 1:
                        if (lessThanHalf) {
                            tvWaitMovie.setTextColor(alphaRed);
                            tvFindView.setTextColor(alphaWhite);
                        } else {
                            tvWaitMovie.setTextColor(alphaWhiteReverse);
                            tvFindView.setTextColor(alphaRedReverse);
                        }
                        break;
                }

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tvHotMovie.setTextColor(selectedColor);
                        tvWaitMovie.setTextColor(unselectedColor);
                        tvFindView.setTextColor(unselectedColor);
                        break;
                    case 1:
                        tvWaitMovie.setTextColor(selectedColor);
                        tvHotMovie.setTextColor(unselectedColor);
                        tvFindView.setTextColor(unselectedColor);
                        break;
                    case 2:
                        tvFindView.setTextColor(selectedColor);
                        tvWaitMovie.setTextColor(unselectedColor);
                        tvHotMovie.setTextColor(unselectedColor);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 点击文字切换Fragment
     *
     * @param view
     */
    @OnClick({R.id.tv_hot_movie, R.id.tv_wait_movie, R.id.tv_find_movie, R.id.tv_city})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_hot_movie:
                vp_movie.setCurrentItem(0);
                break;
            case R.id.tv_wait_movie:
                vp_movie.setCurrentItem(1);
                break;
            case R.id.tv_find_movie:
                vp_movie.setCurrentItem(2);
                break;
            case R.id.tv_city:
                PickCityActivity.start(mContext);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 32&&resultCode==33) {
            tvCity.setText(data.getStringExtra("city_name"));
            hotMovieListFragment.getPresenter().getHotMovieList(12);
        }else if(requestCode==56&&resultCode==33){
            tvCity.setText(data.getStringExtra("city_name"));
            hotMovieListFragment.getPresenter().getHotMovieList(12);
        }
    }
}
