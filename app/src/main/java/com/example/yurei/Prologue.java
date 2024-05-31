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
    private int MariaAffinity = 0;
    private int LuisAffinity = 0;
    private int currentStatsBackground = 0;
    private final int[] StatsBackgrounds = {R.drawable.cristina_stats, R.drawable.luis_stats, R.drawable.maria_stats};
    private boolean animationRunning = false;
    private Button option1Button, option2Button, changeDialogueButton, menuButton, exitButton, saveButton, exitGameButton, inventoryButton,
            exitInventoryButton, mapButton, statsButton, leftButton, rightButton, combatButton;
    private ImageView backgroundImageView, map;
    private View sprite1, sprite2, sprite3, sprite4, menu, statsMenu, object;
    private TextView inventory, reputation, affinity_luis, affinity_maria;
    private String inventoryItems;
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
        statsButton = findViewById(R.id.stats_button);
        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        combatButton = findViewById(R.id.button_combat);
        map = findViewById(R.id.map);
        sprite1 = findViewById(R.id.sra_hernandez);
        sprite2 = findViewById(R.id.luis);
        sprite3 = findViewById(R.id.maria);
        sprite4 = findViewById(R.id.faragonda);
        menu = findViewById(R.id.menu);
        statsMenu = findViewById(R.id.stats_menu);
        object = findViewById(R.id.objeto);
        inventory = findViewById(R.id.inventory);
        reputation = findViewById(R.id.reputation);
        affinity_luis = findViewById(R.id.affinity_luis);
        affinity_maria = findViewById(R.id.affinity_maria);
        inventoryItems = inventory.getText().toString();
        menuButton.setVisibility(View.VISIBLE);
        option1Button.setVisibility(View.INVISIBLE);
        option2Button.setVisibility(View.INVISIBLE);
        exitButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        exitGameButton.setVisibility(View.INVISIBLE);
        inventoryButton.setVisibility(View.INVISIBLE);
        exitInventoryButton.setVisibility(View.INVISIBLE);
        mapButton.setVisibility(View.INVISIBLE);
        statsButton.setVisibility(View.INVISIBLE);
        leftButton.setVisibility(View.INVISIBLE);
        rightButton.setVisibility(View.INVISIBLE);
        combatButton.setVisibility(View.INVISIBLE);
        map.setVisibility(ImageView.INVISIBLE);
        screen.setVisibility(View.INVISIBLE);
        sprite1.setVisibility(View.INVISIBLE);
        sprite2.setVisibility(View.INVISIBLE);
        sprite3.setVisibility(View.INVISIBLE);
        sprite4.setVisibility(View.INVISIBLE);
        menu.setVisibility(View.INVISIBLE);
        statsMenu.setVisibility(View.INVISIBLE);
        object.setVisibility(View.INVISIBLE);
        inventory.setVisibility(View.INVISIBLE);
        reputation.setVisibility(View.INVISIBLE);
        affinity_luis.setVisibility(View.INVISIBLE);
        affinity_maria.setVisibility(View.INVISIBLE);
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
            @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
            @Override
            public void run() {
                if (dialogueIndex < dialogues.length) {
                    if (dialogueIndex == 7) {
                        setBackground(R.drawable.velanimas_entrada);
                        map.setBackground(getResources().getDrawable(R.drawable.mapa_2));
                    }
                    if (dialogueIndex == 10) {
                        setBackground(R.drawable.casa_exterior);
                        map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
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
                        sprite1.setVisibility(View.INVISIBLE);
                    }
                    if (dialogueIndex == 96){
                        setBackground(R.drawable.jardin);
                    }
                    if (dialogueIndex == 97){
                        object.setVisibility(View.VISIBLE);
                        String addItem = inventoryItems + "\n" + "Vial cristalizado";
                        inventory.setText(addItem);
                    }
                    if (dialogueIndex == 98){
                        object.setVisibility(View.INVISIBLE);
                    }
                    if (dialogueIndex == 106){
                        setBackground(R.drawable.bosque);
                        map.setBackground(getResources().getDrawable(R.drawable.mapa_4));
                    }
                    if (dialogueIndex == 107){
                        saveGameProgress();
                    }
                    if (dialogueIndex == 109){
                        sprite3.setVisibility(View.VISIBLE);
                    }
                    if (dialogueIndex == 137){
                        setBackground(R.drawable.cruce);
                        map.setBackground(getResources().getDrawable(R.drawable.mapa_5));
                    }
                    if (dialogueIndex == 147){
                        setBackground(R.drawable.mirador);
                        map.setBackground(getResources().getDrawable(R.drawable.mapa_6));
                    }
                    if (dialogueIndex == 153) {
                        changeDialogueButton.setOnClickListener(v -> {
                            changeDialogueButton.setVisibility(View.INVISIBLE);
                            dialogueIndex++;
                            animateText();
                        });
                    }
                    if (dialogueIndex == 155){
                        setBackground(R.drawable.rannia);
                        map.setBackground(getResources().getDrawable(R.drawable.mapa_rannia));
                    }
                    if (dialogueIndex == 159){
                        setBackground(R.drawable.pasillo);
                        sprite2.setVisibility(View.INVISIBLE);
                        sprite3.setVisibility(View.INVISIBLE);
                    }
                    if (dialogueIndex == 161){
                        setBackground(R.drawable.despacho);
                        sprite4.setVisibility(View.VISIBLE);
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
                        if (dialogueIndex == 108) {
                            combatButton.setVisibility(View.VISIBLE);
                            changeDialogueButton.setVisibility(View.INVISIBLE);
                        } else {
                            if (dialogueIndex < dialogues.length && dialogueIndex != 24 && dialogueIndex != 52 && dialogueIndex != 122
                                    && dialogueIndex != 146 && dialogueIndex != 147 && dialogueIndex != 152) {
                                changeDialogueButton.setVisibility(View.VISIBLE);
                            } else {
                                changeDialogueButton.setVisibility(View.INVISIBLE);

                                if (dialogueIndex == 24) {
                                    option1Button.setVisibility(View.VISIBLE);
                                    option2Button.setVisibility(View.VISIBLE);
                                }
                                if (dialogueIndex == 52) {
                                    option1Button.setVisibility(View.VISIBLE);
                                    option2Button.setVisibility(View.VISIBLE);
                                    option1Button.setText("Saludar a Luis");
                                    option2Button.setText("Ir a mi habitación");
                                }
                                if (dialogueIndex == 122) {
                                    option1Button.setVisibility(View.VISIBLE);
                                    option2Button.setVisibility(View.VISIBLE);
                                    option1Button.setText("Luchar");
                                    option2Button.setText("Escapar");
                                }
                                if (dialogueIndex == 146 || dialogueIndex == 147) {
                                    option1Button.setVisibility(View.VISIBLE);
                                    option2Button.setVisibility(View.VISIBLE);
                                    option1Button.setText("Ir al oeste");
                                    option2Button.setText("Ir al sur");
                                }
                                if (dialogueIndex == 152) {
                                    option1Button.setVisibility(View.VISIBLE);
                                    option2Button.setVisibility(View.VISIBLE);
                                    option1Button.setText("Descansar un rato");
                                    option2Button.setText("Continuar");
                                }
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
                    }
                    if (dialogueIndex == 50 && Counter == 1){
                        dialogueIndex = 56;
                    }
                    if(dialogueIndex == 39 && Counter == 0){
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
                    if (dialogueIndex == 122) {
                        option2Button.setOnClickListener(v -> {
                            if (!animationRunning) {
                                option1Button.setVisibility(View.INVISIBLE);
                                option2Button.setVisibility(View.INVISIBLE);
                                dialogueIndex = 132;

                                LuisAffinity -= 5;
                                MariaAffinity += 5;

                                reputation.setVisibility(View.VISIBLE);
                                reputation.setText("\uD83D\uDC94 Luis " + LuisAffinity);

                                affinity_luis.setText("Afinidad= " + LuisAffinity);
                                affinity_maria.setText("Afinidad= " + MariaAffinity);

                                new Handler().postDelayed(() -> {
                                    reputation.setText("❤️ María +" + MariaAffinity);

                                    new Handler().postDelayed(() -> reputation.setVisibility(View.INVISIBLE), 5000);
                                }, 5000);

                                animateText();
                            }
                        });
                    }
                    if (dialogueIndex == 146 || dialogueIndex == 147) {
                        option1Button.setOnClickListener(v -> {
                            option1Button.setVisibility(View.INVISIBLE);
                            option2Button.setVisibility(View.INVISIBLE);
                            dialogueIndex = 147;
                            Counter++;
                            animateText();
                        });
                        option2Button.setOnClickListener(v -> {
                            if (!animationRunning) {
                                option1Button.setVisibility(View.INVISIBLE);
                                option2Button.setVisibility(View.INVISIBLE);
                                dialogueIndex = 146;
                                animateText();
                            }
                        });
                    }
                    if (dialogueIndex == 151) {
                        option1Button.setOnClickListener(v -> {
                            option1Button.setVisibility(View.INVISIBLE);
                            option2Button.setVisibility(View.INVISIBLE);
                            dialogueIndex = 153;

                            LuisAffinity += 5;
                            MariaAffinity -= 5;

                            reputation.setVisibility(View.VISIBLE);
                            reputation.setText("❤️ Luis +" + LuisAffinity);

                            affinity_luis.setText("Afinidad= " + LuisAffinity);
                            affinity_maria.setText("Afinidad= " + MariaAffinity);

                            new Handler().postDelayed(() -> {
                                reputation.setText("\uD83D\uDC94 María " + MariaAffinity);

                                new Handler().postDelayed(() -> reputation.setVisibility(View.INVISIBLE), 5000);
                            }, 5000);

                            animateText();
                        });
                        option2Button.setOnClickListener(v -> {
                            if (!animationRunning) {
                                option1Button.setVisibility(View.INVISIBLE);
                                option2Button.setVisibility(View.INVISIBLE);
                                dialogueIndex = 154;

                                LuisAffinity -= 5;
                                MariaAffinity += 5;

                                reputation.setVisibility(View.VISIBLE);
                                reputation.setText("\uD83D\uDC94 Luis " + LuisAffinity);

                                affinity_luis.setText("Afinidad= " + LuisAffinity);
                                affinity_maria.setText("Afinidad= " + MariaAffinity);

                                new Handler().postDelayed(() -> {
                                    reputation.setText("❤️ María +" + MariaAffinity);

                                    new Handler().postDelayed(() -> reputation.setVisibility(View.INVISIBLE), 5000);
                                }, 5000);

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
                        statsButton.setVisibility(View.VISIBLE);
                        leftButton.setVisibility(View.VISIBLE);
                        rightButton.setVisibility(View.VISIBLE);
                        changeDialogueButton.setClickable(false);
                        option1Button.setClickable(false);
                        option2Button.setClickable(false);
                        if (currentStatsBackground == 1) {
                            affinity_luis.setVisibility(View.VISIBLE);
                            affinity_maria.setVisibility(View.INVISIBLE);
                        } else if (currentStatsBackground == 2) {
                            affinity_luis.setVisibility(View.INVISIBLE);
                            affinity_maria.setVisibility(View.VISIBLE);
                        } else {
                            affinity_luis.setVisibility(View.INVISIBLE);
                            affinity_maria.setVisibility(View.INVISIBLE);
                        }
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
                        map.setVisibility(View.INVISIBLE);
                        statsButton.setVisibility(View.INVISIBLE);
                        leftButton.setVisibility(View.INVISIBLE);
                        rightButton.setVisibility(View.INVISIBLE);
                        changeDialogueButton.setClickable(true);
                        option1Button.setClickable(true);
                        option2Button.setClickable(true);
                        inventoryButton.setClickable(true);
                        affinity_luis.setVisibility(View.INVISIBLE);
                        affinity_maria.setVisibility(View.INVISIBLE);
                    });
                    inventoryButton.setOnClickListener(v -> {
                        inventoryButton.setVisibility(View.INVISIBLE);
                        exitInventoryButton.setVisibility(View.VISIBLE);
                        statsMenu.setVisibility(View.INVISIBLE);
                        menuButton.setVisibility(View.INVISIBLE);
                        saveButton.setVisibility(View.INVISIBLE);
                        exitGameButton.setVisibility(View.INVISIBLE);
                        exitButton.setVisibility(View.INVISIBLE);
                        mapButton.setVisibility(View.INVISIBLE);
                        map.setVisibility(View.INVISIBLE);
                        statsButton.setVisibility(View.INVISIBLE);
                        leftButton.setVisibility(View.INVISIBLE);
                        rightButton.setVisibility(View.INVISIBLE);
                        changeDialogueButton.setClickable(false);
                        option1Button.setClickable(false);
                        option2Button.setClickable(false);
                        menuButton.setClickable(false);
                        inventory.setVisibility(View.VISIBLE);
                        affinity_luis.setVisibility(View.INVISIBLE);
                        affinity_maria.setVisibility(View.INVISIBLE);
                    });
                    exitInventoryButton.setOnClickListener(v -> {
                        inventoryButton.setVisibility(View.VISIBLE);
                        exitInventoryButton.setVisibility(View.INVISIBLE);
                        exitButton.setVisibility(View.VISIBLE);
                        statsMenu.setVisibility(View.VISIBLE);
                        saveButton.setVisibility(View.VISIBLE);
                        exitGameButton.setVisibility(View.VISIBLE);
                        mapButton.setVisibility(View.VISIBLE);
                        statsButton.setVisibility(View.VISIBLE);
                        leftButton.setVisibility(View.VISIBLE);
                        rightButton.setVisibility(View.VISIBLE);
                        inventory.setVisibility(View.INVISIBLE);
                        changeDialogueButton.setClickable(false);
                        option1Button.setClickable(false);
                        option2Button.setClickable(false);
                        menuButton.setClickable(true);
                        if (currentStatsBackground == 1) {
                            affinity_luis.setVisibility(View.VISIBLE);
                            affinity_maria.setVisibility(View.INVISIBLE);
                        } else if (currentStatsBackground == 2) {
                            affinity_luis.setVisibility(View.INVISIBLE);
                            affinity_maria.setVisibility(View.VISIBLE);
                        } else {
                            affinity_luis.setVisibility(View.INVISIBLE);
                            affinity_maria.setVisibility(View.INVISIBLE);
                        }
                    });
                    mapButton.setOnClickListener(v -> {
                        statsMenu.setVisibility(View.INVISIBLE);
                        map.setVisibility(ImageView.VISIBLE);
                        affinity_luis.setVisibility(View.INVISIBLE);
                        affinity_maria.setVisibility(View.INVISIBLE);
                    });
                    statsButton.setOnClickListener(v -> {
                        statsMenu.setVisibility(View.VISIBLE);
                        map.setVisibility(ImageView.INVISIBLE);

                        if (currentStatsBackground == 1) {
                            affinity_luis.setVisibility(View.VISIBLE);
                            affinity_maria.setVisibility(View.INVISIBLE);
                        } else if (currentStatsBackground == 2) {
                            affinity_luis.setVisibility(View.INVISIBLE);
                            affinity_maria.setVisibility(View.VISIBLE);
                        } else {
                            affinity_luis.setVisibility(View.INVISIBLE);
                            affinity_maria.setVisibility(View.INVISIBLE);
                        }
                    });
                    leftButton.setOnClickListener(v -> {
                        currentStatsBackground--;

                        if (currentStatsBackground < 0) {
                            currentStatsBackground = StatsBackgrounds.length - 1;
                        }

                        statsMenu.setBackgroundResource(StatsBackgrounds[currentStatsBackground]);

                        if (currentStatsBackground == 1) {
                            affinity_luis.setVisibility(View.VISIBLE);
                            affinity_maria.setVisibility(View.INVISIBLE);
                        } else if (currentStatsBackground == 2) {
                            affinity_luis.setVisibility(View.INVISIBLE);
                            affinity_maria.setVisibility(View.VISIBLE);
                        } else {
                            affinity_luis.setVisibility(View.INVISIBLE);
                            affinity_maria.setVisibility(View.INVISIBLE);
                        }

                    });
                    rightButton.setOnClickListener(v -> {
                        currentStatsBackground++;

                        if (currentStatsBackground >= StatsBackgrounds.length) {
                            currentStatsBackground = 0;
                        }

                        statsMenu.setBackgroundResource(StatsBackgrounds[currentStatsBackground]);

                        if (currentStatsBackground == 1) {
                            affinity_luis.setVisibility(View.VISIBLE);
                            affinity_maria.setVisibility(View.INVISIBLE);
                        } else if (currentStatsBackground == 2) {
                            affinity_luis.setVisibility(View.INVISIBLE);
                            affinity_maria.setVisibility(View.VISIBLE);
                        } else {
                            affinity_luis.setVisibility(View.INVISIBLE);
                            affinity_maria.setVisibility(View.INVISIBLE);
                        }
                    });

                    combatButton.setOnClickListener(v -> {
                        if (dialogueIndex == 108) {
                            Intent intent = new Intent(Prologue.this, Prologue_Combat1.class);
                            startActivity(intent);
                        }
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

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void setBackgroundForDialogue(int dialogueIndex) {
        switch (dialogueIndex) {
            case 8, 9, 10 -> {
                setBackground(R.drawable.velanimas_entrada);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_2));
            }
            case 11, 12, 13, 14, 15 -> {
                setBackground(R.drawable.casa_exterior);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
            }
            case 16, 17, 18, 19, 20, 21, 22, 23, 86, 87, 88, 89, 90, 91, 92 -> {
                setBackground(R.drawable.sala_estar);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
                sprite1.setVisibility(View.VISIBLE);
            }
            case 24 -> {
                setBackground(R.drawable.habitaciones);
                sprite1.setVisibility(View.INVISIBLE);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
                option1Button.setText("Luis");
                option2Button.setText("María");
            }
            case 26, 27, 28, 29, 41, 42, 43, 44 -> {
                setBackground(R.drawable.habitaciones);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
                sprite2.setVisibility(View.INVISIBLE);
                sprite3.setVisibility(View.INVISIBLE);
            }
            case 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40 -> {
                setBackground(R.drawable.habitacion_luis);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
                sprite2.setVisibility(View.VISIBLE);
            }
            case 45, 46, 47, 48, 49, 50, 51, 52, 56, 57 -> {
                setBackground(R.drawable.habitacion_maria);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
                sprite3.setVisibility(View.VISIBLE);
            }
            case 58, 59, 60, 61, 62, 63 -> {
                setBackground(R.drawable.habitacion_invitado);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
                sprite2.setVisibility(View.INVISIBLE);
                sprite3.setVisibility(View.VISIBLE);
            }
            case 64 -> {
                final int screenVisibleTimeMillis = 3000;
                setBackground(R.drawable.darkness);
                screen.setVisibility(View.VISIBLE);
                sprite3.setVisibility(View.INVISIBLE);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
                new Handler().postDelayed(() -> screen.setVisibility(View.INVISIBLE), screenVisibleTimeMillis);
            }
            case 65, 66, 67, 68 -> {
                setBackground(R.drawable.darkness);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
                sprite3.setVisibility(View.INVISIBLE);
            }
            case 69, 70 -> {
                setBackground(R.drawable.luz);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
            }
            case 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83 -> {
                setBackground(R.drawable.luz_cerca);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
            }
            case 84, 85 -> {
                setBackground(R.drawable.habitacion_invitado);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
            }
            case 93, 94, 95, 96 -> {
                setBackground(R.drawable.cocina);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
                sprite2.setVisibility(View.VISIBLE);
            }
            case 97, 99, 100, 101, 102, 103, 104, 105, 106 -> {
                setBackground(R.drawable.jardin);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
                sprite2.setVisibility(View.VISIBLE);
            }
            case 98 -> {
                object.setVisibility(View.VISIBLE);
                sprite2.setVisibility(View.VISIBLE);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_3));
            }
            case 107, 108, 109 -> {
                setBackground(R.drawable.bosque);
                sprite2.setVisibility(View.VISIBLE);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_4));
            }
            case 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 133, 134, 135, 136, 137 -> {
                setBackground(R.drawable.bosque);
                sprite2.setVisibility(View.VISIBLE);
                sprite3.setVisibility(View.VISIBLE);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_4));
            }
            case 138, 139, 140, 141, 142, 143, 144, 145, 146, 147 -> {
                setBackground(R.drawable.cruce);
                sprite2.setVisibility(View.VISIBLE);
                sprite3.setVisibility(View.VISIBLE);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_5));
            }
            case 148, 149, 150, 151, 152, 154, 155 -> {
                setBackground(R.drawable.mirador);
                sprite2.setVisibility(View.VISIBLE);
                sprite3.setVisibility(View.VISIBLE);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_6));
            }
            case 156 -> {
                setBackground(R.drawable.rannia);
                sprite2.setVisibility(View.VISIBLE);
                sprite3.setVisibility(View.VISIBLE);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_rannia));
            }

            default -> {
                setBackground(R.drawable.coche);
                map.setBackground(getResources().getDrawable(R.drawable.mapa_yurei));
            }
        }
    }

    private void saveGameProgress() {
        SharedPreferences sharedPreferences = getSharedPreferences("GameProgress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("dialogueIndex", dialogueIndex);
        editor.putInt("charIndex", charIndex);
        editor.putString("currentDialogue", textView.getText().toString());
        editor.putInt("MariaAffinity", MariaAffinity);
        editor.putInt("LuisAffinity", LuisAffinity);
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