package Users;

import java.util.ArrayList;
import java.util.List;

public class Kid extends PersonImpl {
    private int group;
    private String startDate;
    private List<String> absentDates;
    private List<Parent> parents;
    private List<Teacher> teachers;

    public Kid(String name, String home, String birthDate, int group, String startDate) {
        super(name, home, birthDate);
        setGroup(group);
        setStartDate(startDate);
        this.absentDates = new ArrayList<>();
        this.parents = new ArrayList<>();
        this.teachers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return super.toString().concat(String.format("%n%s is from %d group. %n" + //getName(), getGroup()
                "The kid has started in kindergarten on %s%n" + //getStartDate()
                "The kid was absent on %s%n" + //getAbsentDates()
                "The kid's parents are %s%n" + //getParentsNames()
                "%s are teaching %s%n----------%n", //getTeachersNames(), getName()
                getName(), getGroup(), getStartDate(), getAbsentDates(), getParentsNames(), getTeachersNamesInString(), getName()));
    }

    public String getAbsentDates() {
        return String.join(", ", this.absentDates);
    }


    public String getTeachersNamesInString(){
        List<String> teachersNames = new ArrayList<>();
        for (Teacher teacher : this.teachers) {
            teachersNames.add(teacher.getName());
        }
        return String.join(", ", teachersNames);
    }

    public List<String> getTeachersInListString(){
        List<String> teachersNames = new ArrayList<>();
        for (Teacher teacher : this.teachers) {
            teachersNames.add(teacher.getName());
        }
        return teachersNames;
    }

    public String getParentsNames(){
        List<String> parentsNamesList = new ArrayList<>();
        for (Parent parent : this.parents) {
            parentsNamesList.add(parent.getName());
        }

        return String.join(",", parentsNamesList);
    }

    public void addTeacher(Teacher teacher){
        this.teachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher){
        this.teachers.remove(teacher);
    }

    public List<Teacher> getTeachers() {
        return this.teachers;
    }

    public void addParent(Parent parent){
        this.parents.add(parent);
    }

    public void removeParent(Parent parent){
        this.parents.remove(parent);
    }

    public List<Parent> getParents() {
        return this.parents;
    }

    public void addAbsentDate(String absentDate){
        if(validateDate(absentDate)) {
            this.absentDates.add(absentDate);
        } else {
            throw new IllegalArgumentException("Wrong absent date - format dd/MM/yyyy");
        }
    }

    public void setGroup(int group) {
        if(group <= 0){
            throw new IllegalArgumentException("The group number cannot be zero or negative number");
        }
        this.group = group;
    }

    public void setStartDate(String startDate) {
        if(validateDate(startDate)) {
            this.startDate = startDate;
        } else {
            throw new IllegalArgumentException("Wrong start date - format dd/MM/yyyy");
        }
    }

    public int getGroup() {
        return this.group;
    }

    public String getStartDate() {
        return this.startDate;
    }
}
