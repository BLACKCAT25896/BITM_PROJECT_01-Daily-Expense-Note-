package com.example.bitm_project_01dailyexpensenote;


import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    private TextView totalAmount;
    private DailyExpenseAdapter adapter;
    private ExpenseDataOpenHelper helper;
    private List<Expense> expenseList;
    private String sum,temp;
    private int allTotal=0;





    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);



        init(view);

        showexpense();


        return view;

    }
    private void showexpense() {

        Cursor cursor = helper.showExpenseData();
        while (cursor.moveToNext()) {


            int expenseAmount = cursor.getInt(cursor.getColumnIndex(helper.COL_AMOUNT));

            allTotal = allTotal+expenseAmount;


            sum = String.valueOf(allTotal);


            totalAmount.setText(sum);


        }
    }

    private void init(View view) {
        expenseList = new ArrayList<>();
        totalAmount = view.findViewById(R.id.totalAmountTV);
        helper = new ExpenseDataOpenHelper(getContext());
        adapter = new DailyExpenseAdapter(helper,expenseList,getContext());

    }

}
