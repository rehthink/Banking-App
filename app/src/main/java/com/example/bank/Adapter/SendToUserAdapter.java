package com.example.bank.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bank.Data.User;
import com.example.bank.R;

import java.util.ArrayList;

public class SendToUserAdapter extends RecyclerView.Adapter<SendToUserAdapter.ViewHolder> {
    private ArrayList<User> userArrayList;
    private  OnUserListener onUserListener;

    public SendToUserAdapter(ArrayList<User> userArrayList, OnUserListener onUserListener) {
        this.onUserListener = onUserListener;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new SendToUserAdapter.ViewHolder(view, onUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setTag(userArrayList.get(position));
        holder.userName.setText(userArrayList.get(position).getName());
        holder.userAccountBalance.setText(String.format("%d" ,userArrayList.get(position).getBalance()));

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userName, userAccountBalance;
        OnUserListener onUserListener;

        public ViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);
            userAccountBalance =itemView.findViewById(R.id.amount);
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onUserListener.onUserClick(getAdapterPosition());
        }
    }

    public interface OnUserListener {
        void onUserClick(int position);
    }
}
