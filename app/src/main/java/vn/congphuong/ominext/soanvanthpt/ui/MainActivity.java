package vn.congphuong.ominext.soanvanthpt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.congphuong.ominext.soanvanthpt.R;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_lop10)
    Button btnLop10;
    @BindView(R.id.btn_lop11)
    Button btnLop11;
    @BindView(R.id.btn_lop12)
    Button btnLop12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_lop10, R.id.btn_lop11, R.id.btn_lop12})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_lop10:
                Intent intent1=new Intent(this,ListLessonActivity.class);
                intent1.putExtra("lop",10);
                startActivity(intent1);
                break;
            case R.id.btn_lop11:
                Intent intent2=new Intent(this,ListLessonActivity.class);
                intent2.putExtra("lop",10);
                startActivity(intent2);
                break;
            case R.id.btn_lop12:
                Intent intent=new Intent(this,ListLessonActivity.class);
                intent.putExtra("lop",10);
                startActivity(intent);
                break;
        }
    }
}
