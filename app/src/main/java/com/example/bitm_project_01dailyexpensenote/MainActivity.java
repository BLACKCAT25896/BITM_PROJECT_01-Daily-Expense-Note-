package com.example.bitm_project_01dailyexpensenote;

import android.app.DatePickerDialog;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Spinner spinner;
    private TextView fromDate, toDate;
    private ImageButton fromBtn, toBtn;
    private DialogFragment datePicker;
    private DailyExpenseAdapter adapter;
    private ExpenseDataOpenHelper helper;
    private List<Expense>expenseList;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    replaceFragment(new DashboardFragment());


                    return true;
                case R.id.navigation_expense:
                    replaceFragment(new ExpenseFragment());

                    return true;

            }
            return false;
        }
    };

    public void replaceFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(new DashboardFragment());
        setTitle("Daily Expense");


        init();
        initSpinner();

        todaydate();


        fromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "From Date");


            }
        });


        toBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "To Date Date");

            }
        });


    }

    private void todaydate() {
        Calendar calendar = Calendar.getInstance();

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        toDate.setText(currentDateString);
    }

    private void initSpinner() {
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.expensetype, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        adapter.getFilter();

    }

    private void init() {
        fromDate = findViewById(R.id.fromDateTV);
        toDate = findViewById(R.id.toDateTV);
        fromBtn = findViewById(R.id.fromDateBtn);
        toBtn = findViewById(R.id.toDateBtn);
        datePicker = new DatePickerFragment();
        expenseList = new ArrayList<>();
        helper = new ExpenseDataOpenHelper(this);
        adapter = new DailyExpenseAdapter(helper,expenseList,this);


    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        fromDate.setText(currentDateString);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchTtem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchTtem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                adapter.getFilter().filter(s);



                return false;
            }
        });
        return true;
    }
}
