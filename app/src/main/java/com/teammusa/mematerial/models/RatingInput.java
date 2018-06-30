package com.teammusa.mematerial.models;

/**
 * Created by HashCode on 2:33 PM 30/05/2018.
 */
public class RatingInput {

    private String instructor;
    private String student;
    private String course;
    private double stars;

    public RatingInput(String instructor, String student, String course, double stars) {
        this.instructor = instructor;
        this.student = student;
        this.course = course;
        this.stars = stars;
    }
}
