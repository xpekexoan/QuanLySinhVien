package com.example.quanlysinhvien;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateActivity extends AppCompatActivity {
    final  String DATABASE_NAME = "EmployeeDB.db";
    final  int REQUEST_TAKE_PHOTO = 123;
    final  int REQUEST_CHOSE_PHOTO = 321;
    Button btnChonHinh,btnChupHinh,btnLuu,btnHuy;
    EditText edTen,edSdt;
    ImageView imgHinhNen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        addControls();
        addEvents();
        initUI();
    }
    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_TAKE_PHOTO);
    }
    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CHOSE_PHOTO);
    }
    private void addEvents(){
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        btnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                InputStream is = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imgHinhNen.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_TAKE_PHOTO) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinhNen.setImageBitmap(bitmap);
        }
    }

    private void initUI() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID",-1);
        SQLiteDatabase database = DataBase.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery(" SELECT * FROM  SinhVien where ID = ? ", new String[]{id + ""} );
        cursor.moveToFirst();
        String ten = cursor.getString(1);
        String sdt = cursor.getString(2);
        byte[] anh = cursor.getBlob(3);

        Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0,anh.length);
        imgHinhNen.setImageBitmap(bitmap);
        edSdt.setText(sdt);
        edTen.setText(ten);

    }

    private void addControls() {
        btnChonHinh = (Button)findViewById(R.id.bt_chonhinh);
        btnChupHinh = (Button)findViewById(R.id.bt_chuphinh);
        btnLuu = (Button)findViewById(R.id.bt_luu);
        btnHuy = (Button)findViewById(R.id.bt_huy);
        edSdt = (EditText)findViewById(R.id.ed_sdt);
        edTen = (EditText)findViewById(R.id.ed_ten);
        imgHinhNen = (ImageView)findViewById(R.id.img_hinhnen);


    }
}