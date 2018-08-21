package com.baize.fireeyekotlin.mvvm.model;

import android.arch.lifecycle.MutableLiveData;

import com.baize.fireeyekotlin.bean.HomeBean;
import com.baize.fireeyekotlin.http.DefaultSubscriber;
import com.baize.fireeyekotlin.http.reqs.ReqoKaiyan;

import org.jetbrains.annotations.NotNull;

/**
 * Created by 彦泽 on 2018/8/21.
 */

public class HomeVideoRepository {

    public MutableLiveData<HomeBean> getHomeVideoData() {
        final MutableLiveData<HomeBean> data = new MutableLiveData<>();
        ReqoKaiyan.Companion.getInstance().getHomeData()
                .subscribe(new DefaultSubscriber<HomeBean>() {

                    @Override
                    public void _onNext(HomeBean entity) {
                        if (entity != null) {
                            data.setValue(entity);
                        }
                    }

                    @Override
                    public void _onError(@NotNull String errMsg) {
                        data.setValue(null);
                    }
                });
        return data;
    }

}
