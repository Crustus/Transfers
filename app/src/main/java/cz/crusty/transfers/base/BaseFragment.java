package cz.crusty.transfers.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.crusty.transfers.R;

/**
 * Created by Crusty. 03.09.2018
 */
public abstract class BaseFragment extends Fragment implements BaseView {

    private View mContentView;
    private View mErrorView;
    private View mLoadingView;

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_base, container, false);

        mContentView = onCreateContentView(inflater, container, savedInstanceState);

        root.addView(mContentView, 0);

        mErrorView = root.findViewById(R.id.error);
        mLoadingView = root.findViewById(R.id.loading);

        return root;
    }

    protected abstract View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


    @Override
    public void showContent(boolean show) {
        mContentView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(boolean show) {
        mErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showLoading(boolean show) {
        mLoadingView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
