package nam.tran.prm391x_project_1_nam_tran;

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

        showAnimals("sea");
        if (listAnimal != null) {
            AnimalAdapter animalAdapter = new AnimalAdapter(listAnimal, mContext, animalType);
            rvAnimal.setAdapter(animalAdapter);

        }
        initView(view);

        return view;

    }


    private void initView(View view) {

        view.findViewById(R.id.iv_menu).setOnClickListener(view1 -> mDrawer.openDrawer(GravityCompat.START));

        view.findViewById(R.id.contain_sea).setOnClickListener(view12 -> showAnimals("sea"));

        view.findViewById(R.id.contain_mammal).setOnClickListener(view13 -> showAnimals("mammal"));

        view.findViewById(R.id.contain_bird).setOnClickListener(view14 -> showAnimals("bird"));
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
