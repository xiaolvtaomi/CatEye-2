package com.cicinnus.cateye.tools;

import android.content.Context;
import android.content.Intent;

import com.cicinnus.cateye.base.BaseWebViewActivity;
import com.cicinnus.cateye.base.OverlayDemo;
import com.cicinnus.cateye.base.MainActivity;

import com.cicinnus.cateye.module.LoginNewActivity;
import com.cicinnus.cateye.module.RegistNewActivity;
import com.cicinnus.cateye.module.home.ChooseBedDialogAct;
import com.cicinnus.cateye.module.home.JunxunfuActivity;
import com.cicinnus.cateye.module.home.QingjiaAddAct;
import com.cicinnus.cateye.module.home.QingjiaDetailAct;
import com.cicinnus.cateye.module.home.QingjiaListAct;

/**
 * Created by lml on 18/2/7.
 */

public class JumpActUtil {
    
    public static void go2MainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void go2QingjiaAddAct(Context context) {

        Intent intent = new Intent(context, QingjiaAddAct.class);
        context.startActivity(intent);
    }
    public static void go2QingjiaListAct(Context context) {

        Intent intent = new Intent(context, QingjiaListAct.class);
        context.startActivity(intent);
    }
    public static void go2QingjiaDetailAct(Context context) {

        Intent intent = new Intent(context, QingjiaDetailAct.class);
        context.startActivity(intent);
    }
    public static void go2LoginActivity(Context context) {
        Intent intent = new Intent(context, LoginNewActivity.class);
        context.startActivity(intent);
    }
    public static void go2RegistActivity(Context context) {
        Intent intent = new Intent(context, RegistNewActivity.class);
        context.startActivity(intent);
    }
    public static void go2JunxunActivity(Context context) {
        Intent intent = new Intent(context, JunxunfuActivity.class);
        context.startActivity(intent);
    }
    public static void go2BedActivity(Context context) {
        Intent intent = new Intent(context, ChooseBedDialogAct.class);
        context.startActivity(intent);
    }
    public static void go2ZhinanActivity(Context context) {
        BaseWebViewActivity.start(context,"http://cdn.sinacloud.net/diancai/anyang.html", "入学指南");
    }
    public static void go2YidongJunxufu(Context context) {
        BaseWebViewActivity.start(context,"http://cdn.sinacloud.net/diancai/yidong.html", "预存话费送军训服");
    }
    public static void go2LocationAct(Context context) {

      Intent intent = new Intent(context, OverlayDemo.class);
      context.startActivity(intent);
    }
}
