package com.dtu.capstone2.ereading.ui.account.history;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.response.HistoryNewFeed;

import java.util.List;

/**
 * Create by Huynh Vu Ha Lan on 06/05/2019
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.RecyclerViewHolder> {
    private List<HistoryNewFeed> mArrContact;

    HistoryAdapter(List<HistoryNewFeed> data) {
        mArrContact = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_fragment_history, viewGroup, false);
        return new HistoryAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.onBindData(mArrContact.get(i));
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

        void onBindData(HistoryNewFeed listHistory) {
            mTxtTitle.setText((getAdapterPosition() + 1) + ". " + listHistory.getTitleNewsFeed());
            mTxtIntroduction.setText(listHistory.getIntroduction());
        }
    }
}
