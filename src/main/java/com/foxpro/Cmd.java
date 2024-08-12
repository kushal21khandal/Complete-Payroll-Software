package com.foxpro;

import java.io.BufferedReader;
import java.util.ArrayList;

class Cmd {

    public void print(String string) {
        System.out.print(string + " : ");
    }

    public void run(){
        String command = "cmd : "
        ArrayList<Establihment> establishments = new ArrayList<>();
        Establishment establishment;

        boolean acitveState = true;
        String userInput = "";

        String pathToPrntFolder = Config.pathToPrntFolder;

        InputStreamReader inputStraemReader = null;

        try{
            inputStraemReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            System.out.println("######### AUTOMATING THE FIREFOX ##########\n\n");

            System.out.println("path_to_prnt_folder : " + pathToPrntFolder + "\n");

            while(acitveState == true){

                System.out.println("================ ESTABLIHSMENT (est)  /  EMPLOYEE (emp) ======================");
                System.out.print(command)
                userInput = bufferedReader.readLine().trim();

                if ( userInput.equalsIgnoreCase("est") || userInput.equalsIgnoreCase("establishment")){
                    establishment = new Establishment();

                    System.out.println("\n================== ADD / UPDATE - ESTABLISHMENT ====================\n");

                    userInput = bufferedReader.readLine().trim();

                    if ( userInput.equalsIgnoreCase("add")){

                        System.out.println("================ ADD - ESTABLISHMENT ==================");

                        print("PF_REGISTRATION_NUMBER");
                        userInput = bufferedReader.readLine().trim();
                        establishment.pfRegNumber = Integer.parseInt(userInput);

                        print("ESIC_REGISTRAION_NUMBER");
                        userInput = bufferedReader.readLine().trim();
                        establishment.esicRegNumber = Integer.parseInt(userInput);

                        print("COMPANY NAME");
                        userInput = bufferedReader.readLine().trim();
                        establishment.companyName = userInput;

                        print("OWNER NAME")
                        userInput = bufferedReader.readLine().trim();
                        establishment.ownerName = userInput;

                        print("PHONE NUMBER");
                        userInput = bufferedReader.readLine().trim();
                        establishment.phoneNumber = Integer.parseInt(userInput);

                        print("DATE OF PF_REGISTRATION ( DD/MM/YYYY )")
                        userInput = bufferedReader.readLine().trim();
                        establishment.dateOfPfRegistration = userInput;

                        print("DATE OF ESIC_REGISTRATION ( DD/MM/YYYY )")
                        userInput = bufferedReader.readLine().trim();
                        establishment.dateOfEsicRegistration = userInput;

                        print("ADDRESS")
                        userInput = bufferedReader.readLine().trim();
                        establishment.address = userInput;

                        establishment.addEstablishment();

                    }
                    else if ( userInput.equalsIgnoreCase("update")){

                        System.out.println("=================== UPDATE - ESTABLISHMENT ======================");

                        print("PF_REGISTRATION_NUMBER");
                        userInput = bufferedReader.readLine().trim();

                        establishment.getEstablishment(Integer.parseInt( userInput ));

                        



                    }







                }
            }
        }










    }

}