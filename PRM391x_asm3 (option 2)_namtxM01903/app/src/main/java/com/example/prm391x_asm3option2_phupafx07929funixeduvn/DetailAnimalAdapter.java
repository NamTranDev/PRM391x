package com.example.prm391x_asm3option2_phupafx07929funixeduvn;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class DetailAnimalAdapter extends PagerAdapter {

    private static String TAG = DetailAnimalAdapter.class.getName();
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private Context mContext;
    private List<Animal> listAnimal;


    public DetailAnimalAdapter(List<Animal> listAnimal, Context mContext) {
        this.listAnimal = listAnimal;
        this.mContext = mContext;
        sharedPref = mContext.getSharedPreferences("FILE_SAVED", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_detail_animal, container, false);
        Animal item = listAnimal.get(position);

        ImageView iv_bg = view.findViewById(R.id.iv_animal_dtl_background);
        TextView tv_name = view.findViewById(R.id.tv_animal_dtl_name);
        TextView tv_description = view.findViewById(R.id.tv_animal_dtl_text);
        ImageView iv_fav = view.findViewById(R.id.iv_fav);
        TextView tv_phone = view.findViewById(R.id.tv_phone);

        tv_phone.setText(sharedPref.getString(item.getPath() + "_phone", ""));

        if (item.isFav() == false) {
            iv_fav.setImageLevel(0);
        } else if (item.isFav() == true) {
            iv_fav.setImageLevel(1);
        }
        iv_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isFav() == false) {
                    iv_fav.setImageLevel(1);
                    item.setFav(true);

                    // Lưu thông tin isFav
                    editor.putBoolean(item.getPath(), true);
                    editor.apply();
                } else if (item.isFav() == true) {
                    iv_fav.setImageLevel(0);
                    item.setFav(false);

                    // Lưu thông tin isFav
                    editor.remove(item.getPath());
                    editor.apply();
                }
            }
        });

        iv_bg.setImageBitmap(item.getPhotoBg());
        tv_name.setText(item.getName());
        tv_description.setText(item.getContent());

        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog alert = new AlertDialog.Builder(mContext).create();
                View dialog = LayoutInflater.from(mContext).inflate(R.layout.phone_diaglog, null);

                ImageView iv_dialog = dialog.findViewById(R.id.iv_dialog_animal);
                Button btn_dialog_save = dialog.findViewById(R.id.btn_dialog_save);
                Button btn_dialog_delete = dialog.findViewById(R.id.btn_dialog_delete);
                EditText edt_dialog_animal = dialog.findViewById(R.id.edt_dialog_animal);

                edt_dialog_animal.setText(sharedPref.getString(item.getPath() + "_phone", ""));

                btn_dialog_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        tv_phone.setText(edt_dialog_animal.getText());
                        editor.putString(item.getPath() + "_phone", edt_dialog_animal.getText().toString());
                        editor.putString(edt_dialog_animal.getText().toString(), item.getPath());

                        editor.apply();
                        alert.dismiss();
                    }
                });

                btn_dialog_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_phone.setText("");
                        editor.remove(item.getPath() + "_phone");
                        editor.remove(sharedPref.getString(item.getPath() + "_phone", ""));
                        editor.apply();
                        alert.dismiss();
                    }
                });

                iv_dialog.setImageBitmap(item.getPhoto());
                alert.setView(dialog);


                alert.show();
            }
        });


        container.addView(view);
        return view;
    }

    ;


    @Override
    public int getCount() {
        return listAnimal.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
