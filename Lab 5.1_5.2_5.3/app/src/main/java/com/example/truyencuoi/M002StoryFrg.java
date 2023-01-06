package com.example.truyencuoi;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class M002StoryFrg extends Fragment {
    private static final String TAG = M002StoryFrg.class.getName();
    private Context mContext;
    private String topicName;
    private ArrayList<StoryEntity> listStory;

    //set topicName variable's value
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    //The system calls this method to draw the M002StoryFrg Fragment UI for the first time.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate m001_frg_topic layout for m002_frg_story layout
        View rootView = inflater.inflate(R.layout.m002_frg_story, container, false);
        // to draw story list
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

    // this method used to control M002Screen and draw story list
    private void initViews(View v) {
        try {
            // The arrow button controls return to M001Srceen using the two statements below.
            v.findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
            v.findViewById(R.id.iv_back).setOnClickListener(v1 -> backToM001Screen()); // char "->" is Lambda Expression. Read more: https://www.w3schools.com/java/java_lambda.asp

            // set text for component tv_name in actionbar_home layout
            ((TextView) v.findViewById(R.id.tv_name)).setText(topicName);

            // this method used to initialize story list
            loadListStory();

            // Use RecyclerView to show story list
            // READ MORE: https://developer.android.com/guide/topics/ui/layout/recyclerview
            RecyclerView rvStory = v.findViewById(R.id.rv_story);
            rvStory.setLayoutManager(new LinearLayoutManager(mContext));
            rvStory.setAdapter(new StoryAdapter(listStory, mContext));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // call backToM001Screen() method in MainActivity.class
    private void backToM001Screen() {
        ((MainActivity) getActivity()).backToM001Screen();
    }

    // this method used to initialize listStory arraylist
    private void loadListStory() throws Exception {
        //read input from file .txt in directory: assets/story/
        InputStream in = mContext.getAssets().open("story/" + topicName + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String str;
        String name = null;
        StringBuilder content = new StringBuilder();
        listStory = new ArrayList<>();

        // build listStory array list
        while ((str = br.readLine()) != null) {
            if (str.isEmpty()) continue;
            if (name == null) {
                name = str;
            } else if (!str.startsWith("','0');")) {
                content.append(str).append("\n");
            } else {
                listStory.add(new StoryEntity(topicName, name, content.toString()));
                name = null;
                content = new StringBuilder();
            }
        }

        // close bufferedReader
        br.close();
        // show information in Logcat
        Log.i(TAG, "Story list = " + listStory.size());
    }
}