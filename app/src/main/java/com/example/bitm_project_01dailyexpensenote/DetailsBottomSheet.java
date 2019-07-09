package com.example.bitm_project_01dailyexpensenote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DetailsBottomSheet extends BottomSheetDialogFragment {
    private TextView type, amount,date,time;
    private Button showDoc;
    private ImageView showImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_bottom_sheet_layout,container,false);

        init(view);

        showData(view);






        return view;
    }

    private void showData(View view) {
        Bundle mArgs = getArguments();
        String expenseT = mArgs.getString("expName");
        String expenseA = Integer.toString(mArgs.getInt("expAmount"));
        String expenseD = mArgs.getString("expDate");
        String expenseTime = mArgs.getString("expTime");
        type.setText(expenseT);
        amount.setText(expenseA);
        date.setText(expenseD);
        time.setText(expenseTime);

    }


    private void init(View view) {
        type = view.findViewById(R.id.typeNameTv);
        amount = view.findViewById(R.id.amountTv);
        date = view.findViewById(R.id.dateTv);
        time = view.findViewById(R.id.timeTv);

    }
}
