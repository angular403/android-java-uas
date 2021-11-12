package id.lazday.streaming.data.model;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("video_id")
    private String video_id;
    @SerializedName("title")
    private String title;
    @SerializedName("summary")
    private String summary;
    @SerializedName("cover")
    private String cover;
    @SerializedName("category")
    private String category;
    @SerializedName("created")
    private String created;
    @SerializedName("updated")
    private String updated;


    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }


}
