package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    private Button button4, button5;
    private TextView textFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        textFinal = findViewById(R.id.textFinal);
        //Récupération du score envoyé par le intent de l activité précédente.
        int timer = getIntent().getIntExtra("timer", 0);
        //Affichage du score
        textFinal.setText("Ouups! C'est terminé.Votre score est de : " + timer + "s");
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }
    public void retournerVersJeu(View view) {
        Intent jeu = new Intent(MainActivity3.this,MainActivity.class);
        startActivity(jeu);
    }
}