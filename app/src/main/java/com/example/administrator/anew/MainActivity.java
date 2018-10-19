package com.example.administrator.anew;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.administrator.anew.Utils.Fragmentfactory;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    String [] titles={"新闻","美图","我"};
    BottomNavigationBar bottomNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationBar= (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        initBottomBar();//初始化bottomNavigationBar
        initFirstFragment();//设置第一个fragment界面
    }

    private void initFirstFragment() {
        // 使用fragmentTransaction.hide和show可能会导致重影
        FragmentManager fragmentManager = getSupportFragmentManager(); //v4包里的类
        List<Fragment> fragments = fragmentManager.getFragments();//获取有多少个fragment,并且添加到list集合里面
        if (fragments != null && fragments.size() > 0) { //当list集合里面的fragment对象不为空，且fragment对象个数大于0时，执行
            for (Fragment fragment : fragments) {  // 容器中元素类型   临时变量 ：容器变量  foreach循环会自动遍历容器中每个元素
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //移除残留的fragment
                fragmentTransaction.remove(fragment);
                //提交并保存
                fragmentTransaction.commit();
            }
        }
                //进入时默认显示第一个fragment界面
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = Fragmentfactory.getFragment(0);
                fragmentTransaction.add(R.id.fl_container, fragment);
                fragmentTransaction.commit();

    }

    private void initBottomBar() {
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.mine_feedback,titles[0]))
                              .addItem(new BottomNavigationItem(R.mipmap.plugin_selected_2,titles[1]))
                              .addItem(new BottomNavigationItem(R.mipmap.contact_selected_2,titles[2]))
                              .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

                //获取fragment对象
                Fragment fragment= Fragmentfactory.getFragment(position);
                //创建fragment管理器
                FragmentManager fm=getSupportFragmentManager();
                //获取fragment事务
                FragmentTransaction transaction=fm.beginTransaction();
                //判断fragment是否添加
                if (!fragment.isAdded()){ //如果没有添加,进行添加到activity
                    transaction.add(R.id.fl_container,fragment);
                }else {   //添加了，就进行显示
                    transaction.show(fragment);
                }
                //保存并提交
                transaction.commit();
            }

            @Override
            public void onTabUnselected(int position) {

                Fragment fragment=Fragmentfactory.getFragment(position);
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                //离开tab.进行隐藏
                transaction.hide(fragment);
                transaction.commit();

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

}
