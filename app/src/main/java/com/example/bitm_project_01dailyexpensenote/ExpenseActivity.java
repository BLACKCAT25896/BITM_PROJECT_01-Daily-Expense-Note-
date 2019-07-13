package com.example.bitm_project_01dailyexpensenote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText expenseAmount, expenseDate, expenseTime;
    private Spinner spinner;
    private ImageButton date, time;
    private Button addExp, addDocBtn;
    private String type, amount, eDate, eTime,document,image;
    private ExpenseDataOpenHelper helper;
    private DailyExpenseAdapter adapter;
    private List<Expense> expenseList;
    private ImageView openDoc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        setTitle("Add Expense");


        init();
        initSpinner();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "From Date");

            }
        });
        expenseDate.setOnClickListener(new View.OnClickListener() {
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

        expenseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });

        addDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(ExpenseActivity.this, view);


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.camera:

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent,0);


                                Toast.makeText(ExpenseActivity.this, "Camera", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.gallery:

                                Intent intent1 = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent1,1);


                                Toast.makeText(ExpenseActivity.this, "Gallery", Toast.LENGTH_SHORT).show();
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
        


        addExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type = spinner.getSelectedItem().toString();
                amount = expenseAmount.getText().toString();
                eDate = expenseDate.getText().toString();
                eTime = expenseTime.getText().toString();
                document = image;






                helper.insertExpenseData(type, Integer.parseInt(String.valueOf(amount)), eDate, eTime,document);


                Toast.makeText(ExpenseActivity.this, "Data added to Database", Toast.LENGTH_SHORT).show();
                expenseAmount.getText().clear();
                expenseDate.getText().clear();
                expenseTime.getText().clear();




            }
        });


    }



    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 1){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /////////////////***********************************************///////////////////////


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode ==0){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                openDoc.setImageBitmap(bitmap);
                 image = BitMapToString(bitmap);



//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                byte[] byteArray = byteArrayOutputStream .toByteArray();





            }else if(requestCode ==1){

                Uri uri = data.getData();

                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    openDoc.setImageBitmap(bitmap);
                    image = BitMapToString(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.expensetype, R.layout.support_simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }


    private void init() {
        openDoc = findViewById(R.id.openDoc);
        addDocBtn = findViewById(R.id.addDocBtn);
        expenseDate = findViewById(R.id.expenseDate);
        date = findViewById(R.id.date);
        time = findViewById(R.id.timeIV);
        expenseTime = findViewById(R.id.timeET);
        expenseAmount = findViewById(R.id.expenseAET);
        addExp = findViewById(R.id.addExpenseBtn);
        helper = new ExpenseDataOpenHelper(this);
        adapter = new DailyExpenseAdapter(helper, expenseList, this);
        spinner = findViewById(R.id.spinnerView);

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
