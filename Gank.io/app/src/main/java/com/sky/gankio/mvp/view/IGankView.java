package com.sky.gankio.mvp.view;

import com.sky.gankio.entity.GankEntity;

import java.util.List;

/**
 * Created by tonycheng on 2016/11/24.
 */

public interface IGankView {

    void showLoading();

    void addGank(List<GankEntity> gankList);

    void hideLoading();

    void showLoadFailMsg();
}
