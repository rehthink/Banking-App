package com.example.bank.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TransactionHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "transaction.db";
    private static final int DATABASE_VERSION =  1;

    public TransactionHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TRANSACTION_TABLE = " CREATE TABLE " + TransactionConstant.TransactionEntry.TABLE_NAME + " ( "
                + TransactionConstant.TransactionEntry.COLUMN_FROM_NAME + " VARCHAR, "
                + TransactionConstant.TransactionEntry.COLUMN_TO_NAME + " VARCHAR, "
                + TransactionConstant.TransactionEntry.COLUMN_AMOUNT + " INTEGER, "
                + TransactionConstant.TransactionEntry.COLUMN_STATUS + " INTEGER); ";

        sqLiteDatabase.execSQL(SQL_CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int olderVersion, int newVersion) {

        if(olderVersion != newVersion) {
            sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TransactionConstant.TransactionEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }

    public boolean transferMoney (String fromName, String toName, String amount, int status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TransactionConstant.TransactionEntry.COLUMN_FROM_NAME, fromName);
        contentValues.put(TransactionConstant.TransactionEntry.COLUMN_TO_NAME, toName);
        contentValues.put(TransactionConstant.TransactionEntry.COLUMN_AMOUNT, amount);
        contentValues.put(TransactionConstant.TransactionEntry.COLUMN_STATUS, status);

        Long result = sqLiteDatabase.insert(TransactionConstant.TransactionEntry.TABLE_NAME, null, contentValues);

        if(result == -1) {
            return  false;
        }
        else {
            return  true;
        }
    }

}
