package com.cicinnus.cateye.module.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.base.BaseConstant;
import com.cicinnus.cateye.base.BaseWebViewActivity;
import com.cicinnus.cateye.module.movie.hot_movie.HotMovieListBean;
import com.cicinnus.cateye.tools.GlideManager;
import com.cicinnus.cateye.tools.JumpActUtil;

/**
 * Created by Cicinnus on 2017/1/24.
 */

public class QingjiaListAdapter extends BaseMultiItemQuickAdapter<QingjiaListBean, BaseViewHolder> {


  public QingjiaListAdapter() {
    super(null);
    addItemType(BaseConstant.TYPE_HOT_HEADLINE, R.layout.item_qingjialist);
    addItemType(BaseConstant.TYPE_HOT_NORMAL, R.layout.item_qingjialist);
  }


  @Override
  protected void convert(BaseViewHolder helper, final QingjiaListBean item) {
//        helper.setText(R.id.tv_hot_movie_name, String.format("%s", "旭升股份汽车技术股份有限公司"))
//                .setText(R.id.tv_hot_desc, String.format("%s", "2018-6-18"))
//                .setText(R.id.tv_hot_showInfo, String.format("%s", "来源：就业科"));
//        //图片地址不能直接使用，需要进行转换
//        GlideManager.loadImage(mContext,  "http://cdn.sinacloud.net/diancai/job.png", (ImageView) helper.getView(R.id.iv_hot_img));

    helper.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        JumpActUtil.go2QingjiaDetailAct(mContext);
      }
    });
  }
}
