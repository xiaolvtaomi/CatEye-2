package com.cicinnus.cateye.module.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.tools.PreferencesUtil;
import com.cicinnus.cateye.tools.ToastUtil;
import com.cicinnus.cateye.view.CommonDialog;
import com.cicinnus.cateye.view.TextDatePickerView;
import com.cicinnus.cateye.view.TextEditView;
import com.cicinnus.cateye.view.TextSpinnerView;
import com.cicinnus.retrofitlib.base.BaseMVPFragment;

/**
 * 影院列表
 */

public class QingjiaAddFragment extends BaseMVPFragment{



  @BindView(R.id.tev_reason)
  TextEditView tev_reason;
  @BindView(R.id.tsv_type)
  TextSpinnerView tsv_type;
  @BindView(R.id.tdpv_begin)
  TextDatePickerView tdpv_begin;
  @BindView(R.id.tdpv_end)
  TextDatePickerView tdpv_end;

  @BindView(R.id.tv_title)
  TextView tv_title;
  @BindView(R.id.tv_confirm)
  TextView tv_confirm;
    
    public static QingjiaAddFragment newInstance() {

        return new QingjiaAddFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_qingjiaadd;
    }

    @Override
    protected void initEventAndData() {
        tv_title.setText("请假条");

//        View hallTypeContent = LayoutInflater.from(mContext).inflate(R.layout.layout_hall_type, null);
//        //确定
//        hallTypeContent.findViewById(R.id.tv_confirm)
//            .setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                  ToastUtil.showToast("提交成功");
//                  getActivity().finish();
//                }
//            });

      tv_confirm.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
          if(PreferencesUtil.isYouke()){
            CommonDialog.showYoukePermission(mContext);
          }else {
            ToastUtil.showToast("假条已经提交");
            getActivity().finish();
          }
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
