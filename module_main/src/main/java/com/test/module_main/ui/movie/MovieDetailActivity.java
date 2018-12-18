package com.test.module_main.ui.movie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;
import com.test.lib_common.base.BaseMvpActivity;
import com.test.module_main.R;
import com.test.module_main.R2;
import com.test.module_main.adapter.MovieDetailAdapter;
import com.test.module_main.bean.MovieBean;
import com.test.module_main.bean.MovieDetailBean;
import com.test.module_main.bean.PersonBean;
import com.test.module_main.utils.StringFormatUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MovieDetailActivity extends BaseMvpActivity implements MovieContract.MovieView {

    @BindView(R2.id.iv_photo)
    ImageView ivOnePhoto;
    @BindView(R2.id.tv_one_rating_rate)
    TextView tvOneRatingRate;
    @BindView(R2.id.tv_one_rating_number)
    TextView tvOneRatingNumber;
    @BindView(R2.id.tv_one_directors)
    TextView tvOneDirectors;
    @BindView(R2.id.tv_one_casts)
    TextView tvOneCasts;
    @BindView(R2.id.tv_one_genres)
    TextView tvOneGenres;
    @BindView(R2.id.tv_one_day)
    TextView tvOneDay;
    @BindView(R2.id.tv_one_city)
    TextView tvOneCity;
    @BindView(R2.id.ll_one_item)
    LinearLayout llOneItem;
    @BindView(R2.id.ll_Header_view)
    LinearLayout llHeaderView;
    @BindView(R2.id.tv_one_title)
    TextView tvOneTitle;
    @BindView(R2.id.tv_movie_content)
    TextView tvMovieContent;
    @BindView(R2.id.xrv_cast)
    RecyclerView xrvCast;
    @BindView(R2.id.nsv_scrollview)
    NestedScrollView nsvScrollview;
    private MovieBean movieBean;
    private String cast;
    private String id;
    private String url;
    private ImageView imageView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public void initView() {
        imageView = findViewById(R.id.img_item_bg);
        if (getIntent() != null) {
            movieBean = (MovieBean) getIntent().getSerializableExtra("bean");
        }
        id = movieBean.getId();
        url = movieBean.getImages().getLarge();
        getPersenter(MoviePresenter.class).requestMovieInfo(id);
    }

    @Override
    public void initData() {
        cast = StringFormatUtil.formatName(movieBean.getCasts());
        Glide.with(this).load(movieBean.getImages().getMedium()).into(ivOnePhoto);
        tvOneRatingRate.setText(R.string.string_rating+movieBean.getRating().getAverage()+"");
        tvOneRatingNumber.setText(movieBean.getCollect_count()+R.string.string_rating_num+"");
        tvOneDirectors.setText(StringFormatUtil.formatName(movieBean.getDirectors()));
        tvOneCasts.setText(cast);
        tvOneGenres.setText(R.string.String_type+StringFormatUtil.formatGenres(movieBean.getGenres())+"");
        RequestOptions options = new RequestOptions()
                .transforms(new BlurTransformation(23,4));


        Glide.with(this)
                .load(url)
                .apply(options)
                .into(imageView);
    }


    @Override
    public void configPresenter() {
        createPersenter(MoviePresenter.class);
    }

    @Override
    public void finshRefreshComplete(boolean success) {

    }

    @Override
    public void refreshData(MovieDetailBean movieDetailBean) {
        tvOneDay.setText(String.format("上映日期：%s", movieDetailBean.getYear()));
        tvOneCity.setText(String.format("制片国家/地区：%s", StringFormatUtil.formatGenres(movieDetailBean.getCountries())));
        tvOneTitle.setText(StringFormatUtil.formatGenres(movieDetailBean.getAka()));
        tvMovieContent.setText(movieDetailBean.getSummary());
        transformData(movieDetailBean);
    }

    /**
     * 异步线程转换数据
     */
    private void transformData(final MovieDetailBean movieDetailBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < movieDetailBean.getDirectors().size(); i++) {
                    movieDetailBean.getDirectors().get(i).setType("导演");
                }
                for (int i = 0; i < movieDetailBean.getCasts().size(); i++) {
                    movieDetailBean.getCasts().get(i).setType("演员");
                }

                MovieDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter(movieDetailBean);
                    }
                });
            }
        }).start();
    }

    /**
     * 设置导演&演员adapter
     */
    private void setAdapter(MovieDetailBean movieDetailBean) {
        xrvCast.setVisibility(View.VISIBLE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MovieDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvCast.setLayoutManager(mLayoutManager);
        // 需加，不然滑动不流畅
        xrvCast.setNestedScrollingEnabled(false);
        xrvCast.setHasFixedSize(false);

        MovieDetailAdapter mAdapter = new MovieDetailAdapter();
        List<PersonBean> list = new ArrayList<>();
        list.clear();
        list.addAll(movieDetailBean.getDirectors());
        list.addAll(movieDetailBean.getCasts());
        mAdapter.setNewData(list);
        xrvCast.setAdapter(mAdapter);
    }

    /**
     * @param context      activity
     * @param item         bean
     * @param imageView    imageView
     */
    public static void start(Activity context, MovieBean item, ImageView imageView) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("bean", item);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView , "text");//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

}
