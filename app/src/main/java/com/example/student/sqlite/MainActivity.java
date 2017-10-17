package com.example.student.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    EditText ed1,ed2,ed3;
    TextView tv;
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
        tv = (TextView)findViewById(R.id.textView);
        String s;
        StringBuilder sb = new StringBuilder();

        String path = getFilesDir().getAbsolutePath() + File.separator + "student2.sqlite";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, 0);
        //遊標指向 curosr
        Cursor c = db.query("phone", new String[]{"id", "name", "tel", "Addr"}, null, null, null, null, null);
        c.moveToFirst(); //遊標指向最前位

        do {
            s = String.valueOf(c.getString(0)+","+c.getString(1))+","+c.getString(2)+","+c.getString(3);
            Log.d("DB",s);
            sb.append(s+"\n"); // 每串s 都斷行
        } while (c.moveToNext()); //每行資料跑一次
        c.close(); //移至最後時關閉
        db.close();


        tv.setText(sb); //印出至TextView
    }
    public void clickInsert(View v){
    String path = getFilesDir().getAbsolutePath() + File.separator + "student2.sqlite";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path,null,0);

        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);
        ed3 = (EditText)findViewById(R.id.editText3);

        ContentValues cv = new ContentValues();
        //寫入一筆新資料
        cv.put("Name",ed1.getText().toString());
        cv.put("Tel",ed2.getText().toString());
        cv.put("Addr",ed3.getText().toString());

        db.insert("phone",null,cv);
        db.close();
    }
    public void clickDelete(View v){
        String path = getFilesDir().getAbsolutePath() + File.separator + "student2.sqlite";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("phone","id=?",new String[] {"2"}); //Delete id:2 的字串
        db.close();
    }

}
