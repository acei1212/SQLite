package com.example.student.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickcopy(View v) {
        InputStream is = getResources().openRawResource(R.raw.student);
        try {
            OutputStream os = new FileOutputStream(getFilesDir() + File.separator + "student.sqlite");
            int i = 0;
            while (i != -1) {
                i = is.read(); //存取student.sqlite
                os.write(i);   //寫入至檔案
            }
            is.close();
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void clickcopy2(View v) {
        InputStream is = getResources().openRawResource(R.raw.student);
        URI uri = URI.create("file://" + getFilesDir().getAbsolutePath() + File.separator + "student2.sqlite");

        Path p = Paths.get(uri);
        try {
            Files.copy(is, p, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f = new File(getFilesDir() + File.separator + "student2.sqlite");
        Log.d("FILE", String.valueOf(f.exists()));

    }

    public void clickReadDB(View v) { //將資料讀出
        String path = getFilesDir().getAbsolutePath() + File.separator + "student2.sqlite";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, 0);
        //遊標指向 curosr
        Cursor c = db.query("phone", new String[]{"id", "name", "tel", "Addr"}, null, null, null, null, null);
        c.moveToFirst(); //遊標指向最前位
        do {
            Log.d("DB", c.getString(1));
        } while (c.moveToNext()); //移至下一個
        c.close(); //移至最後時關閉
        db.close();
    }
    public void clickInsert(View v){
    String path = getFilesDir().getAbsolutePath() + File.separator + "student2.sqlite";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path,null,0);
        ContentValues cv = new ContentValues();
        //寫入一筆新資料
        cv.put("Name","A6");
        cv.put("Tel","0239");
        cv.put("Addr","ffff");
        db.insert("phone",null,cv);
        db.close();
    }

}
