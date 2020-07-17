package com.lib.frame.view;

import com.lib.frame.viewmodel.BaseViewModel;

/**
 * 共享Activity ViewModel
 *
 * @author EthanCo
 * @since 2018/9/10
 */
public abstract class BaseShareFragment<V, T extends BaseViewModel<V>> extends BaseFragment<V, T> {
    @Override
    protected T createViewModel() {
        if (getActivity() instanceof BaseActivity) {
            return (T) ((BaseActivity) getActivity()).getViewModel();
        }
        throw new IllegalStateException("activity is Not BaseActivity");
    }
}
