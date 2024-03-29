package com.example.bitm_project_01dailyexpensenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ExpenseDataOpenHelper extends SQLiteOpenHelper {


    public static String DATABASE_NAME = "Expense.db";
    public static String TABLE_NAME = "Expense";
    public static String COL_ID = "Id";
    public static String COL_Type = "Type";
    public static String COL_AMOUNT = "Amount";
    public static String COL_DATE = "Date";
    public static String COL_TIME = "Time";
    public static String COL_DOCUMENT = "Document";
    public String CREATE_TABLE = "create table "+TABLE_NAME+" ("+COL_ID+" integer primary key, "+COL_Type+" text not null, "+COL_AMOUNT+" int not null, "+COL_DATE+" text not null, "+COL_TIME+" text , "+COL_DOCUMENT+" text)";
    public static int VERSION = 50;


    public ExpenseDataOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
         sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(sqLiteDatabase);

    }


    public long insertExpenseData(String type, int amount, String date, String time, String document) {

        ContentValues contV = new ContentValues();
        contV.put(COL_Type, type);
        contV.put(COL_AMOUNT, amount);
        contV.put(COL_DATE, date);
        contV.put(COL_TIME, time);
        contV.put(COL_DOCUMENT,document);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long id = sqLiteDatabase.insert(TABLE_NAME, null, contV);
        sqLiteDatabase.close();
        return id;

    }
    public Cursor showExpenseData(){

        String show_all = "select * from " + TABLE_NAME;

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(show_all,null);
        return cursor;
    }

    public void deleteExpenseData(int data){
       getWritableDatabase().delete(TABLE_NAME,"ID=?", new String[]{String.valueOf(data)});

    }
    public boolean updateExpenseData(String type, int amount, String date, String time, String document){
        ContentValues contV = new ContentValues();
        contV.put(COL_Type, type);
        contV.put(COL_AMOUNT, amount);
        contV.put(COL_DATE, date);
        contV.put(COL_TIME, time);
        contV.put(COL_DOCUMENT,document);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_NAME,contV,"ID=?", new String[]{time});
        return true;
    }









}
