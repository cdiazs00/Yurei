package com.example.yurei;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button newGameButton = findViewById(R.id.new_game);

        newGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Prologue.class);
            startActivity(intent);
        });
    }
}