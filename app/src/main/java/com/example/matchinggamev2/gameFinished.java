package com.example.matchinggamev2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class gameFinished extends AppCompatActivity {

    Button BackToGame;
    Button exit;
    TextView placeholder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);


        BackToGame=findViewById(R.id.buttonPlayAgain);

        placeholder=(TextView) findViewById(R.id.textViewGameEnded);

        placeholder.setText(getIntent().getStringExtra("message"));

        BackToGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(gameFinished.this,MainActivity.class);
                startActivity(back);
            }
        });

        exit=findViewById(R.id.buttonExit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(gameFinished.this, MainActivity.class);
                intent.putExtra("message","end");
                startActivity(intent);
            }
        });
    }


}