package com.sky.gankio.mvp.presenter.impl;

import com.sky.gankio.mvp.model.IGankModel;
import com.sky.gankio.mvp.model.impl.GankModelImpl;
import com.sky.gankio.mvp.presenter.IGankPresenter;
import com.sky.gankio.mvp.view.IGankView;
import com.sky.gankio.entity.GankEntity;

import java.util.List;

/**
 * Created by tonycheng on 2016/11/24.
 */

public class GankPresenterImpl implements IGankPresenter,
        GankModelImpl.OnloadGankListListener {

    private IGankView mAndroidView;
    private IGankModel mAndroidModel;

    public GankPresenterImpl(IGankView androidView) {
        mAndroidView = androidView;
        mAndroidModel = new GankModelImpl();
    }

    @Override
    public void loadGankList(String category,int count, int page) {
        mAndroidView.showLoading();
        mAndroidModel.loadGankList(category,count, page, this);
    }

    @Override
    public void onSuccess(List<GankEntity> androidList) {
        mAndroidView.hideLoading();
        mAndroidView.addGank(androidList);
    }

    @Override
    public void onFail(Throwable e) {
        mAndroidView.hideLoading();
        mAndroidView.showLoadFailMsg();
    }
}
