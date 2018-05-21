package com.appdesigndm.loc2loc.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appdesigndm.loc2loc.Callbacks.ItemClickListener;
import com.appdesigndm.loc2loc.Models.UserModel;
import com.appdesigndm.loc2loc.R;
import com.appdesigndm.loc2loc.viewHolder;

import java.util.List;


public class AdapterUserLink extends RecyclerView.Adapter<viewHolder> {

    List<UserModel> listUsers;

    public AdapterUserLink(List<UserModel> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View vista = inflater.inflate(R.layout.item_user_link, parent, false);
        return new viewHolder(vista);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.id_viewholder.setText(listUsers.get(position).getId());
        holder.name_viewholder.setText(listUsers.get(position).getName());
        holder.mail_viewholder.setText(listUsers.get(position).getEmail());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int postion, boolean isLongClick) {


                if (isLongClick) {
                    String valor = (listUsers.get(position).getEmail());
                    Toast.makeText(view.getContext(),valor, Toast.LENGTH_LONG).show();
                } else {
                    String valor = (listUsers.get(position).getName());
                    Toast.makeText(view.getContext(),valor, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }
}

