package com.intern.musicplayertutorial;

import androidx.fragment.app.Fragment;

import com.intern.musicplayertutorial.component.BaseViewImpl;

import java.util.List;

public abstract class BaseFragment<I extends BaseInterfaceView,P extends BasePresenterInterface,O1,O2> extends Fragment{
    protected P mPresenter;
    protected List<O1> List;
    protected O2 object;

    public BaseFragment(){};

    public java.util.List<O1> getList() {
        return List;
    }

    public P getPresenter() {
        return mPresenter;
    }

    public BaseViewImpl getBaseActivity(){
        return (BaseViewImpl) getActivity();
    }

}
