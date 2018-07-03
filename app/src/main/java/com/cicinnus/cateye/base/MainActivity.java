package com.cicinnus.cateye.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.cicinnus.cateye.R;
//import com.cicinnus.cateye.module.cinema.CinemaFragment;
import com.cicinnus.cateye.module.home.ChooseBedFragment;
import com.cicinnus.cateye.module.home.HomeFragment;
import com.cicinnus.cateye.module.mine.MineMVPFragment;
import com.cicinnus.cateye.module.more.MainSignFragment;
import com.cicinnus.cateye.module.movie.hot_movie.HotMovieListFragment;
import com.cicinnus.cateye.tools.JumpActUtil;
import com.cicinnus.cateye.tools.ToastUtil;
import com.cicinnus.retrofitlib.base.BaseMVPActivity;
import com.cicinnus.retrofitlib.utils.CleanLeakUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/1/18.
 */

public class MainActivity extends BaseMVPActivity {


    @BindView(R.id.rg_main)
    RadioGroup rg_main;

    private HotMovieListFragment mHotMovieListFragment;
    private HomeFragment discoverFragment;
    private MainSignFragment signFragment;
    private MineMVPFragment mineFragment;
    private long firstTime;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        setupRg();

    }

    @Override
    protected void initEventAndData(Bundle savedStated) {


        if (savedStated != null) {
            mHotMovieListFragment = (HotMovieListFragment) getSupportFragmentManager().findFragmentByTag("mHotMovieListFragment");
            discoverFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("discoverFragment");
            signFragment = (MainSignFragment) getSupportFragmentManager().findFragmentByTag("signFragment");
            mineFragment = (MineMVPFragment) getSupportFragmentManager().findFragmentByTag("mineFragment");
            switchFragment(BaseConstant.RB_MOVIE);

        } else {
            mHotMovieListFragment = mHotMovieListFragment.newInstance();
            discoverFragment = HomeFragment.newInstance();
            signFragment = MainSignFragment.newInstance();
            mineFragment = MineMVPFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container_main, mHotMovieListFragment, "mHotMovieListFragment")
                    .add(R.id.fl_container_main, discoverFragment, "discoverFragment")
                    .add(R.id.fl_container_main, signFragment, "signFragment")
                    .add(R.id.fl_container_main, mineFragment, "mineFragment")
                    .commit();
            switchFragment(BaseConstant.RB_MOVIE);

        }
    }

    /**
     * RadioGroup点击监听
     */
    private void setupRg() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_movie:
                        switchFragment(BaseConstant.RB_MOVIE);
                        break;
                    case R.id.rb_discover:
                        switchFragment(BaseConstant.RB_DISCOVER);
                        break;
                    case R.id.rb_sign:
                        switchFragment(BaseConstant.RB_SIGN);
                        break;
                    case R.id.rb_mine:
                        switchFragment(BaseConstant.RB_MINE);
                        break;
                }
            }
        });
    }

    /**
     * 切换Fragment
     *
     * @param index
     */
    private void switchFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (index) {
            case BaseConstant.RB_MOVIE:
                transaction
                        .show(discoverFragment)
                        .hide(mHotMovieListFragment)
                        .hide(signFragment)
                        .hide(mineFragment);
                discoverFragment.setUserVisibleHint(true);
                mHotMovieListFragment.setUserVisibleHint(false);
                mineFragment.setUserVisibleHint(false);
                
                
                break;
            case BaseConstant.RB_DISCOVER:
                transaction
                        .show(mHotMovieListFragment)
                        .hide(discoverFragment)
                        .hide(signFragment)
                        .hide(mineFragment);
                mHotMovieListFragment.setUserVisibleHint(true);
                discoverFragment.setUserVisibleHint(false);
                mineFragment.setUserVisibleHint(false);
                
                break;
            case BaseConstant.RB_MINE:
                transaction
                        .show(mineFragment)
                        .hide(discoverFragment)
                        .hide(mHotMovieListFragment)
                        .hide(signFragment);
                mineFragment.setUserVisibleHint(true);
                discoverFragment.setUserVisibleHint(false);
                mHotMovieListFragment.setUserVisibleHint(false);
                break;
            case BaseConstant.RB_SIGN:
                transaction
                        .show(signFragment)
                        .hide(discoverFragment)
                        .hide(mineFragment)
                        .hide(mHotMovieListFragment);
                mineFragment.setUserVisibleHint(true);
                discoverFragment.setUserVisibleHint(false);
                mHotMovieListFragment.setUserVisibleHint(false);
                break;
        }
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 32 && resultCode == 33) {
            mHotMovieListFragment.onActivityResult(requestCode, resultCode, data);
        }else if(requestCode==56&&resultCode==33){
            mHotMovieListFragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pressListener != null) {
                return pressListener.onKeyDownPress(keyCode, event);
            }
            if ((System.currentTimeMillis() - firstTime) > 2000) {
                ToastUtil.showToast("再次点击返回退出应用");
    
                firstTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(MainActivity.this);
        super.onDestroy();
    }
    
    
    public interface OnKeyDownPressListener {
        public boolean onKeyDownPress(int keyCode, KeyEvent event);
    }
    
    private OnKeyDownPressListener pressListener;
    
    public void setPressListener(OnKeyDownPressListener pressListener) {
        this.pressListener = pressListener;
    }


    public void clickZhinan(View view){
        JumpActUtil.go2ZhinanActivity(this);
    }

    public void clickBed(View view){
        JumpActUtil.go2BedActivity(this);
    }

    public void clickXxx(View view){JumpActUtil.go2QingjiaListAct(this);}

    public void clickJunxun(View view){
        JumpActUtil.go2JunxunActivity(this);
    }
}
