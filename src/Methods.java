import Users.Kid;
import Users.Parent;
import Users.Teacher;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Methods {
    public static void editParentInfo(BufferedReader reader, List<Parent> allParents, Parent inputParent, List<Kid> allKids, String role) throws IOException {
        int inputEditParentInfo = 0;
        if(role.equals("parent")){
            System.out.println("""
                    What about this parent you want to edit?
                    1. Home 2. Birthdate 3. Phone 4. Email
                    Enter the number:""");
            inputEditParentInfo = Integer.parseInt(reader.readLine());
            while(inputEditParentInfo <= 0 || inputEditParentInfo > 4){
                System.out.println("""
                                Opps.. I think you messed up.
                                Enter the number of the information you want to edit?
                                1. Home 2. Birthdate 3. Phone 4. Email""");
                inputEditParentInfo = Integer.parseInt(reader.readLine());
            }

        } else if(role.equals("admin") || role.equals("teacher")){
            System.out.println("""
                    What about this parent you want to edit?
                    1. Home 2. Birthdate 3. Phone 4. Email 5. Add a kid
                    Enter the number:""");
            inputEditParentInfo = Integer.parseInt(reader.readLine());
            while(inputEditParentInfo <= 0 || inputEditParentInfo > 5){
                System.out.println("""
                                Opps.. I think you messed up.
                                Enter the number of the information you want to edit?
                                1. Home 2. Birthdate 3. Phone 4. Email 5. Add a kid""");
                inputEditParentInfo = Integer.parseInt(reader.readLine());
            }
        }

        boolean editParentError = true;
        do {
            try {
                String inputEditParentNewInfo = "";
                if(inputEditParentInfo < 5) {
                    System.out.println("Write down the new information:");
                    inputEditParentNewInfo = reader.readLine();
                }

                for (Parent parent2 : allParents) {
                    if (parent2.equals(inputParent)) {
                        if (inputEditParentInfo == 1) { //Edit parent's home
                            parent2.setHome(inputEditParentNewInfo);
                        } else if (inputEditParentInfo == 2) { //Edit parent's birthdate
                            parent2.setBirthDate(inputEditParentNewInfo);
                        } else if (inputEditParentInfo == 3){ //Edit parent's phone
                            parent2.setPhone(inputEditParentNewInfo);
                        } else if (inputEditParentInfo == 4){ //Edit parent's email
                            parent2.setEmail(inputEditParentNewInfo);
                        } else if (inputEditParentInfo == 5){ //Add a kid
                            addKidToParent(reader, inputParent.getName(), inputParent, allKids);
                        }
                    }
                }

                editParentError = false;
            } catch (Exception e) {
                System.out.println(e.getMessage() + "\nTry with new input.");
            }
        } while (editParentError);

        System.out.println("YAY! You successfully changed the information about " + inputParent.getName() + "\n");
    }

    public static void modifyTeacherInfo(BufferedReader reader, String teachersName, Teacher inputTeacher, List<Teacher> allTeachers, List<Kid> allkids) throws IOException {
        String editTeacherText = """
                What do you want to edit?\s
                1. Home 2. Birthdate 3. Add a new kid 4. Add existing kid
                Enter the number""";
        System.out.println(editTeacherText);
        int inputEditTeacherInfo = Integer.parseInt(reader.readLine());

        //Check about the input:
        while(inputEditTeacherInfo <= 0 || inputEditTeacherInfo >= 5){
            System.out.println("Opps.. I think you messed up.\n" + editTeacherText);
            inputEditTeacherInfo = Integer.parseInt(reader.readLine());
        }

        boolean editTeacherError = true;
        do {
            try {
                if(inputEditTeacherInfo == 3){
                    System.out.println("""
                            To add a new kid, it should be written in the following format:\s
                            *name* *home* *birthdate* *group* *startdate*
                            Please note, that they should be splitted ONLY with spaces.""");
                }
                if(inputEditTeacherInfo == 4){
                    printAllKidsNames(allkids);
                    System.out.println("Which kid you want to add: ");
                }

                System.out.println("Write down the information:");
                String inputEditTeacherNewInfo = reader.readLine();

                for (Teacher teacher : allTeachers) {
                    if (teacher.equals(inputTeacher)) {
                        if (inputEditTeacherInfo == 1) { //Edit teacher's home
                            teacher.setHome(inputEditTeacherNewInfo);
                        } else if (inputEditTeacherInfo == 2) { //Edit teacher's birthdate
                            teacher.setBirthDate(inputEditTeacherNewInfo);
                        } else if (inputEditTeacherInfo == 3){ //Edit teacher's kids
                            String[] addKidToTeacher = inputEditTeacherNewInfo.split("\\s+");
                            String nameOfKid = getFirstAndLastNameToFullNameFromArray(addKidToTeacher);
                            Kid newKid = new Kid(nameOfKid, addKidToTeacher[2], addKidToTeacher[3],
                                    Integer.parseInt(addKidToTeacher[4]), addKidToTeacher[5]);
                        } else if (inputEditTeacherInfo == 4){
                            Kid addKidToTeacher = null;
                            for (Kid allkid : allkids) {
                                if(allkid.getName().equals(inputEditTeacherNewInfo)){
                                    addKidToTeacher = allkid;
                                }
                            }

                            boolean addKidToTeacherChecker = true;
                            while (addKidToTeacherChecker){
                                if(addKidToTeacher == null){
                                    System.out.println("Sorry, we can't found kid with this name. Try again");
                                    inputEditTeacherNewInfo = reader.readLine();
                                    for (Kid allkid : allkids) {
                                        if(allkid.getName().equals(inputEditTeacherNewInfo)){
                                            addKidToTeacher = allkid;
                                        }
                                    }
                                } else {
                                    addKidToTeacherChecker = false;
                                }
                            }
                            teacher.addKid(addKidToTeacher);
                        }
                    }
                }
                editTeacherError = false;
            } catch (Exception e) {
                System.out.println(e.getMessage() + "\nTry with new input.");
            }
        } while (editTeacherError);

        System.out.println("YAY! You successfully changed the information about " + inputTeacher.getName() + "\n");

    }

    public static void checkPassword(String inputName, BufferedReader reader, HashMap<String,String> hashmap) throws IOException {
        System.out.println("Hi, " + inputName + "! First you should enter your password: ");
        String inputPass = reader.readLine();
        while(!inputPass.equals(hashmap.get(inputName))){
            System.out.println("Sorry. You entered wrong password. Try again");
            inputPass = reader.readLine();
        }
    }

    public static String checkEnterName(HashMap<String,String> hashmap, String inputName, BufferedReader reader) throws IOException {
        while(!hashmap.containsKey(inputName)){
            System.out.println("Sorry. You entered wrong name. Try again");
            for(String key : hashmap.keySet()) {
                System.out.println("* " + key);
            }
            inputName = reader.readLine();
        }
        return inputName;
    }

    public static int chooseOperation(String role, BufferedReader reader) throws IOException {
        System.out.println("Now, what's next? Choose the number.\n" +
                "1. Add a " + role + " 2. Edit a " + role + " 3. Remove a " + role + " 4. Get info");
        int m = Integer.parseInt(reader.readLine());
        while (m <= 0 || m >= 5) {
            System.out.println("Opps.. I think you messed up.\n Now, what's next? Choose the number.\n" +
                    "1. Add a " + role + " 2. Edit a " + role + " 3. Remove a " + role + " 4. Get info");
            m = Integer.parseInt(reader.readLine());
        }
        return m;
    }

    public static Teacher getTeacherForChange(List<Teacher> allTeachers, BufferedReader reader) throws IOException {
        List<String> teachersNames = new LinkedList<>();
        for (Teacher teacher : allTeachers) {
            teachersNames.add(teacher.getName());
        }
        System.out.print(String.join(", ", teachersNames));
        System.out.println(" | Write down the name.");
        String inputEditTeacherName = reader.readLine();
        Teacher editTeacher = null;
        for (Teacher teacher : allTeachers) {
            if(inputEditTeacherName.equals(teacher.getName())){
                editTeacher = teacher;
            }
        }
        boolean checkerEditTeacher = true;
        while(checkerEditTeacher) {
            if (editTeacher == null) {
                System.out.println("Opps.. I think you messed up. Write down the correct name.");
                inputEditTeacherName = reader.readLine();
                for (Teacher teacher : allTeachers) {
                    if (teacher.getName().equals(inputEditTeacherName)) {
                        editTeacher = teacher;
                    }
                }
            } else {
                checkerEditTeacher = false;
            }
        }
        return editTeacher;
    }

    public static Parent getParentForChange(List<Parent> allParents, BufferedReader reader) throws IOException {
        printAllParentsNames(allParents);
        System.out.println("Write down the name.");
        String inputEditParentName = reader.readLine();
        Parent editParent = null;
        for (Parent parent : allParents) {
            if(parent.getName().equals(inputEditParentName)){
                editParent = parent;
            }
        }
        boolean checkerEditParent = true;
        while(checkerEditParent) {
            if (editParent == null) {
                System.out.println("Opps.. I think you messed up. Write down the correct name.");
                inputEditParentName = reader.readLine();
                for (Parent parent : allParents) {
                    if (parent.getName().equals(inputEditParentName)) {
                        editParent = parent;
                    }
                }
            } else {
                checkerEditParent = false;
            }
        }
        return editParent;
    }

    public static String getFirstAndLastNameToFullNameFromArray(String[] inputArray){
        String fullName = Arrays.toString(Arrays.copyOfRange(inputArray, 0, 2));
        fullName = fullName.replaceAll("[\\[\\](){}]", "");
        fullName = fullName.replaceAll(",", "");
        return fullName;
    }

    public static void addPasswordToTheNewPerson(BufferedReader reader, String role, HashMap<String,String> passwords, String name) throws IOException {
        System.out.println("Now enter the password for this " + role + ":");
        String inputAddTeacherPass = reader.readLine();
        passwords.put(name, inputAddTeacherPass);
    }

    public static void printAllKidsNames(List<Kid> allKids){
        List<String> kidsNames = new ArrayList<>();
        for (Kid allKid : allKids) {
            kidsNames.add(allKid.getName());
        }
        System.out.println(String.join(", ", kidsNames));
    }

    public static void addKidToParent(BufferedReader reader, String parentName, Parent parent, List<Kid> allKids) throws IOException {
        System.out.println("Now enter the name of the kid of " + parentName +
                "\nThe kids that exist in the system are:");
        printAllKidsNames(allKids);
        String inputNameKidToAdd = reader.readLine();
        Kid inputAddKid = null;
        for (Kid kid : allKids) {
            if(kid.getName().equals(inputNameKidToAdd)){
                inputAddKid = kid;
            }
        }

        while(inputAddKid == null){
            System.out.println("Opps.. I think you messed up. Try with new input." +
                    "\nNote that the kid should exist in the system!");
            inputNameKidToAdd = reader.readLine();
            for (Kid kid : allKids) {
                if(kid.getName().equals(inputNameKidToAdd)){
                    inputAddKid = kid;
                }
            }
        }

        parent.addKid(inputAddKid);
    }

    public static void modifyParent(BufferedReader reader, List<Kid> allKids, List<Parent> allParents,HashMap<String, String> parentsPasswords) throws IOException {
        //1. Add a parent 2. Edit a parent 3. Remove a parent 4. Get info
        int numberOfCommand = chooseOperation("teacher", reader);

        if(numberOfCommand == 1){ //Modify the parents -> Add a parent
            String addParentText = """
                                To add a new parent, you should enter information about him.
                                Enter it in the following format: *name* *home* *birthdate* *phone* *email*
                                Note that they should be splitted ONLY with spaces.""";
            System.out.println(addParentText);

            boolean pError = true;
            do {
                try {
                    String[] inputAddParent = reader.readLine().split("\\s+");
                    String inputAddParentName = getFirstAndLastNameToFullNameFromArray(inputAddParent);
                    Parent parent2 = new Parent(inputAddParentName, inputAddParent[2], inputAddParent[3],
                            inputAddParent[4], inputAddParent[5]);
                    pError = false;

                    addKidToParent(reader, inputAddParentName, parent2, allKids);

                    addPasswordToTheNewPerson(reader, "parent", parentsPasswords, inputAddParentName);

                    allParents.add(parent2);
                    System.out.println("YAY! You successfully added a new parent!\n");

                } catch (Exception e) {
                    System.out.println(e.getMessage() + "\n" + addParentText);
                }
            } while (pError);

        } else if(numberOfCommand == 2){ //Modify the parents -> Edit a parent
            System.out.println("Which parent you want to edit?");

            Parent inputParent = getParentForChange(allParents,reader);

            editParentInfo(reader, allParents, inputParent, allKids, "teacher");

        } else if(numberOfCommand == 3){ //Modify the parents -> Remove a parent
            System.out.println("To remove a parent, you should choose which one:");
            Parent inputParentRemove = getParentForChange(allParents, reader);
            allParents.remove(inputParentRemove);
            parentsPasswords.remove(inputParentRemove.getName());
            System.out.println("YAY! You successfully removed " + inputParentRemove.getName() + " from the system.\n");
            for (Kid kid : allKids) {
                if(kid.getParents().contains(inputParentRemove)){
                    kid.removeParent(inputParentRemove);
                }
            }
        } else if(numberOfCommand == 4){
            System.out.println("Enter the name of parent you want the information");
            printAllParentsNames(allParents);
            String inputGetParentName = reader.readLine();
            Parent inputGetParent = null;
            for (Parent allparent : allParents) {
                if(allparent.getName().equals(inputGetParentName)){
                    inputGetParent = allparent;
                }
            }
            while(inputGetParent == null){
                System.out.println("Opps.. I think you messed up. Try with new input.");
                inputGetParentName = reader.readLine();
                for (Parent allparent : allParents) {
                    if(allparent.getName().equals(inputGetParentName)){
                        inputGetParent = allparent;
                    }
                }
            }
            String getInfo = inputGetParent.toString();
            writeInFile(FirstStep.PATH, getInfo);
        }
    }

    public static void printAllParentsNames(List<Parent> allParents){
        List<String> parentsNames = new ArrayList<>();
        for (Parent allParent : allParents) {
            parentsNames.add(allParent.getName());
        }
        System.out.println(String.join(", ", parentsNames));
    }

    public static void modifyKidAddNewKid(BufferedReader reader, Parent parent, List<Parent> allParents){
        boolean kChecker = true;
        do {
            try {
                System.out.println("""
                                        Enter the information about the new kid.
                                        It should be in the following format: *name* *home* *birthdate* *group* *startdate*
                                        Please note, that they should be splitted ONLY with spaces.""");
                String[] inputNewKidInfo = reader.readLine().split("\\s+");
                String inputNewKidName = getFirstAndLastNameToFullNameFromArray(inputNewKidInfo);
                Kid newKid = new Kid(inputNewKidName, inputNewKidInfo[2], inputNewKidInfo[3], Integer.parseInt(inputNewKidInfo[4]), inputNewKidInfo[5]);

                newKid.addParent(parent); //Add Parent to Kid
                for (Parent allParent : allParents) { //Add Kid to Parent
                    if(allParent.equals(parent)){
                        allParent.addKid(newKid);
                    }
                }

                kChecker = false;
            } catch (Exception e){
                System.out.println(e.getMessage() + "\nTry with new input.");
            }
        } while (kChecker);
    }

    public static void modifyKidEditKid (BufferedReader reader, List<Kid> kids, List<Parent> allParents, List<Teacher> allTeachers, List<Kid> allKids) throws IOException {
        System.out.println("Enter the name of kid you want to edit.");
        printAllKidsNames(allKids);
        System.out.println(" | Write down the name.");
        String editKidName = reader.readLine();

        Kid editKid = null;

        for (Kid kid : kids) {
            if(kid.getName().equals(editKidName)){
                editKid = kid;
            }
        }
        while(editKid == null){
            System.out.println("Opps.. I think you messed up. Try with new input.");
            editKidName = reader.readLine();
            for (Kid kid : kids) {
                if(kid.getName().equals(editKidName)){
                    editKid = kid;
                }
            }
        }

        String messedUpText = "Opps.. I think you messed up. Try with new input.";

        String editKidText = """
                Which information you want to edit. Enter the number
                1. Home 2. Birthdate 3. Group 4. Startdate 5. Add absent date 6. Add parent 7. Add teacher""";
        int commandEditKid = Integer.parseInt(reader.readLine());

        while(commandEditKid<= 0 || commandEditKid >= 8){
            System.out.println(messedUpText + "\n" + editKidText);
            commandEditKid = Integer.parseInt(reader.readLine());
        }

        boolean keChecker = true;
        do{
            try{
                if(commandEditKid <= 5){
                    System.out.println("Enter the new information: ");
                    String inputEditKidNewInfo = reader.readLine();
                    if(commandEditKid == 1){
                        editKid.setHome(inputEditKidNewInfo);
                    } else if(commandEditKid == 2){
                        editKid.setBirthDate(inputEditKidNewInfo);
                    } else if(commandEditKid == 3){
                        editKid.setGroup(Integer.parseInt(inputEditKidNewInfo));
                    } else if(commandEditKid == 4){
                        editKid.setStartDate(inputEditKidNewInfo);
                    } else if(commandEditKid == 5){
                        editKid.addAbsentDate(inputEditKidNewInfo);
                    }
                } else if(commandEditKid == 6){
                    System.out.println("Enter the name of the parent\nNote that he should exist in the system");
                    String inputEditKidParentName = reader.readLine();
                    Parent inputEditAddParent = null;
                    for (Parent allParent : allParents) {
                        if(allParent.getName().equals(inputEditKidParentName)){
                            inputEditAddParent = allParent;
                        }
                    }

                    while(inputEditAddParent == null){
                        System.out.println(messedUpText);
                        inputEditKidParentName = reader.readLine();
                        for (Parent allParent : allParents) {
                            if(allParent.getName().equals(inputEditKidParentName)){
                                inputEditAddParent = allParent;
                            }
                        }
                    }
                    editKid.addParent(inputEditAddParent);
                } else if(commandEditKid == 7){
                    System.out.println("Enter the name of the teacher\nNote that he should exist in the system");
                    String inputEditTeacherName = reader.readLine();
                    Teacher inputEditAddTeacher = null;
                    for (Teacher allTeacher : allTeachers) {
                        if(allTeacher.getName().equals(inputEditTeacherName)){
                            inputEditAddTeacher = allTeacher;
                        }
                    }

                    while (inputEditAddTeacher == null){
                        System.out.println(messedUpText);
                        inputEditTeacherName = reader.readLine();
                        for (Teacher allTeacher : allTeachers) {
                            if(allTeacher.getName().equals(inputEditTeacherName)){
                                inputEditAddTeacher = allTeacher;
                            }
                        }
                    }
                    editKid.addTeacher(inputEditAddTeacher);
                }

                System.out.println("YAY! You successfully updated the info about " + editKidName);
                keChecker = false;
            } catch (Exception e){
                System.out.println(e.getMessage() + "\n" + messedUpText);
            }
        } while (keChecker);

    }

    public static void modifyKidRemoveKid(BufferedReader reader, List<Kid> kids, List<Kid> allKids, List<Parent> allParents, List<Teacher> allTeachers) throws IOException {
        System.out.println("Enter the name of the kid you want to remove.");
        String inputRemoveKidName = reader.readLine();
        Kid inputRemoveKid = null;
        for (Kid allKid : kids) {
            if(allKid.getName().equals(inputRemoveKidName)){
                inputRemoveKid = allKid;
            }
        }

        while(inputRemoveKid == null){
            System.out.println("Opps.. I think you messed up. Try with new input.");
            inputRemoveKidName = reader.readLine();
            for (Kid allKid : kids) {
                if(allKid.getName().equals(inputRemoveKidName)){
                    inputRemoveKid = allKid;
                }
            }
        }

        allKids.remove(inputRemoveKid);
        for (Parent allParent : allParents) {
            if(allParent.containKid(inputRemoveKid)){
                allParent.removeKid(inputRemoveKid);
            }
        }
        for (Teacher allTeacher : allTeachers) {
            if(allTeacher.containsKid(inputRemoveKid)){
                allTeacher.removeKid(inputRemoveKid);
            }
        }
        System.out.println("YAY! You successfully removed " + inputRemoveKidName);
    }

    public static void writeInFile(String PATH, String getInfo) throws IOException {
        FileWriter writer = new FileWriter(PATH);

        boolean writeError = true;
        do{
            try{
                writer.write(getInfo);
                writer.close();
                writeError = false;
            } catch (Exception e){
                System.out.println("Opps.. Something was wrong.");
            }
        } while(writeError);
        System.out.println("YAY! You successfully saved the information in a file. Go and check it!\n");
    }
}
