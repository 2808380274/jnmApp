package com.example.administrator.anew.UI.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.anew.UI.Fragment.InlandFragment;
import com.example.administrator.anew.UI.Fragment.InternationalFragment;
import com.example.administrator.anew.UI.Fragment.RecreationFragment;
import com.example.administrator.anew.UI.Fragment.SociteyFragment;
import com.example.administrator.anew.UI.Fragment.TopFragment;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/9/21.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    String [] mTitles;
   ArrayList<Fragment> arrayList;
    public ViewPagerAdapter(FragmentManager fm, String [] mTitles,ArrayList<Fragment>arrayList) {
        super(fm);
      this.arrayList=arrayList;
        this.mTitles=mTitles;
    }

    @Override
    public Fragment getItem(int position) {
       /* if (position==0){
          return   new TopFragment();
        }else if (position==1){
          return   new SociteyFragment();
        }else if (position==2){
           return new RecreationFragment();
        }else if (position==3){
            return new InternationalFragment();
        }else if (position==4){
            new InlandFragment();
        }
        return new TopFragment();*/
       return arrayList.get(position);
    }

    @Override
    public int getCount() {
//        return  mTitles.length;
          return  arrayList.size();
    }

    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }





}
