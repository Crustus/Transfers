package cz.crusty.transfers.ui.overview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cz.crusty.transfers.R;
import cz.crusty.transfers.data.model.transaction.Transaction;
import cz.crusty.transfers.data.model.transaction.Type;

import java.util.List;

/**
 *
 */
public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> {

    private List<Transaction> mData;

    private final OnOverviewListInteractionListener mListener;

    public OverviewAdapter(OnOverviewListInteractionListener listener) {
        mListener = listener;
    }

    public void setData(List<Transaction> transactions) {
        mData = transactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_overview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Transaction transaction = mData.get(position);
        holder.mItem = transaction;
        if(transaction.mDirection == Type.INCOMING)
            holder.mIcon.setImageResource(R.drawable.arrow_circled_right);
        if(transaction.mDirection == Type.OUTGOING)
            holder.mIcon.setImageResource(R.drawable.arrow_circled_left);
        holder.mIdView.setText(transaction.mAmountInAccountCurrency+" Kc"); // TODO account currency
        holder.mContentView.setText(transaction.mDirection.toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIcon;
        public final TextView mIdView;
        public final TextView mContentView;
        public Transaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIcon = (ImageView) view.findViewById(R.id.icon);
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
