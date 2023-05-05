package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv1;
    Button button1, button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
    }
    public void jeu(View view) {
        Intent versAutreActivite2 = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(versAutreActivite2);
    }
    public void done(View view){
        finish();
    }
}