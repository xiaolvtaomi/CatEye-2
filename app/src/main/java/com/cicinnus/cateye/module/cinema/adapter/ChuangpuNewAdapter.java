package com.cicinnus.cateye.module.cinema.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.module.cinema.bean.ChuangpuBean;
import java.util.ArrayList;
import java.util.List;

public class ChuangpuNewAdapter extends BaseAdapter {
	private ArrayList<ChuangpuBean> data;
	private Context context;
	public int selectedPos = 0;

	public ChuangpuNewAdapter(Context _context) {
		context = _context;
		data = new ArrayList<ChuangpuBean>();
	}

	public void setData(List<ChuangpuBean> temp) {
		if (temp != null) {
			data = (ArrayList<ChuangpuBean>) temp;
		}
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_service, null);
			holder = new ViewHolder();
			holder.tv_service = (TextView) convertView
					.findViewById(R.id.tv_service);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}


		holder.tv_name.setText(data.get(position).getName());
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
				if (selectedPos != position) {
					data.get(selectedPos).setName("");
					selectedPos = position;
					data.get(selectedPos).setName("我");
				}
			}
		});


		return convertView;
	}
	

	class ViewHolder {
		TextView tv_service;
		TextView tv_name;
	}
}
