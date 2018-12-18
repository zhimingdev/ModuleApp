package com.test.module_main.ui.movie;

import com.test.lib_common.base.IBaseView;
import com.test.module_main.bean.MovieDetailBean;

public interface MovieContract {

    interface MovieView extends IBaseView {
        void refreshData(MovieDetailBean movieDetailBean);
    }

    interface IMoviePresenter {
        void requestMovieInfo(String id);
    }
}
