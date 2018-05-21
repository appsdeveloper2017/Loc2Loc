package com.appdesigndm.loc2loc;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.appdesigndm.loc2loc.Callbacks.ItemClickListener;

public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView id_viewholder;
    public TextView name_viewholder;
    public TextView mail_viewholder;

    private ItemClickListener itemClickListener;


    public viewHolder(View itemView) {
        super(itemView);
        id_viewholder = (TextView) itemView.findViewById(R.id.item_id);
        name_viewholder = (TextView)itemView.findViewById(R.id.item_name);
        mail_viewholder = (TextView) itemView.findViewById(R.id.item_mail);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}

