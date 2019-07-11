package com.example.bitm_project_01dailyexpensenote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import static android.graphics.Bitmap.*;

public class DailyExpenseAdapter extends RecyclerView.Adapter<DailyExpenseAdapter.ViewHolder> implements PopupMenu.OnMenuItemClickListener {
    private long allTotal=0;
    private List<Expense> expenseList;
    private Context context;
    private ExpenseDataOpenHelper helper;






    public DailyExpenseAdapter(ExpenseDataOpenHelper helper, List<Expense> expenseList, Context context) {
        this.helper = helper;
        this.expenseList = expenseList;
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

        long totalAmount = Integer.valueOf(expense.getExpenseAmount());
        allTotal = allTotal + totalAmount;

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

//                                Toast.makeText(context, "Update item Clicked", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, UpdateExpenseActivity.class);
                                Bundle args = new Bundle();
                                args.putString("expName", expense.getExpenseName());
                                args.putInt("expAmount", expense.getExpenseAmount());
                                args.putString("expDate", expense.getDate());
                                args.putString("expTime", expense.getTime());

                                context.startActivity(intent);
                                return true;
                            case R.id.deleteItem:


                               //helper.deleteExpenseData(expenseList.get(position).getId());
                                expenseList.remove(holder.getAdapterPosition());

                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyDataSetChanged();


                                Toast.makeText(context, "Delete item Clicked", Toast.LENGTH_SHORT).show();
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
                args.putString("expName", expense.getExpenseName());
                args.putInt("expAmount", expense.getExpenseAmount());
                args.putString("expDate", expense.getDate());
                args.putString("expTime", expense.getTime());



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
