package com.example.prm391x_asm3option2_phupafx07929funixeduvn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalHolder> {

    private List<Animal> listAnimal;
    private Context mContext;
    private String animalType;

    public AnimalAdapter(List<Animal> listAnimal, Context mContext, String animalType) {
        this.listAnimal = listAnimal;
        this.mContext = mContext;
        this.animalType = animalType;
    }

    @NonNull
    @Override
    public AnimalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_animal, parent, false);
        return new AnimalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalHolder holder, int position) {
        Animal animal = listAnimal.get(position);

        holder.iv_item_animal.setImageBitmap(animal.getPhoto());
        holder.tv_item_animal.setText(animal.getName());
        holder.iv_item_animal.setTag(animal);

        if (animal.isFav() == true) {
            holder.iv_fav.setVisibility(View.VISIBLE);
        } else if (animal.isFav() == false) {
            holder.iv_fav.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listAnimal.size();
    }

    public class AnimalHolder extends RecyclerView.ViewHolder {

        ImageView iv_item_animal;
        TextView tv_item_animal;
        ImageView iv_fav;

        public AnimalHolder(@NonNull View itemView) {
            super(itemView);

            iv_item_animal = itemView.findViewById(R.id.iv_item_animal);
            tv_item_animal = itemView.findViewById(R.id.tv_item_animal);
            iv_fav = itemView.findViewById(R.id.iv_fav);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    view.startAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_grow_fade_in_from_bottom));

                    ((MainActivity) mContext).gotoDetailFragment(animalType, listAnimal, (Animal) itemView.findViewById(R.id.iv_item_animal).getTag());

                }
            });
        }
    }
}
