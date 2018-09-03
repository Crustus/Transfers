package cz.crusty.transfers.ui.overview;

import android.os.Bundle;

import cz.crusty.transfers.R;
import cz.crusty.transfers.base.BaseActivity;

public class OverviewActivity extends BaseActivity {

    private static final String TAG = OverviewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        addFragment(R.id.root, OverviewFragment.newInstance());

    }

}
