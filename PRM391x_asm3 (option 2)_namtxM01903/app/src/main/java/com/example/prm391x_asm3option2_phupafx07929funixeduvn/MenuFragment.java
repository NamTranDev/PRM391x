package com.example.prm391x_asm3option2_phupafx07929funixeduvn;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {

    private static final String TAG = MenuFragment.class.getName();
    SharedPreferences sharedPref;
    private Context mContext;
    private RecyclerView rvAnimal;
    private List<Animal> listAnimal;
    private DrawerLayout mDrawer;
    private RecyclerView.LayoutManager mLayoutManager;
    private String animalType;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
        sharedPref = mContext.getSharedPreferences("FILE_SAVED", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        mDrawer = view.findViewById(R.id.drawer);
        rvAnimal = view.findViewById(R.id.rv_animal);

        if (listAnimal == null) {
            initView(view);
        } else {
            AnimalAdapter animalAdapter = new AnimalAdapter(listAnimal, mContext, animalType);
            rvAnimal.setAdapter(animalAdapter);

            initView(view);
        }

        return view;

    }


    private void initView(View view) {

        view.findViewById(R.id.iv_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });

        view.findViewById(R.id.iv_sea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnimals("sea");
            }
        });

        view.findViewById(R.id.iv_mammal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnimals("mammal");
            }
        });

        view.findViewById(R.id.iv_bird).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnimals("bird");
            }
        });
    }

    private void showAnimals(String animalType) {
        this.animalType = animalType;

        mDrawer.closeDrawer(GravityCompat.START, true);

        listAnimal = new ArrayList<Animal>();

        try {
            String[] listAnimals = mContext.getAssets().list("photo/" + animalType);

            for (String item : listAnimals) {
                String path = "photo/" + animalType + "/" + item;
                Bitmap photo = BitmapFactory.decodeStream(mContext.getAssets().open(path));
                String photoName = item.substring(3, item.indexOf("."));
                String name = photoName.substring(0, 1).toUpperCase() + photoName.substring(1).toLowerCase().replace("_", " ");
                Bitmap photoBg = BitmapFactory.decodeStream(mContext.getAssets().open("photo_bg/" + animalType + "/bg_" + photoName + ".jpg"));
                Boolean isFav = sharedPref.getBoolean(path, false);//false;

                InputStream in = null;
                in = mContext.getAssets().open("text/" + animalType + "/" + photoName + ".txt");

                BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

                String str = null;
                StringBuilder description = new StringBuilder();

                while ((str = br.readLine()) != null) {
                    description.append(str);
                }
                br.close();

                String content = description.toString();

                Log.i(TAG, "path :" + path);
                listAnimal.add(new Animal(path, photo, photoBg, name, content, isFav));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnimalAdapter animalAdapter = new AnimalAdapter(listAnimal, mContext, animalType);
        rvAnimal.setAdapter(animalAdapter);
    }


}
