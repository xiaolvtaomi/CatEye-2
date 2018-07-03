package com.cicinnus.cateye.module.discover;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.base.BaseConstant;
import com.cicinnus.cateye.base.BaseWebViewActivity;
import com.cicinnus.cateye.tools.GlideManager;
import com.cicinnus.cateye.tools.TimeUtils;

/**
 * Created by Administrator on 2017/1/18.
 */

public class DiscoverAdapter extends BaseMultiItemQuickAdapter<DiscoverBean.DataBean.FeedsBean, BaseViewHolder> {
    public DiscoverAdapter() {
        super(null);
        addItemType(BaseConstant.TYPE_DISCOVER_ONE_IMG, R.layout.item_discover_one_img);
        addItemType(BaseConstant.TYPE_DISCOVER_MULTI_IMG, R.layout.item_discover_muilt_img);
        addItemType(BaseConstant.TYPE_DISCOVER_BIG_IMG, R.layout.item_discover_big_img);
        addItemType(BaseConstant.TYPE_DISCOVER_ONE_BIG_IMG, R.layout.item_discover_one_big_img);

    }
    
    private final String tempUrl = "http://www.ayzy.cn/upload/20180117/df2be4a0-3842-437b-8833-96f9648cd9ce.png";
    @Override
    protected void convert(BaseViewHolder helper, final DiscoverBean.DataBean.FeedsBean item) {

        switch (helper.getItemViewType()) {
            case BaseConstant.TYPE_DISCOVER_ONE_IMG:
                helper.setText(R.id.tv_disc_title, "校领导慰问渔洋村驻村干部及困难党员")
                        .setText(R.id.tv_viewCount, String.format("%s", item.getViewCount()))
                        .setText(R.id.tv_video_comment, String.format("%s", item.getCommentCount()))
                        .setText(R.id.tv_nickName, String.format("%s", "宣传部"))
                        .setText(R.id.tv_time, TimeUtils.dateYMDHM(item.getTime()));
                GlideManager.loadImage(mContext, tempUrl, (ImageView) helper.getView(R.id.iv_img));
                break;
            case BaseConstant.TYPE_DISCOVER_MULTI_IMG:
                helper.setText(R.id.tv_disc_title, "安阳市2018年水库移民农村电子商务培训班圆满结束")
                        .setText(R.id.tv_viewCount, String.format("%s", item.getViewCount()))
                        .setText(R.id.tv_video_comment, String.format("%s", item.getCommentCount()))
                        .setText(R.id.tv_nickName, String.format("%s", "宣传部"))
                        .setText(R.id.tv_time, TimeUtils.dateYMDHM(item.getTime()));
                helper.itemView
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BaseWebViewActivity.start(mContext, item.getImages().get(0).getTargetId());
                            }
                        });
                GlideManager.loadImage(mContext, tempUrl, (ImageView) helper.getView(R.id.iv_img1));
                GlideManager.loadImage(mContext, tempUrl, (ImageView) helper.getView(R.id.iv_img2));
                GlideManager.loadImage(mContext, tempUrl, (ImageView) helper.getView(R.id.iv_img3));
                break;
            case BaseConstant.TYPE_DISCOVER_BIG_IMG:
                helper.setText(R.id.tv_disc_title, "关于2018年寒假放假安排的通知")
                        .setText(R.id.tv_viewCount, String.format("%s", item.getViewCount()))
                        .setText(R.id.tv_video_comment, String.format("%s", item.getCommentCount()))
                        .setText(R.id.tv_nickName, String.format("%s", "宣传部"))
                        .setText(R.id.tv_time, TimeUtils.dateYMDHM(item.getTime()));
                helper.setText(R.id.tv_imgCount, String.format("%s", item.getImageCount()));
                GlideManager.loadImage(mContext, tempUrl, (ImageView) helper.getView(R.id.iv_img1));
                GlideManager.loadImage(mContext, tempUrl, (ImageView) helper.getView(R.id.iv_img2));
                GlideManager.loadImage(mContext, tempUrl, (ImageView) helper.getView(R.id.iv_img3));
                break;
            case BaseConstant.TYPE_DISCOVER_ONE_BIG_IMG:
                helper.setText(R.id.tv_disc_title, "关于2018年寒假放假安排的通知")
                        .setText(R.id.tv_nickName, String.format("%s", "宣传部"));
                GlideManager.loadImage(mContext, tempUrl, (ImageView) helper.getView(R.id.iv_img));
                break;
        }


    }
}
