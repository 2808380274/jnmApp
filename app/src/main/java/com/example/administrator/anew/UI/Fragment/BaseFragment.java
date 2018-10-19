package com.example.administrator.anew.UI.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.anew.BaseView;
import com.example.administrator.anew.Presenter.MainPresenter;
import com.example.administrator.anew.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/9/7.
 */

public abstract class BaseFragment extends Fragment implements BaseView {
    private Unbinder mUnbinder;

    private View mRootView;

     ProgressDialog mProgressDialog;

    SmartRefreshLayout refreshLayout;
    RecyclerView  recyclerview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

          mRootView=inflater.inflate(setLayout(),container,false);
          mUnbinder=  ButterKnife.bind(this, mRootView);
          return mRootView;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        init();
        initRefresh();
        initData();
        initView();
    }

    protected abstract void initView();

    public abstract int setLayout();
    protected abstract void initData();








    public  void  init(){
        refreshLayout=mRootView.findViewById(R.id.refreshLayout);
    }
    private void initRefresh() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                  initData();

            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableScrollContentWhenRefreshed(true);//是否在刷新完成时滚动列表显示新的内容 1.0.5
        refreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        refreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        refreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
        refreshLayout.setEnableLoadMoreWhenContentNotFull(true);//是否在列表不满一页时候开启上拉加载功能
    }

    @Override
    public void showLodading() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("loading......");

        mProgressDialog.show();

    }

    @Override
    public void dimissLoading() {
        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
    }
}
