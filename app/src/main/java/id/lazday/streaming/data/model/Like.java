package id.lazday.streaming.data.model;

import com.google.gson.annotations.SerializedName;

public class Like {

    public String getLike_id() {
        return like_id;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public String getList_id() {
        return list_id;
    }

    public String getCreated() {
        return created;
    }

    public String getVideo_id() {
        return video_id;
    }

    public String getTitle() {
        return title;
    }

    public String getFilename() {
        return filename;
    }

    public String getView() {
        return view;
    }

    @SerializedName("like_id")
    private String like_id;
    @SerializedName("android_id")
    private String android_id;
    @SerializedName("list_id")
    private String list_id;
    @SerializedName("created")
    private String created;

    @SerializedName("video_id")
    private String video_id;
    @SerializedName("title")
    private String title;
    @SerializedName("filename")
    private String filename;
    @SerializedName("view")
    private String view;
}
