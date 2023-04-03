package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private EditText input1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        input1 = findViewById(R.id.input1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = input1.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(MainActivity.this, "Veuillez entrer votre nom", Toast.LENGTH_SHORT).show();
                } else {
                    Intent versAutreActivite = new Intent(MainActivity.this, MainActivity2.class);
                    versAutreActivite.putExtra("mon_nom", name);
                    startActivity(versAutreActivite);
                }
            }
        });
    }
}