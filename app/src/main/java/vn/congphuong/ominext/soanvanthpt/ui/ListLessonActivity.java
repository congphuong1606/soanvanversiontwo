package vn.congphuong.ominext.soanvanthpt.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.congphuong.ominext.soanvanthpt.R;

public class ListLessonActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lesson);
        Intent intent = getIntent();
        int lop = intent.getIntExtra("lop", 0);
    }
}
