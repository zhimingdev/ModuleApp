package com.test.lib_common.base;

public interface IBaseView {
    void configPresenter();

    <P extends BasePresenter> P getPersenter(Class<P> aclass);

    void createPersenter(Class<? extends BasePresenter>... classes);

    void finshRefreshComplete(boolean success);
}
