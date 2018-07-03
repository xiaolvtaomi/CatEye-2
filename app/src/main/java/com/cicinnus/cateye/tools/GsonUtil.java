package com.cicinnus.cateye.tools;

import com.cicinnus.cateye.module.discover.DiscoverHeaderBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by lml on 17/7/13.
 */

public class GsonUtil {
    private static Gson gson = new Gson();

    public static Gson getGson(){
        return gson;
    }

    /**
     * json转换为bean
     * @param json
     * @param c
     * @return
     *
     */
    public static Object getObject(String json, Class c){
        return gson.fromJson(json, c);
    }

    /**
     * json转换为map
     * @param json
     * @return
     */
    public static Map getMap(String json){
        return gson.fromJson(json, Map.class);
    }

    /**
     * json转换为list
     * @param json
     * @return
     */
    public static<T> List getList(String json, T objectBean){
        Type dataType = new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(json, dataType);
    }

    /**
     * bean/map/list转换为json
     * @return
     */
    public static String getJson(Object src, Type typeOfSrc){
        return gson.toJson(src, typeOfSrc);
    }

    public static String getJson(Object src){
        return gson.toJson(src);
    }
}
