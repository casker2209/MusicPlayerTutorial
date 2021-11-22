package com.intern.musicplayertutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intern.musicplayertutorial.component.BaseViewImpl;
import com.intern.musicplayertutorial.module.album.AlbumListAdapter;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseAdapter<O> extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {
    protected Context context;
    protected List<O> list;

    public BaseAdapter(Context context,List<O> list){
        this.context = context;
        this.list = list;
    }

    public List<O> getList() {
        return list;
    }

    public void setList(List<O> list) {
        this.list = list;
    }

    protected BaseViewImpl getBaseActivity(){
        return (BaseViewImpl) context;
    }


    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(getLayoutId(),parent,false);
        return getHolder(view);
    }

    protected abstract int getLayoutId();

    protected abstract BaseHolder getHolder(View view);


    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.BaseHolder holder, int position) {
        holder.initData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public abstract class BaseHolder extends RecyclerView.ViewHolder{
        public BaseHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }



        protected abstract void initData(int position);
    }
}
