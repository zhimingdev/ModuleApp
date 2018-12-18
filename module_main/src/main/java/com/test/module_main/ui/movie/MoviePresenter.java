package com.test.module_main.ui.movie;

import com.test.lib_common.base.BasePresenter;
import com.test.lib_common.http.HttpResponse;
import com.test.lib_common.http.RetrofitNewHelper;
import com.test.module_main.api.ApiService;
import com.test.module_main.bean.MovieDetailBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviePresenter extends BasePresenter<MovieContract.MovieView> implements MovieContract.IMoviePresenter {
    @Override
    public void requestMovieInfo(String id) {
        RetrofitNewHelper.getNewInstance("")
                .create(ApiService.class)
                .getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieDetailBean movieDetailBean) {
                        getView().refreshData(movieDetailBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
