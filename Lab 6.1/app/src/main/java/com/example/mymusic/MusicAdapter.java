package com.example.mymusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {

    private final ArrayList<SongEntity> listSong;
    private final Context mContext;

    public MusicAdapter(ArrayList<SongEntity> listSong, Context mContext) {
        this.listSong = listSong;
        this.mContext = mContext;
    }

    //Phương thức này được gọi khi tạo mới RecycleView
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_song, parent, false);

        return new MusicHolder(view);
    }

   //phương thức này dùng để gắn data vào view.
    @Override
    public void onBindViewHolder(MusicHolder holder, int position) {

        SongEntity item = listSong.get(position);

        holder.tvName.setText(item.getName());
        holder.tvName.setTag(item);
    }

    //phương thức này dùng để lấy tổng số item data hiển thị trong RecycleView
    @Override
    public int getItemCount() {
        return listSong.size();

    }


    public class MusicHolder extends ViewHolder {

        TextView tvName;

        public MusicHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_song);

            itemView.setOnClickListener(v -> {

                v.startAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_fade_in));

                ((MainActivity) mContext).playSong((SongEntity) tvName.getTag());

            });

        }
    }
}
