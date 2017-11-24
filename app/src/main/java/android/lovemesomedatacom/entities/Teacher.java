package android.lovemesomedatacom.entities;

/**
 * Created by 1331680 on 11/24/2017.
 */

public class Teacher {
    private String first_name;
    private String last_name;
    private String full_name;
    private String email;
    private String office;
    private String local;
    private String website;
    private String bio;
    private String image;
    private Position[] positions;
    private Department[] departments;
    private Sector[] sectors;

    public Teacher(){
        this("","","","","","","","","", null, null, null);
    }

    public Teacher(String first_name, String last_name, String full_name, String email, String office,
                   String local, String website, String bio, String image, Position[] positions,
                   Department[] departments, Sector[] sectors){
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

    public Position[] getPositions() {
        return positions;
    }

    public void setPositions(Position[] positions) {
        this.positions = positions;
    }

    public Department[] getDepartments() {
        return departments;
    }

    public void setDepartments(Department[] departments) {
        this.departments = departments;
    }

    public Sector[] getSectors() {
        return sectors;
    }

    public void setSectors(Sector[] sectors) {
        this.sectors = sectors;
    }
}
