package android.lovemesomedatacom.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Sebastian Ramirez
 */

<<<<<<< HEAD
public class Friend implements Parcelable {
=======
public class Friend implements Parcelable{
>>>>>>> 19eafd42b3ada0bde40d7aa75b4d76fefcc5bbc1

    private String firstName;
    private String lastName;
    private String email;

    public Friend(){
        this("", "", "");
    }

    public Friend(String firstName, String lastName, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

<<<<<<< HEAD

    public Friend(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        this.firstName = data[0];
        this.lastName = data[1];
        this.email = data[2];
    }


=======
    protected Friend(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
>>>>>>> 19eafd42b3ada0bde40d7aa75b4d76fefcc5bbc1

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
<<<<<<< HEAD
        parcel.writeStringArray(new String[]{this.firstName,this.lastName,this.email});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Friend createFromParcel(Parcel in){
            return new Friend(in);
        }

        public Friend[] newArray(int size){
            return new Friend[size];
        }
    };
=======
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(email);
    }
>>>>>>> 19eafd42b3ada0bde40d7aa75b4d76fefcc5bbc1
}
