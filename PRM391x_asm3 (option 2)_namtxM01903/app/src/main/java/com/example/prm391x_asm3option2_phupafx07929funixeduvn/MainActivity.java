package com.example.prm391x_asm3option2_phupafx07929funixeduvn;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews() {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.ln_main, new MenuFragment(), null)
                .addToBackStack(null).commit();

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG
            }, 100);
        }
    }

    public void gotoDetailFragment(String animalType, List<Animal> listAnimal, Animal animal) {
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setData(listAnimal, animalType, animal);

        Log.i(TAG, "gotoDetailFragment: pass");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ln_main, detailFragment, null)
                .addToBackStack(null)
                .commit();


    }

    public void backtoMenuFragment(String animalType, List<Animal> listAnimal) {
        getSupportFragmentManager().popBackStackImmediate();

    }
}