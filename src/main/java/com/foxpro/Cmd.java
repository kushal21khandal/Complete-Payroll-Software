package com.foxpro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;

class Cmd {


    HashMap<Long, Establishment> activeSessions = new HashMap<>();

    public void print(String string) {
        System.out.print(string + " : ");
    }

    public boolean checkInActiveSesssion(long pfRegNumber) {
        return activeSessions.containsKey(pfRegNumber);
    }

    public Establishment getFromActiveSessions(long pfRegNumber) {
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
    public void printSalaryStructure(SalaryStructure salaryStructure ) {
        print("basic");
        System.out.println(salaryStructure.basic);
        print("hra");
        System.out.println(salaryStructure.hra);
        print("convence");
        System.out.println(salaryStructure.convence);
        print("overtime");
        System.out.println(salaryStructure.overtime);
        print("washing allowance");
        System.out.println(salaryStructure.washingAllowance);
        print("msl1");
        System.out.println(salaryStructure.msl1);
        print("msl2");
        System.out.println(salaryStructure.msl2);
        print("msl3");
        System.out.println(salaryStructure.msl3);
    }

    public void run(String pathMain) throws IOException {
        // String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        HashMap<String , Integer> months = new HashMap<>();
        months.put("JAN", 31);
        months.put("FEB", 28);
        months.put("MAR", 31);
        months.put("APR", 30);
        months.put("MAY", 31);
        months.put("JUN", 30);
        months.put("JUL", 31);
        months.put("AUG", 31);
        months.put("SEP", 30);
        months.put("OCT", 31);
        months.put("NOV", 30);
        months.put("DEC", 31);

        String command = "";
        Establishment establishment;
        SalaryStructure salaryStructure;

        boolean mainState = true;
        boolean establishmentState = true;
        boolean employeeState = true;
        String userInput;

        InputStreamReader inputStreamReader = null;

        // Establishment fields
        long pfRegNumber, esicRegNumber, phoneNumber;
        String companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration;

        // Employees fields
        Employees employees;
        int year;
        String month = "", path_pf_csv = "", path_client_csv_optional = "" , region_optional = "";
        int daysInMonth = 0;


        // salary structure fields
        double
            basic,
            hra,
            convence ,
            overtime ,
            washingAllowance ,
            msl1 ,
            msl2 ,
            msl3 ;

        try {
            inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            System.out.println("######### AUTOMATING THE FOXPRO ##########\n\n");

            System.out.println("path_to_prnt_folder : " + pathMain + "\n");
            Manager.initiateMainConnection();

            while (mainState == true) {
                command = "cmd";

                System.out.println("\n\n\n\n================ ESTABLIHSMENT (est)  /  EMPLOYEE (emp) ======================");
                System.out.println("\ncommmands->   estab( add / update )  , emp( add / add_manually ) , backup , exit/quit \n");

                print(command);
                userInput = bufferedReader.readLine().trim();

                if (userInput.equalsIgnoreCase("est") || userInput.equalsIgnoreCase("establishment")) {
                    command = "estab";

                    // opening the connection
                    // Manager.initiateMainConnection();

                    while (establishmentState) {
                        System.out.println("\n================== ADD / UPDATE - ESTABLISHMENT ====================\n");

                        print(command);
                        userInput = bufferedReader.readLine().trim();

                        if (userInput.equalsIgnoreCase("add")) {

                            System.out.println("================ ADD - ESTABLISHMENT ==================");

                            print("PF_REGISTRATION_NUMBER");
                            pfRegNumber = Long.parseLong(bufferedReader.readLine().trim());

                            print("ESIC_REGISTRAION_NUMBER");
                            esicRegNumber = Long.parseLong(bufferedReader.readLine().trim());

                            print("COMPANY NAME");
                            companyName = bufferedReader.readLine().trim();

                            print("OWNER NAME");
                            ownerName = bufferedReader.readLine().trim();

                            print("PHONE NUMBER");
                            phoneNumber = Long.parseLong(bufferedReader.readLine().trim());

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
                            pfRegNumber = Long.parseLong(bufferedReader.readLine().trim());

                            if (checkInActiveSesssion(pfRegNumber)) {
                                establishment = getFromActiveSessions(pfRegNumber);
                            } else {
                                establishment = EstablishmentFactory.getEstablishment(pfRegNumber);
                                activeSessions.put(pfRegNumber, establishment);
                            }

                            print("Update Establishment or Update Salary Structure");
                            userInput = bufferedReader.readLine().trim();

                            if (userInput.matches("sal.*")) {
                                salaryStructure = SalaryStructureFactory.getSalaryStructure( pfRegNumber);

                                System.out.println("\n\n");
                                printSalaryStructure(salaryStructure);


                                System.out.println("\nCOMPARE OLD DETAILS WITH UPCOMING CHANGES  AND  PRESS [ENTER] IF DOES NOT REQUIRE ANY CHANGES\n");

                                print("basic");
                                userInput = bufferedReader.readLine().trim();
                                basic = userInput.equals("") ? salaryStructure.basic : Double.parseDouble(userInput);

                                print("hra");
                                userInput = bufferedReader.readLine().trim();
                                hra = userInput.equals("") ? salaryStructure.hra : Double.parseDouble(userInput);

                                print("convence");
                                userInput = bufferedReader.readLine().trim();
                                convence = userInput.equals("") ? salaryStructure.convence : Double.parseDouble(userInput);

                                print("overtime");
                                userInput = bufferedReader.readLine().trim();
                                overtime = userInput.equals("") ? salaryStructure.overtime : Double.parseDouble(userInput);

                                print("washing allowance");
                                userInput = bufferedReader.readLine().trim();
                                washingAllowance = userInput.equals("") ? salaryStructure.washingAllowance : Double.parseDouble(userInput);

                                print("msl1");
                                userInput = bufferedReader.readLine().trim();
                                msl1 = userInput.equals("") ? salaryStructure.msl1 : Double.parseDouble(userInput);

                                print("msl2");
                                userInput = bufferedReader.readLine().trim();
                                msl2 = userInput.equals("") ? salaryStructure.msl2 : Double.parseDouble(userInput);

                                print("msl3");
                                userInput = bufferedReader.readLine().trim();
                                msl3 = userInput.equals("") ? salaryStructure.msl3 : Double.parseDouble(userInput);

                                System.out.println("\n Confirm changes : ");
                                print("basic");
                                System.out.println(basic);
                                print("hra");
                                System.out.println(hra);
                                print("convence");
                                System.out.println(convence);
                                print("overtime");
                                System.out.println(overtime);
                                print("washing allowance");
                                System.out.println(washingAllowance);
                                print("msl1");
                                System.out.println(msl1);
                                print("msl2");
                                System.out.println(msl2);
                                print("msl3");
                                System.out.println(msl3);

                                print("\nMAKE CHANGES ( y / n )");
                                userInput = bufferedReader.readLine().trim();
                                if (userInput.equalsIgnoreCase("y")) {
                                    salaryStructure.updateSalaryStructure(pfRegNumber , basic, hra, washingAllowance, convence, overtime, msl1, msl2, msl3);
                                } else {
                                    System.out.println("aborted");
                                }


                            } else {

                                System.out.println("\n\n");
                                printEstablishment(establishment);

                                System.out.println("\nCOMPARE OLD DETAILS WITH UPCOMING CHANGES  AND  PRESS [ENTER] IF DOES NOT REQUIRE ANY CHANGES\n");

                                print("PF_REGISTRATION_NUMBER");
                                userInput = bufferedReader.readLine().trim();
                                pfRegNumber = userInput.equals("") ? establishment.pfRegNumber : Long.parseLong(userInput);

                                print("ESIC_REGISTRAION_NUMBER");
                                userInput = bufferedReader.readLine().trim();
                                esicRegNumber = userInput.equals("") ? establishment.esicRegNumber : Long.parseLong(userInput);

                                print("COMPANY NAME");
                                userInput = bufferedReader.readLine().trim();
                                companyName = userInput.equals("") ? establishment.companyName : userInput;

                                print("OWNER NAME");
                                userInput = bufferedReader.readLine().trim();
                                ownerName = userInput.equals("") ? establishment.ownerName : userInput;

                                print("PHONE NUMBER");
                                userInput = bufferedReader.readLine().trim();
                                phoneNumber = userInput.equals("") ? establishment.phoneNumber : Long.parseLong(userInput);

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
                            }
                        } else if (userInput.equalsIgnoreCase("quit") || userInput.equalsIgnoreCase("exit")) {
                            //closing the connection
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
                            pfRegNumber = Long.parseLong(bufferedReader.readLine().trim());

                            print("YEAR");
                            year = Integer.parseInt(bufferedReader.readLine().trim());
                            print("MONTH");
                            month = bufferedReader.readLine().trim();

                            print("days in month");
                            daysInMonth = Integer.parseInt(( bufferedReader.readLine().trim() ));

                            print("region ( destrict based ) [ if no region : leave empty ]");
                            region_optional = bufferedReader.readLine().trim();

                            print("PATH TO PF SITE CSV FILE ");
                            path_pf_csv = bufferedReader.readLine().trim();

                            print("PATH TO CLIENT SIDE CSV FILE ( if no path : automatic switch to manaul mode )");
                            path_client_csv_optional = bufferedReader.readLine().trim();

                            if ( region_optional.isBlank()){
                                region_optional = null;
                            }

                            if ( path_client_csv_optional.isBlank()){
                                path_client_csv_optional = null;
                            }



                            for (String m : months.keySet()){
                                if ( m.equals(month)){
                                        employees = new Employees(pfRegNumber , year , month , daysInMonth , region_optional , path_pf_csv , path_client_csv_optional);
                                        employees.addEmployees(bufferedReader);
                                    break;
                                }
                                else{
                                    if (m.equals("DEC")){
                                        System.out.println("month not in the correct format , use months from the below list ( CAPITAL LETTERS) ");
                                        System.out.println(months.keySet());
                                    }
                                }
                            }



                        } else if (userInput.equalsIgnoreCase("add_manually")) {
                            System.out.println(":this is not going to work");

                        } else if (userInput.equalsIgnoreCase("quit") || userInput.equalsIgnoreCase("exit")) {
                            Manager.closeEmployeeConnection();
                            employeeState = false;
                        } else {
                            System.out.println("command not found , use  [add / add_manually] for adding employeess ");
                        }

                    }
                } else if (userInput.equalsIgnoreCase("report")) {
                    System.out.println("\n================== GENERATE - REPORT ====================\n");
                    print("pf_reg_number");
                    pfRegNumber = Long.parseLong(bufferedReader.readLine().trim());
                    print("year");
                    year = Integer.parseInt(bufferedReader.readLine().trim());
                    print("month");
                    month = bufferedReader.readLine().trim();
                    print("region ( optional , if no particular region -> leave empty : press [ENTER])");
                    region_optional = bufferedReader.readLine().trim();
                    try {
                            for (String m : months.keySet()){
                                if (m.equals(month)){
                                    Manager.GenerateReport.generateReport(pfRegNumber , year , month , region_optional.isBlank() ? null : region_optional, months.get(m));
                                    break;
                                }
                                else{
                                    if (m.equals("DEC")){
                                        System.out.println("month not in the correct format , use months from the below list ( CAPITAL LETTERS) ");
                                        System.out.println(months.keySet());
                                    }
                                }
                            }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }


                } else if (userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("quit")) {
                    mainState = false;
                } else {
                    System.out.println("command not found , use commands :  est , emp , report , exit/quit\n\n");
                }
            }
            bufferedReader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            Manager.closeMainConnection();
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            // if ( logFile != null){
            //     logFile.close();
            // }
        }

    }

}
