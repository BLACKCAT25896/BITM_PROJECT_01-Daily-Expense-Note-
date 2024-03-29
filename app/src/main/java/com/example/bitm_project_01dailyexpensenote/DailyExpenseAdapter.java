package com.example.bitm_project_01dailyexpensenote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.graphics.Bitmap.*;

public class DailyExpenseAdapter extends RecyclerView.Adapter<DailyExpenseAdapter.ViewHolder> implements PopupMenu.OnMenuItemClickListener, Filterable {
    private long allTotal = 0;
    private List<Expense> expenseList;
    private List<Expense> expenseListFull;
    private Context context;
    private ExpenseDataOpenHelper helper;


    public DailyExpenseAdapter(ExpenseDataOpenHelper helper, List<Expense> expenseList, Context context) {
        this.helper = helper;
        this.expenseList = expenseList;
        // expenseListFull = new ArrayList<>(expenseList);
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_layout, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Expense expense = expenseList.get(position);

        holder.expenseName.setText(expense.getExpenseName());
        holder.expenseAmount.setText(Double.toString(expense.getExpenseAmount()));



        holder.imageButtonmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //showPopUp(view);
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.updateItem:

                                Intent intent = new Intent(context, UpdateExpenseActivity.class);
                                Bundle bundle = new Bundle();

                                intent.putExtra("expName", expense.getExpenseName());
                                intent.putExtra("expAmount", expense.getExpenseAmount());
                                intent.putExtra("expDate", expense.getDate());
                                intent.putExtra("expTime", expense.getTime());
                                intent.putExtra("img", expense.getImage());

                                context.startActivity(intent);
                                return true;
                            case R.id.deleteItem:
                                helper = new ExpenseDataOpenHelper(context);


                                helper.deleteExpenseData(expense.getId());
                                expenseList.remove(position);

                                notifyItemRemoved(position);
                                notifyDataSetChanged();



                                return true;
                            default:


                        }

                        return false;
                    }
                });
                popupMenu.inflate(R.menu.popup);
                popupMenu.show();

                // return true;


            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();
                Intent intent = new Intent();
                args.putString("expName", expense.getExpenseName());
                args.putInt("expAmount", expense.getExpenseAmount());
                args.putString("expDate", expense.getDate());
                args.putString("expTime", expense.getTime());
                args.putString("img", expense.getImage());


                BottomSheetDialogFragment bottomSheet = new DetailsBottomSheet();


                bottomSheet.setArguments(args);

                bottomSheet.show(((FragmentActivity) context).getSupportFragmentManager(), bottomSheet.getTag());


            }
        });

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    @Override
    public Filter getFilter() {
        return expenseFilter;
    }

    private Filter expenseFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Expense> filterExpenseList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filterExpenseList.addAll(expenseList);

            } else {

                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Expense filter : expenseList) {

                    if (filter.getExpenseName().toLowerCase().contains(filterPattern)) {

                        filterExpenseList.add(filter);

                    }
                }


            }

            FilterResults results = new FilterResults();
            results.values = filterExpenseList;
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            expenseList.clear();
            expenseList.addAll((List) filterResults.values);
            notifyDataSetChanged();


        }
    };


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


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.updateItem:
                return true;
            case R.id.deleteItem:
                return true;
            default:
                return false;
        }
    }
}
