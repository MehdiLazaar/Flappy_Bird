package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {
    private Button button4, button5;
    private TextView textFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        textFinal = findViewById(R.id.textFinal);
        //Récupération du score envoyé par le intent de l activité précédente.
        int timer = getIntent().getIntExtra("timer", 0);
        //Affichage du score
        textFinal.setText("Votre score est de : " + timer);

    }

    public void retournerVersJeu(View view) {
        Intent jeu = new Intent(MainActivity4.this,MainActivity3.class);
        startActivity(jeu);
    }
    public void done(View view){
        finish();
    }
}