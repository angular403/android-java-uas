package id.lazday.streaming.data.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("user_id")
    private String user_id;
    @SerializedName("android_id")
    private String android_id;
    @SerializedName("status")
    private String status;
    @SerializedName("created")
    private String created;
    @SerializedName("last_login")
    private String last_login;

    public String getUser_id() {
        return user_id;
    }
    public String getAndroid_id() {
        return android_id;
    }
    public String getStatus() {
        return status;
    }
    public String getCreated() {
        return created;
    }
    public String getLast_login() {
        return last_login;
    }
}
