package com.example.bitm_project_01dailyexpensenote;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class DetailsBottomSheet extends BottomSheetDialogFragment {
    private TextView type, amount, date, time;
    private Button showDoc;
    private ImageView showImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_bottom_sheet_layout, container, false);

        init(view);

        showData(view);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }


        return view;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void showData(View view) {
        Bundle mArgs = getArguments();
        String expenseT = mArgs.getString("expName");
        String expenseA = Integer.toString(mArgs.getInt("expAmount"));
        String expenseD = mArgs.getString("expDate");
        String expenseTime = mArgs.getString("expTime");
        String img = mArgs.getString("img");
        Bitmap docc = StringToBitMap(img);


        type.setText(expenseT);
        amount.setText(expenseA);
        date.setText(expenseD);
        time.setText(expenseTime);
        showImg.setImageBitmap(docc);

        showDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showImg.setVisibility(view.VISIBLE);


            }
        });


    }


    private void init(View view) {
        type = view.findViewById(R.id.typeNameTv);
        amount = view.findViewById(R.id.amountTv);
        date = view.findViewById(R.id.dateTv);
        time = view.findViewById(R.id.timeTv);
        showImg = view.findViewById(R.id.imageShow);
        showDoc = view.findViewById(R.id.showDoc);

    }
}
