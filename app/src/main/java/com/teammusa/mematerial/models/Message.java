package com.teammusa.mematerial.models;

/**
 * Created by HashCode on 2:58 PM 30/05/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("user")
    @Expose
    private Metadata user;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("timestamp")
    @Expose
    private long timestamp;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Metadata getUser() {
        return user;
    }

    public void setUser(Metadata user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
