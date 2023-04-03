package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView tv3;
    Button button2, button3, button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv3 = findViewById(R.id.tv3);
        button2 = findViewById(R.id.button2);
        String name = getIntent().getStringExtra("mon_nom");
        tv3.setText("Bienvenue " + name);
    }
    public void pp(View view) {
        Intent versAutreActivite3 = new Intent(MainActivity2.this,MainActivity3.class);
        startActivity(versAutreActivite3);
    }
}