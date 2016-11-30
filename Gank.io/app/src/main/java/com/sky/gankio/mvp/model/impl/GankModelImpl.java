package com.sky.gankio.mvp.model.impl;

import com.sky.gankio.entity.GankEntity;
import com.sky.gankio.http.ApiService;
import com.sky.gankio.http.HttpResult;
import com.sky.gankio.http.HttpResultSubscriber;
import com.sky.gankio.http.RetrofitClient;
import com.sky.gankio.mvp.model.IGankModel;
import com.sky.gankio.utils.Debugger;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tonycheng on 2016/11/24.
 */

public class GankModelImpl implements IGankModel {

    @Override
    public void loadGankList(String category, int count, int page, final OnloadGankListListener listener) {
        ApiService apiService = RetrofitClient.getInstance().getApiService();

        Observable<HttpResult<List<GankEntity>>> androidList = apiService.getGankList(category, count, page);
        androidList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultSubscriber<List<GankEntity>>() {
                    @Override
                    public void onSuccess(HttpResult<List<GankEntity>> result) {
                        Debugger.d(result.getResults().toString());
                        listener.onSuccess(result.getResults());
                    }

                    @Override
                    public void onFail(Throwable e) {
                        listener.onFail(e);
                    }
                });
    }

    public interface OnloadGankListListener {
        void onSuccess(List<GankEntity> gankList);

        void onFail(Throwable e);
    }
}
