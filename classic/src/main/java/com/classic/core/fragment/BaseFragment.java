package com.classic.core.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.classic.core.interfaces.IFragment;
import com.classic.core.interfaces.IRegister;
import com.classic.core.utils.SharedPreferencesUtil;

/**
 * Fragment父类
 *
 * @author 续写经典
 * @date 2015/12/16
 */
public abstract class BaseFragment extends Fragment
    implements IFragment, IRegister, View.OnClickListener {
    private final String SP_NAME = "firstConfig";
    protected Activity activity;
    protected View parentView;
    private SharedPreferencesUtil spUtil;

    @Override public void onFirst() {
    }

    @Override public void initInstanceState(Bundle savedInstanceState) {
    }

    @Override public void initData() {
    }

    @Override public void initView(View parentView) {
    }

    @Override public void onChange() {
    }

    @Override public void onHidden() {
    }

    @Override public void viewClick(View v) {
    }

    @Override public void showProgress() {
    }

    @Override public void hideProgress() {
    }

    @Override public void register() {
    }

    @Override public void unRegister() {
    }

    @Override public void onClick(View v) {
        viewClick(v);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        activity = getActivity();
        parentView = inflater.inflate(getLayoutResId(), container, false);
        spUtil = new SharedPreferencesUtil(getActivity(), SP_NAME);
        final String simpleName = this.getClass().getSimpleName();
        if (spUtil.getBooleanValue(simpleName, true)) {
            onFirst();
            spUtil.putBooleanValue(simpleName, false);
        }
        initInstanceState(savedInstanceState);
        initData();
        initView(parentView);
        return parentView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        register();
    }

    @Override public void onDestroyView() {
        unRegister();
        super.onDestroyView();
    }
}
