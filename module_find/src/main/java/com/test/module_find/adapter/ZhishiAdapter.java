package com.test.module_find.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.test.lib_common.base.baseAdapter.BaseRecyclerViewAdapter;
import com.test.lib_common.base.baseAdapter.BaseRecyclerViewHolder;
import com.test.lib_common.utils.TimeUtil;
import com.test.module_find.R;
import com.test.module_find.bean.GankIoDataBean;
import com.test.module_find.databinding.ItemZhishiBinding;

public class ZhishiAdapter extends BaseRecyclerViewAdapter<GankIoDataBean.ResultBean> {


    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent,R.layout.item_zhishi);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<GankIoDataBean.ResultBean,ItemZhishiBinding> {

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(GankIoDataBean.ResultBean object, int position) {
            binding.setResultsBean(object);
            if (object.getImages() != null
                    && object.getImages().size() > 0
                    && !TextUtils.isEmpty(object.getImages().get(0))) {
                Glide.with(binding.getRoot().getContext()).load(object.getImages().get(0)).into(binding.ivAndroidPic);
            }
//            binding.tvAndroidDes.setText(object.getDesc());
//            binding.tvAndroidWho.setText(object.getWho());
//            binding.tvAndroidTime.setText(TimeUtil.getTranslateTime(object.getPublishedAt()));
        }
    }
}
