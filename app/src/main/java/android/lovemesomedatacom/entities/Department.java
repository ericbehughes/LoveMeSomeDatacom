package android.lovemesomedatacom.entities;

/**
 * Created by 1331680 on 11/24/2017.
 */

public class Department {
    private String department;

    public Department(){
        this("");
    }

    public Department(String department){
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Department{" +
                "department='" + department + '\'' +
                '}';
    }
}
