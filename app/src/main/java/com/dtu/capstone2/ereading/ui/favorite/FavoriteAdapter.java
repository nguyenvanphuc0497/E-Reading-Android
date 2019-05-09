package com.dtu.capstone2.ereading.ui.favorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.Favorite;

import java.util.List;

/**
 * Create by Vo The Doan on 04/30/2019
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.RecyclerViewHolder> {
    private List<Favorite> mArrContact;
    private OnItemListener listener;

    FavoriteAdapter(List<Favorite> data) {
        mArrContact = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_favorite, parent, false);
        return new RecyclerViewHolder(view);
    }

    void setItemDeleteOnClickListener(OnItemListener onItemListener) {
        listener = onItemListener;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.onBindData(mArrContact.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrContact.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWord, tvMeanShort, tvType;
        private ImageView imgDelete;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tvWord);
            tvMeanShort = itemView.findViewById(R.id.tvNghia);
            imgDelete = itemView.findViewById(R.id.imgdeleteitem);
            tvType = itemView.findViewById(R.id.tvtype);
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }

        void onBindData(Favorite favorite) {
            tvWord.setText((getAdapterPosition() + 1) + ". " + favorite.getStrWord());
            tvMeanShort.setText(favorite.getStrMeanShort());
            tvType.setText(favorite.getStrType());
        }
    }

    interface OnItemListener {
        void onItemClick(int position);
    }
}
