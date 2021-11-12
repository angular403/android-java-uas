package id.lazday.streaming.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallResponse {

    @SerializedName("response")
    private String response;
    @SerializedName("videos")
    private List<Video> videos;
    @SerializedName("list")
    private List<ListVideo> lists;
    @SerializedName("categories")
    private List<Category> categories;
    @SerializedName("users")
    private List<User> users;
    @SerializedName("likes")
    private List<Like> likes;

    public String getResponse() {
        return response;
    }
    public List<Video> getVideos() {
        return videos;
    }
    public List<ListVideo> getLists() {
        return lists;
    }
    public List<Category> getCategories() {
        return categories;
    }
    public List<User> getUsers() {
        return users;
    }
    public List<Like> getLikes() {
        return likes;
    }


}
