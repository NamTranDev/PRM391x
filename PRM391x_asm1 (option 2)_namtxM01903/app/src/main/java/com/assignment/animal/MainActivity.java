package com.assignment.animal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivElephant, ivDragonFly, ivDolphin, ivDog, ivPig, ivDuck, ivLadybug, ivTurtle,
            ivPenguin;
    ImageView ivFavoriteElephant, ivFavoriteDragonFly, ivFavoriteDolphin, ivFavoriteDog,
            ivFavoritePig, ivFavoriteDuck, ivFavoriteLadybug, ivFavoriteTurtle, ivFavoritePenguin;
    boolean favoriteState = false;
    String animalType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {

        ivFavoriteElephant = findViewById(R.id.ivFavoriteElephant);
        ivFavoriteDragonFly = findViewById(R.id.ivFavoriteDragonFly);
        ivFavoriteDolphin = findViewById(R.id.ivFavoriteDolphin);
        ivFavoriteDog = findViewById(R.id.ivFavoriteDog);
        ivFavoritePig = findViewById(R.id.ivFavoritePig);
        ivFavoriteDuck = findViewById(R.id.ivFavoriteDuck);
        ivFavoriteLadybug = findViewById(R.id.ivFavoriteLadybug);
        ivFavoriteTurtle = findViewById(R.id.ivFavoriteTurtle);
        ivFavoritePenguin = findViewById(R.id.ivFavoritePenguin);

        ivElephant = findViewById(R.id.ivElephant);
        ivElephant.setOnClickListener(this);

        ivDragonFly = findViewById(R.id.ivDragonFly);
        ivDragonFly.setOnClickListener(this);

        ivDolphin = findViewById(R.id.ivDolphin);
        ivDolphin.setOnClickListener(this);

        ivDog = findViewById(R.id.ivDog);
        ivDog.setOnClickListener(this);

        ivPig = findViewById(R.id.ivPig);
        ivPig.setOnClickListener(this);

        ivDuck = findViewById(R.id.ivDuck);
        ivDuck.setOnClickListener(this);

        ivLadybug = findViewById(R.id.ivLadybug);
        ivLadybug.setOnClickListener(this);

        ivTurtle = findViewById(R.id.ivTurtle);
        ivTurtle.setOnClickListener(this);

        ivPenguin = findViewById(R.id.ivPenguin);
        ivPenguin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, DetailActivity.class);

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_fadein);

        if (view.getId() == R.id.ivElephant) {

            ivElephant.startAnimation(animFadeIn);

            intent.putExtra("ANIMAL", "elephant");

            favoriteState = ivFavoriteElephant.getVisibility() != View.GONE;
            intent.putExtra("FAVORITE_MAIN", favoriteState);

        } else if (view.getId() == R.id.ivDragonFly) {

            ivDragonFly.startAnimation(animFadeIn);

            intent.putExtra("ANIMAL", "dragonfly");

            favoriteState = ivFavoriteDragonFly.getVisibility() != View.GONE;
            intent.putExtra("FAVORITE_MAIN", favoriteState);

        } else if (view.getId() == R.id.ivDolphin) {

            ivDolphin.startAnimation(animFadeIn);

            intent.putExtra("ANIMAL", "dolphin");

            favoriteState = ivFavoriteDolphin.getVisibility() != View.GONE;
            intent.putExtra("FAVORITE_MAIN", favoriteState);

        } else if (view.getId() == R.id.ivDog) {

            ivDog.startAnimation(animFadeIn);

            intent.putExtra("ANIMAL", "dog");

            favoriteState = ivFavoriteDog.getVisibility() != View.GONE;
            intent.putExtra("FAVORITE_MAIN", favoriteState);

        } else if (view.getId() == R.id.ivPig) {

            ivPig.startAnimation(animFadeIn);

            intent.putExtra("ANIMAL", "pig");

            favoriteState = ivFavoritePig.getVisibility() != View.GONE;
            intent.putExtra("FAVORITE_MAIN", favoriteState);

        } else if (view.getId() == R.id.ivDuck) {

            ivDuck.startAnimation(animFadeIn);

            intent.putExtra("ANIMAL", "duck");

            favoriteState = ivFavoriteDuck.getVisibility() != View.GONE;
            intent.putExtra("FAVORITE_MAIN", favoriteState);

        } else if (view.getId() == R.id.ivLadybug) {

            ivLadybug.startAnimation(animFadeIn);

            intent.putExtra("ANIMAL", "ladybug");

            favoriteState = ivFavoriteLadybug.getVisibility() != View.GONE;
            intent.putExtra("FAVORITE_MAIN", favoriteState);

        } else if (view.getId() == R.id.ivTurtle) {

            ivTurtle.startAnimation(animFadeIn);

            intent.putExtra("ANIMAL", "turtle");

            favoriteState = ivFavoriteTurtle.getVisibility() != View.GONE;
            intent.putExtra("FAVORITE_MAIN", favoriteState);

        } else if (view.getId() == R.id.ivPenguin) {

            ivPenguin.startAnimation(animFadeIn);

            intent.putExtra("ANIMAL", "penguin");

            favoriteState = ivFavoritePenguin.getVisibility() != View.GONE;
            intent.putExtra("FAVORITE_MAIN", favoriteState);
        }

        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 101) {

            favoriteState = data.getBooleanExtra("FAV", false);
            animalType = data.getStringExtra("TYPE");

            if (animalType != null) {
                switch (animalType) {
                    case "elephant":
                        if (favoriteState) {
                            ivFavoriteElephant.setVisibility(View.VISIBLE);
                        } else {
                            ivFavoriteElephant.setVisibility(View.GONE);
                        }
                        break;

                    case "dragonfly":
                        if (favoriteState) {
                            ivFavoriteDragonFly.setVisibility(View.VISIBLE);
                        } else {
                            ivFavoriteDragonFly.setVisibility(View.GONE);
                        }
                        break;

                    case "dolphin":
                        if (favoriteState) {
                            ivFavoriteDolphin.setVisibility(View.VISIBLE);
                        } else {
                            ivFavoriteDolphin.setVisibility(View.GONE);
                        }
                        break;

                    case "dog":
                        if (favoriteState) {
                            ivFavoriteDog.setVisibility(View.VISIBLE);
                        } else {
                            ivFavoriteDog.setVisibility(View.GONE);
                        }
                        break;

                    case "pig":
                        if (favoriteState) {
                            ivFavoritePig.setVisibility(View.VISIBLE);
                        } else {
                            ivFavoritePig.setVisibility(View.GONE);
                        }
                        break;

                    case "duck":
                        if (favoriteState) {
                            ivFavoriteDuck.setVisibility(View.VISIBLE);
                        } else {
                            ivFavoriteDuck.setVisibility(View.GONE);
                        }
                        break;

                    case "ladybug":
                        if (favoriteState) {
                            ivFavoriteLadybug.setVisibility(View.VISIBLE);
                        } else {
                            ivFavoriteLadybug.setVisibility(View.GONE);
                        }
                        break;

                    case "turtle":
                        if (favoriteState) {
                            ivFavoriteTurtle.setVisibility(View.VISIBLE);
                        } else {
                            ivFavoriteTurtle.setVisibility(View.GONE);
                        }
                        break;

                    case "penguin":
                        if (favoriteState) {
                            ivFavoritePenguin.setVisibility(View.VISIBLE);
                        } else {
                            ivFavoritePenguin.setVisibility(View.GONE);
                        }
                        break;
                }
            }


        }
    }
}