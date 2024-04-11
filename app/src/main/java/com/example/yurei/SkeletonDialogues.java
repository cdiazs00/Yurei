package com.example.yurei;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SkeletonDialogues extends AppCompatActivity {

    private TextView textView;
    private String[] dialogues;
    private int dialogueIndex = 0;
    private int charIndex = 0;
    private boolean animationRunning = false;
    private Button option1Button;
    private Button option2Button;
    private Button changeDialogueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogues_skeleton);

        textView = findViewById(R.id.text);
        changeDialogueButton = findViewById(R.id.button_next);
        option1Button = findViewById(R.id.button_option1);
        option2Button = findViewById(R.id.button_option2);
        option1Button.setVisibility(View.GONE);
        option2Button.setVisibility(View.GONE);
        changeDialogueButton.setVisibility(View.INVISIBLE);

        dialogues = readDialoguesFromCSV(R.raw.dialogues);

        animateText();

        changeDialogueButton.setOnClickListener(v -> {
            if (!animationRunning) {
                changeDialogueButton.setVisibility(View.INVISIBLE);
                charIndex = 0;
                animationRunning = true;
                animateText();
            }
        });

        option1Button.setOnClickListener(v -> {
            Intent intent = new Intent(this, SkeletonRPG.class);
            startActivity(intent);
        });
        option2Button.setOnClickListener(v -> {
            if (!animationRunning) {
                option1Button.setVisibility(View.INVISIBLE);
                option2Button.setVisibility(View.INVISIBLE);
                charIndex = 0;
                animationRunning = true;
                animateText();
            }
        });
    }

    private void animateText() {
        final int delayMillis = 100;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialogueIndex < dialogues.length) {
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
                        if (dialogueIndex < dialogues.length && dialogueIndex != 5) {
                            changeDialogueButton.setVisibility(View.VISIBLE);
                        } else {
                            changeDialogueButton.setVisibility(View.INVISIBLE);
                            if (dialogueIndex == 5) {
                                option1Button.setVisibility(View.VISIBLE);
                                option2Button.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }
        }, delayMillis);
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