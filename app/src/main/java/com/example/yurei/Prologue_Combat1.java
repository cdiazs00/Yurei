package com.example.yurei;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Prologue_Combat1 extends AppCompatActivity {

    private View ally1, MagicMenu, ItemMenu;
    private AnimatedImageDrawable animationDrawable;
    ImageView gif1, fireball_gif, explosion_gif, enemy_marker1;
    private List<Allies> AllyList;
    private List<Enemies> EnemyList;
    private TextView ally_stats, enemy_stats, Dialogues;
    private Enemies selectedEnemy;
    private boolean isFireballSelected = false;
    private Handler dialogueHandler;
    private int enemyButtonPressCount = 0;

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prologue_combat1);

        ally1 = findViewById(R.id.ally1);
        MagicMenu = findViewById(R.id.magicmenu);
        ItemMenu = findViewById(R.id.itemmenu);
        Dialogues = findViewById(R.id.dialogues);
        gif1 = findViewById(R.id.gif1);
        fireball_gif = findViewById(R.id.fireball_gif);
        explosion_gif = findViewById(R.id.explosion_gif);
        enemy_marker1 = findViewById(R.id.enemy_marker1);
        enemy_marker1.setVisibility(View.INVISIBLE);

        Button attackButton = findViewById(R.id.attack);
        Button magicButton = findViewById(R.id.magic);
        Button fireballButton = findViewById(R.id.fireball);
        Button itemButton = findViewById(R.id.item);
        Button phialButton = findViewById(R.id.phial);
        Button enemyButton1 = findViewById(R.id.enemy_button1);

        ally_stats = findViewById(R.id.ally_stats);
        enemy_stats = findViewById(R.id.enemy_stats);

        AllyList = new ArrayList<>();
        EnemyList = new ArrayList<>();

        List<String> skillsLuis = new ArrayList<>();
        skillsLuis.add("Zarpazo");

        AllyList.add(new Allies("Cristina", 40, 55, 0, 5));
        EnemyList.add(new Enemies("Luis", 50, skillsLuis));

        displayAllyStats();
        displayEnemyStats();

        setupDialogueSequence();

        attackButton.setOnClickListener(v -> {
            enemyButton1.setVisibility(View.VISIBLE);
            enemy_marker1.setVisibility(View.VISIBLE);
            MagicMenu.setVisibility(View.INVISIBLE);
            ItemMenu.setVisibility(View.INVISIBLE);
            isFireballSelected = false;
        });

        magicButton.setOnClickListener(v -> {
            enemyButton1.setVisibility(View.INVISIBLE);
            enemy_marker1.setVisibility(View.INVISIBLE);
            MagicMenu.setVisibility(View.VISIBLE);
            ItemMenu.setVisibility(View.INVISIBLE);
            isFireballSelected = false;
        });

        fireballButton.setOnClickListener(v -> {
            enemyButton1.setVisibility(View.VISIBLE);
            enemy_marker1.setVisibility(View.VISIBLE);
            isFireballSelected = true;
        });

        itemButton.setOnClickListener(v -> {
            enemyButton1.setVisibility(View.INVISIBLE);
            enemy_marker1.setVisibility(View.INVISIBLE);
            MagicMenu.setVisibility(View.INVISIBLE);
            ItemMenu.setVisibility(View.VISIBLE);
            isFireballSelected = false;
        });

        phialButton.setOnClickListener(v -> {
            showFifthDialogue();
            ItemMenu.setVisibility(View.INVISIBLE);
            restorePM();
        });

        enemyButton1.setOnClickListener(v -> {
            selectedEnemy = EnemyList.get(0);
            enemyButtonPressCount++;
            if (isFireballSelected) {
                animateFireball();
            } else {
                animateSprites();
            }
            enemyButton1.setVisibility(View.INVISIBLE);
            MagicMenu.setVisibility(View.INVISIBLE);
            enemy_marker1.setVisibility(View.INVISIBLE);
            if (enemyButtonPressCount == 1) {
                showThirdDialogue();
            } else if (enemyButtonPressCount == 2) {
                showFourthDialogue();
            }
        });
    }

    private void setupDialogueSequence() {
        dialogueHandler = new Handler(Looper.getMainLooper());
        final String[] dialogueLines = {
                "Muy bien, empecemos por lo básico.",
                "Realiza un ATAQUE básico cuerpo a cuerpo."
        };

        Runnable dialogueRunnable = new Runnable() {
            int currentLineIndex = 0;

            @Override
            public void run() {
                if (currentLineIndex < dialogueLines.length) {
                    Dialogues.setText(dialogueLines[currentLineIndex]);
                    currentLineIndex++;
                    dialogueHandler.postDelayed(this, 5000);
                } else {
                    Dialogues.setVisibility(View.INVISIBLE);
                }
            }
        };

        dialogueHandler.post(dialogueRunnable);
    }

    private void showThirdDialogue() {
        final String thirdDialogueLine = "No está mal, ahora veamos cómo está tu MAGIA.";
        Dialogues.setVisibility(View.VISIBLE);
        Dialogues.setText(thirdDialogueLine);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> Dialogues.setVisibility(View.INVISIBLE), 5000);
    }

    private void showFourthDialogue() {
        final String fourthDialogueLine = "Muy bien, ahora bebe del VIAL y recupera tus PM.";
        Dialogues.setVisibility(View.VISIBLE);
        Dialogues.setText(fourthDialogueLine);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> Dialogues.setVisibility(View.INVISIBLE), 5000);
    }

    private void showFifthDialogue() {
        final String fifthDialogueLine = "Muy bien, dejémoslo por ahora";
        Dialogues.setVisibility(View.VISIBLE);
        Dialogues.setText(fifthDialogueLine);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            Dialogues.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(Prologue_Combat1.this, Prologue.class);
            startActivity(intent);
            finish();
        }, 5000);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void displayAllyStats() {
        StringBuilder allyInfoBuilder = new StringBuilder();
        for (Allies ally : AllyList) {
            String allyInfo = " " + ally.getNombre() + " PV: " + ally.getPV() + " PM: " + ally.getPM() + "\n";
            allyInfoBuilder.append(allyInfo);
        }
        ally_stats.setText(allyInfoBuilder.toString());
        ally_stats.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void displayEnemyStats() {
        StringBuilder enemyInfoBuilder = new StringBuilder();
        for (Enemies enemy : EnemyList) {
            String enemyInfo = enemy.getNombre() + " " + enemy.getPV() + "\n";
            enemyInfoBuilder.append(enemyInfo);
        }
        enemy_stats.setText(enemyInfoBuilder.toString());
        enemy_stats.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void animateSprites() {
        View selectedOption = findViewById(R.id.enemy1);
        ImageView selectedOptionGif = gif1;

        if (selectedOption != null && selectedOptionGif != null) {
            ally1.setBackgroundResource(R.drawable.sora2);
            GifCoordinates(ally1, selectedOption, selectedOptionGif);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void animateFireball() {
        View selectedOption = findViewById(R.id.enemy1);
        ImageView selectedOptionGif = fireball_gif;

        Allies ally = AllyList.get(0);
        int fireballCost = 10;
        if (ally.getPM() >= fireballCost) {
            ally.setPM(ally.getPM() - fireballCost);
            displayAllyStats();
        } else {
            return;
        }

        if (selectedOption != null && selectedOptionGif != null) {
            fireball_gif.setVisibility(View.VISIBLE);
            GifCoordinates(fireball_gif, selectedOption, selectedOptionGif);
        }
    }

    private void restorePM() {
        int pmToRestore = 10;
        for (Allies ally : AllyList) {
            ally.setPM(ally.getPM() + pmToRestore);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            displayAllyStats();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void GifCoordinates(View sprite, View targetView, ImageView gif) {
        float targetX = targetView.getX();
        float targetY = targetView.getY();

        float spriteX = sprite.getX();
        float spriteY = sprite.getY();

        float deltaX = targetX - spriteX - 175;
        float deltaY = targetY - spriteY;

        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation moveRight = new TranslateAnimation(0, deltaX, 0, deltaY);
        moveRight.setDuration(1400);

        animationSet.addAnimation(moveRight);

        animationSet.setAnimationListener(new TranslateAnimation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(() -> showGif(gif), 1000);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isFireballSelected) {
                    applyFireballDamageToEnemy(selectedEnemy);
                    gif.setVisibility(View.INVISIBLE);
                } else {
                    applyDamageToEnemy(selectedEnemy);
                    ally1.setBackgroundResource(R.drawable.sora);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        sprite.startAnimation(animationSet);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void showGif(ImageView gifImageView) {
        gifImageView.setVisibility(View.VISIBLE);
        gifImageView.setImageResource(isFireballSelected ? R.drawable.fireball : R.drawable.sword_slash);

        if (isFireballSelected) {
            gifImageView.setImageResource(R.drawable.fireball);
            Handler handler = new Handler(Looper.getMainLooper());

            handler.postDelayed(() -> {
                explosion_gif.setVisibility(View.VISIBLE);
                explosion_gif.setImageResource(R.drawable.explosion);

                handler.postDelayed(() -> explosion_gif.setVisibility(View.INVISIBLE), 1000);
            }, 500);
        } else {
            gifImageView.setImageResource(R.drawable.sword_slash);
        }

        animationDrawable = (AnimatedImageDrawable) gifImageView.getDrawable();
        animationDrawable.start();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> hideGif(gifImageView), 0);
    }

    private void applyDamageToEnemy(Enemies enemy) {
        if (enemy != null) {
            int damage = AllyList.get(0).getATK();

            enemy.setPV(enemy.getPV() - damage);
            if (enemy.getPV() <= 0) {
                enemy.setPV(0);
                if (EnemyList.indexOf(enemy) == 0) {
                    findViewById(R.id.enemy1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.enemy_button1).setEnabled(false);
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                displayEnemyStats();
            }
        }
    }

    private void applyFireballDamageToEnemy(Enemies enemy) {
        if (enemy != null) {
            int damage = 0;

            enemy.setPV(enemy.getPV() - damage);
            if (enemy.getPV() <= 0) {
                enemy.setPV(0);
                if (EnemyList.indexOf(enemy) == 0) {
                    findViewById(R.id.enemy1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.enemy_button1).setEnabled(false);
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                displayEnemyStats();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void hideGif(ImageView gifImageView) {
        animationDrawable.stop();
        gifImageView.setVisibility(View.INVISIBLE);
    }
}