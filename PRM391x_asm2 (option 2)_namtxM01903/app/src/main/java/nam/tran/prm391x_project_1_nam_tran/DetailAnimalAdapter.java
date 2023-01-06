package nam.tran.prm391x_project_1_nam_tran;

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

        if (!item.isFav()) {
            iv_fav.setImageLevel(0);
        } else if (item.isFav()) {
            iv_fav.setImageLevel(1);
        }
        iv_fav.setOnClickListener(view1 -> {
            if (!item.isFav()) {
                iv_fav.setImageLevel(1);
                item.setFav(true);

                editor.putBoolean(item.getPath(), true);
                editor.apply();
            } else if (item.isFav()) {
                iv_fav.setImageLevel(0);
                item.setFav(false);

                editor.remove(item.getPath());
                editor.apply();
            }
        });

        iv_bg.setImageBitmap(item.getPhotoBg());
        tv_name.setText(item.getName());
        tv_description.setText(item.getContent());

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
