package android.lovemesomedatacom.entities;

/**
 * @author Sebastian Ramirez
 */

public class Friend {

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
    public String toString(){
        return this.firstName+" "+this.lastName+" "+this.email;
    }

}
