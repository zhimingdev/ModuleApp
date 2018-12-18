package com.test.module_main.adapter;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.lib_common.base.BaseMvpActivity;
import com.test.module_main.R;
import com.test.module_main.ui.movie.MovieDetailActivity;
import com.test.module_main.bean.MovieBean;
import com.test.module_main.utils.StringFormatUtil;

public class GirlAdapter extends BaseQuickAdapter<MovieBean,BaseViewHolder> {

    public ImageView imageView;
    private BaseMvpActivity activity;

    public GirlAdapter(BaseMvpActivity mActivity) {
        super(R.layout.item_movie);
        this.activity = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MovieBean item) {
        imageView = (ImageView) helper.getView(R.id.iv_one_photo);
        Glide.with(mContext).load(item.getImages().getLarge()).into(imageView);
        helper.setText(R.id.tv_one_title,item.getTitle());
        helper.setText(R.id.tv_one_directors,StringFormatUtil.formatName(item.getDirectors()));
        helper.setText(R.id.tv_one_casts,StringFormatUtil.formatName(item.getCasts()));
        helper.setText(R.id.tv_one_genres,R.string.String_type+StringFormatUtil.formatGenres(item.getGenres()));
        helper.setText(R.id.tv_one_rating_rate,R.string.string_rating+item.getRating().getAverage()+"");


        helper.getView(R.id.ll_one_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieDetailActivity.start(activity,item,imageView);
            }
        });
    }
}
