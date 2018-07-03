package com.cicinnus.cateye.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cicinnus.cateye.R;


public abstract class MyBaseFragment extends Fragment {
	/**
     * 页面是否进入pause状态
     */
    private boolean isPaused;
	private UniLoadingDialog mLoadingDialog ;

	/**
	 * 屏幕的宽度、高度、密度
	 */
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected float mDensity;
	
	public MyBaseFragment() {
		super();
	}

	protected List<AsyncTask<Void, Void, Boolean>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();

	protected abstract void handleMsg(Message msg);
	protected Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.SHOWPROGRESS:
				if(mLoadingDialog == null){
					mLoadingDialog = new UniLoadingDialog(getActivity(), "请求提交中");
				}
				mLoadingDialog.show();
				break;
			case Constants.HIDEPROGRESS:
				if(mLoadingDialog != null){
					mLoadingDialog.dismiss();
				}
				break;
			case Constants.ERROR:
				if(getActivity() == null)return;
				showCustomToast(TextUtils.isEmpty((String)msg.obj) == true ? "" : (String)msg.obj );
				break;
			default:
				break;
			}
			handleMsg(msg);
		};
	};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return container;
	};
	

	@Override
	public void onPause() {
        isPaused = true;
        super.onPause();
    }
	
	@Override
	public void onResume() {
		super.onResume();
		isPaused = false;
	}
	
	/**
	 * 是否是paused状态<BR>
	 * @return
	 */
	protected boolean isPaused()
    {
        return isPaused;
    }
	
	
	protected void showLoadingDialog(String text) {
		if (!isPaused){
			if (text != null) {
				mLoadingDialog.setText(text);
			}
			mLoadingDialog.show();
		}
	}

	protected void dismissLoadingDialog() {
		if (mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
	}
	
	@Override
	public void onDestroy() {
		clearAsyncTask();
		
		if(mLoadingDialog != null){
			mLoadingDialog.dismiss();
			mLoadingDialog = null;
		}
		super.onDestroy();
	}
	
	protected void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
		mAsyncTasks.add(asyncTask.execute());
	}

	protected void clearAsyncTask() {
		Iterator<AsyncTask<Void, Void, Boolean>> iterator = mAsyncTasks
				.iterator();
		while (iterator.hasNext()) {
			AsyncTask<Void, Void, Boolean> asyncTask = iterator.next();
			if (asyncTask != null && !asyncTask.isCancelled()) {
				asyncTask.cancel(true);
			}
		}
		mAsyncTasks.clear();
	}
	
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(getActivity()).inflate(
				R.layout.common_toast, null);
		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(getActivity());
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}
	
}
