package com.dtu.capstone2.ereading.ui.account.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.response.HistoryNewFeed;
import com.dtu.capstone2.ereading.ui.utils.BaseDIffUtilCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Create by Huynh Vu Ha Lan on 06/05/2019
 */
public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<HistoryNewFeed> mArrContact;

    HistoryAdapter(List<HistoryNewFeed> data) {
        mArrContact = data;
    }

    void onNewData(List<HistoryNewFeed> newData, RecyclerView recyclerView) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new BaseDIffUtilCallBack(newData, mArrContact));
        diffResult.dispatchUpdatesTo(this);
        this.mArrContact.clear();
        this.mArrContact.addAll(newData);
        recyclerView.scrollToPosition(0);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_history, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NotNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                if (key.equals("introduction")) {
                    Toast.makeText(holder.itemView.getContext(), "HistoryNewFeed " + position + " : introduction Changed", Toast.LENGTH_SHORT).show();
                }
                if (key.equals("timeCreate")) {
                    Toast.makeText(holder.itemView.getContext(), "HistoryNewFeed " + position + " : timeCreate Changed", Toast.LENGTH_SHORT).show();
                }
                if (key.equals("titleNewsFeed")) {
                    Toast.makeText(holder.itemView.getContext(), "HistoryNewFeed " + position + " : titleNewsFeed Changed", Toast.LENGTH_SHORT).show();
                }
                if (holder instanceof ItemViewHolder) {
                    populateItemRows((ItemViewHolder) holder, position);
                } else if (holder instanceof LoadingViewHolder) {
                    showLoadingView((LoadingViewHolder) holder, position);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mArrContact == null ? 0 : mArrContact.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mArrContact.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    /**
     * This class is view holder of this adapter
     */
    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtTitle;
        private TextView mTxtIntroduction;
        private TextView mTxtTime;

        private ItemViewHolder(View itemView) {
            super(itemView);
            mTxtTitle = itemView.findViewById(R.id.txt_history_title);
            mTxtIntroduction = itemView.findViewById(R.id.txt_history_introduction);
            mTxtTime = itemView.findViewById(R.id.txt_history_time);
        }

        private void onBindData(HistoryNewFeed historyNewFeed) {
            mTxtTitle.setText((getAdapterPosition() + 1) + ". " + historyNewFeed.getTitleNewsFeed());
            mTxtIntroduction.setText(historyNewFeed.getIntroduction());
            mTxtTime.setText(historyNewFeed.getTimeCreate());
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {
        viewHolder.onBindData(mArrContact.get(position));
    }
}
