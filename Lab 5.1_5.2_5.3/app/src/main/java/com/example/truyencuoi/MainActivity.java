package com.example.truyencuoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String topicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //show Fragment M000SplashFrg to show M000Screen (m000_frg_splash.xml)
        showFrg(new M000SplashFrg());
    }

    //show Fragment with parameter frg
    private void showFrg(Fragment frg) {
        // id:R.id.ln_main is activity_main layout xml
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, frg, null).commit();
    }

    // show M001Screen
    public void gotoM001Screen() {
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, new M001TopicFrg(), null).commit();
    }

    // this method will be called from M001TopicFrg or from M003DetailStoryFrg
    // topicName is the tag that gets from the layout of M001Screen or M003Screen
    public void gotoM002Screen(String topicName) {
        this.topicName = topicName;
        M002StoryFrg frg = new M002StoryFrg();
        // set topicName of M002StoryFrg Fragment = this.topicName
        frg.setTopicName(topicName);
        //show M002StoryFrg Fragment
        showFrg(frg);
    }

    // this method will be called from StoryAdapter
    public void gotoM003Screen(ArrayList<StoryEntity> listStory, StoryEntity story) {
        M003DetailStoryFrg frg = new M003DetailStoryFrg();
        // story is a tag from StoryAdapter
        frg.setData(topicName, listStory, story);
        showFrg(frg);
    }

    // this method will be called from M002StoryFrg
    // executed this method will show M001Screen
    public void backToM001Screen() {
        gotoM001Screen();
    }
}