package com.example.truyencuoi;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class M003DetailStoryFrg extends Fragment {
    private Context mContext;
    private ArrayList<StoryEntity> listStory;
    private String topicName;
    private StoryEntity currentStory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate m002_frg_story layout for m003_frg_detail_story
        View rootView = inflater.inflate(R.layout.m003_frg_detail_story, container, false);

        initViews(rootView);
        //To draw a UI for your Fragment, you must return the root View of your Fragment layout.
        return rootView;
    }

    // The system calls onAttach() method when a fragment is first attached to its context.
    @Override
    public void onAttach(Context context) {
        // In case, override this method you must call through to the superclass implementation.
        super.onAttach(context);
        mContext = context;
    }

    // this method used to control M003Screen and show story detail
    private void initViews(View v) {
        // The arrow button controls return to M002Srceen using the two statements below.
        v.findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        v.findViewById(R.id.iv_back).setOnClickListener(v1 -> gotoM002Screen(topicName));

        // set text for component tv_name in actionbar_home layout
        ((TextView) v.findViewById(R.id.tv_name)).setText(topicName);

        // Use ViewPager to show story detail and slide between fragments
        // READ MORE: https://developer.android.com/training/animation/screen-slide
        ViewPager vp = v.findViewById(R.id.vp_story);
        DetailStoryAdapter adapter = new DetailStoryAdapter(listStory, mContext);
        vp.setAdapter(adapter);
        vp.setCurrentItem(listStory.indexOf(currentStory), true);
    }

    // to call m002StoryFrg fragment
    private void gotoM002Screen(String topicName) {
        ((MainActivity) getActivity()).gotoM002Screen(topicName);
    }

    // setData method used to initial value for the variable currentStory,topicName,listStory
    public void setData(String topicName, ArrayList<StoryEntity> listStory, StoryEntity currentStory) {
        this.currentStory = currentStory;
        this.topicName = topicName;
        this.listStory = listStory;
    }
}

