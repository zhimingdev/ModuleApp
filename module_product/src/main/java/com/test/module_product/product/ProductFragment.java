package com.test.module_product.product;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.test.lib_common.base.BaseMvpFragment;
import com.test.lib_common.config.Config;
import com.test.module_product.R;
import com.test.module_product.R2;
import com.test.module_product.adpter.ProductAdapter;
import com.test.module_product.bean.ResponseGrilsBean;

import java.util.List;

import butterknife.BindView;

public class ProductFragment extends BaseMvpFragment implements ProductContract.IView {

    @BindView(R2.id.ll_product)
    LinearLayout llProduct;
    @BindView(R2.id.rcv_product)
    RecyclerView rcvProduct;
    @BindView(R2.id.fragment_product_srl)
    SmartRefreshLayout fragmentProductSrl;
    private ProductPersenter mProductPersenter;
    private ProductAdapter mProductAdapter;
    int number = Config.CURRENTPAGE;
    List<ResponseGrilsBean> mGrilsBeanList;

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    protected int configLayout() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initView() {
        mProductPersenter = getPersenter(ProductPersenter.class);
        mProductPersenter.requestProduct(mActivity,Config.CURRENTPAGE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, Config.ROWNUMBER);
        rcvProduct.setLayoutManager(gridLayoutManager);
        mProductAdapter = new ProductAdapter();
        mProductAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mProductAdapter.isFirstOnly(false);
        rcvProduct.setAdapter(mProductAdapter);

        fragmentProductSrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mProductPersenter.requestProduct(mActivity,++number);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mProductPersenter.requestProduct(mActivity, Config.CURRENTPAGE);
            }
        });
    }

    @Override
    protected void initData() {
        mProductAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String url = mProductAdapter.getItem(position).getUrl();
                ARouter.getInstance().build("/product/ProductPhotoActivity")
                        .withString("url",url).navigation();
            }
        });
    }

    @Override
    public void configPresenter() {
        createPersenter(ProductPersenter.class);
    }

    @Override
    public void finshRefreshComplete(boolean success) {
        fragmentProductSrl.finishRefresh();
        fragmentProductSrl.finishLoadMore();
    }

    @Override
    public void refreshProductView(List<ResponseGrilsBean> results) {
        mProductAdapter.setNewData(results);
    }

    @Override
    public void loadMore(List<ResponseGrilsBean> results) {
        mProductAdapter.addData(results);
    }

    @Override
    protected void initImmersionBar(boolean isChange) {
        super.initImmersionBar(true);
    }

}
