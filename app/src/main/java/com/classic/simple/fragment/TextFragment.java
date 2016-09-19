package com.classic.simple.fragment;

import com.classic.core.fragment.BaseFragment;
import com.classic.core.utils.ToastUtil;
import com.classic.simple.R;

public class TextFragment extends BaseFragment {

    @Override public int getLayoutResId() {
        return R.layout.fragment_text;
    }

    @Override public void onFragmentShow() {
        super.onFragmentShow();
        ToastUtil.showToast(getActivity(), "TextFragment --> onFragmentShow()");
    }

    @Override public void onFragmentHide() {
        super.onFragmentHide();
        ToastUtil.showToast(getActivity(), "TextFragment --> onFragmentHide()");
    }
}
