package com.example.administrator.anew.Utils;

import android.support.v4.app.Fragment;

import com.example.administrator.anew.UI.Fragment.HomeFragment;
import com.example.administrator.anew.UI.Fragment.PersonageFragment;
import com.example.administrator.anew.UI.Fragment.PictureFragment;

/**
 * Created by Administrator on 2018/10/14.
 */

public class Fragmentfactory {
    public static HomeFragment home=null;
    public static PictureFragment picture=null;
    public static PersonageFragment personage=null;
    public static Fragment getFragment(int position){
        switch (position){
            case 0:
                if (home==null){
                    home=new HomeFragment();
                }
                return  home;
            case 1:
                if (picture==null){
                    picture=new PictureFragment();
                }
                return picture;
            case 2:
                if (personage==null){
                    personage=new PersonageFragment();
                }
                return personage;
        }
        return home;
    }
}
