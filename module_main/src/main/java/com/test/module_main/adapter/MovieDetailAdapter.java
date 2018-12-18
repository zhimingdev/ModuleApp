package com.test.module_main.adapter;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.module_main.R;
import com.test.module_main.bean.PersonBean;


/**
 * Created by jingbin on 2016/12/10.
 */

public class MovieDetailAdapter extends BaseQuickAdapter<PersonBean,BaseViewHolder> {
    public MovieDetailAdapter() {
        super(R.layout.item_movie_detail_person);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonBean item) {
        ImageView view = (ImageView) helper.getView(R.id.iv_avatar);
        Glide.with(mContext).load(item.getAvatars().getLarge()).into(view);
        helper.setText(R.id.tv_one_name,item.getName());
        helper.setText(R.id.tv_two_type,item.getType());
    }
}
