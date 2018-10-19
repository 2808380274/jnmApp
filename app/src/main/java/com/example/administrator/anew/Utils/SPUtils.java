package com.example.administrator.anew.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名 : PracticalDemo
 * 包名 : cn.dragoliu.practicaldemo.utils
 * 文件名 : SPUtils
 * 创建者 : Kyle
 * 创建时间 : 2018-01-26
 * 描述 : SharedPreferences封装
 */

public class SPUtils {

    public static final String NAME = "config";

    /**
     * 存储字符串型数据
     */
    public static void putString(Context mContext, String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取字符串型数据
     */
    public static String getString(Context mContext, String key, String defaultValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 存储整型数据
     */
    public static void putInteger(Context mContext, String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 获取整型数据
     */
    public static int getInteger(Context mContext, String key, int defaultValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /**
     * 存储布尔型数据
     */
    public static void putBoolean(Context mContext, String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取布尔型数据
     */
    public static boolean getBoolean(Context mContext, String key, boolean defaultValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 删除单个数据
     */
    public static void remove(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    /**
     * 删除单个数据
     */
    public static void clear(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

}
