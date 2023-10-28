import Users.Kid;
import Users.Parent;
import Users.Teacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class FirstStep extends Methods {
    public static final String PATH = "/Users/misha/Documents/GitHub/FinalProject_JavaCourse0223/src/information";

    public static void startApp() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Hashmap for the administrator:
        HashMap<String,String> administratorsPasswords = new LinkedHashMap<>();
        administratorsPasswords.put("Administrator", Passwords.ADMINISTRATOR_PASS);

        //Hashmap & List for parents:
        HashMap<String,String> parentsPasswords = new LinkedHashMap<>();
        Parent parent1 = new Parent("Hanna Davis", "Chicago", "21/03/1990", "+359888765432", "office@website.com");
        Parent parent2 = new Parent("Mark Rodriguez", "Spain", "11/05/1992", "+359888888886", "sample@example.com");
        parentsPasswords.put(parent1.getName(), Passwords.PARENT_HANNA_DAVIS_PASS);
        parentsPasswords.put(parent2.getName(), Passwords.PARENT_MARK_RODRIGUEZ_PASS);
        List<Parent> allParents = new LinkedList<>();
        allParents.add(parent1);
        allParents.add(parent2);

        //Hashmap & List for teachers:
        HashMap<String,String> teachersPasswords = new LinkedHashMap<>();
        Teacher teacher1 = new Teacher("Robert Smith", "Paris", "03/10/1972");
        Teacher teacher2 = new Teacher("Patricia Miller", "London", "15/07/1975");
        teachersPasswords.put(teacher1.getName(), Passwords.TEACHER_ROBERT_SMITH_PASS);
        teachersPasswords.put(teacher2.getName(), Passwords.TEACHER_PATRICIA_MILLER_PASS);
        List<Teacher> allTeachers = new LinkedList<>();
        allTeachers.add(teacher1);
        allTeachers.add(teacher2);

        //List for kids:
        List<Kid> allKids = new LinkedList<>();
        Kid kid1 = new Kid("Peter Davis", "New Orleans", "12/12/2019", 2, "01/02/2023");
        Kid kid2 = new Kid("Ivan Ivanov", "Sofia", "01/01/2020", 5, "01/03/2023");
        Kid kid3 = new Kid("Pesho Petrov", "Bulgaria", "02/02/2020", 3, "15/03/2023");
        kid1.addParent(parent1);
        kid1.addTeacher(teacher1);
        kid1.addAbsentDate("03/03/2023");
        kid3.addParent(parent1);
        kid3.addTeacher(teacher2);
        parent1.addKid(kid1);
        parent1.addKid(kid3);
        kid2.addParent(parent2);
        parent2.addKid(kid2);
        allKids.add(kid1);
        allKids.add(kid2);
        allKids.add(kid3);

        teacher2.addKid(kid1);
        teacher1.addKid(kid2);
        teacher2.addKid(kid3);

        System.out.println("""
                Hi there! This is the system of Kindergarten "Sunshine"
                Please choose your role by entering the number:\s
                1. Administrator 2. Teacher 3. Parent""");

        int role = Integer.parseInt(reader.readLine());

        while(role <=0 || role >= 4){
            System.out.println("""
                Opps.. I think you messed up.
                Please choose your role by entering the number:\s
                1. Administrator 2. Teacher 3. Parent""");
            role = Integer.parseInt(reader.readLine());
        }

        String inputName = "";

        if(role == 1){ //Administrator
            checkPassword("Administrator", reader, administratorsPasswords);
        } else if (role == 2){ //Teacher
            System.out.println("Choose which teacher are you: ");
            for(String key : teachersPasswords.keySet()){
                System.out.println("* " + key);
            }

            inputName = reader.readLine();
            inputName = checkEnterName(teachersPasswords, inputName, reader);
            checkPassword(inputName, reader, teachersPasswords);
        } else if (role == 3){ // Parent
            System.out.println("Choose which parent are you: ");
            for(String key : parentsPasswords.keySet()){
                System.out.println("* " + key);
            }

            inputName = reader.readLine();
            inputName = checkEnterName(parentsPasswords, inputName, reader);
            checkPassword(inputName, reader, parentsPasswords);
        }

        boolean checker = true;
        System.out.println("YAY! You entered the system.");

        //----------MAIN PART:
        while(checker) {
            if (role == 1) { //Administrator
                checker = AdminFunc.administratorFunctionality(reader, allTeachers, teachersPasswords, checker, allKids, allParents, parentsPasswords);

            } else if (role == 2) { //Teacher
                checker = TeacherFunc.teacherFunctionality(inputName, reader, allTeachers, allKids, allParents, parentsPasswords, checker);

            } else if (role == 3) { //Parent
                checker = ParentFunc.parentFunctionality(inputName, reader, allParents, allKids, allTeachers, checker);
            }
        }
    }
}