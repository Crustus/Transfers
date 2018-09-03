package cz.crusty.transfers.ui.detail;

import android.content.Intent;
import android.os.Bundle;

import cz.crusty.transfers.R;
import cz.crusty.transfers.base.BaseActivity;

public class DetailActivity extends BaseActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_TRANSACTION_ID = "transaction_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        long transactionId = -1;
        transactionId = intent.getLongExtra(EXTRA_TRANSACTION_ID, transactionId);

        addFragment(R.id.root, DetailFragment.newInstance(transactionId));

    }

}
