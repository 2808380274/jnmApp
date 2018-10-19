package com.example.administrator.anew.Utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fullcircle on 2018/10/18.
 */

public class Utils {

    public static boolean checkUsername(String username){
        //alt shift  + 上下
        if(TextUtils.isEmpty(username)){
            return false;
        }else{
            //用户名要求字母开头 字母和数字组成 5 16位
            return username.matches("^[0-9]{10}$");
        }
    }

    public static boolean checkPassword(String pwd){
        if(TextUtils.isEmpty(pwd)){
            return false;
        }
        //密码要求 数字和文字组成 5~16位
        return pwd.matches("^[0-9a-zA-Z]{5,16}$");
    }
    //java 中实现首字母大写
    //substring(),返回一新字符串，它包含此字符串从索引beginIndex后的所有字符
    //toUpperCase将String中的所有字符都转换为大写
    public static String getFirstChar(String str){
        if(str ==null ){
            return null;
        }else{
            return str.substring(0,1).toUpperCase();
        }
    }
    public static String getDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
