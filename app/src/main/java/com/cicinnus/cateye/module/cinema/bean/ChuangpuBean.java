package com.cicinnus.cateye.module.cinema.bean;


import android.text.TextUtils;
import java.io.Serializable;

/**
 * Created by Cicinnus on 2017/6/20.
 */

public class ChuangpuBean implements Serializable{
    String name = "";
    String chuangpu ;

    public String getChuangpu() {
        return chuangpu;
    }

    public void setChuangpu(String chuangpu) {
        this.chuangpu = chuangpu;
    }

    public String getName() {
        return TextUtils.isEmpty(name)?"":name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
