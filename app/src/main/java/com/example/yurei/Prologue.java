package com.example.yurei;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Button option1Button;
    private Button option2Button;
    private Button changeDialogueButton;
    private ImageView backgroundImageView;
    private View sprite1, sprite2, sprite3;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prologue);

        textView = findViewById(R.id.text);
        changeDialogueButton = findViewById(R.id.button_next);
        option1Button = findViewById(R.id.button_option1);
        option2Button = findViewById(R.id.button_option2);
        sprite1 = findViewById(R.id.sra_hernandez);
        sprite2 = findViewById(R.id.luis);
        sprite3 = findViewById(R.id.maria);
        option1Button.setVisibility(View.GONE);
        option2Button.setVisibility(View.GONE);
        sprite1.setVisibility(View.GONE);
        sprite2.setVisibility(View.GONE);
        sprite3.setVisibility(View.GONE);
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
                    if (dialogueIndex == 29){
                        setBackground(R.drawable.habitacion_luis);
                        sprite2.setVisibility(View.VISIBLE);
                    }
                    if (dialogueIndex == 44){
                        setBackground(R.drawable.habitacion_maria);
                        sprite3.setVisibility(View.VISIBLE);
                    }

                    if (dialogueIndex == 51){
                        setBackground(R.drawable.habitaciones);
                        sprite3.setVisibility(View.INVISIBLE);
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
                    } else if (dialogueIndex == 52) {
                        option1Button.setOnClickListener(v -> {
                            option1Button.setVisibility(View.INVISIBLE);
                            option2Button.setVisibility(View.INVISIBLE);
                            dialogueIndex = 25;
                            animateText();
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
                }
            }
        }, delayMillis);
    }

    private void setBackground(int resId) {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(resId);
        backgroundImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        backgroundImageView.setImageDrawable(drawable);
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