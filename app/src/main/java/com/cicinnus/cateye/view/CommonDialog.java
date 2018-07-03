package com.cicinnus.cateye.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.tools.JumpActUtil;
import com.cicinnus.cateye.tools.ToastUtil;

/**
 * Description: dialog相关操作类
 * Author: lizhen
 * Create Date: 2018-03-06
 */
public class CommonDialog {
  public static void showYoukePermission(Context context){
    new AlertDialog.Builder(context)
        .setCancelable(true)
        .setMessage("游客帐号只能浏览不能提交信息～")
        .setPositiveButton("我知道了", (dialog, which) -> {
          dialog.dismiss();
        }).show();
  }

    public static void showJiaofeiAndCancle(final Fragment fragment, String msg){
        new AlertDialog.Builder(fragment.getContext())
                .setCancelable(true)
                .setMessage(msg+"\n系统将为您的选择保存48小时，请即时向学校缴费卡中缴费～")
                .setPositiveButton("我知道了", (dialog, which) -> {
                    dialog.dismiss();
                    fragment.getActivity().finish();
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).show();
    }
    public static void showSignNo(final Fragment fragment){
        new AlertDialog.Builder(fragment.getContext())
                .setCancelable(true)
                .setMessage("没有可以签到的考勤点，请确认以下情况\n1，您已经分配了学院、班级信息；\n2，您的老师已经设置了考勤点；\n3，您已经允许了智慧迎新的定位权限")
                .setPositiveButton("我知道了", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }


  public static void showJiaofeiAndCancle(Activity activity){
    new AlertDialog.Builder(activity)
        .setCancelable(true)
        .setMessage("系统将为您的选择保存48小时，请即时向学校缴费卡中缴费～")
        .setPositiveButton("我知道了", (dialog, which) -> {
          dialog.dismiss();
          activity.finish();
        }).show();
  }

  public static void showYidongAndCancle(Activity activity){
    new AlertDialog.Builder(activity)
        .setCancelable(true)
        .setMessage("参加安阳移动校园迎新活动，免费获得军训服")
        .setPositiveButton("免费领军训服", (dialog, which) -> {
          JumpActUtil.go2YidongJunxufu(activity);
          dialog.dismiss();
          activity.finish();
        })
        .setNegativeButton("任性不要", (dialog, which) -> {
          dialog.dismiss();
          JumpActUtil.go2LocationAct(activity);
        }).show();
  }

    
    public interface OnPermissionDialogCancelLisneter{
        public void onCancel();
    }


    public interface CallbackListener {
        void cancel();

        void confirm(Object... obj);
    }
}
