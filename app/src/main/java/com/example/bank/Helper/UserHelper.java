package com.example.bank.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UserHelper extends SQLiteOpenHelper {

    String TABLE_NAME = UserConstant.UserEntry.TABLE_NAME;
    private static final String DATABASE_NAME = "User.db";
    private static final int DATABASE_VERSION = 1;


    public UserHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_USER_TABLE = " CREATE TABLE " + UserConstant.UserEntry.TABLE_NAME + " ( "
                +UserConstant.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " INTEGER, " +
                UserConstant.UserEntry.COLUMN_USER_NAME + " VARCHAR, " +
                UserConstant.UserEntry.COLUMN_USER_EMAIL + " VARCHAR, "+
                UserConstant.UserEntry.COLUMN_USER_IFSC_CODE + " VARCHAR, "+
                UserConstant.UserEntry.COLUMN_USER_PHONE_NO + " VARCHAR, " +
                UserConstant.UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);

        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(8282, 'Rehan Khan', 'rehnk007@gmail.com', 1160, 7061456744, 10000)" );
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(4040, 'Farhan Khan', 'fkhan@gmail.com', 1160, 9757787440, 15000)" );
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(6744, 'Farrukh Jawed', 'fjawed@gmail.com', 1160, 9757787420, 11000)" );
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(6724, 'Shahrukh Jawed', 'sjawed@gmail.com', 1211, 8757787420, 12000)" );
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(6824, 'Naqi', 'naqi@gmail.com', 1212, 6757787420, 9000)" );
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(6884, 'Kashif', 'kashif@gmail.com', 1212, 6787787420, 18000)" );
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(6804, 'Zeeshan', 'zeeshan@gmail.com', 1202, 678087420, 14000)" );
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values(6874, 'Kaushik', 'kaushikn@gmail.com', 1292, 608087420, 15000)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int olderVersion, int newVersion) {

        if(olderVersion != newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserConstant.UserEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
    public Cursor readAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + UserConstant.UserEntry.TABLE_NAME, null);
        return cursor;
    }

    public Cursor readParticularData(int accountNo) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + UserConstant.UserEntry.TABLE_NAME + " where "+
                UserConstant.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = "+ accountNo, null);

        return cursor;
    }

    public void updateAmount (int accountNo, int amount) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL( " update " + UserConstant.UserEntry.TABLE_NAME + " set " + UserConstant.UserEntry.COLUMN_USER_ACCOUNT_BALANCE
        + " = "+ amount + " where " + UserConstant.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = "+ accountNo);

    }
}
