package com.example.bank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserData extends AppCompatActivity {

    TextView name, email, accountNo, balance, ifscCode, phoneNo;
    Button transferMoney;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        accountNo = findViewById(R.id.account_no);
        balance = findViewById(R.id.balance);
        ifscCode = findViewById(R.id.ifsc_id);
        phoneNo = findViewById(R.id.phone_no);
        transferMoney = findViewById(R.id.transfer_money);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null ) {

            name.setText(extras.getString("NAME"));
            accountNo.setText(String.valueOf(extras.getInt("ACCOUNT_NO")));
            email.setText(extras.getString("EMAIL"));
            phoneNo.setText(extras.getString("PHONE_NO"));
            ifscCode.setText(extras.getString("IFSC_CODE"));
            balance.setText(String.valueOf(extras.getInt("BALANCE")));

        }
        else {


        }

        transferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterAmount();
            }
        });
    }

    private void enterAmount() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserData.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog, null);
        mBuilder.setTitle("Enter Amount").setView(mView).setCancelable(false);

        final EditText mAmount = mView.findViewById(R.id.enterMoney);
        mBuilder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(UserData.this, sendToUser.class);
                startActivity(intent);
                
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                alertDialog.dismiss();
                transactionCancel();
            }
        });
        
        alertDialog = mBuilder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    int currentBalance = Integer.parseInt(String.valueOf(balance.getText()));
                    if (mAmount.getText().toString().isEmpty()) {
                        mAmount.setError("Please enter amount!");
                    } else if (Integer.parseInt(mAmount.getText().toString()) > currentBalance) {

                        mAmount.setError("Your Account don't have enough balance!");
                    } else {
                        Intent intent = new Intent(UserData.this, sendToUser.class);

                        intent.putExtra("FROM_USER_ACCOUNT_NO", Integer.parseInt(accountNo.getText().toString()));
                        intent.putExtra("FROM_USER_NAME", name.getText());
                        intent.putExtra("FROM_USER_ACCOUNT_BALANCE", balance.getText());
                        intent.putExtra("TRANSFER_AMOUNT", Integer.parseInt(mAmount.getText().toString()));

                        startActivity(intent);
                        finish();

                    }

            }
        });
        
        
    }

    private void transactionCancel() {
        AlertDialog.Builder exit_btn = new AlertDialog.Builder(UserData.this);
        exit_btn.setTitle("Do you want to cancel the Transaction?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(UserData.this,"Transaction Cancelled!",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                enterAmount();
            }
        });

        AlertDialog alertExit = exit_btn.create();
        alertExit.show();
    }
}