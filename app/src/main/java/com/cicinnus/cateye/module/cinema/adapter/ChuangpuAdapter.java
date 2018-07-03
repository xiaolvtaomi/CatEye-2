package com.cicinnus.cateye.module.cinema.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.module.cinema.bean.ChuangpuBean;
import com.cicinnus.cateye.view.TextSpinnerView;
import java.util.ArrayList;
import java.util.List;

public class ChuangpuAdapter extends RecyclerView.Adapter<ChuangpuAdapter.CommonViewHolder> {

    private final LayoutInflater mLayoutInflater;
    public int selectedPos = -1;
    private ArrayList<ChuangpuBean> data;

    private Context context;

    public ChuangpuAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context ;
        data = new ArrayList<ChuangpuBean>();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(mLayoutInflater.inflate(R.layout.item_service, parent, false));
    }


    public void setData(List<ChuangpuBean> temp) {
        if (temp != null) {
            data = (ArrayList<ChuangpuBean>) temp;
        }
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        if(!TextUtils.isEmpty(data.get(position).getName())
            || selectedPos == position
            ){
          holder.tv_name.setVisibility(View.VISIBLE);
        }else{
          holder.tv_name.setVisibility(View.INVISIBLE);
        }
        if(selectedPos == position){
          holder.tv_name.setText("我");
        }else {
          holder.tv_name.setText(data.get(position).getName());
        }
        holder.tv_service.setText(data.get(position).getChuangpu());
        holder.tv_service.setTextColor(!TextUtils.isEmpty(data.get(position).getName()) ?
            context.getResources().getColor(R.color.colorPrimary) :
            context.getResources().getColor(R.color.text_gray_deep));
        holder.tv_service.setBackground(!TextUtils.isEmpty(data.get(position).getName()) ?
            context.getResources().getDrawable(R.drawable.bg_service_checked) :
            context.getResources().getDrawable(R.drawable.bg_service_normal));

        holder.tv_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // 是空，或者 是我选的
                if (TextUtils.isEmpty(data.get(position).getName()) && selectedPos != position) {
                    selectedPos = position;
//                    data.get(selectedPos).setName("我");
                    notifyDataSetChanged();
                    if(mSelectedListener != null){
                      mSelectedListener.onSelected(selectedPos, data.get(selectedPos).getChuangpu());
                    }
                }

            }
        });
    }

    public int getSelectedPos(){
      return selectedPos ;
    }

    public String getSelectedPosValue(){
      if(selectedPos < 0){
        return "床铺未定";
      }else{
        return data.get(selectedPos).getChuangpu();
      }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CommonViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_service;
        public TextView tv_name;

        public CommonViewHolder(View itemView) {
            super(itemView);
            tv_service = itemView.findViewById(R.id.tv_service);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

  public interface SelectedListener{
    public void onSelected(int pos, String value);
  }
  private SelectedListener mSelectedListener ;
  public void setSelectedListener(SelectedListener _SelectedListener){
    if(_SelectedListener != null){
      mSelectedListener = _SelectedListener ;
    }
  }
}
