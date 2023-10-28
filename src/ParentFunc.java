import Users.Kid;
import Users.Parent;
import Users.Teacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class ParentFunc extends Methods {

    public static boolean parentFunctionality
            (String inputName, BufferedReader reader, List<Parent> allParents, List<Kid> allKids,
             List<Teacher> allTeachers, boolean checker)
            throws IOException {
        System.out.println("Welcome, Parent " + inputName +
                "\nWhat do you want to do? Choose the number.\n" +
                "1. Modify your own information 2.Modify a kid 3. Get info 4. Exit the system");
        int commandParent = Integer.parseInt(reader.readLine());

        while(commandParent <= 0 || commandParent >=4){
            System.out.println("Opps.. I think you messed up. Try with new input.");
            commandParent = Integer.parseInt(reader.readLine());
        }

        Parent parentProfile = null;

        for (Parent allParent : allParents) {
            if(allParent.getName().equals(inputName)){
                parentProfile = allParent;
            }
        }

        if(commandParent == 1){ //Parent -> Modify own info
            editParentInfo(reader, allParents, parentProfile, allKids, "parent");

        } else if(commandParent == 2){ //Parent -> Modify a kid
            //1. Add a kid 2. Edit a kid 3. Remove a kid
            int numberOfCommand = chooseOperation("kid", reader);

            if(numberOfCommand == 1) { //Parent -> Modify a kid -> Add a kid
                modifyKidAddNewKid(reader, parentProfile, allParents);

            } else if(numberOfCommand == 2){ //Parent -> Modify a kid -> Edit a kid
                assert parentProfile != null;
                modifyKidEditKid(reader, parentProfile.getKids(), allParents, allTeachers, allKids);

            } else if(numberOfCommand == 3){ //Parent -> Modify a kid -> Remove a kid
                assert parentProfile != null;
                modifyKidRemoveKid(reader, parentProfile.getKids(), allKids, allParents, allTeachers);
            }
        } else if(commandParent == 3){ //Parent -> Get info
            String getInfo = parentProfile.toString();
            writeInFile(FirstStep.PATH, getInfo);

        } else if(commandParent == 4){ //Parent -> Exit the system
            checker = false;
        }
        return checker;
    }
}
