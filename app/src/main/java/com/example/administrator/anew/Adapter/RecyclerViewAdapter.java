package com.example.administrator.anew.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.anew.DataBean.EntryBean;
import com.example.administrator.anew.R;

import java.util.List;

/**
 * Created by Administrator on 2018/10/14.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
   private Fragment fragment;  //考虑fragment生命周期
    private List<EntryBean> list;

    public void setList(List<EntryBean> list) {
        this.list = list;
    }

    public List<EntryBean> getList() {
        return list;
    }

    public RecyclerViewAdapter(Fragment fragment) {

        this.fragment = fragment;
    }

   /* public RecyclerViewAdapter(Context context) {
        this.context = context;
    }*/

    @Override
    public int getItemViewType(int position) {
        WindowManager windowManager = (WindowManager)fragment.getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        final int screenWidth = dm.widthPixels;
        return Math.round((float) screenWidth / (float) list.get(position).getHeight() * 10f);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(fragment.getActivity(), R.layout.item_image,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        EntryBean entryBean =list.get(position);
        Log.i("数据：",list.get(position).getUrl());
//       Glide.with(fragment).load(entryBean.getUrl()).skipMemoryCache(true) .thumbnail(0.1f).into(holder.imageView);
        Log.i("图片", entryBean.getUrl());
        //存在记录的高度时先Layout再异步加载图片
        if (list.get(holder.getAdapterPosition()).getHeight() > 0) {
            ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
            layoutParams.height = list.get(holder.getAdapterPosition()).getHeight();
        }

        WindowManager windowManager = (WindowManager)fragment.getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        final int screenWidth = dm.widthPixels;
        final String url = list.get(position).getUrl();
        Glide.with(fragment).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>(screenWidth, screenWidth) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if(holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                            if (list.get(holder.getAdapterPosition()).getHeight() <= 0) {
                                int width = resource.getWidth();
                                int height = resource.getHeight();
                                int realHeight = screenWidth * height / width / 2;
                                list.get(holder.getAdapterPosition()).setHeight(realHeight);
                                ViewGroup.LayoutParams lp = holder.imageView.getLayoutParams();
                                lp.height = realHeight;
                                if(width < screenWidth / 2)
                                    lp.width = screenWidth / 2;
                            }
                            holder.imageView.setImageBitmap(resource);
                        }
                    }
                });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                   listener.onClick(url);
                     listener.onLongClick(url);
                    //Log.i("网址",mDatas.get(position).getUrl().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view);
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
        void onLongClick(String url);
    }

}