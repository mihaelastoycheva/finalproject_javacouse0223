package Users;

import Users.Person;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PersonImpl implements Person {
    private String name;
    private String home;
    private String birthDate;

    public PersonImpl(String name, String home, String birthDate) {
        setName(name);
        setHome(home);
        setBirthDate(birthDate);
    }

    public void setName(String name) {
        Pattern p = Pattern.compile("[^A-z ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        boolean b = m.find();

        if (b) {
            throw new IllegalArgumentException("Wrong name. There is a special character in the string");
        }
        this.name = name;
    }

    public void setHome(String home) {
        Pattern p = Pattern.compile("[^A-z ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(home);
        boolean b = m.find();

        if (b) {
            throw new IllegalArgumentException("Wrong name of home. There is a special character in the string");
        }
        this.home = home;
    }

    public void setBirthDate(String birthDate) {
        if(validateDate(birthDate)) {
            this.birthDate = birthDate;
        } else {
            throw new IllegalArgumentException("Wrong birth date - format dd/MM/yyyy");
        }
    }

    protected boolean validateDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            Date d1 = sdf.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getHome() {
        return this.home;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    @Override
    public String toString() {
        return String.format("%s is born on %s in %s", getName(), getBirthDate(), getHome());
    }
}
