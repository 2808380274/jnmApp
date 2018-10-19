package com.example.administrator.anew.Presenter;

import com.example.administrator.anew.BaseView;
import com.example.administrator.anew.Model.ModelCallBack;
import com.example.administrator.anew.Model.downloadModel;
import com.example.administrator.anew.Model.httpModel;
import com.example.administrator.anew.MyView;
import com.example.administrator.anew.Presenter.Contract.MainContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/10/19.
 */

public class downLoadPresenter implements MainContract.loadingPresenter {
    downloadModel model=new downloadModel();
    MyView.ImgView view;

    public downLoadPresenter(MyView.ImgView view) {
        this.view = view;
    }

    @Override
    public void requestData(String path_url) {
        view.showding();
        model.getImgData(path_url, new ModelCallBack.ImgCallBack() {
            @Override
            public void success(ResponseBody value) {
                if(value!=null){
                    view.success(value);
                }
              view.dismiss();
            }
        });
    }
}
