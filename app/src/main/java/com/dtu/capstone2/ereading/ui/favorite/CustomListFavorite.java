package com.dtu.capstone2.ereading.ui.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.listFavorite;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Vo The Doan on 04/30/2019
 */
public class CustomListFavorite extends ArrayAdapter<listFavorite> {
    private Context context;
    private int resource;
    private List<listFavorite> arrContact;

    public CustomListFavorite(Context context, int resource, ArrayList<listFavorite> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_favorite, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvword = (TextView) convertView.findViewById(R.id.tvWord);
            viewHolder.tvnghia = (TextView) convertView.findViewById(R.id.tvNghia);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        listFavorite contact = arrContact.get(position);
        viewHolder.tvword.setText(contact.getStrWord());
        viewHolder.tvnghia.setText(contact.getStrMeanShort());
        return convertView;
    }

    public class ViewHolder {
        TextView tvword, tvnghia;
    }
}
