package com.example.prm391x_asm3option2_phupafx07929funixeduvn;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class DetailFragment extends Fragment {

    private static String TAG = DetailFragment.class.getName();
    private Context mContext;
    private List<Animal> listAnimal;
    private Animal currentItem;
    private String animalType;


    public void setData(List<Animal> listAnimal, String animalType, Animal currentItem) {
        this.listAnimal = listAnimal;
        this.animalType = animalType;
        this.currentItem = currentItem;

        Log.i(TAG, "listAnimal: " + listAnimal.get(0).getName());
        Log.i(TAG, "animalType: " + animalType);
        Log.i(TAG, "currentItem: " + currentItem.getName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, null);
        initViews(view);
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initViews(View view) {

        view.findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        view.findViewById(R.id.iv_menu).setVisibility(View.GONE);


        view.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).backtoMenuFragment(animalType, listAnimal);
            }
        });

        ViewPager vp = view.findViewById(R.id.vp_animal_dtl);
        DetailAnimalAdapter adapter = new DetailAnimalAdapter(listAnimal, mContext);

        vp.setAdapter(adapter);
        vp.setCurrentItem(listAnimal.indexOf(currentItem));
    }
}
