import Users.Kid;
import Users.Parent;
import Users.Teacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminFunc extends Methods {
    public static boolean administratorFunctionality
            (BufferedReader reader, List<Teacher> allTeachers, HashMap<String,String> teachersPasswords,
             boolean checker, List<Kid> allKids, List<Parent> allParents, HashMap<String,String> parentsPasswords)
            throws IOException {

        System.out.println("""
                        Welcome, Administrator!
                        What do you want to do? Choose the number.
                        1. Modify the teachers 2. Modify the parents 3. Exit the system""");
        int n = Integer.parseInt(reader.readLine());
        while (n <= 0 || n >= 4) {
            System.out.println("""
                            Opps.. I think you messed up.
                            What do you want to do? Choose the number.
                            1. Modify the teachers 2. Modify the parents""");
            n = Integer.parseInt(reader.readLine());
        }

        if (n == 1) { //Administrator -> 1. Modify the teachers
            int numberOfCommand = chooseOperation("teacher", reader);
            //1. Add a teacher 2. Edit a teacher 3. Remove a teacher 4. Get info

            if (numberOfCommand == 1) { //Administrator -> 1. Modify the teachers -> 1. Add a teacher
                String addNewTeacherText = """
                        To add a new teacher, you should enter information about him
                        Enter it in the following format: *name* *home* *birthdate*
                        Note that they should be split ONLY with spaces""";
                System.out.println(addNewTeacherText);

                boolean tError = true;
                do {
                    try {
                        String[] inputAddTeacher = reader.readLine().split("\\s+");
                        String inputAddTeacherName = Methods.getFirstAndLastNameToFullNameFromArray(inputAddTeacher);
                        Teacher teacher = new Teacher(inputAddTeacherName, inputAddTeacher[2], inputAddTeacher[3]);
                        tError = false;

                        Methods.addPasswordToTheNewPerson(reader, "teacher", teachersPasswords, inputAddTeacherName);

                        allTeachers.add(teacher);
                        System.out.println("YAY! You successfully added a new teacher!\n");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println(addNewTeacherText);
                    }
                } while (tError);

            } else if (numberOfCommand == 2) { //Administrator -> 1. Modify the teachers -> 2. Edit a teacher
                System.out.println("Which teacher you want to edit?");
                Teacher inputTeacher = getTeacherForChange(allTeachers,reader);

                modifyTeacherInfo(reader, inputTeacher.getName(), inputTeacher, allTeachers, allKids);

            } else if (numberOfCommand == 3) { //Administrator -> Modify the teachers -> Remove a teacher
                System.out.println("To remove a teacher, you should choose which one");
                Teacher inputTeacherRemove = getTeacherForChange(allTeachers, reader);
                allTeachers.remove(inputTeacherRemove); //Премахване от списъка с всички учители
                teachersPasswords.remove(inputTeacherRemove.getName()); //Премахване от хешмапа с учителите и паролите
                for (Kid allKid : allKids) { //Премахване от информацията за дете, на което учителя е преподавал
                    if(allKid.getTeachersInListString().contains(inputTeacherRemove.getName())){
                        allKid.removeTeacher(inputTeacherRemove);
                    }
                }
                System.out.println("YAY! You successfully removed " + inputTeacherRemove.getName() + " from the system.\n");

            } else if (numberOfCommand == 4){ //Administrator -> Modify the teachers -> Get info
                System.out.println("You will get the information in file, named \"information\"" +
                        "\nEnter the name of teachers you want information about. ");

                //Изброяване на всички учители:
                List<String> teachersNames = new ArrayList<>();
                for (Teacher teacher : allTeachers) {
                    teachersNames.add(teacher.getName());
                }
                System.out.println(String.join(", ", teachersNames));

                String getInfoTeacherName = reader.readLine();
                //----- Check the input
                Teacher getInfoTeacher = null;
                for (Teacher teacher : allTeachers) {
                    if(teacher.getName().equals(getInfoTeacherName)){
                        getInfoTeacher = teacher;
                    }
                }

                while(getInfoTeacher == null){
                    System.out.println("Opps.. I think you messed up. Try with new input.");
                    getInfoTeacherName = reader.readLine();
                    for (Teacher teacher : allTeachers) {
                        if(teacher.getName().equals(getInfoTeacherName)){
                            getInfoTeacher = teacher;
                        }
                    }
                }
                //--------
                String getInfo = getInfoTeacher.toString();
                Methods.writeInFile(FirstStep.PATH, getInfo);
            }

        } else if (n == 2) { //Administrator -> Modify the parents
            modifyParent(reader, allKids, allParents, parentsPasswords);

        } else if (n == 3){ //Administrator -> Exit the program
            checker = false;
        }
        return checker;
    }
}
