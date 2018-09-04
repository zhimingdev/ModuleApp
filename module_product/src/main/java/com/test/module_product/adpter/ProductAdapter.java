package com.test.module_product.adpter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.module_product.R;
import com.test.module_product.bean.ResponseGrilsBean;

public class ProductAdapter extends BaseQuickAdapter<ResponseGrilsBean,BaseViewHolder> {

    public ProductAdapter() {
        super(R.layout.item_gril);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResponseGrilsBean item) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_product_item);
        Glide.with(mContext).load(item.getUrl()).into(imageView);
    }
}
