package com.cicinnus.cateye.module.home;

import butterknife.OnClick;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.base.BaseActivity;

/**
 * Created by lml on 18/2/10.
 */

public class QingjiaListAct extends BaseActivity {

  @Override
  protected int getLayout() {
    return R.layout.activity_qingjia;
  }

  @OnClick(R.id.rl_back)
  void onClick() {
    finish();
  }
}