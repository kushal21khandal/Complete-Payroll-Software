package com.foxpro;

import java.io.BufferedReader;
import java.io.FileReader;

import com.foxpro.databaseManager.EmployeeDatabaseHandler;

class Employees {

    String month;
    int year;
    long pfRegNumber;
    String pathPfCSV, pathClientCSV;
    int month_days;


    SalaryStructure salaryStructure;

    Employees(long pfRegNumber, int year, String month, String pathPfCSV, String pathClientCSV) {
        this.pfRegNumber = pfRegNumber;
        this.year = year;
        this.month = month;
        this.pathPfCSV = pathPfCSV;
        this.pathClientCSV = pathClientCSV;
        Manager.checkAndCreateDir(pfRegNumber, year, month);

        salaryStructure = SalaryStructureFactory.getSalaryStructure(pfRegNumber);
    }

    void addEmployees(BufferedReader consoleBufferedReader) {

        final double PF_REDUCTION_PERCENTAGE = Config.getPfDeductionPercentage();
        final double ESIC_DEDUCTION_PERCENTAGE = Config.getEsicDeductionPercentage();



        FileReader reader = null;
        String line ;
        String[] lineSplit ;
        double attendance , totalSalary , basic , hra , convence, overtime , washingAllowance , msl1 , msl2 , msl3 , pfReduction , esicReduction , pfPaidByEmployer , pfPaidByEmployee , netPayableAmount;
        int index = 0;


        try {

                Manager.initiateEmployeesConnection(pfRegNumber + "", year + "" , month, month + ".db");
                Manager.executeCreateTableCommand(pfRegNumber , year , month , month+".db");

                // import pathPfSiteCSV into table created
                Manager.executeFillTableCommand(pfRegNumber + "" , year + "" , month , month+".db" , pathPfCSV);

                System.out.print("total days in month : ");
                month_days =Integer.parseInt(  consoleBufferedReader.readLine().trim() );


                reader = new FileReader(pathClientCSV);
                BufferedReader fileBufferedReader = new BufferedReader(reader);

                while( ( line = fileBufferedReader.readLine()  ) != null){

                    if ( index != 0){
                        lineSplit = line.split(",");
                        EmployeeDatabaseHandler.printEmployeeDetails(lineSplit[0]);

                        System.out.print("totalSalary : ");
                        totalSalary = Double.parseDouble( consoleBufferedReader.readLine().trim() );
                        System.out.print("Attendance : ");
                        attendance = Double.parseDouble(consoleBufferedReader.readLine().trim());

                        basic = ( salaryStructure.basic * totalSalary) / 100;
                        hra = (salaryStructure.hra * totalSalary) /100 ;
                        convence = ( salaryStructure.convence * totalSalary) / 100;
                        overtime = ( salaryStructure.overtime * totalSalary) / 100 ;
                        washingAllowance = ( salaryStructure.washingAllowance * totalSalary) / 100 ;

                        pfReduction =

                        //coditions
                        /*
                         * no pf reduction under 15000 ( y ) : where y ?
                         * no pf reduction over the age of 58 : where age ?
                         * pf reduction rate 12% of x : where x ?
                         * esic reduction rate is 0.75% of x : where x ?
                         */




















                    }
                    index++;

                }

                fileBufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Manager.closeEmployeeConnection();
            if ( reader != null){
                reader = null;
            }
        }

    }

}
