package com.example.timeline.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timeline.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.widget.Toast.LENGTH_SHORT;

public class EventActivity extends AppCompatActivity {
    private EditText time;
    private EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = (EditText) findViewById(R.id.time);
                content = (EditText) findViewById(R.id.content);
                String dtime = time.getText().toString();
                String dcontent = content.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("time",dtime);
                intent.putExtra("content",dcontent);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

}
