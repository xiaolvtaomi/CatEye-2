package com.cicinnus.retrofitlib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP工具类
 *
 *  用法:SPUtils.getInstance(context,sp_name)
 *              .putString(key1,value1)
 *              .putString(key2,value2)
 *              .putBoolean(key3,true)
 *              ...
 *              .apply();
 * putXX 之后必须调用apply()方法才会正确
 */

public class SPUtils {
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private static SPUtils instance;


    @SuppressLint("CommitPrefEdits")
    public static SPUtils getInstance(Context context, String fileName) {
        if (instance == null) {
            instance = new SPUtils();
        }
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sp.edit();

        return instance;
    }

    /**
     * 最后需要调用，否则无法保存
     */
    public void apply() {
        editor.apply();
    }

    /**
     * 删除所有数据
     */
    public void deleteSPData() {
        editor.clear().apply();
    }


    public SPUtils putString(String key, String value) {
        editor.putString(key, value);
        return this;
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }


    public SPUtils putInt(String key, int value) {
        editor.putInt(key, value);
        return this;
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public SPUtils putLong(String key, Long value) {
        editor.putLong(key, value);
        return this;
    }

    public SPUtils putDouble(String key, Double value) {
        editor.putLong(key, Double.doubleToRawLongBits(value));
        return this;
    }

    public Double getDouble(String key, Double defValue) {
        return Double.longBitsToDouble(sp.getLong(key, Double.doubleToLongBits(defValue)));
    }


    public Long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public SPUtils putBoolean(String key, boolean isAccess) {
        editor.putBoolean(key, isAccess);
        editor.commit();

        return this;
    }

    public Boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }
    
    
    public void setBDTime(String time){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("loctime", time);
        editor.commit();
    }
    public String getBDTime(){
        return sp.getString("loctime", "");
    }
    
    
    public double getBDLat(){
        return Double.parseDouble(sp.getString("latitude", "0"));
    }
    public void setBDLat(double lat){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("latitude", lat+"");
        editor.commit();
    }
    public double getBDLon(){
        return Double.parseDouble(sp.getString("lontitude", "0"));
    }
    public void setBDLon(double lon){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("lontitude", lon+"");
        editor.commit();
    }
    public String getBDProvince(){
        return sp.getString("bdprovince", "");
    }
    public void setBDProvince(String province){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("bdprovince", province);
        editor.commit();
    }
    public String getBDCity(){
        return sp.getString("bdcity", "城市");
    }
    public void setBDCity(String bdcity){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("bdcity", bdcity);
        editor.commit();
    }
    public String getBDCountry(){
        return sp.getString("bdcountry", "");
    }
    public void setBDCountry(String bdcountry){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("bdcountry", bdcountry);
        editor.commit();
    }
    public String getBDStreet(){
        return sp.getString("bdstreet", "");
    }
    public void setBDStreet(String bdstreet){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("bdstreet", bdstreet);
        editor.commit();
    }
    public String getBDStreetNumber(){
        return sp.getString("bdstreetNumber", "");
    }
    public void setBDStreetNumber(String bdstreetNumber){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("bdstreetNumber", bdstreetNumber);
        editor.commit();
    }
}
