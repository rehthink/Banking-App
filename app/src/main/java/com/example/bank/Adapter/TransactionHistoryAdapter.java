package com.example.bank.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bank.Data.Transaction;
import com.example.bank.R;
import java.util.ArrayList;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {

    private ArrayList<Transaction> transactionArrayList;
    public TransactionHistoryAdapter(Context context, ArrayList<Transaction> list) {
        transactionArrayList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_history_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(transactionArrayList.get(position));
        holder.fromName.setText(transactionArrayList.get(position).getFromUser());
        holder.toName.setText(transactionArrayList.get(position).getToUser());
        holder.amountTransferred.setText(String.format("%d",transactionArrayList.get(position).getAmountTransferred()));

        if(transactionArrayList.get(position).getStatus() == 1) {
            String s = "#3EB9DF";
            holder.cardView.setCardBackgroundColor(Color.parseColor("#246827"));
        }
        else{
            holder.cardView.setCardBackgroundColor(Color.argb(100,239,100,100));
        }

    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fromName, toName, amountTransferred, date, time;
        CardView cardView;
        LinearLayout toUserInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fromName = itemView.findViewById(R.id.from_name);
            toName = itemView.findViewById(R.id.to_name);
            amountTransferred = itemView.findViewById(R.id.t_amount);
            date = itemView.findViewById(R.id.t_date);
            time = itemView.findViewById(R.id.t_time);
            cardView = itemView.findViewById(R.id.transaction_card_view);
            toUserInfo = itemView.findViewById(R.id.to_user_info);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }
    }
}
