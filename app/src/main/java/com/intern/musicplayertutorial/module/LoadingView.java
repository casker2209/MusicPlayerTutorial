package com.intern.musicplayertutorial.module;

import android.app.Activity;
import android.content.Context;

public interface LoadingView {
    Context getActivity();

    void startNewActivity();
}
