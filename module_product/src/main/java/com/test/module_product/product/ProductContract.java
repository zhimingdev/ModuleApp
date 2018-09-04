package com.test.module_product.product;

import android.content.Context;

import com.test.lib_common.base.IBaseView;
import com.test.module_product.bean.ResponseGrilsBean;

import java.util.List;

public interface ProductContract {

    interface IView extends IBaseView {
        void refreshProductView(List<ResponseGrilsBean> results);
        void loadMore(List<ResponseGrilsBean> results);
    }

    interface IProductPersenter {
        void requestProduct(Context context, int i);
    }
}
