package com.dtu.capstone2.ereading.ui.favorite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.listFavorite;
import com.dtu.capstone2.ereading.ui.newfeed.PageNewFeedAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Vo The Doan on 04/30/2019
 */
public class CustomListFavorite extends RecyclerView.Adapter<CustomListFavorite.RecyclerViewHolder>{
    private Context mcontext;
    private List<listFavorite> mArrContact;

    public CustomListFavorite(List<listFavorite> data ,Context context) {
        mArrContact = data;
        mcontext = context;
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
        holder.txtWord.setText((position+1)+". "+contact.getStrWord());
        holder.txtNgia.setText(contact.getStrMeanShort());
    }

    @Override
    public int getItemCount() {
        return mArrContact.size();
    }
    public class RecyclerViewHolder  extends RecyclerView.ViewHolder {
        TextView txtWord, txtNgia;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtWord = (TextView) itemView.findViewById(R.id.tvWord);
            txtNgia =(TextView) itemView.findViewById(R.id.tvNghia);
        }
    }
}
