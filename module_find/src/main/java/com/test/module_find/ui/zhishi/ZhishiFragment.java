package com.test.module_find.ui.zhishi;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.test.module_find.R;
import com.test.module_find.databinding.ZhishiFragmentBinding;

public class ZhishiFragment extends Fragment {

    private ZhishiFragmentBinding binding;
    private GankCtl gankCtl;

    public static ZhishiFragment newInstance() {
        ZhishiFragment zhishiFragment = new ZhishiFragment();
        Bundle bundle = new Bundle();
        zhishiFragment.setArguments(bundle);
        return zhishiFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.zhishi_fragment,container,false);
        gankCtl = new GankCtl(binding);
        binding.setGankCtl(gankCtl);
        return binding.getRoot();
    }

}
