package com.example.administrator.anew.UI.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.anew.Adapter.RecyclerViewAdapter;
import com.example.administrator.anew.DataBean.EntryBean;
import com.example.administrator.anew.HttpData.ApiService;
import com.example.administrator.anew.MyView;
import com.example.administrator.anew.Presenter.Contract.MainContract;
import com.example.administrator.anew.Presenter.ImagePresenter;
import com.example.administrator.anew.Presenter.downLoadPresenter;
import com.example.administrator.anew.R;
import com.example.administrator.anew.UI.Activity.WebContextActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureFragment extends Fragment implements MainContract.DataView {


    public PictureFragment() {
        // Required empty public constructor
    }

   RecyclerView recyclerView;
    ProgressDialog dialog;
    MainContract.DataPresenter presenter;
    RecyclerViewAdapter adapter;
    private SmartRefreshLayout refreshLayout;
    downLoadPresenter loadPresenter;
    Bitmap bitmap=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_picture, container, false);
        // Inflate the layout for this fragment
        recyclerView=view.findViewById(R.id.recycler_view);
        dialog=new ProgressDialog(getActivity());
        presenter=new ImagePresenter(this);
    //    loadPresenter=new downLoadPresenter(this);
        initData();
        return view;

   }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        refreshLayout=view.findViewById(R.id.refresh_Layout);
        initRefresh();
    }
    private void initRefresh() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                initData();

            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000);
            }
        });
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableScrollContentWhenRefreshed(true);//是否在刷新完成时滚动列表显示新的内容 1.0.5
        refreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        refreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        refreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
        refreshLayout.setEnableLoadMoreWhenContentNotFull(true);//是否在列表不满一页时候开启上拉加载功能
    }

    private void initData() {
   //    presenter.requestData(15,1);
        Random rand = new Random();
        int i = rand.nextInt(30);
        int n=rand.nextInt(9);
        if (n>0){
            int d=n+10;
            presenter.requestData(d,i);
        }else if (n==0){
            int d=n+1;
            presenter.requestData(d,i);
        }

    }

    @Override
    public void showLodading() {
     dialog.show();
    }

    @Override
    public void dimissLoading() {
      if (dialog.isShowing()){
          dialog.dismiss();
      }
    }

    @Override
    public void showResult(List<EntryBean> datas) {
       initRecyclerView(datas);
    }

    @Override
    public void showNodata() {

    }

    @Override
    public void showError(String msg) {

    }
    public void initRecyclerView(List<EntryBean> datas){
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter=new RecyclerViewAdapter(this);
        adapter.setList(datas);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new RecyclerViewAdapter.MyClickListener() {
            @Override
            public void onClick(String url) {
                Intent intent=new Intent(getActivity(), WebContextActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }

            @Override
            public void onLongClick(String url) {
                Log.i("图片地址:",url);
                String path_url= String.valueOf(url.indexOf("0"));
               // loadPresenter.requestData(path_url);
                downLoadImg(path_url);
            }
        });
    }
  /*//下载开始
    @Override
    public void showding() {
        Toast.makeText(getActivity(),"开始下载",Toast.LENGTH_SHORT);
    }
//下载成功
    @Override
    public void dismiss() {
        Toast.makeText(getActivity(),"下载成功",Toast.LENGTH_SHORT);
    }
//分层写的长按时会卡,这里记下
    @Override
    public void success(ResponseBody value) {
        byte[] bys=new byte[0];
        try {
            bys = value.bytes(); //注意：把byte[]转换为bitmap时，也是耗时操作，也必须在子线程
        final     Bitmap bitmap = BitmapFactory.decodeByteArray(bys, 0, bys.length);
            getActivity().runOnUiThread(new Runnable() {//开启主线程更新UI
                        @Override
                        public void run() {

                            try {//调用saveFile方法
                                saveFile(bitmap, Environment.getExternalStorageDirectory() + "/imgpic/");
                                Log.e("TAG", Environment.getExternalStorageDirectory() + "/imgpic/");
                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }
                        });


        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/

     //没有成功实现下载。。。。。。一定要改
    private void downLoadImg(final String url) {


        new Thread(new Runnable() {
            @Override
            public void run() {
               //OK设置请求超时时间，读取超时时间
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .build();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.Img_url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

                ApiService apiService = retrofit.create(ApiService.class);
                Observable<ResponseBody> observable = apiService.downLoadImg(url);


                observable.subscribeOn(Schedulers.io())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody value) {

                                byte[] bys = new byte[1024];
                                try {

                                    bys = value.bytes(); //注意：把byte[]转换为bitmap时，也是耗时操作，也必须在子线程
                                    Log.i("value",value.bytes().toString());
                                    bitmap = BitmapFactory.decodeByteArray(bys, 0, bys.length);
                                    Log.i("bitmap",bitmap.toString());
                                    getActivity().runOnUiThread(new Runnable() {//开启主线程更新UI
                                        @Override
                                        public void run() {


                                            try {//调用saveFile方法
                                                saveFile(bitmap, Environment.getExternalStorageDirectory() + "/desheng/");
                                            //    Log.e("TAG", Environment.getExternalStorageDirectory() + "/imgpic/");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });


            }
        }).start();

    }
    //保存图片到SD卡
    public void saveFile(Bitmap bm,String filename) throws IOException {

        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "desheng");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
       filename = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            //MyToastUtils.showShortToast(context, "保存失败");
            e.printStackTrace();
        } catch (IOException e) {
            //  MyToastUtils.showShortToast(context, "保存失败");
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                    file.getAbsolutePath(), filename, null);
            //  MyToastUtils.showShortToast(context, "保存成功");
            Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            //  MyToastUtils.showShortToast(context, "保存失败");
            e.printStackTrace();
        }
        // 最后通知图库更新
        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));

    }
}
