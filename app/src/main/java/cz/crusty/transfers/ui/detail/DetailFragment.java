package cz.crusty.transfers.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cz.crusty.transfers.R;
import cz.crusty.transfers.data.model.transaction.Transaction;
import cz.crusty.transfers.data.model.transaction.TransactionDetail;
import cz.crusty.transfers.data.model.transaction.Type;
import cz.crusty.transfers.base.BaseFragment;

/**
 *
 */
public class DetailFragment extends BaseFragment
        implements DetailContract.View {

    private static final String TAG = DetailFragment.class.getSimpleName();

    private static final String ARG_TRANSACTION_ID = "transaction_id";

    private DetailPresenter mPresenter;

    private ImageView mIcon;
    private TextView mAmount;
    private TextView mDirection;
    private TextView mAccountNumber;
    private TextView mAccountName;
    private TextView mBankCode;


    public static DetailFragment newInstance(long transactionId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putLong(DetailFragment.ARG_TRANSACTION_ID, transactionId);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long transactionId = -1;
        Bundle args = getArguments();
        if (args != null) {
            transactionId = args.getLong(ARG_TRANSACTION_ID);
        }

        mPresenter = new DetailPresenter(this, transactionId);
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        Context context = view.getContext();

        mIcon = view.findViewById(R.id.icon);
        mAmount = view.findViewById(R.id.amount);
        mDirection = view.findViewById(R.id.direction);
        mAccountNumber = view.findViewById(R.id.account_number_value);
        mAccountName = view.findViewById(R.id.account_name_value);
        mBankCode = view.findViewById(R.id.bank_code_value);

        return view;
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
    public void refreshData(Transaction transaction) {
        if(transaction.mDirection == Type.INCOMING)
            mIcon.setImageResource(R.drawable.arrow_circled_right);
        if(transaction.mDirection == Type.OUTGOING)
            mIcon.setImageResource(R.drawable.arrow_circled_left);
        mAmount.setText(transaction.mAmountInAccountCurrency+" Kc"); // TODO account currency
        mDirection.setText(transaction.mDirection.toString());

        TransactionDetail detail = transaction.getDetail();
        mAccountNumber.setText(""+detail.mAccountNumber);
        mAccountName.setText(detail.mAccountName);
        mBankCode.setText(""+detail.mBankCode);
    }

}
