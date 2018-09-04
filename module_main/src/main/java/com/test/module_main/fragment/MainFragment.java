package com.test.module_main.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.test.lib_common.base.BaseMvpFragment;
import com.test.lib_common.utils.MyAdvertisementView;
import com.test.module_main.R;
import com.test.module_main.R2;
import com.test.module_main.adapter.GirlAdapter;
import com.test.module_main.bean.GrilBean;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFragment extends BaseMvpFragment implements MainContract.IView {
    @BindView(R2.id.banner)
    MZBannerView mBanner;
    @BindView(R2.id.test_rcv)
    RecyclerView testRcv;
    @BindView(R2.id.fragment_mainsrl)
    SmartRefreshLayout fragmentMainsrl;
    private List<Integer> list = new ArrayList<>();
    private MainPresenter mMainPresenter;
    private GirlAdapter mGirlAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int configLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        mMainPresenter = getPersenter(MainPresenter.class);
        mMainPresenter.requestData(mActivity);
        fragmentMainsrl.setEnableOverScrollDrag(false);
        list.clear();
        list.add(R.drawable.banner_one);
        list.add(R.drawable.banner_two);
        list.add(R.drawable.banner_three);
        list.add(R.drawable.banner_four);
        mBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                Toast.makeText(mActivity, "点击的是" + i, Toast.LENGTH_SHORT).show();
            }
        });
        // 设置数据
        mBanner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testRcv.setLayoutManager(linearLayoutManager);
        mGirlAdapter = new GirlAdapter();
        mGirlAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mGirlAdapter.isFirstOnly(false);
        testRcv.setAdapter(mGirlAdapter);

        final MyAdvertisementView myAdvertisementView = new MyAdvertisementView(mActivity);
        myAdvertisementView.showDialog();

        myAdvertisementView.setDismissListener(new MyAdvertisementView.DismissListener() {
            @Override
            public void onClick(View view) {
                myAdvertisementView.dismiss();
            }
        });

        myAdvertisementView.setInverestListener(new MyAdvertisementView.InverstListener() {
            @Override
            public void onClick(View view) {
                myAdvertisementView.dismiss();
                ARouter.getInstance().build("/product/ProductPhotoActivity")
                        .withString("url","http://pic.qiantucdn.com/58pic/26/33/53/39V58PICAfM_1024.jpg").navigation();
            }
        });
    }

    @Override
    protected void initData() {
        fragmentMainsrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mMainPresenter.requestData(mActivity);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.start();//开始轮播
    }

    @Override
    public void configPresenter() {
        createPersenter(MainPresenter.class);
    }

    @Override
    public void finshRefreshComplete(boolean success) {
        fragmentMainsrl.finishRefresh();
    }

    @Override
    public void refreshView(List<GrilBean> results) {
        System.out.println("接受到数据" + new Gson().toJson(results));
        mGirlAdapter.setNewData(results);
    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            Glide.with(context).load(data).into(mImageView);
        }
    }

    @Override
    protected void initImmersionBar(boolean isChange) {
        super.initImmersionBar(false);
    }

}
