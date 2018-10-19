package com.example.administrator.anew.Utils;

import android.support.v4.app.Fragment;

import com.example.administrator.anew.UI.Fragment.InlandFragment;
import com.example.administrator.anew.UI.Fragment.InternationalFragment;
import com.example.administrator.anew.UI.Fragment.RecreationFragment;
import com.example.administrator.anew.UI.Fragment.SociteyFragment;
import com.example.administrator.anew.UI.Fragment.TopFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/9/22.
 */

public class CommUtils {
    public static String [] mTitles ={"头条","社会","国内","国际","娱乐"};
    public static String type="top";
    public static String key="bc0a3a53be1e97115c2313e638662cae";
    public static String socitey="shehui";
    public static String recreation="guonei";
    public static String international="guoji";
    public static String inland="yule";
    public ArrayList<Fragment> Fragments(){
        ArrayList<Fragment> arrayList=new ArrayList<>();
        arrayList.add(new TopFragment());
        arrayList.add(new SociteyFragment());
        arrayList.add(new RecreationFragment());
        arrayList.add(new InternationalFragment());
        arrayList.add(new InlandFragment());
        return arrayList;
    }
}
