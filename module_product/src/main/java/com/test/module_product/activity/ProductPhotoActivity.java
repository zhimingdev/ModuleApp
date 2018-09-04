package com.test.module_product.activity;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.test.lib_common.base.BaseMvpActivity;
import com.test.module_product.R;
import com.test.module_product.R2;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

@Route(path = "/product/ProductPhotoActivity")
public class ProductPhotoActivity extends BaseMvpActivity {

    @BindView(R2.id.activity_photo_imageview)
    PhotoView activityPhotoImageview;
    @BindView(R2.id.fab)
    FloatingActionButton fab;

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_photo;
    }

    @Override
    public void initView() {
        String url = getIntent().getStringExtra("url");
        Glide.with(mContext).load(url).into(activityPhotoImageview);
    }

    @Override
    public void initData() {

    }

    @Override
    public void configPresenter() {

    }

    @Override
    public void finshRefreshComplete(boolean success) {

    }

    @OnClick({R2.id.fab})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.fab) {

        }
    }
}
