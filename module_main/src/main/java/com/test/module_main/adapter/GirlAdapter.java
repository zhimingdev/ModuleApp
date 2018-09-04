package com.test.module_main.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.module_main.R;
import com.test.module_main.bean.GrilBean;

public class GirlAdapter extends BaseQuickAdapter<GrilBean,BaseViewHolder> {

    public GirlAdapter() {
        super(R.layout.item_grils);
    }

    @Override
    protected void convert(BaseViewHolder helper, GrilBean item) {
        helper.setText(R.id.tv_msg_one,item.getTitle());
        helper.setText(R.id.tv_msg_two,item.getIntroduction());
        helper.setText(R.id.tv_msg_three,item.getOccupation());
        helper.setText(R.id.tv_msg_four,item.getUserName());
        ImageView imageView = (ImageView) helper.getView(R.id.iv_left_icon);
        Glide.with(mContext).load(item.getImg()).into(imageView);
    }
}
