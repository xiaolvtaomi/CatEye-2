package com.cicinnus.cateye.module.more;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cicinnus.cateye.R;
import com.cicinnus.cateye.module.more.bean.ContactBean;
import com.cicinnus.cateye.module.more.bean.RecentSignin;

public class SigninMemberAdapter extends BaseAdapter {
	private ArrayList<RecentSignin> data;
	private HashMap<String, ContactBean> userdata;
	private Context context;

	public SigninMemberAdapter(Context _context) {
		context = _context;
		data = new ArrayList<RecentSignin>();
		userdata = new HashMap<String, ContactBean>();
	}

	public void setData(ArrayList<RecentSignin> temp) {
		if (temp != null) {
			data = temp;
//			contactDBUtils = new ContactDBUtils(context);
//			if (userdata == null) {
//				userdata = new HashMap<String, ContactBean>();
//			}
//			for (RecentSignin recent : temp) {
//				if (recent != null) {
//					ContactBean user = contactDBUtils.getContactByUid(recent
//							.getUid());
//					userdata.put(recent.getUid(), user);
//				}
//			}
//			contactDBUtils.release();
		}
	}

	int j = 0;

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

	int i = 0;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.signin_qiandao_people, null);
			holder = new ViewHolder();
			holder.iv_icon = (ImageView) convertView
					.findViewById(R.id.contact_icon);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.contact_time);
			holder.tv_dis = (TextView) convertView
					.findViewById(R.id.tv_dis);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.contact_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ContactBean user = userdata.get(data.get(position).getUid());
		if (user != null) {
			holder.tv_name.setText(user.getName());
		} else {
			holder.tv_name.setText(TextUtils.isEmpty(data.get(position)
					.getUid()) ? "未知" : data.get(position).getUid());
		}
		holder.tv_time.setText(TextUtils.isEmpty(data.get(position)
				.getSignTime()) ? "??:??" : data.get(position).getSignTime()
				.substring(11, 16));
		
		
		if(TextUtils.isEmpty(data.get(position).getDistance())){
			holder.tv_dis.setVisibility(View.INVISIBLE);
		}else{
			double dis = Double.parseDouble(data.get(position).getDistance()) ;
			if(dis > 1000){
				holder.tv_dis.setText(""+aboutDis(dis/1000)+"km");
				holder.tv_dis.setVisibility(View.VISIBLE);
			}else if(dis > 500){
				holder.tv_dis.setText(""+(int)dis+"m");
				holder.tv_dis.setVisibility(View.VISIBLE);
			}else{
				holder.tv_dis.setVisibility(View.INVISIBLE);
			}
		}

		return convertView;
	}
	
	private String aboutDis(double db){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(db);
	}

	class ViewHolder {
		ImageView iv_icon;
		TextView tv_dis;
		TextView tv_time;
		TextView tv_name;
	}
}
