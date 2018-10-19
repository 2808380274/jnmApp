package com.example.administrator.anew.Presenter;


import com.example.administrator.anew.Bean.NewsBean;
import com.example.administrator.anew.Bean.ResultBean;
import com.example.administrator.anew.Model.httpModel;
import com.example.administrator.anew.Presenter.Contract.MainContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/8.
 */

public class MainPresenter implements MainContract.Presenter {

    MainContract.View mView;
    httpModel model =new httpModel();

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestData(String type, String key) {
     mView.showLodading();
      model.getNews(type,key)
              .subscribeOn(Schedulers.io())     //被观察者所在线程      io线程
              .observeOn(AndroidSchedulers.mainThread())  //观察者所在线程    主线程
              .subscribe(new Observer<NewsBean<ResultBean>>() {
                  Disposable mDisposable;
                  @Override
                  public void onSubscribe(@NonNull Disposable d) {
                              mDisposable=d;
                  }

                  @Override
                  public void onNext(@NonNull NewsBean<ResultBean> resultBeanNewsBean) {
                             if (resultBeanNewsBean!=null){
                                 mView.showResult(resultBeanNewsBean.getResult().getData());
                             }else {
                                 mView.showNodata();
                             }
                             mView.dimissLoading();
                  }

                  @Override
                  public void onError(@NonNull Throwable e) {
                        mView.dimissLoading();

                  }

                  @Override
                  public void onComplete() {
                       mView.dimissLoading();
                  }
              });


    }
}
