package Users;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parent extends PersonImpl {
    private String phone;
    private String email;
    private List<Kid> kids;

    public Parent(String name, String home, String birthDate, String phone, String email) {
        super(name, home, birthDate);
        setPhone(phone);
        setEmail(email);
        this.kids = new ArrayList<>();
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getKidsInformation(){
        String kidsInfo = "";
        for (Kid kid : kids) {
            kidsInfo = kidsInfo.concat(kid.toString());
        }
        return kidsInfo;
    }

    @Override
    public String toString() {
        return super.toString().concat(String.format("%nContacts: %s %s%n" + //getPhone(), getEmail()
                "%nInformation about the kids:%n%s" + //getKidsInformation()
                "%n", getPhone(), getEmail(), getKidsInformation()));
    }

    public List<Kid> getKids() {
        return this.kids;
    }

    public boolean containKid(Kid kid){
        if(this.kids.contains(kid)){
            return true;
        }
        return false;
    }

    public void removeKid(Kid kid){
        this.kids.remove(kid);
    }

    public void addKid(Kid kid){
        this.kids.add(kid);
    }

    public void setPhone(String phone) {
        if(validatePhone(phone)) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Wrong phone number - format +359#########");
        }
    }

    private boolean validatePhone(String phone){
        if(Character.toLowerCase(phone.charAt(0)) == '+' &&
                Character.toLowerCase(phone.charAt(1)) == '3' &&
                Character.toLowerCase(phone.charAt(2)) == '5' &&
                Character.toLowerCase(phone.charAt(3)) == '9' &&
                phone.length() == 13){
            return true;
        }
        return false;
    }

    private boolean validateEmail(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public void setEmail(String email) {
        if(validateEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Wrong email input");
        }
    }

}
