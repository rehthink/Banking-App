package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.example.bank.Adapter.UserListAdapter;
import com.example.bank.Data.User;
import com.example.bank.Helper.UserConstant;
import com.example.bank.Helper.UserHelper;

import java.util.ArrayList;

public class UsersList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> userArrayList;

    private UserHelper userHelper;

    @Override
    protected void onCreate(Bundle   savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        userArrayList = new ArrayList<User>();
        userHelper = new UserHelper(this);
        displayDataBaseInfo();

        recyclerView = findViewById(R.id.all_users_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new UserListAdapter(this, userArrayList);
        recyclerView.setAdapter(myAdapter);

    }

    private void displayDataBaseInfo() {
        userArrayList.clear();

        Cursor cursor = new UserHelper(this).readAllData();
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
    }
}