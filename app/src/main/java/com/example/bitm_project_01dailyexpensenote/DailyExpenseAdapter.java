package com.example.bitm_project_01dailyexpensenote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class DailyExpenseAdapter extends RecyclerView.Adapter<DailyExpenseAdapter.ViewHolder> {


    private ExpenseDataOpenHelper helper;
    private List<Expense> expenseList;
    private Context context;



    public DailyExpenseAdapter(ExpenseDataOpenHelper helper, List<Expense> expenseList, Context context) {
        this.helper = helper;
        this.expenseList = expenseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Expense expense = expenseList.get(position);
        holder.expenseName.setText(expense.getExpenseName());
        holder.imageButtonmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
       holder.expenseAmount.setText(Double.toString(expense.getExpenseAmount()));
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               BottomSheetDialogFragment bottomSheetDialogFragment = new DetailsBottomSheet();

              bottomSheetDialogFragment.show(bottomSheetDialogFragment.getFragmentManager(),"Details");

           }
       });

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView expenseName, expenseAmount;
        private ImageButton imageButtonmenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            expenseName = itemView.findViewById(R.id.expenseName);
            expenseAmount = itemView.findViewById(R.id.expenseAmount);
            imageButtonmenu = itemView.findViewById(R.id.editOrUpdateMenu);
        }
    }
}
