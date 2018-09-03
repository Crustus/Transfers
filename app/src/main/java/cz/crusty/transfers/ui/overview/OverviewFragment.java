package cz.crusty.transfers.ui.overview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.List;

import cz.crusty.transfers.BuildConfig;
import cz.crusty.transfers.R;
import cz.crusty.transfers.data.model.transaction.Transaction;
import cz.crusty.transfers.base.BaseFragment;
import cz.crusty.transfers.ui.detail.DetailActivity;
import cz.crusty.transfers.utils.InputUtils;
import cz.crusty.transfers.widget.FilterBox.FilterBoxAnimation;
import cz.crusty.transfers.widget.FilterBox.FilterBoxView;
import cz.crusty.transfers.widget.FilterBox.FilterBoxListener;

/**
 *
 */
public class OverviewFragment extends BaseFragment
        implements OverviewContract.View, OnOverviewListInteractionListener {

    private static final String TAG = OverviewFragment.class.getSimpleName();

    private FilterBoxView mFilterBox;
    private int mFilterBoxHeight;

    private OverviewAdapter mAdapter;

    private final OverviewPresenter mPresenter = new OverviewPresenter(this);


    public static OverviewFragment newInstance() {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public OverviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {

        }
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        Context context = view.getContext();

        // filter box
        mFilterBox = view.findViewById(R.id.filter_box);
        mFilterBox.setChangeListener(new FilterBoxListener() {
            @Override
            public void onTextChanged(String query) {
                if(BuildConfig.DEBUG)
                    Log.d(TAG, "filter: "+query);

                mPresenter.onFilterTextChanged(query);
            }

            @Override
            public void onTypeChanged(int checkedId) {
                if(BuildConfig.DEBUG)
                    Log.d(TAG, "checkedId: "+checkedId);

                mPresenter.onFilterTypeChanged(checkedId);
            }
        });
        mFilterBox.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mFilterBoxHeight = mFilterBox.getMeasuredHeight();
                mFilterBox.setVisibility(View.GONE);
                mFilterBox.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });

        TextView filterSwitch = view.findViewById(R.id.filter_switch);
        filterSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFilterBox.getVisibility() == View.VISIBLE) {
                    FilterBoxAnimation.collapse(mFilterBox, mFilterBoxHeight);
                    InputUtils.hideKeyboard(mFilterBox);
                }
                else
                    FilterBoxAnimation.expand(mFilterBox, mFilterBoxHeight);
            }
        });

        // Set the adapter
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new OverviewAdapter(this);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void refreshData(List<Transaction> data) {
        mAdapter.setData(data);
    }

    @Override
    public void onStart() {
        super.onStart();

        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onListFragmentInteraction(Transaction item) {
        if(BuildConfig.DEBUG)
            Log.d(TAG, item.toString());

        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TRANSACTION_ID, item.mId);
        startActivity(intent);
    }

}
