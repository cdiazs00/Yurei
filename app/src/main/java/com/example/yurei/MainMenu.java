package com.example.yurei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button newGameButton = findViewById(R.id.new_game);
        Button loadGameButton = findViewById(R.id.load_game);

        loadGameButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("GameProgress", MODE_PRIVATE);
            int dialogueIndex = sharedPreferences.getInt("dialogueIndex", -1);
            int charIndex = sharedPreferences.getInt("charIndex", -1);

            if (dialogueIndex != -1 && charIndex != -1) {
                Intent intent = new Intent(this, Prologue.class);
                intent.putExtra("dialogueIndex", dialogueIndex - 1);
                intent.putExtra("charIndex", charIndex);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No hay datos guardados", Toast.LENGTH_SHORT).show();
            }
        });

        newGameButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("GameProgress", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(this, Prologue.class);
            startActivity(intent);
        });
    }
}