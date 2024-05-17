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

    private TextView textView, screen;
    private String[] dialogues;
    private int dialogueIndex = 0;
    private int charIndex = 0;
    private boolean animationRunning = false;
    private Button option1Button, option2Button, changeDialogueButton, menuButton, exitButton, saveButton, exitGameButton, inventoryButton,
            exitInventoryButton, mapButton;
    private ImageView backgroundImageView, map;
    private View sprite1, sprite2, sprite3, menu, statsMenu, object;
    private int Counter = 0;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prologue);

        textView = findViewById(R.id.text);
        screen = findViewById(R.id.transition);
        changeDialogueButton = findViewById(R.id.button_next);
        option1Button = findViewById(R.id.button_option1);
        option2Button = findViewById(R.id.button_option2);
        menuButton = findViewById(R.id.button_menu);
        exitButton = findViewById(R.id.button_exit_menu);
        saveButton = findViewById(R.id.save_button);
        exitGameButton = findViewById(R.id.button_exit_game);
        inventoryButton = findViewById(R.id.button_inventory);
        exitInventoryButton = findViewById(R.id.button_exit_inventory);
        mapButton = findViewById(R.id.map_button);
        map = findViewById(R.id.map);

        sprite1 = findViewById(R.id.sra_hernandez);
        sprite2 = findViewById(R.id.luis);
        sprite3 = findViewById(R.id.maria);
        menu = findViewById(R.id.menu);
        statsMenu = findViewById(R.id.stats_menu);
        object = findViewById(R.id.objeto);

        option1Button.setVisibility(View.GONE);
        option2Button.setVisibility(View.GONE);
        menuButton.setVisibility(View.VISIBLE);
        exitButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        exitGameButton.setVisibility(View.INVISIBLE);
        inventoryButton.setVisibility(View.INVISIBLE);
        exitInventoryButton.setVisibility(View.INVISIBLE);
        mapButton.setVisibility(View.INVISIBLE);
        map.setVisibility(ImageView.INVISIBLE);
        screen.setVisibility(View.INVISIBLE);

        sprite1.setVisibility(View.GONE);
        sprite2.setVisibility(View.GONE);
        sprite3.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);
        statsMenu.setVisibility(View.GONE);
        object.setVisibility(View.GONE);
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
        setBackgroundForDialogue(dialogueIndex);

        exitGameButton.setOnClickListener(v -> {
            intent[0] = new Intent(this, MainMenu.class);
            startActivity(intent[0]);
        });
    }

    private void animateText() {
        final int delayMillis = 1;
        final int screenVisibleTimeMillis = 3000;

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
                    if (dialogueIndex == 15 || dialogueIndex == 85) {
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
                    if (dialogueIndex == 57){
                        setBackground(R.drawable.habitacion_invitado);
                        sprite2.setVisibility(View.INVISIBLE);
                        sprite3.setVisibility(View.VISIBLE);
                    }
                    if (dialogueIndex == 63){
                        setBackground(R.drawable.darkness);
                        screen.setVisibility(View.VISIBLE);
                        sprite3.setVisibility(View.INVISIBLE);

                        new Handler().postDelayed(() -> screen.setVisibility(View.INVISIBLE), screenVisibleTimeMillis);
                    }
                    if (dialogueIndex == 68){
                        setBackground(R.drawable.luz);
                    }
                    if (dialogueIndex == 70){
                        setBackground(R.drawable.luz_cerca);
                    }
                    if (dialogueIndex == 83){
                        setBackground(R.drawable.habitacion_invitado);
                    }
                    if (dialogueIndex == 92){
                        setBackground(R.drawable.cocina);
                        sprite2.setVisibility(View.VISIBLE);
                    }
                    if (dialogueIndex == 96){
                        setBackground(R.drawable.jardin);
                    }
                    if (dialogueIndex == 97){
                        object.setVisibility(View.VISIBLE);
                    }
                    if (dialogueIndex == 98){
                        object.setVisibility(View.INVISIBLE);
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
                                dialogueIndex = 55;
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
                        mapButton.setVisibility(View.VISIBLE);
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
                        mapButton.setVisibility(View.INVISIBLE);
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
                    });mapButton.setOnClickListener(v -> map.setVisibility(ImageView.VISIBLE));
                }
            }
        }, delayMillis);
    }

    private void setBackground(int resId) {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(resId);
        backgroundImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        backgroundImageView.setImageDrawable(drawable);
    }

    @SuppressLint("SetTextI18n")
    private void setBackgroundForDialogue(int dialogueIndex) {
        switch (dialogueIndex) {
            case 8, 9, 10 -> setBackground(R.drawable.velanimas_entrada);
            case 11, 12, 13, 14, 15 -> setBackground(R.drawable.casa_exterior);
            case 16, 17, 18, 19, 20, 21, 22, 23, 86, 87, 88, 89, 90, 91, 92 -> {
                setBackground(R.drawable.sala_estar);
                sprite1.setVisibility(View.VISIBLE);
            }
            case 24 -> {
                setBackground(R.drawable.habitaciones);
                sprite1.setVisibility(View.INVISIBLE);
                option1Button.setText("Luis");
                option2Button.setText("María");
            }
            case 26, 27, 28, 29, 41, 42, 43, 44 -> {
                setBackground(R.drawable.habitaciones);
                sprite2.setVisibility(View.INVISIBLE);
                sprite3.setVisibility(View.INVISIBLE);
            }
            case 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40 -> {
                setBackground(R.drawable.habitacion_luis);
                sprite2.setVisibility(View.VISIBLE);
            }
            case 45, 46, 47, 48, 49, 50, 51, 52, 56, 57 -> {
                setBackground(R.drawable.habitacion_maria);
                sprite3.setVisibility(View.VISIBLE);
            }
            case 58, 59, 60, 61, 62, 63 -> {
                setBackground(R.drawable.habitacion_invitado);
                sprite2.setVisibility(View.INVISIBLE);
                sprite3.setVisibility(View.VISIBLE);
            }
            case 64 -> {
                final int screenVisibleTimeMillis = 3000;
                setBackground(R.drawable.darkness);
                screen.setVisibility(View.VISIBLE);
                sprite3.setVisibility(View.INVISIBLE);
                new Handler().postDelayed(() -> screen.setVisibility(View.INVISIBLE), screenVisibleTimeMillis);
            }
            case 65, 66, 67, 68 -> {
                setBackground(R.drawable.darkness);
                sprite3.setVisibility(View.INVISIBLE);
            }
            case 69, 70 -> setBackground(R.drawable.luz);
            case 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83 -> setBackground(R.drawable.luz_cerca);
            case 84, 85 -> setBackground(R.drawable.habitacion_invitado);
            case 93, 94, 95, 96 -> {
                setBackground(R.drawable.cocina);
                sprite2.setVisibility(View.VISIBLE);
            }
            case 97, 99, 100, 101, 102, 103, 104 -> {
                setBackground(R.drawable.jardin);
                sprite2.setVisibility(View.VISIBLE);
            }
            case 98 -> object.setVisibility(View.VISIBLE);

            default -> setBackground(R.drawable.coche);
        }
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