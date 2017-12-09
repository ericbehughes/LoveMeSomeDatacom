package android.lovemesomedatacom.entities;

import java.util.Comparator;

/**
 * The TeacherNameComparator is used to be able to sort a list of teacher objects according to
 * the Teacher implementation of the compareTo method.
 *
 * @author Sebastian Ramirez
 */

public class TeacherNameComparator implements Comparator<Teacher> {

    @Override
    public int compare(Teacher teacher, Teacher otherTeacher) {
        return teacher.compareTo(otherTeacher);
    }
}
