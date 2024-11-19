package com.foxpro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

class Cmd {

    HashMap<Integer, Establishment> activeSessions = new HashMap<>();

    public void print(String string) {
        System.out.print(string + " : ");
    }

    public boolean checkInActiveSesssion(int pfRegNumber) {
        return activeSessions.containsKey(pfRegNumber);
    }

    public Establishment getFromActiveSessions(int pfRegNumber) {
        return activeSessions.get(pfRegNumber);
    }

    public void printEstablishment(Establishment establishment) {
        print("ESIC REGISTRATION");
        System.out.println(establishment.esicRegNumber);
        print("COMPANY NAME");
        System.out.println(establishment.companyName);
        print("OWNER NAME");
        System.out.println(establishment.ownerName);
        print("PHONE NUMBER");
        System.out.println(establishment.phoneNumber);
        print("date of pf registration");
        System.out.println(establishment.dateOfPfRegistration);
        print("date of esic registration");
        System.out.println(establishment.dateOfEsicRegistration);
        print("address");
        System.out.println(establishment.address);

    }

    public void run(String pathMain) throws  IOException{
        String command = "";
        Establishment establishment;

        boolean mainState = true;
        boolean establishmentState = true;
        boolean employeeState = true;
        String userInput;

        InputStreamReader inputStreamReader = null;

        // Establishment fields
        int pfRegNumber, esicRegNumber, phoneNumber;
        String companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration;

        // Employees fields
        Employees employees;
        int year;
        String month = "", pathPfCSV = "", pathClientCSV = "";

        try {
            inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            System.out.println("######### AUTOMATING THE FOXPRO ##########\n\n");

            System.out.println("path_to_prnt_folder : " + pathMain + "\n");

            while (mainState == true) {
                command = "cmd";

                System.out.println("\n\n\n\n================ ESTABLIHSMENT (est)  /  EMPLOYEE (emp) ======================");
                System.out.println("\ncommmands->   estab( add / update )  , emp( add / add_manually ) , backup , exit/quit \n");

                print(command);
                userInput = bufferedReader.readLine().trim();

                if (userInput.equalsIgnoreCase("est") || userInput.equalsIgnoreCase("establishment")) {
                    command = "estab";




                    // opening the connection
                    Manager.initiateMainConnection();




                    while (establishmentState) {
                        System.out.println("\n================== ADD / UPDATE - ESTABLISHMENT ====================\n");

                        print(command);
                        userInput = bufferedReader.readLine().trim();

                        if (userInput.equalsIgnoreCase("add")) {

                            System.out.println("================ ADD - ESTABLISHMENT ==================");

                            print("PF_REGISTRATION_NUMBER");
                            pfRegNumber = Integer.parseInt(bufferedReader.readLine().trim());

                            print("ESIC_REGISTRAION_NUMBER");
                            esicRegNumber = Integer.parseInt(bufferedReader.readLine().trim());

                            print("COMPANY NAME");
                            companyName = bufferedReader.readLine().trim();

                            print("OWNER NAME");
                            ownerName = bufferedReader.readLine().trim();

                            print("PHONE NUMBER");
                            phoneNumber = Integer.parseInt(bufferedReader.readLine().trim());

                            print("DATE OF PF_REGISTRATION ( DD/MM/YYYY )");
                            dateOfPfRegistration = bufferedReader.readLine().trim();

                            print("DATE OF ESIC_REGISTRATION ( DD/MM/YYYY )");
                            dateOfEsicRegistration = bufferedReader.readLine().trim();

                            print("ADDRESS");
                            address = bufferedReader.readLine().trim();

                            establishment = new Establishment(pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
                            establishment.addEstablishment();
                            activeSessions.put(pfRegNumber, establishment);

                        } else if (userInput.equalsIgnoreCase("update")) {

                            System.out.println("=================== UPDATE - ESTABLISHMENT ======================");

                            print("PF_REGISTRATION_NUMBER");
                            pfRegNumber = Integer.parseInt(bufferedReader.readLine().trim());

                            if (checkInActiveSesssion(pfRegNumber)) {
                                establishment = getFromActiveSessions(pfRegNumber);
                            } else {
                                establishment = EstablishmentFactory.getEstablishment(pfRegNumber);
                                activeSessions.put(pfRegNumber, establishment);
                            }

                            System.out.println("\n\n");
                            printEstablishment(establishment);

                            System.out.println("\nCOMPARE OLD DETAILS WITH UPCOMING CHANGES  AND  PRESS [ENTER] IF DOES NOT REQUIRE ANY CHANGES\n");

                            print("PF_REGISTRATION_NUMBER");
                            userInput = bufferedReader.readLine().trim();
                            pfRegNumber = userInput.equals("") ? establishment.pfRegNumber : Integer.parseInt(userInput);

                            print("ESIC_REGISTRAION_NUMBER");
                            userInput = bufferedReader.readLine().trim();
                            esicRegNumber = userInput.equals("") ? establishment.esicRegNumber : Integer.parseInt(userInput);

                            print("COMPANY NAME");
                            userInput = bufferedReader.readLine().trim();
                            companyName = userInput.equals("") ? establishment.companyName : userInput;

                            print("OWNER NAME");
                            userInput = bufferedReader.readLine().trim();
                            ownerName = userInput.equals("") ? establishment.ownerName : userInput;

                            print("PHONE NUMBER");
                            userInput = bufferedReader.readLine().trim();
                            phoneNumber = userInput.equals("") ? establishment.phoneNumber : Integer.parseInt(userInput);

                            print("DATE OF PF_REGISTRATION ( DD/MM/YYYY )");
                            userInput = bufferedReader.readLine().trim();
                            dateOfPfRegistration = userInput.equals("") ? establishment.dateOfPfRegistration : userInput;

                            print("DATE OF ESIC_REGISTRATION ( DD/MM/YYYY )");
                            userInput = bufferedReader.readLine().trim();
                            dateOfEsicRegistration = userInput.equals("") ? establishment.dateOfEsicRegistration : userInput;

                            print("ADDRESS");
                            userInput = bufferedReader.readLine().trim();
                            address = userInput.equals("") ? establishment.address : userInput;

                            System.out.println("\n Confirm changes : ");
                            print("PF REISTRATION");
                            System.out.println(pfRegNumber);
                            print("ESIC REGISTRATION");
                            System.out.println(esicRegNumber);
                            print("COMPANY NAME");
                            System.out.println(companyName);
                            print("OWNER NAME");
                            System.out.println(ownerName);
                            print("PHONE NUMBER");
                            System.out.println(phoneNumber);
                            print("date of pf registration");
                            System.out.println(dateOfPfRegistration);
                            print("date of esic registration");
                            System.out.println(dateOfEsicRegistration);
                            print("address");
                            System.out.println(address);

                            print("\nMAKE CHANGES ( y / n )");
                            userInput = bufferedReader.readLine().trim();
                            if (userInput.equalsIgnoreCase("y")) {
                                establishment.updateEstablishment(pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
                            } else {
                                System.out.println("aborted");
                            }
                        } else if (userInput.equalsIgnoreCase("quit") || userInput.equalsIgnoreCase("exit")) {
                            //closing the connection
                            Manager.closeMainConnection();
                            establishmentState = false;
                        } else {
                            System.out.println("command not found , use [ add / update ] for adding or updating establishments");
                        }
                    }

                } else if (userInput.equals("emp") || userInput.equals("employees") || userInput.equals("employee")) {

                    command = "emp";

                    while (employeeState) {

                        System.out.println("\n================== ADD - EMPLOYEES ====================\n");

                        print(command);
                        userInput = bufferedReader.readLine().trim();

                        if (userInput.equalsIgnoreCase("add")) {

                            print("PF REGISTRATION NUMBER");
                            pfRegNumber = Integer.parseInt(bufferedReader.readLine().trim());

                            print("YEAR");
                            year = Integer.parseInt(bufferedReader.readLine().trim());
                            print("MONTH");
                            month = bufferedReader.readLine().trim();
                            print("PATH TO PF SITE CSV FILE");
                            pathPfCSV = bufferedReader.readLine().trim();


                            print("PATH TO CLIENT SIDE CSV FILE");
                            pathClientCSV = bufferedReader.readLine().trim();

                            employees = new Employees(pfRegNumber, year, month.substring(0, 3).toUpperCase(), pathPfCSV, pathClientCSV);
                            employees.addEmployees();


                        } else if (userInput.equalsIgnoreCase("add_manually")) {
                            System.out.println("user working");

                        } else if (userInput.equalsIgnoreCase("quit") || userInput.equalsIgnoreCase("exit")) {
                            employeeState = false;
                        } else {
                            System.out.println("command not found , use  [add / add_manually] for adding employeess ");
                        }

                    }
                } else if (userInput.equalsIgnoreCase("backup")) {


                } else if (userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("quit")) {
                    mainState = false;
                } else {
                    System.out.println("command not found , use commands :  est , emp , backup , exit/quit\n\n");
                }
            }
            bufferedReader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            System.exit(0);
        }

    }

}
