package com.example.bank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bank.Adapter.SendToUserAdapter;
import com.example.bank.Data.User;
import com.example.bank.Helper.TransactionConstant;
import com.example.bank.Helper.TransactionHelper;
import com.example.bank.Helper.UserConstant;
import com.example.bank.Helper.UserHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class sendToUser extends AppCompatActivity implements SendToUserAdapter.OnUserListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> userArrayList;

    private UserHelper userHelper;
    String date= null, time = null;
    String fromUserName, toName, fromUserAccountBalance, transferAmount;
    int fromUserAccountNo, toUserAccountNo, toAccountBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_user);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy, hh:mm a");
        String date_and_time = simpleDateFormat.format(calendar.getTime());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null ){
            fromUserName = bundle.getString("FROM_USER_NAME");
            fromUserAccountNo = bundle.getInt("FROM_USER_ACCOUNT_NO");
            fromUserAccountBalance = bundle.getString("FROM_USER_ACCOUNT_BALANCE");
            transferAmount = bundle.getString("TRANSFER_AMOUNT");

        }

        userArrayList = new ArrayList<User>();
        userHelper = new UserHelper(this);
        recyclerView = findViewById(R.id.send_to_user_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SendToUserAdapter(userArrayList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(sendToUser.this);
        builder.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TransactionHelper transactionHelper = new TransactionHelper(getApplicationContext());
                        SQLiteDatabase sqLiteDatabase = transactionHelper.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();

                        contentValues.put(TransactionConstant.TransactionEntry.COLUMN_FROM_NAME, fromUserName);
                        contentValues.put(TransactionConstant.TransactionEntry.COLUMN_TO_NAME, toName);
                        contentValues.put(TransactionConstant.TransactionEntry.COLUMN_AMOUNT, transferAmount);
                        contentValues.put(TransactionConstant.TransactionEntry.COLUMN_STATUS, 0);

                        sqLiteDatabase.insert(TransactionConstant.TransactionEntry.TABLE_NAME, null, contentValues);
                        Toast.makeText(sendToUser.this, "Transaction Cancelled!", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(sendToUser.this, UsersList.class));
                        finish();



                    }
                }).setNegativeButton("No", null);
        AlertDialog alertExit = builder.create();
        alertExit.show();


    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDbInfo();
    }

    private void displayDbInfo() {

        SQLiteDatabase sqLiteDatabase = userHelper.getReadableDatabase();

        String[] projection = {
                        UserConstant.UserEntry.COLUMN_USER_ACCOUNT_NUMBER,
                        UserConstant.UserEntry.COLUMN_USER_NAME,
                        UserConstant.UserEntry.COLUMN_USER_EMAIL,
                        UserConstant.UserEntry.COLUMN_USER_IFSC_CODE,
                        UserConstant.UserEntry.COLUMN_USER_PHONE_NO,
                        UserConstant.UserEntry.COLUMN_USER_ACCOUNT_BALANCE,
        };

        Cursor cursor = sqLiteDatabase.query(
                UserConstant.UserEntry.TABLE_NAME,
                projection, null,null,null,null,null
        );

        try {
            int phoneColumnIndex = cursor.getColumnIndex(UserConstant.UserEntry.COLUMN_USER_PHONE_NO);
            int emailColumnIndex = cursor.getColumnIndex(UserConstant.UserEntry.COLUMN_USER_EMAIL);
            int ifscColumnIndex = cursor.getColumnIndex(UserConstant.UserEntry.COLUMN_USER_IFSC_CODE);
            int accountNumberColumnIndex = cursor.getColumnIndex(UserConstant.UserEntry.COLUMN_USER_ACCOUNT_NUMBER);
            int nameColumnIndex = cursor.getColumnIndex(UserConstant.UserEntry.COLUMN_USER_NAME);
            int accountBalColumnIndex = cursor.getColumnIndex(UserConstant.UserEntry.COLUMN_USER_ACCOUNT_BALANCE);

            while(cursor.moveToNext()) {
                String currentName = cursor.getString(nameColumnIndex);
                int accountNumber = cursor.getInt(accountNumberColumnIndex);
                String email = cursor.getString(emailColumnIndex);
                String phone = cursor.getString(phoneColumnIndex);
                String ifscCode = cursor.getString(ifscColumnIndex);
                int accountBalance = cursor.getInt(accountBalColumnIndex);

                userArrayList.add(new User( currentName, phone,ifscCode, email, accountNumber, accountBalance));
            }
        } finally {
            cursor.close();
        }
    }


    @Override
    public void onUserClick(int position) {
        toUserAccountNo = userArrayList.get(position).getAccountNumber();
        toName = userArrayList.get(position).getName();
        toAccountBalance = userArrayList.get(position).getBalance();

        calculateAmount();


        new TransactionHelper(this).transferMoney(fromUserName, toName, transferAmount,1 );

        Toast.makeText(this, "Transaction Successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(sendToUser.this, MainActivity.class));
        finish();


    }

    private void calculateAmount() {
        try{
            Integer currentAmount = Integer.parseInt(fromUserAccountBalance);
            Integer transferAmountInt = Integer.parseInt(transferAmount);
            Integer remainingAmount = currentAmount - transferAmountInt;
            Integer increasedAmount = transferAmountInt + toAccountBalance;

            new UserHelper(this).updateAmount(fromUserAccountNo, remainingAmount);
            new UserHelper(this).updateAmount(toUserAccountNo, increasedAmount);
        }catch (NumberFormatException exception){

        }



    }
}