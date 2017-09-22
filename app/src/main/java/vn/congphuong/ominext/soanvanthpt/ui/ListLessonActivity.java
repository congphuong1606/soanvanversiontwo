package vn.congphuong.ominext.soanvanthpt.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.congphuong.ominext.soanvanthpt.R;
import vn.congphuong.ominext.soanvanthpt.adapter.LessonAdapter;
import vn.congphuong.ominext.soanvanthpt.common.Contants;
import vn.congphuong.ominext.soanvanthpt.data.Database;
import vn.congphuong.ominext.soanvanthpt.data.Lesson;
import vn.congphuong.ominext.soanvanthpt.event.OnClickLessonAdapter;

public class ListLessonActivity extends AppCompatActivity
        implements OnClickLessonAdapter {
    @BindView(R.id.btn_back_listLesson)
    Button btnBackListLesson;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.tv_lop)
    TextView tvLop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_tapmot)
    Button btnTapmot;
    @BindView(R.id.btn_taphai)
    Button btnTaphai;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.rcv_lesson)
    RecyclerView rcvLesson;
    @BindView(R.id.v_mot)
    View vMot;
    @BindView(R.id.v_hai)
    View vHai;
    private ArrayList<Lesson> lessons;
    private ArrayList<Lesson> listSeach;
    private LessonAdapter lessonAdapter;
    private SQLiteDatabase database;
    private String lop;
    private String chapterMot;
    private String chapterHai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lesson);
        ButterKnife.bind(this);
        setLop();
        setChapter();
        setAdapter();
        readData(chapterMot);
        changStatus(1);
       seach();

    }

    private void seach() {
        searchView.setQueryHint(Contants.SV_HINT);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lessonAdapter.filter(newText.toString().trim(),listSeach);
                rcvLesson.invalidate();
                rcvLesson.smoothScrollToPosition(0);
                return true;
            }
        });

    }

    private void setLop() {
        Intent intent = getIntent();
        lop = intent.getStringExtra("lop");
        tvLop.setText((String.valueOf(lop)));

    }

    private void setAdapter() {
        lessons = new ArrayList<Lesson>();
        listSeach = new ArrayList<Lesson>();
        rcvLesson.setLayoutManager(new
                GridLayoutManager(this, 1));
        rcvLesson.setHasFixedSize(true);
        lessonAdapter = new LessonAdapter(lessons, this);
        rcvLesson.setAdapter(lessonAdapter);

    }

    private void setChapter() {
        chapterMot = lop.concat("1");
        chapterHai = lop.concat("2");
    }

    private void readData(String chapter) {
        database = Database.initDatabase(this, Contants.DATABASE_NAME);
        Cursor cursor = database.rawQuery(Contants.SQL + chapter + Contants.ASC, null);
        lessons.clear();
        listSeach.clear();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(1);
                    String path = cursor.getString(2);
                    lessons.add(new Lesson(name, path));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        listSeach.addAll(lessons);
        lessonAdapter.notifyDataSetChanged();
        rcvLesson.smoothScrollToPosition(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(String path) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("path", path);
        startActivity(intent);
    }

    @OnClick({R.id.btn_back_listLesson,
            R.id.btn_tapmot, R.id.btn_taphai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_listLesson:
                onBackPressed();
                break;
            case R.id.btn_tapmot:
                readData(chapterMot);
                changStatus(1);
                break;
            case R.id.btn_taphai:
                readData(chapterHai);
                changStatus(2);
                break;
        }
    }

    private void changStatus(int i) {
        int on = R.color.red;
        int off = R.color.colorPrimary;
        if (i == 1) {
            vMot.setBackgroundColor(getResources().getColor(on));
            vHai.setBackgroundColor(getResources().getColor(off));
        }
        if (i == 2) {
            vMot.setBackgroundColor(getResources().getColor(off));
            vHai.setBackgroundColor(getResources().getColor(on));
        }
    }
}
