package com.test.module_main;

import android.os.Process;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.test.lib_common.base.BaseMvpActivity;
import com.test.module_find.FindFragment;
import com.test.module_main.fragment.MainFragment;
import com.test.module_product.product.ProductFragment;

import butterknife.BindView;

@Route(path = "/main/MainActivity")
public class MainActivity extends BaseMvpActivity {
    @BindView(R2.id.fl_main)
    FrameLayout mMainFrameLayout;
    @BindView(R2.id.navigation)
    BottomNavigationView navigation;
    private Fragment currentFragment = new Fragment();
    private MainFragment mMainFragment;
    private ProductFragment mProductFragment;
    private FindFragment mFindFragment;
    private long exitTime;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int i = item.getItemId();
            if (i == R.id.navigation_home) {
                if (mMainFragment == null) {
                    mMainFragment = MainFragment.newInstance();
                }
                showFragment(mMainFragment);
                return true;
            } else if (i == R.id.navigation_dashboard) {
                if (mProductFragment == null) {
                    mProductFragment = ProductFragment.newInstance();
                }
                showFragment(mProductFragment);
                return true;
            } else if (i == R.id.navigation_notifications) {
                if (mFindFragment == null) {
                    mFindFragment = FindFragment.newInstance();
                }
                showFragment(mFindFragment);
                return true;
            } else if (i == R.id.navigation_mine) {
                if (mMainFragment == null) {
                    mMainFragment = MainFragment.newInstance();
                }
                showFragment(mMainFragment);
                return true;
            }
            return false;
        }

    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mMainFragment = MainFragment.newInstance();
        mProductFragment = ProductFragment.newInstance();
        mFindFragment = FindFragment.newInstance();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showFragment(mMainFragment);

//        final MyAdvertisementView myAdvertisementView = new MyAdvertisementView(this);
//        myAdvertisementView.showDialog();
//
//        myAdvertisementView.setDismissListener(new MyAdvertisementView.DismissListener() {
//            @Override
//            public void onClick(View view) {
//                myAdvertisementView.dismiss();
//            }
//        });
//
//        myAdvertisementView.setInverestListener(new MyAdvertisementView.InverstListener() {
//            @Override
//            public void onClick(View view) {
//                myAdvertisementView.dismiss();
//                ARouter.getInstance().build("/product/ProductPhotoActivity")
//                        .withString("url","http://pic.qiantucdn.com/58pic/26/33/53/39V58PICAfM_1024.jpg").navigation();
//            }
//        });
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

    /**
     * 展示Fragment
     */
    private void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.fl_main, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 绑定物理返回键
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // creatDialog();
            // 如果两次按键时间间隔大于2000毫秒，则不退出
            // ActivityManager.getInstance().cleanAllActivity();
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                // 记录exitTime
                exitTime = System.currentTimeMillis();
            } else {
                // 否则退出程序
                finish();
                Process.killProcess(Process.myPid());
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initImmersionBar(boolean isChange) {
        super.initImmersionBar(true);
    }
}
