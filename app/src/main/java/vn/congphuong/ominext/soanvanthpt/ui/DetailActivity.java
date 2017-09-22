package vn.congphuong.ominext.soanvanthpt.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.ClipboardManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.congphuong.ominext.soanvanthpt.R;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.btn_detail_back)
    Button btnDetailBack;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.fab_coppy)
    FloatingActionButton fabCoppy;
    @BindView(R.id.av_detail)
    AdView avDetail;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        loadAds();
        setText();


    }

    private void setText() {
        Intent intent = getIntent();
        String fileName = intent.getStringExtra("path");
        if (!TextUtils.isEmpty(fileName)) {
            String data = getData(fileName);
            tvContent.setText(Html.fromHtml(data));
        }
    }

    private String getData(String fileName) {
        StringBuilder  buf =new StringBuilder();
        InputStream json = null;
        try{
            json = getAssets().open(fileName);
            BufferedReader in =null;
            in = new BufferedReader(new InputStreamReader(json,"UTF-8"));
            String str;
            while ((str = in.readLine())!=null){
                buf.append(str);
            }
            in.close();
        }catch (UnsupportedEncodingException e){

        }catch (IOException e){
            e.printStackTrace();
        }
        return buf.toString();
    }

    private void loadAds() {
        adRequest = new AdRequest.Builder().build();
        avDetail.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @OnClick({R.id.btn_detail_back, R.id.fab_coppy, R.id.av_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_detail_back:
                onBackPressed();
                break;
            case R.id.fab_coppy:
                coppy();
                break;
            case R.id.av_detail:
                break;
        }
    }

    private void coppy() {
        ((ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE)).setText(tvContent.getText().toString());
        Toast.makeText(DetailActivity.this,"bạn đã Coppy",Toast.LENGTH_SHORT).show();
    }
}
