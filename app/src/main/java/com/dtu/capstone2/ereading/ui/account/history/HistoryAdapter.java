package com.dtu.capstone2.ereading.ui.account.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.response.ListHistory;
import java.util.List;

/**
 * Create by Huynh Vu Ha Lan on 06/05/2019
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.RecyclerViewHolder> {

    private List<ListHistory> mArrContact;

    HistoryAdapter(List<ListHistory> data) {
        mArrContact = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_fragment_history, parent, false);
        return new HistoryAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.RecyclerViewHolder holder, int position) {
        ListHistory contact = mArrContact.get(position);
        holder.mTxtTitle.setText((position + 1) + ". " + contact.getTitleNewsFeed());
        holder.mTxtIntroduction.setText(contact.getIntroduction());
    }

    @Override
    public int getItemCount() {
        return mArrContact.size();
    }

    /**
     * This class is view holder of this adapter
     */
    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtTitle;
        private TextView mTxtIntroduction;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            mTxtTitle = itemView.findViewById(R.id.txt_history_title);
            mTxtIntroduction = itemView.findViewById(R.id.txt_history_introduction);
        }
    }
}
