package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bank.Adapter.TransactionHistoryAdapter;
import com.example.bank.Data.Transaction;
import com.example.bank.Data.User;
import com.example.bank.Helper.TransactionConstant;
import com.example.bank.Helper.TransactionHelper;
import com.example.bank.Helper.UserConstant;

import java.util.ArrayList;

public class TransactionHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Transaction> transactionArrayList;

    private TransactionHelper transactionHelper;
    TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        emptyList = findViewById(R.id.empty_text);

        transactionArrayList = new ArrayList<Transaction>();
        transactionHelper = new TransactionHelper(this);

        displayDbInfo();

        recyclerView = findViewById(R.id.transactionsList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TransactionHistoryAdapter(this, transactionArrayList);
        recyclerView.setAdapter(adapter);

    }

    private void displayDbInfo() {
        SQLiteDatabase sqLiteDatabase = transactionHelper.getReadableDatabase();

        String[] projection = {
                TransactionConstant.TransactionEntry.COLUMN_FROM_NAME,
                TransactionConstant.TransactionEntry.COLUMN_TO_NAME,
                TransactionConstant.TransactionEntry.COLUMN_AMOUNT,
                TransactionConstant.TransactionEntry.COLUMN_STATUS
        };

        Cursor cursor = sqLiteDatabase.query(
                TransactionConstant.TransactionEntry.TABLE_NAME ,
                projection, null,null,null,null,null);

        try {
            int fromNameColumnIndex = cursor.getColumnIndex(TransactionConstant.TransactionEntry.COLUMN_FROM_NAME);
            int toNameColumnIndex = cursor.getColumnIndex(TransactionConstant.TransactionEntry.COLUMN_TO_NAME);
            int amountColumnIndex = cursor.getColumnIndex(TransactionConstant.TransactionEntry.COLUMN_AMOUNT);
            int statusColumnIndex = cursor.getColumnIndex(TransactionConstant.TransactionEntry.COLUMN_STATUS);

            while(cursor.moveToNext()) {
                String fromName = cursor.getString(fromNameColumnIndex);
                String toName = cursor.getString(toNameColumnIndex);
                int amount = cursor.getInt(amountColumnIndex);
                int status = cursor.getInt(statusColumnIndex);

                transactionArrayList.add(new Transaction( fromName, toName, amount, status));
            }
        } finally {
            cursor.close();
        }

        if (transactionArrayList.isEmpty()) {
            emptyList.setVisibility(View.VISIBLE);
        }
        else {
            emptyList.setVisibility(View.GONE);
        }



    }
}