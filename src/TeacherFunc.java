import Users.Kid;
import Users.Parent;
import Users.Teacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeacherFunc extends Methods {
    public static boolean teacherFunctionality
            (String inputName, BufferedReader reader, List<Teacher> allTeachers, List<Kid> allKids, List<Parent> allParents,
             HashMap<String,String> parentsPasswords, boolean checker)
            throws IOException {
        System.out.println("Welcome, Teacher " + inputName +
                "\nWhat do you want to do? Choose the number.\n" +
                "1. Modify your own information 2. Modify a parent 3.Modify a kid 4. Get info 5. Exit the system");
        int command = Integer.parseInt(reader.readLine());

        //In inputTeacher we are saving not only the name of the teacher, but the whole info about the teacher
        Teacher inputTeacher = null;
        for (Teacher teacher : allTeachers) {
            if(teacher.getName().equals(inputName)){
                inputTeacher = teacher;
            }
        }

        while (command <= 0 || command >= 6) {
            System.out.println("""
                            Opps.. I think you messed up.
                            What do you want to do? Choose the number.
                            1. Modify your own information 2. Modify a parent 3.Modify a kid 4. Get info 5. Exit the system""");
            command = Integer.parseInt(reader.readLine());
        }

        if(command == 1){ //Teacher -> Modify your own info
            modifyTeacherInfo(reader, inputName, inputTeacher, allTeachers, allKids);
        } else if(command == 2){ //Teacher -> Modify a parent
            modifyParent(reader, allKids, allParents, parentsPasswords);

        } else if(command == 3){ //Teacher -> Modify a kid
            //1. Add a kid 2. Edit a kid 3. Remove a kid 4. Get info
            int numberOfCommand = Methods.chooseOperation("kid", reader);

            if(numberOfCommand == 1){ //Teacher -> Modify a kid -> Add a kid
                //----- Установяване на родителя
                System.out.println("First enter the name of the parent of the new kid. The existing parents are: ");
                printAllParentsNames(allParents);
                String parentName = reader.readLine();
                Parent parentOfNewKid = null;
                for (Parent allparents : allParents) {
                    if(allparents.getName().equals(parentName)){
                        parentOfNewKid = allparents;
                    }
                }
                while(parentOfNewKid == null){
                    System.out.println("""
                                    Opps.. I think you messed up. Try with new input.
                                    Enter the correct name of the parent of the new kid.
                                    Note that this parent should exist in the system.""");
                    parentName = reader.readLine();
                    for (Parent allparents : allParents) {
                        if(allparents.getName().equals(parentName)){
                            parentOfNewKid = allparents;
                        }
                    }
                }
                //----
                modifyKidAddNewKid(reader, parentOfNewKid, allParents);


            } else if(numberOfCommand == 2){ //Teacher -> Modify a kid -> Edit a kid
                modifyKidEditKid(reader, allKids, allParents, allTeachers, allKids);

            } else if(numberOfCommand == 3){ //Teacher -> Modify a kid -> Remove a kid
                modifyKidRemoveKid(reader, allKids, allKids, allParents, allTeachers);

            } else if(numberOfCommand == 4){ //Teacher -> Modify a kid -> Get info
                System.out.println("Enter the name of kid you want information about.");

                List<String> kidsNames = new ArrayList<>();
                for (Kid allkid : allKids) {
                    kidsNames.add(allkid.getName());
                }
                System.out.println(String.join(", ", kidsNames));

                String inputKidName = reader.readLine();

                Kid inputGetInfoKid = null;
                for (Kid allKid : allKids) {
                    if(allKid.getName().equals(inputKidName)){
                        inputGetInfoKid = allKid;
                    }
                }
                while(inputGetInfoKid == null){
                    System.out.println("Opps.. I think you messed up. Try with new input.");
                    inputKidName = reader.readLine();
                    for (Kid allKid : allKids) {
                        if(allKid.getName().equals(inputKidName)){
                            inputGetInfoKid = allKid;
                        }
                    }
                }
                String getInfo = inputGetInfoKid.toString();
                Methods.writeInFile(FirstStep.PATH, getInfo);

            }

        } else if(command == 4) { //Teacher -> Get Info
            assert inputTeacher != null;
            String getInfo = inputTeacher.toString();
            writeInFile(FirstStep.PATH, getInfo);

        } else if(command == 5){ //Teacher -> Exit the system
            checker = false;
        }
        return checker;
    }
}
