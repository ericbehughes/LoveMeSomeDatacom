package android.lovemesomedatacom.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;

/**
 * Course class used to represent a course. Implements Parcelable
 * @author Rhai
 */

// Class is Parcelable so that a list of courses can be saved to the bundle
public class Course implements Parcelable {
    private String title;
    private String courseName;
    private String teacherName;
    private String dateCancelled;
    private String description;


    public Course(){

    }
    public Course(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        this.title = data[0];
        this.courseName = data[1];
        this.teacherName = data[2];
        this.dateCancelled = data[3];
        this.description = data[4];
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.title,this.courseName,this.teacherName,this.dateCancelled,this.description});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Course createFromParcel(Parcel in){
            return new Course(in);
        }

        public Course[] newArray(int size){
            return new Course[size];
        }
    };
}
