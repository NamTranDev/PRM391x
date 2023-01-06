package com.example.truyencuoi;
/*this is a Object
* this Object used to contain the data of listStory ArrayList in M002StoryFrg
* this Object used in StoryAdapter class and DetailStoryAdapter class*/
public class StoryEntity {
    private final String topicName;
    private final String name;
    private final String content;


    public StoryEntity(String topicName, String name, String content) {
        this.topicName = topicName;
        this.name = name;
        this.content = content;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
