package com.cicinnus.cateye.module;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.cicinnus.cateye.R;
import com.cicinnus.cateye.tools.JumpActUtil;
import com.cicinnus.retrofitlib.utils.SPUtils;

import java.lang.ref.WeakReference;

import static com.cicinnus.cateye.base.Constants.SP_CONSTANTS;


/**
 * Created by tiansj on 15/7/29.
 */
public class SplashActivity extends FragmentActivity {

    private Button btnHome, btnRegist;
//    private CirclePageIndicator indicator;
//    private ViewPager pager;
//    private GalleryPagerAdapter adapter;
//    private int[] images = {
////            R.drawable.newer03,
//            R.drawable.newer04
//    };
    
    private WeakReference<Activity> mWeakReference = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeakReference = new WeakReference<Activity>(this);
        
        setContentView(R.layout.activity_splash);

        final boolean firstTimeUse = SPUtils.getInstance(this, SP_CONSTANTS).getBoolean("first-time-use", true);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(mWeakReference != null && mWeakReference.get() != null) {
//                    if (firstTimeUse) {
//                        Animation fadeOut = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fadeout);
//                        fadeOut.setFillAfter(true);
//                        findViewById(R.id.guideImage).startAnimation(fadeOut);
//                        initGuideGallery();
//                    } else {
//                        JumpActUtil.go2LoginActivity(mWeakReference.get());
//                        finish();
//                    }
//                }
//            }
//        }, 1500);

      initGuideGallery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtils.getInstance(mWeakReference.get(), SP_CONSTANTS).putBoolean("first-time-use", false);

    }

    private void initGuideGallery() {
        final Animation fadeIn= AnimationUtils.loadAnimation(this, R.anim.fadein);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnRegist = (Button) findViewById(R.id.btnRegist);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.getInstance(mWeakReference.get(), SP_CONSTANTS).putBoolean("first-time-use", false);
                JumpActUtil.go2LoginActivity(SplashActivity.this);
                finish();
            }
        });
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpActUtil.go2RegistActivity(SplashActivity.this);
            }
        });

//        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
//        indicator.setVisibility(View.VISIBLE);
//        pager = (ViewPager) findViewById(R.id.pager);
//        pager.setVisibility(View.VISIBLE);
//
//        adapter = new GalleryPagerAdapter();
//        pager.setAdapter(adapter);
//        indicator.setViewPager(pager);

//        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == images.length - 1) {
//                    btnHome.setVisibility(View.VISIBLE);
//                    btnRegist.setVisibility(View.VISIBLE);
//                    btnHome.startAnimation(fadeIn);
//                    btnRegist.startAnimation(fadeIn);
//                } else {
//                    btnHome.setVisibility(View.GONE);
//                    btnRegist.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });


        btnHome.setVisibility(View.VISIBLE);
        btnRegist.setVisibility(View.VISIBLE);
        btnHome.startAnimation(fadeIn);
        btnRegist.startAnimation(fadeIn);
    }

//    public class GalleryPagerAdapter extends PagerAdapter {
//
//        @Override
//        public int getCount() {
//            return images.length;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            ImageView item = new ImageView(SplashActivity.this);
//            item.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            item.setImageResource(images[position]);
//            container.addView(item);
//            return item;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup collection, int position, Object view) {
//            collection.removeView((View) view);
//        }
//    }

}
