package com.example.toastemoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class m001_frg_face_emoji extends Fragment implements View.OnClickListener {

    private static final int[] ids = {R.id.iv_face1, R.id.iv_face2, R.id.iv_face3, R.id.iv_face4,
            R.id.iv_face5, R.id.iv_face6, R.id.iv_face7, R.id.iv_face8, R.id.iv_face9};
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_m001_frg_face_emoji, container, false);
        initViews(rootView);
        return rootView;
//        return inflater.inflate(R.layout.fragment_m001_frg_face_emoji, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    private void initViews(View v) {
        for (int id : ids) {
            v.findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        ImageView ivFace = (ImageView) v;
        showToast(ivFace.getDrawable());
    }

    private void showToast(Drawable drawable) {
        Toast toast = new Toast(mContext);
        ImageView ivEmoji = new ImageView(mContext);
        ivEmoji.setImageDrawable(drawable);
        toast.setView(ivEmoji);
        toast.show();
    }
}