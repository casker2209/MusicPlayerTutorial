package com.intern.musicplayertutorial.module.songlist;

import com.intern.musicplayertutorial.BaseInterfaceView;
import com.intern.musicplayertutorial.component.BaseViewImpl;

public interface SongListScreenView extends BaseInterfaceView {
    SongListScreenPresenterInterface getmPresenter();

    BaseViewImpl getBaseActivity();

}
