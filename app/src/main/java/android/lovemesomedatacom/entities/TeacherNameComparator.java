package android.lovemesomedatacom.entities;

import java.util.Comparator;

/**
 * Created by 1331680 on 11/27/2017.
 */

public class TeacherNameComparator implements Comparator<Teacher> {
    @Override
    public int compare(Teacher teacher, Teacher otherTeacher) {
        return teacher.compareTo(otherTeacher);
    }
}
