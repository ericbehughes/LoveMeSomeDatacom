package android.lovemesomedatacom.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Sebastian Ramirez
 */

public class Friend implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    private String firstName;
    private String lastName;
    private String email;

    public Friend(){
        this("", "", "");
    }

    /**
     * Non-default constructor. Takes in a Parcel object and recreates a Teacher from it.
     *
     * @param in the parcel that contains the teacher object.
     */
    public Friend(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
    }

    public Friend(String firstName, String lastName, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friend friend = (Friend) o;

        if (!getFirstName().equals(friend.getFirstName())) return false;
        if (!getLastName().equals(friend.getLastName())) return false;
        return getEmail().equals(friend.getEmail());

    }

    @Override
    public int hashCode() {
        int result = getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getEmail().hashCode();
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.firstName);
        parcel.writeString(this.lastName);
        parcel.writeString(this.email);
    }

}
