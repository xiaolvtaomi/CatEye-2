package com.cicinnus.cateye.module.home;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.OnClick;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.base.BaseActivity;
import com.cicinnus.cateye.tools.PreferencesUtil;
import com.cicinnus.cateye.view.CommonDialog;
import com.hhy.library.FlowTagLayout;
import com.hhy.library.OnTagClickListener;
import com.hhy.library.OnTagSelectListener;
import java.util.ArrayList;
import java.util.List;

public class JunxunfuActivity extends BaseActivity {


    TextView tvTitle;

    private FlowTagLayout mColorFlowTagLayout;
    private FlowTagLayout mSizeFlowTagLayout;
//    private FlowTagLayout mMobileFlowTagLayout;
    private TagAdapter<String> mSizeTagAdapter;
    private TagAdapter<String> mColorTagAdapter;
//    private TagAdapter<String> mMobileTagAdapter;


    private void initMobileData() {
//        List<String> dataSource = new ArrayList<>();
//        dataSource.add("android");
//        dataSource.add("安卓");
//        dataSource.add("SDK源码");
//        dataSource.add("IOS");
//        dataSource.add("iPhone");
//        dataSource.add("游戏");
//        dataSource.add("fragment");
//        dataSource.add("viewcontroller");
//        dataSource.add("cocoachina");
//        dataSource.add("移动研发工程师");
//        dataSource.add("移动互联网");
//        dataSource.add("高薪+期权");
//        mMobileTagAdapter.onlyAddAll(dataSource);
    }

    private void initColorData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("男生");
        dataSource.add("女生");
        mColorTagAdapter.onlyAddAll(dataSource);
    }

    /**
     * 初始化数据
     */
    private void initSizeData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("XS (150)");
        dataSource.add("S (160)");
        dataSource.add("M (170)");
        dataSource.add("L (175)");
        dataSource.add("XL (180)");
        dataSource.add("XXL (185)");
        dataSource.add("XXXL (185+)");
        mSizeTagAdapter.onlyAddAll(dataSource);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_junxun;
    }


    @Override
    protected void initEventAndData() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("选择军训服");

        mColorFlowTagLayout = (FlowTagLayout) findViewById(R.id.color_flow_layout);
        mSizeFlowTagLayout = (FlowTagLayout) findViewById(R.id.size_flow_layout);
//        mMobileFlowTagLayout = (FlowTagLayout) findViewById(R.id.mobile_flow_layout);

        //颜色
        mColorTagAdapter = new TagAdapter<>(this);
        mColorFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mColorFlowTagLayout.setAdapter(mColorTagAdapter);
        mColorFlowTagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
//                Snackbar.make(view, "颜色:" + parent.getAdapter().getItem(position), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        //尺寸
        mSizeTagAdapter = new TagAdapter<>(this);
        mSizeFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mSizeFlowTagLayout.setAdapter(mSizeTagAdapter);
        mSizeFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
//                if (selectedList != null && selectedList.size() > 0) {
//                    StringBuilder sb = new StringBuilder();
//                    for (int i : selectedList) {
//                        sb.append(parent.getAdapter().getItem(i));
//                        sb.append(":");
//                    }
//                    Snackbar.make(parent, "移动研发:" + sb.toString(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }else{
//                    Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
            }
        });

        //移动研发标签
//        mMobileTagAdapter = new TagAdapter<>(this);
//        mMobileFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
//        mMobileFlowTagLayout.setAdapter(mMobileTagAdapter);
//        mMobileFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
//            @Override
//            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
//                if (selectedList != null && selectedList.size() > 0) {
//                    StringBuilder sb = new StringBuilder();
//
//                    for (int i : selectedList) {
//                        sb.append(parent.getAdapter().getItem(i));
//                        sb.append(":");
//                    }
//                    Snackbar.make(parent, "移动研发:" + sb.toString(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }else{
//                    Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//            }
//        });

        initColorData();

        initSizeData();

//        initMobileData();

        //清除所有已经被选择的选项
        findViewById(R.id.bt_clear_all)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mColorFlowTagLayout.clearAllOption();
                    mSizeFlowTagLayout.clearAllOption();
//                        mMobileFlowTagLayout.clearAllOption();
                }
            });
        findViewById(R.id.bt_confirm)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(PreferencesUtil.isYouke()){
                        CommonDialog.showYoukePermission(JunxunfuActivity.this);
                    }else {
                        CommonDialog.showYidongAndCancle(JunxunfuActivity.this);
                    }
                }
            });


    }

  @OnClick(R.id.rl_back)
  void onClick() {
    finish();
  }
}
