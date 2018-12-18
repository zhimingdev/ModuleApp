package com.test.module_find.ui.zhishi;

import android.databinding.DataBindingUtil;
import android.support.annotation.MainThread;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.lib_common.base.BaseApplication;
import com.test.lib_common.config.Config;
import com.test.lib_common.http.RetrofitNewHelper;
import com.test.module_find.ApiService;
import com.test.module_find.R;
import com.test.module_find.adapter.ZhishiAdapter;
import com.test.module_find.bean.GankIoDataBean;
import com.test.module_find.databinding.ItemZhishiBinding;
import com.test.module_find.databinding.ZhishiFragmentBinding;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GankCtl {
    ZhishiFragmentBinding binding;
    private final GankIoDataBean.ResultBean resultBean;
    private ZhishiAdapter zhishiAdapter;
    private List<GankIoDataBean.ResultBean> results;
    private MyAdapter myAdapter;

    public GankCtl(ZhishiFragmentBinding binding) {
        this.binding = binding;
        resultBean = new GankIoDataBean.ResultBean();
        initRecyclerView();
    }

    public void requestData () {
        RetrofitNewHelper.getNewInstance(Config.DEFAULT)
                .create(ApiService.class)
                .getGankIoData("all",1,20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankIoDataBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankIoDataBean gankIoDataBean) {
                        results = gankIoDataBean.getResults();
                        zhishiAdapter.addAll(results);
                        zhishiAdapter.notifyDataSetChanged();

                        //另一种写法
//                        myAdapter.setNewData(results);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(BaseApplication.getInstance());
        binding.xrvCustom.setLayoutManager(layoutManager);
        zhishiAdapter = new ZhishiAdapter();
        binding.xrvCustom.setAdapter(zhishiAdapter);

//        myAdapter = new MyAdapter();
//        binding.xrvCustom.setAdapter(myAdapter);

        requestData();
    }

    private class MyAdapter extends BaseQuickAdapter<GankIoDataBean.ResultBean,BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_zhishi,results);
        }


        @Override
        protected void convert(BaseViewHolder helper, GankIoDataBean.ResultBean item) {
            ((ItemZhishiBinding) DataBindingUtil.bind(helper.itemView)).setResultsBean(item);
        }
    }
}
