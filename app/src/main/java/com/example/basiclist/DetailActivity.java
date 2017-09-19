package com.example.basiclist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textView = (TextView) findViewById(R.id.textView2);
        //인텐트를 통해 넘어온 값 꺼내기
        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        String result = bundle.getString("valueKey");
        //인텐트에서 값을 바로 꺼내는 방법
        String result = intent.getStringExtra("valueKey");
        textView.setText(result);



    }
}
