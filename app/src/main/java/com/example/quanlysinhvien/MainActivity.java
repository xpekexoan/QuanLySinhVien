package com.example.quanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String DATABASE_NAME = "EmployeeDB.db";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<NhanVien> list;
    AdapterNhanVien adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        readData();
    }

    private void addControls() {
        listView = (ListView)findViewById(R.id.listview);
        list = new ArrayList<>();
        adapter = new AdapterNhanVien(this,list);
        listView.setAdapter(adapter);
    }
    private void readData(){
        database = DataBase.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery(" SELECT * FROM  SinhVien ", null);
        list.clear();
        for(int i = 0; i< cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String sdt = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            list.add(new NhanVien(id,ten,sdt,anh));
            adapter.notifyDataSetChanged();
        }
    }
}