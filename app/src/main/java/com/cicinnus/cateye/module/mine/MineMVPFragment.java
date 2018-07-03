package com.cicinnus.cateye.module.mine;

import android.widget.TextView;

import com.cicinnus.cateye.R;
import com.cicinnus.retrofitlib.base.BaseMVPFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/1/18.
 */

public class MineMVPFragment extends BaseMVPFragment {
    
    
    @BindView(R.id.tv_title)
    TextView tv_title ;

    public static MineMVPFragment newInstance() {

        return new MineMVPFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initEventAndData() {
        tv_title.setText("我的");

    }
}
