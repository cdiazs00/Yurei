package com.example.yurei;

import android.annotation.SuppressLint;
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

public class SkeletonRPG extends AppCompatActivity {

    private View ally1, ally2, ally3, MagicMenu;
    private AnimatedImageDrawable animationDrawable;
    private int spriteTurn = 1;
    ImageView gif1, gif2, gif3;
    private List<Allies> AllyList;
    private List<Enemies> EnemyList;
    private TextView ally_stats;
    private TextView enemy_stats;
    private Enemies selectedEnemy;

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rpg_skeleton);

        ally1 = findViewById(R.id.ally1);
        ally2 = findViewById(R.id.ally2);
        ally3 = findViewById(R.id.ally3);

        MagicMenu = findViewById(R.id.magicmenu);

        gif1 = findViewById(R.id.gif1);
        gif2 = findViewById(R.id.gif2);
        gif3 = findViewById(R.id.gif3);

        Button attackButton = findViewById(R.id.attack);
        Button magicButton = findViewById(R.id.magic);
        Button itemButton = findViewById(R.id.item);
        Button ultimateButton = findViewById(R.id.ultimate);

        Button enemyButton1 = findViewById(R.id.enemy_button1);
        Button enemyButton2 = findViewById(R.id.enemy_button2);
        Button enemyButton3 = findViewById(R.id.enemy_button3);

        ally_stats = findViewById(R.id.ally_stats);
        enemy_stats = findViewById(R.id.enemy_stats);

        AllyList = new ArrayList<>();
        EnemyList = new ArrayList<>();

        List<String> skillsSombra = new ArrayList<>();
        skillsSombra.add("Zarpazo");
        List<String> skillsHechicero = new ArrayList<>();
        skillsHechicero.add("Bola de fuego");
        skillsHechicero.add("Rayo");

        AllyList.add(new Allies("Sora", 100, 75, 10, 5));
        AllyList.add(new Allies("Cloud", 150, 60, 15, 7));
        AllyList.add(new Allies("Sephiroth", 200, 100, 20, 10));

        EnemyList.add(new Enemies("Sombra", 50, skillsSombra));
        EnemyList.add(new Enemies("Hechicero", 100, skillsHechicero));
        EnemyList.add(new Enemies("Sombra", 50, skillsSombra));

        displayAllyStats();
        displayEnemyStats();

        attackButton.setOnClickListener(v -> {
            enemyButton1.setVisibility(View.VISIBLE);
            enemyButton2.setVisibility(View.VISIBLE);
            enemyButton3.setVisibility(View.VISIBLE);
            MagicMenu.setVisibility(View.INVISIBLE);
        });

        magicButton.setOnClickListener(v -> {
            enemyButton1.setVisibility(View.INVISIBLE);
            enemyButton2.setVisibility(View.INVISIBLE);
            enemyButton3.setVisibility(View.INVISIBLE);
            MagicMenu.setVisibility(View.VISIBLE);
        });

        enemyButton1.setOnClickListener(v -> {
            selectedEnemy = EnemyList.get(0);
            animateSprites(0);
            enemyButton1.setVisibility(View.INVISIBLE);
            enemyButton2.setVisibility(View.INVISIBLE);
            enemyButton3.setVisibility(View.INVISIBLE);
        });

        enemyButton2.setOnClickListener(v -> {
            selectedEnemy = EnemyList.get(1);
            animateSprites(1);
            enemyButton1.setVisibility(View.INVISIBLE);
            enemyButton2.setVisibility(View.INVISIBLE);
            enemyButton3.setVisibility(View.INVISIBLE);
        });

        enemyButton3.setOnClickListener(v -> {
            selectedEnemy = EnemyList.get(2);
            animateSprites(2);
            enemyButton1.setVisibility(View.INVISIBLE);
            enemyButton2.setVisibility(View.INVISIBLE);
            enemyButton3.setVisibility(View.INVISIBLE);
        });
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
    private void animateSprites(int selectedOptionIndex) {
        View selectedOption = null;
        ImageView selectedOptionGif = null;

        switch (selectedOptionIndex) {
            case 0:
                selectedOption = findViewById(R.id.enemy1);
                selectedOptionGif = gif1;
                break;
            case 1:
                selectedOption = findViewById(R.id.enemy2);
                selectedOptionGif = gif2;
                break;
            case 2:
                selectedOption = findViewById(R.id.enemy3);
                selectedOptionGif = gif3;
                break;
        }

        if (selectedOption != null && selectedOptionGif != null) {
            switch (spriteTurn) {
                case 1:
                    ally1.setBackgroundResource(R.drawable.sora2);
                    GifCoordinates(ally1, selectedOption, selectedOptionGif);
                    break;
                case 2:
                    ally2.setBackgroundResource(R.drawable.sora2);
                    GifCoordinates(ally2, selectedOption, selectedOptionGif);
                    break;
                case 3:
                    ally3.setBackgroundResource(R.drawable.sora2);
                    GifCoordinates(ally3, selectedOption, selectedOptionGif);
                    break;
            }
            spriteTurn = (spriteTurn % 3) + 1;
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
                applyDamageToEnemy(selectedEnemy);
                switch (spriteTurn) {
                    case 1:
                        ally1.setBackgroundResource(R.drawable.sora);
                        ally2.setBackgroundResource(R.drawable.cloud);
                        ally3.setBackgroundResource(R.drawable.sephiroth);
                        break;
                    case 2:
                        ally1.setBackgroundResource(R.drawable.sora);
                        ally2.setBackgroundResource(R.drawable.cloud);
                        ally3.setBackgroundResource(R.drawable.sephiroth);
                        break;
                    case 3:
                        ally1.setBackgroundResource(R.drawable.sora);
                        ally2.setBackgroundResource(R.drawable.cloud);
                        ally3.setBackgroundResource(R.drawable.sephiroth);
                        break;
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
        gifImageView.setImageResource(R.drawable.sword_slash);
        animationDrawable = (AnimatedImageDrawable) gifImageView.getDrawable();
        animationDrawable.start();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> hideGif(gifImageView), 500);
    }

    private void applyDamageToEnemy(Enemies enemy) {
        if (enemy != null) {
            int attackerATK = 0;
            switch (spriteTurn) {
                case 1:
                    attackerATK = AllyList.get(2).getATK();
                    break;
                case 2:
                    attackerATK = AllyList.get(0).getATK();
                    break;
                case 3:
                    attackerATK = AllyList.get(1).getATK();
                    break;
            }
            int damage = attackerATK;

            enemy.setPV(enemy.getPV() - damage);
            if (enemy.getPV() <= 0) {
                enemy.setPV(0);
                switch (EnemyList.indexOf(enemy)) {
                    case 0:
                        findViewById(R.id.enemy1).setVisibility(View.INVISIBLE);
                        findViewById(R.id.enemy_button1).setEnabled(false);
                        break;
                    case 1:
                        findViewById(R.id.enemy2).setVisibility(View.INVISIBLE);
                        findViewById(R.id.enemy_button2).setEnabled(false);
                        break;
                    case 2:
                        findViewById(R.id.enemy3).setVisibility(View.INVISIBLE);
                        findViewById(R.id.enemy_button3).setEnabled(false);
                        break;
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