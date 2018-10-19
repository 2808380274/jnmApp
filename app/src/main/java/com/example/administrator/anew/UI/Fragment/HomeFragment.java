package com.example.administrator.anew.UI.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.anew.R;
import com.example.administrator.anew.UI.Adapter.ViewPagerAdapter;
import com.example.administrator.anew.Utils.CommUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
    ViewPager viewPager;
    TabLayout tabLayout;
    CommUtils utils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView =  contentView = inflater.inflate(R.layout.fragment_home,
                container, false);
        if (contentView != null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null) {
                parent.removeView(contentView);
            }
            return contentView;
        }
        return contentView;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
           viewPager= (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout= (TabLayout) view.findViewById(R.id.tab_layout);
          initTabLayout();
    }

    private void initTabLayout() {
        utils=new CommUtils();
        //这里要写getChildFragmentManager();
      ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager(),utils.mTitles,utils.Fragments());
        viewPager.setOffscreenPageLimit(utils.Fragments().size());
        //viewPager.setCurrentItem(0);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
