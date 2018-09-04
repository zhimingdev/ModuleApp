package com.test.module_product.product;

import android.content.Context;

import com.test.lib_common.base.BasePresenter;
import com.test.lib_common.config.Config;
import com.test.lib_common.http.BaseObserver;
import com.test.lib_common.http.HttpResponse;
import com.test.lib_common.http.RetrofitNewHelper;
import com.test.module_product.api.ProductService;
import com.test.module_product.bean.ResponseGrilsBean;

import java.util.List;

public class ProductPersenter extends BasePresenter<ProductContract.IView> implements ProductContract.IProductPersenter {
    @Override
    public void requestProduct(Context context, final int i) {
        RetrofitNewHelper.getNewInstance(Config.DEFAULT)
                .create(ProductService.class)
                .getGirls(i)
                .compose(this.<HttpResponse<List<ResponseGrilsBean>>>setThread())
                .subscribe(new BaseObserver<List<ResponseGrilsBean>>(context) {
                    @Override
                    public void onSuccess(HttpResponse<List<ResponseGrilsBean>> t) throws Exception {
                        List<ResponseGrilsBean> results = t.results;
                        if (i == Config.COMMON_CURRENTPAGE) {
                            getView().refreshProductView(results);
                        }else {
                            getView().loadMore(results);
                        }
                        getView().finshRefreshComplete(true);
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) throws Exception {
                        getView().finshRefreshComplete(false);
                    }
                });
    }
}
