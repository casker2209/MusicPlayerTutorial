package com.intern.musicplayertutorial;

import android.content.Context;

import java.util.List;

public abstract class BaseFragmentAdapter<F,O> extends BaseAdapter<O> {
    protected F fragment;

    public BaseFragmentAdapter(Context context, List<O> list,F fragment) {
        super(context, list);
        this.fragment = fragment;
    }

    public F getFragment() {
        return fragment;
    }
}
