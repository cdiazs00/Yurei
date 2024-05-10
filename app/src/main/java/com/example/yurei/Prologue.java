package com.example.yurei;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Prologue extends AppCompatActivity {

    private TextView textView;
    private String[] dialogues;
    private int dialogueIndex = 0;
    private int charIndex = 0;
    private boolean animationRunning = false;
    private Button option1Button, option2Button, changeDialogueButton, menuButton, exitButton, saveButton, exitGameButton, inventoryButton, exitInventoryButton;
    private ImageView backgroundImageView;
    private View sprite1, sprite2, sprite3, menu, statsMenu;
    private int Counter = 0;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prologue);

        textView = findViewById(R.id.text);
        changeDialogueButton = findViewById(R.id.button_next);
        option1Button = findViewById(R.id.button_option1);
        option2Button = findViewById(R.id.button_option2);
        menuButton = findViewById(R.id.button_menu);
        exitButton = findViewById(R.id.button_exit_menu);
        saveButton = findViewById(R.id.save_button);
        exitGameButton = findViewById(R.id.button_exit_game);
        inventoryButton = findViewById(R.id.button_inventory);
        exitInventoryButton = findViewById(R.id.button_exit_inventory);

        sprite1 = findViewById(R.id.sra_hernandez);
        sprite2 = findViewById(R.id.luis);
        sprite3 = findViewById(R.id.maria);
        menu = findViewById(R.id.menu);
        statsMenu = findViewById(R.id.stats_menu);

        option1Button.setVisibility(View.GONE);
        option2Button.setVisibility(View.GONE);
        menuButton.setVisibility(View.VISIBLE);
        exitButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        exitGameButton.setVisibility(View.INVISIBLE);
        inventoryButton.setVisibility(View.INVISIBLE);
        exitInventoryButton.setVisibility(View.INVISIBLE);

        sprite1.setVisibility(View.GONE);
        sprite2.setVisibility(View.GONE);
        sprite3.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);
        statsMenu.setVisibility(View.GONE);
        changeDialogueButton.setVisibility(View.INVISIBLE);
        backgroundImageView = findViewById(R.id.escenas);

        dialogues = readDialoguesFromCSV(R.raw.prologue);

        animateText();

        changeDialogueButton.setOnClickListener(v -> {
            if (!animationRunning) {
                changeDialogueButton.setVisibility(View.INVISIBLE);
                charIndex = 0;
                animationRunning = true;
                animateText();
            }
        });
        saveButton.setOnClickListener(v -> saveGameProgress());

        final Intent[] intent = {getIntent()};
        int dialogueIndex = intent[0].getIntExtra("dialogueIndex", 0);
        int charIndex = intent[0].getIntExtra("charIndex", 0);
        String currentDialogue = intent[0].getStringExtra("currentDialogue");
        if (dialogueIndex == 0 && charIndex == 0 && currentDialogue == null) {
            SharedPreferences sharedPreferences = getSharedPreferences("GameProgress", Context.MODE_PRIVATE);
            dialogueIndex = sharedPreferences.getInt("dialogueIndex", 0);
            charIndex = sharedPreferences.getInt("charIndex", 0);
            currentDialogue = sharedPreferences.getString("currentDialogue", "");
        }

        this.dialogueIndex = dialogueIndex;
        this.charIndex = charIndex;
        textView.setText(currentDialogue);

        exitGameButton.setOnClickListener(v -> {
            intent[0] = new Intent(this, MainMenu.class);
            startActivity(intent[0]);
        });
    }

    private void animateText() {
        final int delayMillis = 1;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (dialogueIndex < dialogues.length) {
                    if (dialogueIndex == 7) {
                        setBackground(R.drawable.velanimas_entrada);
                    }
                    if (dialogueIndex == 10) {
                        setBackground(R.drawable.casa_exterior);
                    }
                    if (dialogueIndex == 15) {
                        setBackground(R.drawable.sala_estar);
                        sprite1.setVisibility(View.VISIBLE);
                    }
                    if (dialogueIndex == 23){
                        setBackground(R.drawable.habitaciones);
                        sprite1.setVisibility(View.INVISIBLE);
                        option1Button.setText("Luis");
                        option2Button.setText("María");
                    }
                    if (dialogueIndex == 25 || dialogueIndex == 40){
                        setBackground(R.drawable.habitaciones);
                        sprite2.setVisibility(View.INVISIBLE);
                        sprite3.setVisibility(View.INVISIBLE);
                    }
                    if (dialogueIndex == 29){
                        setBackground(R.drawable.habitacion_luis);
                        sprite2.setVisibility(View.VISIBLE);
                    }
                    if (dialogueIndex == 44 || dialogueIndex == 55){
                        setBackground(R.drawable.habitacion_maria);
                        sprite3.setVisibility(View.VISIBLE);
                    }

                    String fullText = dialogues[dialogueIndex];
                    if (charIndex <= fullText.length()) {
                        String partialText = fullText.substring(0, charIndex);
                        textView.setText(partialText);
                        charIndex++;
                        handler.postDelayed(this, delayMillis);
                    } else {
                        charIndex = 0;
                        dialogueIndex++;
                        animationRunning = false;
                        if (dialogueIndex < dialogues.length && dialogueIndex != 24 && dialogueIndex != 52) {
                            changeDialogueButton.setVisibility(View.VISIBLE);
                        } else {
                            changeDialogueButton.setVisibility(View.INVISIBLE);
                            if (dialogueIndex == 24) {
                                option1Button.setVisibility(View.VISIBLE);
                                option2Button.setVisibility(View.VISIBLE);
                            }
                            if (dialogueIndex == 52){
                                option1Button.setVisibility(View.VISIBLE);
                                option2Button.setVisibility(View.VISIBLE);
                                option1Button.setText("Saludar a Luis");
                                option2Button.setText("Ir a mi habitación");
                            }
                        }
                    }
                    if (dialogueIndex == 23) {
                        option1Button.setOnClickListener(v -> {
                            option1Button.setVisibility(View.INVISIBLE);
                            option2Button.setVisibility(View.INVISIBLE);
                            dialogueIndex = 25;
                            Counter++;
                            animateText();
                        });
                        option2Button.setOnClickListener(v -> {
                            if (!animationRunning) {
                                option1Button.setVisibility(View.INVISIBLE);
                                option2Button.setVisibility(View.INVISIBLE);
                                dialogueIndex = 40;
                                animateText();
                            }
                        });
                    } if (dialogueIndex == 50 && Counter == 1){
                        dialogueIndex = 56;
                    } if(dialogueIndex == 39 && Counter == 0){
                        dialogueIndex = 55;
                        Counter++;
                    }
                    if (dialogueIndex == 52) {
                        option1Button.setOnClickListener(v -> {
                            option1Button.setVisibility(View.INVISIBLE);
                            option2Button.setVisibility(View.INVISIBLE);
                            dialogueIndex = 53;
                            animateText();
                        });
                        changeDialogueButton.setOnClickListener(v -> {
                            if (dialogueIndex == 55 && Counter == 0) {
                                dialogueIndex = 25;
                                animateText();
                            } else {
                                if (!animationRunning) {
                                    changeDialogueButton.setVisibility(View.INVISIBLE);
                                    charIndex = 0;
                                    animationRunning = true;
                                    animateText();
                                }
                            }
                        });
                        option2Button.setOnClickListener(v -> {
                            if (!animationRunning) {
                                option1Button.setVisibility(View.INVISIBLE);
                                option2Button.setVisibility(View.INVISIBLE);
                                dialogueIndex = 52;
                                animateText();
                            }
                        });
                    }
                    menuButton.setOnClickListener(v -> {
                        menu.setVisibility(View.VISIBLE);
                        statsMenu.setVisibility(View.VISIBLE);
                        menuButton.setVisibility(View.INVISIBLE);
                        exitButton.setVisibility(View.VISIBLE);
                        saveButton.setVisibility(View.VISIBLE);
                        exitGameButton.setVisibility(View.VISIBLE);
                        inventoryButton.setVisibility(View.VISIBLE);
                        changeDialogueButton.setClickable(false);
                        option1Button.setClickable(false);
                        option2Button.setClickable(false);
                    });
                    exitButton.setOnClickListener(v -> {
                        menu.setVisibility(View.INVISIBLE);
                        statsMenu.setVisibility(View.INVISIBLE);
                        menuButton.setVisibility(View.VISIBLE);
                        exitButton.setVisibility(View.INVISIBLE);
                        saveButton.setVisibility(View.INVISIBLE);
                        exitGameButton.setVisibility(View.INVISIBLE);
                        inventoryButton.setVisibility(View.INVISIBLE);
                        changeDialogueButton.setClickable(true);
                        option1Button.setClickable(true);
                        option2Button.setClickable(true);
                        inventoryButton.setClickable(true);
                    });
                    inventoryButton.setOnClickListener(v -> {
                        inventoryButton.setVisibility(View.INVISIBLE);
                        exitInventoryButton.setVisibility(View.VISIBLE);
                        statsMenu.setVisibility(View.INVISIBLE);
                        menuButton.setVisibility(View.INVISIBLE);
                        saveButton.setVisibility(View.INVISIBLE);
                        exitGameButton.setVisibility(View.INVISIBLE);
                        exitButton.setVisibility(View.INVISIBLE);
                        changeDialogueButton.setClickable(false);
                        option1Button.setClickable(false);
                        option2Button.setClickable(false);
                        menuButton.setClickable(false);
                    });
                    exitInventoryButton.setOnClickListener(v -> {
                        inventoryButton.setVisibility(View.VISIBLE);
                        exitInventoryButton.setVisibility(View.INVISIBLE);
                        exitButton.setVisibility(View.VISIBLE);
                        statsMenu.setVisibility(View.VISIBLE);
                        saveButton.setVisibility(View.VISIBLE);
                        exitGameButton.setVisibility(View.VISIBLE);
                        changeDialogueButton.setClickable(false);
                        option1Button.setClickable(false);
                        option2Button.setClickable(false);
                        menuButton.setClickable(true);
                    });
                }
            }
        }, delayMillis);
    }

    private void setBackground(int resId) {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(resId);
        backgroundImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        backgroundImageView.setImageDrawable(drawable);
    }

    private void saveGameProgress() {
        SharedPreferences sharedPreferences = getSharedPreferences("GameProgress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("dialogueIndex", dialogueIndex);
        editor.putInt("charIndex", charIndex);
        editor.putString("currentDialogue", textView.getText().toString());
        editor.apply();
        Toast.makeText(this, "Progreso guardado", Toast.LENGTH_SHORT).show();
    }

    private String[] readDialoguesFromCSV(int resourceId) {
        try {
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder dialoguesBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                dialoguesBuilder.append(line).append("\n");
            }
            inputStream.close();
            return dialoguesBuilder.toString().split("\n");
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}