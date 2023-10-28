package Users;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends PersonImpl {
    private List<Kid> teachersKids;

    public Teacher(String name, String home, String birthDate) {
        super(name, home, birthDate);
        teachersKids = new ArrayList<>();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public String getKidsNames(){
        List<String> kidsNames = new ArrayList<>();
        for (Kid kid : this.teachersKids) {
            kidsNames.add(kid.getName());
        }
        return String.join(", ", kidsNames);
    }

    @Override
    public String toString() {
        return super.toString().concat(String.format("%nThe kids that %s leads are %s", getName(), getKidsNames()));
    }

    public boolean containsKid(Kid kid){
        if(teachersKids.contains(kid)){
            return true;
        }
        return false;
    }

    public void addKid(Kid kid){
        this.teachersKids.add(kid);
    }

    public void removeKid(Kid kid){
        this.teachersKids.remove(kid);
    }

    public List<Kid> getTeachersKids(){
        return this.teachersKids;
    }
}
