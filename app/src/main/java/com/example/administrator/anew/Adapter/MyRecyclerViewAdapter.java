package com.example.administrator.anew.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.anew.Bean.ResultBean;
import com.example.administrator.anew.R;

import java.util.List;



/**
 * Created by Administrator on 2018/9/9.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<ResultBean.DataBean> mDatas;

    public MyRecyclerViewAdapter(Context context, List<ResultBean.DataBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       final ResultBean.DataBean dataBean= mDatas.get(position);
       holder.tv_title.setText(dataBean.getTitle());
       holder.tv_time.setText(dataBean.getDate());
       holder.tv_type.setText(dataBean.getAuthor_name());
        Glide.with(context).load(dataBean.getThumbnail_pic_s()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   //单击事件
                              if (listener!=null){
                                  listener.onClick(mDatas.get(position).getUrl());
                                  Log.i("网址",mDatas.get(position).getUrl().toString());
                              }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_type;
        TextView tv_title;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
          tv_time=itemView.findViewById(R.id.tv_time);
           tv_title=itemView.findViewById(R.id.tv_title);
            tv_type=itemView.findViewById(R.id.tv_type);
            image=itemView.findViewById(R.id.image);
        }
    }

//声明一个点击事件接口类型的成员变量

private MyClickListener listener;
//传入点击事件具体实现的方法
public void setListener(MyClickListener listener) {
    this.listener = listener;
}
//声明一个点击事件的接口
public interface MyClickListener{
    void onClick(String url);

}
}
