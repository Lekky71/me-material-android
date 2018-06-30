
package com.teammusa.mematerial.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("instructor")
    @Expose
    private String instructor;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("student")
    @Expose
    private String student;
    @SerializedName("stars")
    @Expose
    private Double stars;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.instructor);
        dest.writeString(this.course);
        dest.writeString(this.student);
        dest.writeValue(this.stars);
        dest.writeValue(this.v);
    }

    public Rating() {
    }

    protected Rating(Parcel in) {
        this.id = in.readString();
        this.instructor = in.readString();
        this.course = in.readString();
        this.student = in.readString();
        this.stars = (Double) in.readValue(Double.class.getClassLoader());
        this.v = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel source) {
            return new Rating(source);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };
}
