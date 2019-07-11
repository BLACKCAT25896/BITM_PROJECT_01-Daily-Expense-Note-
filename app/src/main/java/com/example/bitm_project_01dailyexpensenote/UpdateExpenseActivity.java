package com.example.bitm_project_01dailyexpensenote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class UpdateExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText expenseAmount, expenseDate, expenseTime;
    private Spinner spinner;
    private ImageButton date, time;
    private Button addExp,addDcBtn;
    private String type, amount, eDate, eTime,document;
    private ExpenseDataOpenHelper helper;
    private DailyExpenseAdapter adapter;
    private List<Expense> expenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense);
        setTitle("Update Expense");

        init();
        initSpinner();


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "From Date");

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });

        addDcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(UpdateExpenseActivity.this, view);


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.camera:

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent,0);


                                Toast.makeText(UpdateExpenseActivity.this, "Camera", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.gallery:

                                Intent intent1 = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent1,1);


                                Toast.makeText(UpdateExpenseActivity.this, "Gallery", Toast.LENGTH_SHORT).show();
                                return true;
                            default:




                        }




                        return false;
                    }
                });

                popupMenu.inflate(R.menu.document);
                popupMenu.show();

            }
        });


        Intent intent = getIntent();

        String expType = intent.getStringExtra("expName");
        String expAmount = intent.getStringExtra("expAmount");
        String expDate = intent.getStringExtra("expDate");
        String expTime = intent.getStringExtra("expTime");
        byte[] image = intent.getByteArrayExtra("img");

        spinner.setTag(expType);
        expenseAmount.setText(expAmount);
        expenseDate.setText(expDate);
        expenseTime.setText(expTime);










        addExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                type = spinner.getSelectedItem().toString();
                amount = expenseAmount.getText().toString();
                eDate = expenseDate.getText().toString();
                eTime = expenseTime.getText().toString();

                //helper.insertExpenseData(type, Integer.parseInt(String.valueOf(amount)), eDate, eTime,null);


                Toast.makeText(UpdateExpenseActivity.this, "Data updated to Database", Toast.LENGTH_SHORT).show();



                startActivity(new Intent(UpdateExpenseActivity.this, ExpenseFragment.class));


            }
        });


    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.expensetype,R.layout.support_simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void init() {
        expenseDate = findViewById(R.id.expenseDate);
        date = findViewById(R.id.date);
        time = findViewById(R.id.timeIV);
        expenseTime = findViewById(R.id.timeET);
        expenseAmount = findViewById(R.id.expenseAET);
        addExp = findViewById(R.id.addExpenseBtn);
        helper = new ExpenseDataOpenHelper(this);
        adapter = new DailyExpenseAdapter(helper, expenseList, this);
        spinner = findViewById(R.id.spinnerView);
        addDcBtn = findViewById(R.id.addDocBtn);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        expenseDate.setText(currentDateString);

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        expenseTime.setText("Hours: " + hour + "  Minutes: " + minute);

    }
}



