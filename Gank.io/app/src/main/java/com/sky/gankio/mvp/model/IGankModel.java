package com.sky.gankio.mvp.model;

import com.sky.gankio.mvp.model.impl.GankModelImpl;

/**
 * Created by tonycheng on 2016/11/24.
 */

public interface IGankModel {

    void loadGankList(String category, int count, int page,
                      GankModelImpl.OnloadGankListListener listener);
}
