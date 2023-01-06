package com.example.truyencuoi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*StoryAdapter class used to customize option RecyclerView
* M002StoryFrg used this class to create story list.*/
public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder> {
    private final ArrayList<StoryEntity> listStory;
    private final Context mContext;

    //contructor with parameter
    public StoryAdapter(ArrayList<StoryEntity> listStory, Context mContext) {
        this.listStory = listStory;
        this.mContext = mContext;
    }

    /*Được gọi khi RecyclerView cần một RecyclerView.ViewHolder mới của loại đã cho để đại diện cho một Item.
     *Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     * https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup,%20int)*/
    @Override
    public StoryAdapter.StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_story, parent, false);
        return new StoryHolder(view);
    }

    /*Được gọi bởi RecyclerView để hiển thị dữ liệu ở vị trí được chỉ định.
    * Called by RecyclerView to display the data at the specified position.
    * This method should update the contents of the RecyclerView.ViewHolder.itemView to reflect the item at the given position.
    */
    @Override
    public void onBindViewHolder(StoryAdapter.StoryHolder holder, int position) {
        StoryEntity item = listStory.get(position);

        holder.tvName.setTag(item);
        holder.tvName.setText(item.getName());
    }

    /*Trả về tổng số mục trong tập dữ liệu
    * Returns the total number of items in the data set held by the adapter.*/
    @Override
    public int getItemCount() {
        return listStory.size();
    }

    public class StoryHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public StoryHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_story);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // go to M003Screen with parameter is tag of item
                    ((MainActivity)mContext).gotoM003Screen(listStory, (StoryEntity)tvName.getTag());
                }
            });
        }
    }
}