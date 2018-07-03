package com.cicinnus.cateye.module.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.base.BaseWebViewActivity;
import com.cicinnus.cateye.module.discover.DiscoverAdapter;
import com.cicinnus.cateye.module.discover.DiscoverBean;
import com.cicinnus.cateye.module.discover.DiscoverContract;
import com.cicinnus.cateye.module.discover.DiscoverContract.IDiscoverView;
import com.cicinnus.cateye.module.discover.DiscoverHeaderBean;
import com.cicinnus.cateye.module.discover.DiscoverPresenter;
import com.cicinnus.cateye.tools.GlideManager;
import com.cicinnus.cateye.tools.GsonUtil;
import com.cicinnus.cateye.tools.ToastUtil;
import com.cicinnus.cateye.view.Banner;
import com.cicinnus.cateye.view.MyPullToRefreshListener;
import com.cicinnus.cateye.view.ProgressLayout;
import com.cicinnus.cateye.view.SuperSwipeRefreshLayout;
import com.cicinnus.retrofitlib.base.BaseMVPFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.wangyuwei.banner.BannerEntity;
import me.wangyuwei.banner.BannerView;

/**
 * Created by Administrator on 2017/1/18.
 */

public class HomeFragment extends BaseMVPFragment<DiscoverPresenter> implements IDiscoverView {


    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;
    @BindView(R.id.swipe)
    SuperSwipeRefreshLayout swipe;
    @BindView(R.id.rv_discover)
    RecyclerView rvDiscover;
    @BindView(R.id.tv_title)
    TextView tv_title ;

  private BannerView mBannerView;
    private View headerView;
//    private Banner banner;
    private boolean isFirst = true;
    private MyPullToRefreshListener pullToRefreshListener;
//    List<Banner.BannerPicBean.PicBean> beanList = null ;

    private String mock_header_json = "[{\"description\":\"\",\"id\":39,\"image\":{\"authorId\":0,\"height\":86," +
            "\"id\":2719364,\"sizeType\":0,\"targetId\":39,\"targetType\":8,\"url\":\"http://p0.meituan" +
            ".net/movie/5acd468360744ef1358d2e7276e5c5504617.png\",\"width\":86},\"tag\":\"\",\"title\":\"学校介绍\"," +
            "\"url\":\"meituanmovie://www.meituan.com/web?url=http://m.maoyan.com/newGuide/top10\"}," +
            "{\"description\":\"\",\"id\":36,\"image\":{\"authorId\":0,\"height\":86,\"id\":2719367,\"sizeType\":0," +
            "\"targetId\":36,\"targetType\":8,\"url\":\"http://p1.meituan" +
            ".net/movie/ba7ce8110dafc249dcd3db2978d96c032811.png\",\"width\":86},\"tag\":\"\",\"title\":\"专业介绍\"," +
            "\"url\":\"meituanmovie://www.meituan.com/web?url=http://m.maoyan.com/information?_v_=yes\"}," +
            "{\"description\":\"\",\"id\":45,\"image\":{\"authorId\":0,\"height\":86,\"id\":2751974,\"sizeType\":0," +
            "\"targetId\":45,\"targetType\":8,\"url\":\"http://p1.meituan" +
            ".net/movie/3596742a83baf834a80dcd86ae8749fe3356.png\",\"width\":86},\"tag\":\"\",\"title\":\"师资情况\"," +
            "\"url\":\"meituanmovie://www.meituan.com/web?url=http://m.maoyan.com/store?_v_=yes\"}," +
            "{\"description\":\"\",\"id\":38,\"image\":{\"authorId\":0,\"height\":86,\"id\":2719365,\"sizeType\":0," +
            "\"targetId\":38,\"targetType\":8,\"url\":\"http://p0.meituan" +
            ".net/movie/91619295e2c79bc3cd755cec0ceaf47c3921.png\",\"width\":86},\"tag\":\"\",\"title\":\"新生指南\"," +
            "\"url\":\"meituanmovie://www.meituan.com/web?swipepop=false&url=https://m.maoyan" +
            ".com/newGuide/maoyanpiaofang\"},{\"description\":\"\",\"id\":39,\"image\":{\"authorId\":0,\"height\":86," +
            "\"id\":2719364,\"sizeType\":0,\"targetId\":39,\"targetType\":8,\"url\":\"http://p0.meituan" +
            ".net/movie/5acd468360744ef1358d2e7276e5c5504617.png\",\"width\":86},\"tag\":\"\",\"title\":\"热门选课\"," +
            "\"url\":\"meituanmovie://www.meituan.com/web?url=http://m.maoyan.com/newGuide/top10\"}," +
            "{\"description\":\"\",\"id\":36,\"image\":{\"authorId\":0,\"height\":86,\"id\":2719367,\"sizeType\":0," +
            "\"targetId\":36,\"targetType\":8,\"url\":\"http://p1.meituan" +
            ".net/movie/ba7ce8110dafc249dcd3db2978d96c032811.png\",\"width\":86},\"tag\":\"\",\"title\":\"社团风采\"," +
            "\"url\":\"meituanmovie://www.meituan.com/web?url=http://m.maoyan.com/information?_v_=yes\"}," +
            "{\"description\":\"\",\"id\":45,\"image\":{\"authorId\":0,\"height\":86,\"id\":2751974,\"sizeType\":0," +
            "\"targetId\":45,\"targetType\":8,\"url\":\"http://p1.meituan" +
            ".net/movie/3596742a83baf834a80dcd86ae8749fe3356.png\",\"width\":86},\"tag\":\"\",\"title\":\"教务系统\"," +
            "\"url\":\"meituanmovie://www.meituan.com/web?url=http://m.maoyan.com/store?_v_=yes\"}," +
            "{\"description\":\"\",\"id\":38,\"image\":{\"authorId\":0,\"height\":86,\"id\":2719365,\"sizeType\":0," +
            "\"targetId\":38,\"targetType\":8,\"url\":\"http://p0.meituan" +
            ".net/movie/91619295e2c79bc3cd755cec0ceaf47c3921.png\",\"width\":86},\"tag\":\"\",\"title\":\"更多内容\"," +
            "\"url\":\"meituanmovie://www.meituan.com/web?swipepop=false&url=https://m.maoyan" +
            ".com/newGuide/maoyanpiaofang\"}]";
    
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    private DiscoverAdapter discoverAdapter;
    private int offset = 0;
    private int limit = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected DiscoverPresenter getPresenter() {
        return new DiscoverPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
    
        tv_title.setText("安阳职业技术学院");
        discoverAdapter = new DiscoverAdapter();
        rvDiscover.setLayoutManager(new LinearLayoutManager(mContext));
        rvDiscover.setAdapter(discoverAdapter);
        //RV头部栏目
        headerView = mContext.getLayoutInflater().inflate(R.layout.item_home_header, (ViewGroup) rvDiscover.getParent(), false);
//        banner = (Banner) headerView.findViewById(R.id.banner);
//        beanList = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            Banner.BannerPicBean.PicBean bean = new Banner.BannerPicBean.PicBean();
//            bean.picUrl = "http://www.ayzy.cn/upload/20180117/6ce67f9a9a7c4f8eb2e3f4184f28b9be.jpg";
//            beanList.add(bean);
//        }

         mBannerView = (BannerView) headerView.findViewById(R.id.banner_view);
        List<BannerEntity> entities = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            BannerEntity bean = new BannerEntity();
            bean.imageUrl = "http://www.ayzy.cn/upload/20180117/6ce67f9a9a7c4f8eb2e3f4184f28b9be.jpg";
            bean.title = "欢迎新生入学";
            entities.add(bean);
        }
      BannerEntity beanYidong = new BannerEntity();
      beanYidong.imageUrl =
          "http://www.10086.cn/uploadBaseDir/content/jpg/20180530/20180530160733798YTY.jpg";
      beanYidong.title = "移动和4G欢迎新生入校";
      entities.add(beanYidong);
        mBannerView.setEntities(entities);
      mBannerView.setAutoScroll(1);

        
        discoverAdapter.addHeaderView(headerView);
        Type dataType = new TypeToken<List<DiscoverHeaderBean.DataBean>>(){}.getType();
        List<DiscoverHeaderBean.DataBean> data = GsonUtil.getGson().fromJson(mock_header_json, dataType);
        addDiscoverHeaderData(data);
//        banner.setPic(beanList);
        //下拉刷新

        pullToRefreshListener = new MyPullToRefreshListener(mContext,swipe);
        swipe.setOnPullRefreshListener(pullToRefreshListener);
        pullToRefreshListener.setOnRefreshListener(new MyPullToRefreshListener.OnRefreshListener() {
            @Override
            public void refresh() {
                offset = 0;
                discoverAdapter.setNewData(new ArrayList<DiscoverBean.DataBean.FeedsBean>());
                mPresenter.getDiscoverData(offset, limit);
            }
        });

        discoverAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getDiscoverData(offset, limit);
            }
        },rvDiscover);
    
        mPresenter.getDiscoverData(offset, limit);
        discoverAdapter.notifyDataSetChanged();
    }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  @Override
    protected void lazyLoadEveryTime() {
        if (isFirst) {
            
            isFirst = false;
        }
    
        
    }

    @Override
    public void addDiscoverData(List<DiscoverBean.DataBean.FeedsBean> feeds) {
        if (feeds.size() > 0) {
            offset += 10;
            discoverAdapter.addData(feeds);
            discoverAdapter.loadMoreComplete();
        } else {
            discoverAdapter.loadMoreEnd();
        }
    }

    @Override
    public void addDiscoverHeaderData(final List<DiscoverHeaderBean.DataBean> data) {
        ImageView[] imageViews = new ImageView[]{((ImageView) headerView.findViewById(R.id.iv_header1)),
                ((ImageView) headerView.findViewById(R.id.iv_header2)),
                ((ImageView) headerView.findViewById(R.id.iv_header3)),
                ((ImageView) headerView.findViewById(R.id.iv_header4)),
                ((ImageView) headerView.findViewById(R.id.iv_header5)),
                ((ImageView) headerView.findViewById(R.id.iv_header6)),
                ((ImageView) headerView.findViewById(R.id.iv_header7)),
                ((ImageView) headerView.findViewById(R.id.iv_header8))
        };
        TextView[] tv_content = new TextView[]{
                ((TextView) headerView.findViewById(R.id.tv_header1)),
                ((TextView) headerView.findViewById(R.id.tv_header2)),
                ((TextView) headerView.findViewById(R.id.tv_header3)),
                ((TextView) headerView.findViewById(R.id.tv_header4)),
                ((TextView) headerView.findViewById(R.id.tv_header5)),
                ((TextView) headerView.findViewById(R.id.tv_header6)),
                ((TextView) headerView.findViewById(R.id.tv_header7)),
                ((TextView) headerView.findViewById(R.id.tv_header8))
        };
        for (int i = 0; i < data.size(); i++) {
            GlideManager.loadImage(mContext, data.get(i).getImage().getUrl(), imageViews[i]);
            tv_content[i].setText(data.get(i).getTitle());
            final int finalI = i;
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (finalI){
                        case 0:
                            BaseWebViewActivity.start(mContext,"http://cdn.sinacloud.net/diancai/homeinfo_anyangzhiyejishu.html");
                            break;
                        case 1:
                            XueyuanListActivity.start(mContext, false);
                            break;
                        case 2:
                            ToastUtil.showToast("正在开发....");
                            break;
                        case 3:
                            BaseWebViewActivity.start(mContext,"https://baijiahao.baidu.com/s?id=1573711882436691");
                            break;
                        case 4:
                            ClassPlanActivity.start(mContext, 346641,"说话之道选修课");
                            break;
                        case 5:
                            ToastUtil.showToast("正在开发....");
                            break;
                        case 6:
                            ToastUtil.showToast("正在开发....");
                            break;
                    }
//                    BaseWebViewActivity.start(mContext,data.get(finalI).getUrl());
                }
            });
        }
    }

    @Override
    public void showLoading() {
        if (!progressLayout.isContent()) {
            progressLayout.showLoading();
        }
    }

    @Override
    public void showContent() {
        pullToRefreshListener.refreshDone();
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(String errorMsg) {
        Logger.e(errorMsg);
        pullToRefreshListener.refreshDone();
        discoverAdapter.loadMoreEnd();
        progressLayout.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset = 0;
                mPresenter.getDiscoverData(offset, limit);
                mPresenter.getDiscoverHeader("7.8.0");
                if (isFirst) {
                    isFirst = false;
                }
            }
        });
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
