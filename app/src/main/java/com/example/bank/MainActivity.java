package com.example.bank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CardView customer;

    CardView transactionHistory;

    ProgressBar progressBar1, progressBar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customer = findViewById(R.id.customer);

        transactionHistory = findViewById(R.id.transactionHistory);
        progressBar1 = findViewById(R.id.progressBar3);
        progressBar2 = findViewById(R.id.progressBar2);

        progressBar1.setVisibility(View.GONE);
        progressBar2.setVisibility(View.GONE);


        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar1.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MainActivity.this, UsersList.class);

                startActivity(intent);
                progressBar1.setVisibility(View.GONE);
            }
        });



        transactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar2.setVisibility(View.VISIBLE);
              Intent intent = new Intent(MainActivity.this,TransactionHistory.class );
              startActivity(intent);
              progressBar2.setVisibility(View.GONE);
            }
        });


    }

}