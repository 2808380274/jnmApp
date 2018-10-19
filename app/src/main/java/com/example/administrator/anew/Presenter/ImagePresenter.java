package com.example.administrator.anew.Presenter;

import com.example.administrator.anew.Bean.ImgBean;
import com.example.administrator.anew.DataBean.EntryBean;
import com.example.administrator.anew.Model.httpModel;
import com.example.administrator.anew.Presenter.Contract.MainContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/14.
 */

public class ImagePresenter implements MainContract.DataPresenter {
    MainContract.DataView  dataView;
    httpModel model =new httpModel();
    public ImagePresenter(MainContract.DataView dataView) {
        this.dataView = dataView;
    }

    @Override
    public void requestData(int count,int page) {
        dataView.showLodading();
        model.getImg(count ,page)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(
                      new Consumer<ImgBean<EntryBean>>() {
                          @Override
                          public void accept(ImgBean<EntryBean> resultBeanImgBean) throws Exception {
                                          if (resultBeanImgBean !=null){
                                            dataView.showResult(resultBeanImgBean.getResults());
                                              dataView.dimissLoading();
                                          }
                          }

                      }

               );
    }
}
