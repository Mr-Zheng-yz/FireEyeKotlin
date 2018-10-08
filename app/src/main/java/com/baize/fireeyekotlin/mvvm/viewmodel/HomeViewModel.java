package com.baize.fireeyekotlin.mvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.baize.fireeyekotlin.bean.HomeBean;
import com.baize.fireeyekotlin.mvvm.model.HomeMoreVideoRepository;
import com.baize.fireeyekotlin.mvvm.model.HomeVideoRepository;

/**
 * Created by 彦泽 on 2018/8/21.
 */

public class HomeViewModel extends ViewModel {

    private MutableLiveData<HomeBean> homeVideoBean;
    private HomeVideoRepository homeVideoRepository;
    private HomeMoreVideoRepository homeMoreVideoRepository;

    /**
     * UserRepository parameter is provided by Dagger 2
     */
    public HomeViewModel() {
        homeVideoRepository = new HomeVideoRepository();
        homeMoreVideoRepository = new HomeMoreVideoRepository();
    }

    public LiveData<HomeBean> getHomeVideo() {
        if (homeVideoBean == null
                || homeVideoBean.getValue() == null) {
            homeVideoBean = new MutableLiveData<>();
            return loadHomeVideo();
        } else {
            return homeVideoBean;
        }
    }

    public LiveData<HomeBean> getHomeMoreVideo(String date){
        return homeMoreVideoRepository.getHomeMoreData(date);
    }

    private MutableLiveData<HomeBean> loadHomeVideo() {
        MutableLiveData<HomeBean> homeVideo = homeVideoRepository.getHomeVideoData();
        setHomeVideoBean(homeVideo);
        return homeVideo;
    }

    private void setHomeVideoBean(MutableLiveData<HomeBean> homeBean) {
        this.homeVideoBean = homeBean;
    }
}
