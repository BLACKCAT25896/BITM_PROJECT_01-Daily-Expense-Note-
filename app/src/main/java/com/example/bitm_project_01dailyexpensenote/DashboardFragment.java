package com.example.bitm_project_01dailyexpensenote;


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




    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);



        init(view);
//        Double totalAmount = 0.0;
//        for (int i = 0; i< expenseList.size(); i++){
//
//
//            totalAmount += expenseList.get(i).getExpenseAmount();
//        }
//
//        settotalAmount(view);

        return view;

    }

    private void settotalAmount(View view) {
        totalAmount.setText(String.valueOf(totalAmount));
    }

    private void init(View view) {
        expenseList = new ArrayList<>();
        totalAmount = view.findViewById(R.id.totalAmountTV);
        helper = new ExpenseDataOpenHelper(getContext());
        adapter = new DailyExpenseAdapter(helper,expenseList,getContext());

    }

}
