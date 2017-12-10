package android.lovemesomedatacom.entities;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by 1513733 on 11/24/2017.
 */

public class Course {
    private String title;
    private String courseName;
    private String teacherName;
    private String dateCancelled;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


    public String getDateCancelled() {
        return dateCancelled;
    }

    public void setDateCancelled(String dateCancelled) {
        this.dateCancelled = dateCancelled;
    }

    public Spanned getDescription() {
        return Html.fromHtml(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return this.courseName+"\n\t"+this.teacherName;
    }



}
