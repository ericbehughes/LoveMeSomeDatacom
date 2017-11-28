package android.lovemesomedatacom.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The Teacher class is in charge of modeling a Teacher object in a manner that the Firebase framework
 * will be able to translate a json object to this type of object. All the class' attributes match
 * the name of the json objects in the database. The Teacher class implements the Parcelable interface
 * in order to facilitate flow of information between the FindTeacherActivity and the ChooseTeacherActivity
 * and the TeacherContactActivity. It also impelements the Comparable interface in order to sort the contents
 * of any list of Teachers.
 *
 * Help on how to use the Parcelable interface taken from Lars Vogel at:
 *  http://www.vogella.com/tutorials/AndroidParcelable/article.html
 */

public class Teacher implements Parcelable, Comparable<Teacher> {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Teacher createFromParcel(Parcel in) {
            return new Teacher(in);
        }

        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };

    private String first_name;
    private String last_name;
    private String full_name;
    private String email;
    private String office;
    private String local;
    private String website;
    private String bio;
    private String image;
    private List<String> positions;
    private List<String> departments;
    private List<String> sectors;

    /**
     * Default no-param constructor.
     */
    public Teacher() {
        this("", "", "", "", "", "", "", "", "", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<String>());
    }

    /**
     * Non-default constructor. Takes in a Parcel object and recreates a Teacher from it.
     *
     * @param in the parcel that contains the teacher object.
     */
    public Teacher(Parcel in) {
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.full_name = in.readString();
        this.email = in.readString();
        this.office = in.readString();
        this.local = in.readString();
        this.website = in.readString();
        this.bio = in.readString();
        this.image = in.readString();
        this.positions = in.createStringArrayList();
        this.departments = in.createStringArrayList();
        this.sectors = in.createStringArrayList();
    }

    /**
     * Non-default constructor that initializes all the object's attributes.
     * @param first_name
     * @param last_name
     * @param full_name
     * @param email
     * @param office
     * @param local
     * @param website
     * @param bio
     * @param image
     * @param positions
     * @param departments
     * @param sectors
     */
    public Teacher(String first_name, String last_name, String full_name, String email, String office,
                   String local, String website, String bio, String image, List<String> positions,
                   List<String> departments, List<String> sectors) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.full_name = full_name;
        this.email = email;
        this.office = office;
        this.local = local;
        this.website = website;
        this.bio = bio;
        this.image = image;
        this.positions = positions;
        this.departments = departments;
        this.sectors = sectors;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public List<String> getSectors() {
        return sectors;
    }

    public void setSectors(List<String> sectors) {
        this.sectors = sectors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        if (first_name != null ? !first_name.equals(teacher.first_name) : teacher.first_name != null)
            return false;
        if (last_name != null ? !last_name.equals(teacher.last_name) : teacher.last_name != null)
            return false;
        if (full_name != null ? !full_name.equals(teacher.full_name) : teacher.full_name != null)
            return false;
        if (email != null ? !email.equals(teacher.email) : teacher.email != null) return false;
        if (office != null ? !office.equals(teacher.office) : teacher.office != null) return false;
        if (local != null ? !local.equals(teacher.local) : teacher.local != null) return false;
        if (website != null ? !website.equals(teacher.website) : teacher.website != null)
            return false;
        if (bio != null ? !bio.equals(teacher.bio) : teacher.bio != null) return false;
        if (image != null ? !image.equals(teacher.image) : teacher.image != null) return false;
        if (positions != null ? !positions.equals(teacher.positions) : teacher.positions != null)
            return false;
        if (departments != null ? !departments.equals(teacher.departments) : teacher.departments != null)
            return false;
        return sectors != null ? sectors.equals(teacher.sectors) : teacher.sectors == null;

    }

    @Override
    public int hashCode() {
        int result = first_name != null ? first_name.hashCode() : 0;
        result = 31 * result + (last_name != null ? last_name.hashCode() : 0);
        result = 31 * result + (full_name != null ? full_name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (office != null ? office.hashCode() : 0);
        result = 31 * result + (local != null ? local.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (bio != null ? bio.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (positions != null ? positions.hashCode() : 0);
        result = 31 * result + (departments != null ? departments.hashCode() : 0);
        result = 31 * result + (sectors != null ? sectors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", full_name='" + full_name + '\'' +
                ", email='" + email + '\'' +
                ", office='" + office + '\'' +
                ", local='" + local + '\'' +
                ", website='" + website + '\'' +
                ", bio='" + bio + '\'' +
                ", image='" + image + '\'' +
                ", positions=" + positions +
                ", departments=" + departments +
                ", sectors=" + sectors +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.first_name);
        parcel.writeString(this.last_name);
        parcel.writeString(this.full_name);
        parcel.writeString(this.email);
        parcel.writeString(this.office);
        parcel.writeString(this.local);
        parcel.writeString(this.website);
        parcel.writeString(this.bio);
        parcel.writeString(this.image);
        parcel.writeStringList(this.positions);
        parcel.writeStringList(this.departments);
        parcel.writeStringList(this.sectors);
    }

    @Override
    public int compareTo(@NonNull Teacher teacher) {
        return this.full_name.compareToIgnoreCase(teacher.getFull_name());
    }
}
