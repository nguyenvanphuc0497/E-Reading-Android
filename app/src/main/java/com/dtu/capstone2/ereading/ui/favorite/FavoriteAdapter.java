package com.dtu.capstone2.ereading.ui.favorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.listFavorite;

import java.util.List;

/**
 * Create by Vo The Doan on 04/30/2019
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.RecyclerViewHolder> {
    private List<listFavorite> mArrContact;

    FavoriteAdapter(List<listFavorite> data) {
        mArrContact = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_favorite, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        listFavorite contact = mArrContact.get(position);
        holder.tvWord.setText((position + 1) + ". " + contact.getStrWord());
        holder.tvMeanShort.setText(contact.getStrMeanShort());
    }

    @Override
    public int getItemCount() {
        return mArrContact.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvWord, tvMeanShort;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tvWord);
            tvMeanShort = itemView.findViewById(R.id.tvNghia);
        }
    }
}
