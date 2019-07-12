package com.example.bitm_project_01dailyexpensenote;

import android.widget.ImageView;

public class Expense {
    private int id;
    private String expenseName;
    private int expenseAmount;
    private String date;
    private String time;
    private String image;

    public Expense(int id, String expenseName, int expenseAmount, String date, String time, String image) {
        this.id = id;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.date = date;
        this.time = time;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public int getExpenseAmount() {
        return expenseAmount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }
}


