package com.example.administrator.anew.Presenter.Contract;



import com.example.administrator.anew.BaseView;
import com.example.administrator.anew.Bean.ResultBean;
import com.example.administrator.anew.DataBean.EntryBean;
import com.example.administrator.anew.Presenter.BasePresenter;

import junit.runner.BaseTestRunner;

import java.util.List;

/**
 * Created by Administrator on 2018/9/8.
 */

public interface MainContract {
    interface Presenter extends BasePresenter {
        //实现请求数据
       void   requestData(String type,String key);
    }
    interface View extends BaseView {
        void  showResult(List<ResultBean.DataBean> datas);  //显示数据
        void showNodata();  //没有数据
        void showError(String msg);//出现错误
       // void  showRefreshLayout();
    }
    interface DataPresenter extends BasePresenter{
        void requestData(int count,int page);
    }
    interface DataView extends BaseView{
        void  showResult(List<EntryBean> datas);  //显示数据
        void showNodata();  //没有数据
        void showError(String msg);//出现错误
    }
    interface loadingPresenter extends BasePresenter{
        void requestData(String path_url);
    }
}
