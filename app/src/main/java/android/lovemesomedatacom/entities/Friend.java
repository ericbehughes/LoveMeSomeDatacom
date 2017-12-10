package android.lovemesomedatacom.entities;

/**
 * Created by ehugh on 12/9/2017.
 */

public class Friend {

    private String firstName;
    private String lastName;
    private String email;

    public Friend(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString(){
        return this.firstName+" "+this.lastName+" "+this.email;
    }

}
