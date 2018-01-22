package vn.congphuong.ominext.soanvanthpt.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
    EditText editText;
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
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lessonAdapter.filter(charSequence.toString().trim(), listSeach);
                rcvLesson.invalidate();
                rcvLesson.smoothScrollToPosition(0);
                if (charSequence.length() == 0) {
                    editText.setFocusable(false);
                    editText.setFocusableInTouchMode(false);
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
    public void onClick(String path, String name) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("path", path);
        intent.putExtra("name", name);
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
        int imgonResource = R.drawable.ic_tap_mot;
        int imgoffResource = R.drawable.ic_tap_hai;
        int on = R.color.oke;
        int o = R.color.notoke;
        int off = R.color.abc;
        if (i == 1) {
            btnTapmot.setCompoundDrawablesWithIntrinsicBounds(imgonResource, 0, 0, 0);
            btnTapmot.setTextColor(getResources().getColor(on));
            btnTaphai.setCompoundDrawablesWithIntrinsicBounds(imgoffResource, 0, 0, 0);
            btnTaphai.setTextColor(getResources().getColor(o));
            vMot.setBackgroundColor(getResources().getColor(on));
            vHai.setBackgroundColor(getResources().getColor(off));
        }
        if (i == 2) {
            btnTapmot.setCompoundDrawablesWithIntrinsicBounds(imgoffResource, 0, 0, 0);
            btnTapmot.setTextColor(getResources().getColor(o));
            btnTaphai.setCompoundDrawablesWithIntrinsicBounds(imgonResource, 0, 0, 0);
            btnTaphai.setTextColor(getResources().getColor(on));
            vMot.setBackgroundColor(getResources().getColor(off));
            vHai.setBackgroundColor(getResources().getColor(on));
        }
    }
}
