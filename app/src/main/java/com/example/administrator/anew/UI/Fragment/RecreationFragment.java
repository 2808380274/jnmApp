package com.example.administrator.anew.UI.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.anew.Adapter.MyRecyclerViewAdapter;
import com.example.administrator.anew.Bean.ResultBean;
import com.example.administrator.anew.Presenter.Contract.MainContract;
import com.example.administrator.anew.Presenter.MainPresenter;
import com.example.administrator.anew.R;
import com.example.administrator.anew.UI.Activity.WebContextActivity;
import com.example.administrator.anew.Utils.CommUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecreationFragment extends Fragment implements MainContract.View  {


    public RecreationFragment() {
        // Required empty public constructor
    }
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ProgressDialog dialog;
    MyRecyclerViewAdapter adapter;
    MainContract.Presenter presenter;
    CommUtils utils;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_recreation,container,false);
        ButterKnife.bind(this,view);
        presenter=new MainPresenter(this);
        utils=new CommUtils();
        dialog=new ProgressDialog(getActivity());
        initData();
        return view;


    }


    private void initData() {
        presenter.requestData(utils.recreation,utils.key);
    }

    private void initRecyclerView(List<ResultBean.DataBean> datas) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter=new MyRecyclerViewAdapter(getActivity(),datas);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new MyRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onClick(String url) {
                Intent intent=new Intent(getActivity(), WebContextActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }



        });
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
    public void showResult(List<ResultBean.DataBean> datas) {
        initRecyclerView(datas);
    }

    @Override
    public void showNodata() {

    }

    @Override
    public void showError(String msg) {

    }

}
