package com.test.module_find;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.alertview.AlertView;
import com.test.lib_common.base.BaseMvpFragment;
import com.test.lib_common.view.TitleBar;
import com.test.module_find.adapter.MyAdapter;
import com.test.module_find.ui.thirdparty.ThirdFragment;
import com.test.module_find.ui.zhishi.ZhishiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FindFragment extends BaseMvpFragment {

    @BindView(R2.id.fragment_find_titlebar)
    TitleBar fragmentFindTitlebar;
    @BindView(R2.id.activity_find_tl)
    TabLayout activityFindTl;
    @BindView(R2.id.activity_find_vp)
    ViewPager activityFindVp;

    List<String> list = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();
    private MyAdapter myAdapter;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    protected int configLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {
        list.add("第三方工具");
        list.add("干货订制");
        activityFindTl.setTabMode(TabLayout.MODE_FIXED);
        fragmentList.add(ThirdFragment.newInstance());
        fragmentList.add(ZhishiFragment.newInstance());
        myAdapter = new MyAdapter(mActivity.getSupportFragmentManager(),fragmentList,list);
        activityFindVp.setAdapter(myAdapter);
        activityFindTl.setupWithViewPager(activityFindVp);
    }

    @Override
    protected void initData() {
        fragmentFindTitlebar.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertView("提示", "主要是实现相关功能的测试,以及案例", null, new String[]{"确定"}, null, mActivity,
                        AlertView.Style.Alert, null).show();

//                final RxDialogSure rxDialogSure = new RxDialogSure(mActivity);
//                rxDialogSure.setContent("主要是实现相关功能的测试,以及案例");
//                rxDialogSure.setTitle("提示");
//                rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rxDialogSure.cancel();
//                    }
//                });
//                rxDialogSure.show();
            }
        });
    }

    @Override
    public void configPresenter() {

    }

    @Override
    public void finshRefreshComplete(boolean success) {

    }


    @Override
    protected void initImmersionBar(boolean isChange) {
        super.initImmersionBar(true);
    }
}
