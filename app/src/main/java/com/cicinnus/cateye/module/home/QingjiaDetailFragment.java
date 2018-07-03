package com.cicinnus.cateye.module.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.view.TextEditView;
import com.cicinnus.cateye.view.TextSpinnerView;
import com.cicinnus.cateye.view.TextTextView;
import com.cicinnus.retrofitlib.base.BaseMVPFragment;

/**
 * 影院列表
 */

public class QingjiaDetailFragment extends BaseMVPFragment {


  @BindView(R.id.ttv_person)
  TextTextView ttv_person;
  @BindView(R.id.ttv_reason)
  TextTextView ttv_reason;
  @BindView(R.id.ttv_begin)
  TextTextView ttv_begin;
  @BindView(R.id.ttv_end)
  TextTextView ttv_end;
  @BindView(R.id.et_reply)
  EditText et_reply;
  @BindView(R.id.iv_accept)
  ImageView iv_accept;
  @BindView(R.id.iv_reject)
  ImageView iv_reject;

  @BindView(R.id.tv_title)
  TextView tv_title;

  public static QingjiaDetailFragment newInstance() {

    return new QingjiaDetailFragment();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_qingjiadetail;
  }

  @Override
  protected void initEventAndData() {
    tv_title.setText("请假条详情");

    iv_accept
        .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getActivity().finish();
          }
        });
    iv_reject
        .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getActivity().finish();
          }
        });

  }


  @Override
  protected void lazyLoadEveryTime() {
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
  }


}
