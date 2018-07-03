package com.cicinnus.cateye.module.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cicinnus.cateye.R;
import com.cicinnus.cateye.module.cinema.adapter.ChuangpuAdapter;
import com.cicinnus.cateye.module.cinema.bean.ChuangpuBean;
import com.cicinnus.cateye.tools.GsonUtil;
import com.cicinnus.cateye.tools.PreferencesUtil;
import com.cicinnus.cateye.view.CommonDialog;
import com.cicinnus.cateye.view.TextSpinnerView;
import com.cicinnus.retrofitlib.base.BaseMVPFragment;

import butterknife.BindView;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 影院列表
 */

public class ChooseBedFragment extends BaseMVPFragment {


  @BindView(R.id.tsv_xiaoqu)
  TextSpinnerView tsv_xiaoqu;
  @BindView(R.id.tsv_louceng)
  TextSpinnerView tsv_louceng;
  @BindView(R.id.tsv_sushelou)
  TextSpinnerView tsv_sushelou;
  @BindView(R.id.tsv_sushe)
  TextSpinnerView tsv_sushe;
  @BindView(R.id.rv_chuangpu)
  RecyclerView rv_chuangpu;

  @BindView(R.id.tv_title)
  TextView tv_title;
  @BindView(R.id.tv_selected)
  TextView tv_selected;
  @BindView(R.id.tv_confirm)
  TextView tv_confirm;

  private StringBuffer sb = new StringBuffer();

  private ChuangpuAdapter adapter;


  public static ChooseBedFragment newInstance() {

    return new ChooseBedFragment();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_choosebed;
  }

  @Override
  protected void initEventAndData() {
    tv_title.setText("选择床铺");

    tsv_xiaoqu.setSelectedListener(new TextSpinnerView.TSVListener() {
      @Override
      public void onSelected(int pos, int lasPos) {
        if (lasPos != pos) {
          refreshSb();
        }
      }
    });
    tsv_sushelou.setSelectedListener(new TextSpinnerView.TSVListener() {
      @Override
      public void onSelected(int pos, int lasPos) {
        if (lasPos != pos) {
          refreshSb();
        }
      }
    });
    tsv_louceng.setSelectedListener(new TextSpinnerView.TSVListener() {
      @Override
      public void onSelected(int pos, int lasPos) {
        if (lasPos != pos) {
          refreshSb();


        }
      }
    });
    tsv_sushe.setSelectedListener(new TextSpinnerView.TSVListener() {
      @Override
      public void onSelected(int pos, int lasPos) {
        if (lasPos != pos) {
          refreshSb();

        }
      }
    });

    initRv();


    tv_confirm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(PreferencesUtil.isYouke()){
          CommonDialog.showYoukePermission(mContext);
        }else {
          CommonDialog.showJiaofeiAndCancle(ChooseBedFragment.this, "您当前的床铺选择是：[" + sb.toString() + "]");
        }
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    refreshSb();
  }

  public void refreshSb(){
    sb = new StringBuffer();
    sb.append(tsv_xiaoqu.getValue()).append(" ");
    sb.append(tsv_sushelou.getValue()).append(" ");
    sb.append(tsv_louceng.getValue()).append(" ");
    sb.append(tsv_sushe.getValue()).append("号宿舍");
    sb.append(adapter.getSelectedPosValue());
    tv_selected.setText("您当前的床铺选择是：["+sb.toString()+"]");
  }

  @Override
  protected void lazyLoadEveryTime() {
  }

  /**
   * 初始化列表
   */
  private void initRv() {

    rv_chuangpu.setLayoutManager(new GridLayoutManager(mContext, 4));
    adapter = new ChuangpuAdapter(getActivity());
    adapter.setSelectedListener(new ChuangpuAdapter.SelectedListener() {
      @Override
      public void onSelected(int pos, String value) {
        refreshSb();
      }
    });
    rv_chuangpu.setAdapter(adapter);
    Type dataType = new TypeToken<List<ChuangpuBean>>(){}.getType();
    data = GsonUtil.getGson().fromJson(mock_beds, dataType);
    adapter.setData(data);

  }
  private ArrayList<ChuangpuBean> data = new ArrayList<>();

  private final String mock_beds = "[{\"count\":150,\"id\":-1,\"chuangpu\":\"1床上铺\"},{\"count\":150,\"id\":-1,\"chuangpu\":\"1床下铺\", \"name\":\"张杰\"},{\"count\":150,\"id\":-1,\"chuangpu\":\"2床上铺\"},{\"count\":150,\"id\":-1,\"chuangpu\":\"2床下铺\"},{\"count\":150,\"id\":-1,\"chuangpu\":\"3床上铺\"},{\"count\":150,\"id\":-1,\"chuangpu\":\"3床下铺\"},{\"count\":150,\"id\":-1,\"chuangpu\":\"4床上铺\", \"name\":\"谢光熙\"},{\"count\":150,\"id\":-1,\"chuangpu\":\"4床下铺\"}]";


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
  }


}
