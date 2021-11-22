package com.intern.musicplayertutorial;

import com.intern.musicplayertutorial.api.Api;
import com.intern.musicplayertutorial.api.RetrofitClient;

public abstract class BaseInteractor {
    private Api api;
    public Api getApi() {
        return api;
    }
    public BaseInteractor(){
        this.api = RetrofitClient.getInstance().getMyApi();
    }


}
