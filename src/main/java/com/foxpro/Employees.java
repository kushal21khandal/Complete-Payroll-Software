package com.foxpro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.foxpro.databaseManager.EmployeeDatabaseHandler;

class Employees {


    private static final Logger logger = Logs.logger;


    /*
     * connection for employees
     * established inside constructor and closed inside the finally block inside function addEmployees
     */


    String month,
            region_optional,
            path_pf_csv,
            path_client_csv;

    int daysInMonth, year;

    long pfRegNumber;

    double percentBasic,
            percentHra,
            percentConv,
            percentWashingAllowance,
            percentOvertime,
            percentMsl1,
            percentMsl2,
            percentMsl3;

    SalaryStructure salaryStructure;

    final double PF_DEDUCTION_RATE = Config.getPfDeductionPercentage();
    final double ESIC_DEDUCTION_RATE = Config.getEsicDeductionPercentage();

    Employees(long pfRegNumber, int year, String month, int daysInMonth, String region_optional, String path_pf_csv, String path_client_csv) {

        logger.log(Level.INFO, "Employees.java\n\tpfRegNumber : {0}\n\tyear : {1}\n\tmonth : {2}\n\tdaysInMonth : {3}\n\tregion_optional : {4}\n\tpath_pf_csv : {5}\n\tpath_client_csv : {6}", new Object[]{pfRegNumber, year, month, daysInMonth, region_optional, path_pf_csv, path_client_csv});

        this.pfRegNumber = pfRegNumber;
        this.year = year;
        this.month = month;
        this.daysInMonth = daysInMonth;
        this.region_optional = region_optional;
        this.path_pf_csv = path_pf_csv;
        this.path_client_csv = path_client_csv;

        salaryStructure = SalaryStructureFactory.getSalaryStructure(pfRegNumber);

        percentBasic = salaryStructure.basic;
        percentHra = salaryStructure.hra;
        percentConv = salaryStructure.convence;
        percentWashingAllowance = salaryStructure.washingAllowance;
        percentOvertime = salaryStructure.overtime;
        percentMsl1 = salaryStructure.msl1;
        percentMsl2 = salaryStructure.msl2;
        percentMsl3 = salaryStructure.msl3;


        // if dir/db exists , else create dir/db
        Manager.checkAndCreateDir(pfRegNumber, year, month, region_optional);

        // now dir & db exists , initiate connection
        Manager.initiateEmployeesConnection(pfRegNumber, year, month, region_optional);



        // create table data
        Manager.executeCreateTableCommand(pfRegNumber, year, month, region_optional);


        // fill table with provided data
        Manager.executeFillTableCommand(pfRegNumber, year, month, region_optional, path_pf_csv);

    }

    void addEmployees(BufferedReader mainBufferedReader) throws IOException {
        FileReader fileReader = null;
        long uan = 0;
        String[] inputArr;
        String input;


        boolean incentiveProvided = false;

        double total_salary, attendance;

        double total_basic,
                total_hra,
                total_conv,
                total_overtime,
                total_washingAllowance,
                calc_basic,
                calc_hra,
                calc_conv,
                calc_overtime,
                calc_washingAllowance,
                calc_incentive = 0,
                pf_salary, pf_deduction, esic_salary, esic_deduction, total_deduction, calc_salary, netPayableAmount,
                pf_paid_by_employee = 0, pf_paid_by_employer = 0;



        if (path_client_csv == null) {
            System.out.println("\n  enter exit / quit in uan to exit");
            System.out.print("uan : ");

            while (!(input = mainBufferedReader.readLine()).equals("quit")) {
                input = input.trim();

                if (input.isBlank()) {
                    System.out.print("name : ");
                    input = mainBufferedReader.readLine().trim();
                    // to do : print by name
                    uan = EmployeeDatabaseHandler.printEmployeeDetailsByName(input);

                } else {
                    uan = Long.parseLong(input);
                    EmployeeDatabaseHandler.printEmployeeDetails(input);
                }

                System.out.print("salary : ");
                total_salary = Double.parseDouble(mainBufferedReader.readLine().trim());
                System.out.print("attendance : ");
                attendance = Double.parseDouble(mainBufferedReader.readLine().trim());


                System.out.print("include incentive : ");
                if ( mainBufferedReader.readLine().trim().equalsIgnoreCase("y")){
                    if ( incentiveProvided == false){
                        System.out.print("add incentive amount : ");
                        calc_incentive = Double.parseDouble(mainBufferedReader.readLine().trim());
                        incentiveProvided = true;
                    }
                    else{
                        System.out.println("incentive included and set to : " + calc_incentive);
                    }
                }
                else{
                    calc_incentive = 0;
                }

                logger.info("salary structure details : ");
                logger.log(Level.INFO, "basic % ; {0}", percentBasic);
                logger.log(Level.INFO, "hra % : {0}", percentHra);
                logger.log(Level.INFO, "conv % : {0}", percentConv);
                logger.log(Level.INFO, "washing allowance % : {0}", percentWashingAllowance);
                logger.log(Level.INFO, "overtime % : {0}", percentOvertime);

                total_basic = Math.ceil( (total_salary * percentBasic) / 100 );
                total_hra = Math.ceil( (total_salary * percentHra) / 100 );
                total_conv =Math.ceil ( (total_salary * percentConv) / 100 );
                total_washingAllowance = Math.ceil( (total_salary * percentWashingAllowance) / 100 );
                total_overtime = Math.ceil( (total_salary * percentOvertime) / 100 );

                logger.log(Level.INFO , "total basic : {0}" , total_basic);
                logger.log(Level.INFO , "total hra : {0}" , total_hra);
                logger.log(Level.INFO , "total conv : {0}" , total_conv);
                logger.log(Level.INFO , "total washingAllowance : {0}" , total_washingAllowance);
                logger.log(Level.INFO , "total overtime : {0}" , total_overtime);

                calc_basic = Math.ceil( (total_basic * attendance) / daysInMonth );
                calc_hra = Math.ceil( (total_hra * attendance) / daysInMonth );
                calc_conv = Math.ceil( (total_conv * attendance) / daysInMonth );
                calc_overtime = Math.ceil( (total_overtime * attendance) / daysInMonth );
                calc_washingAllowance = Math.ceil( (total_washingAllowance * attendance) / daysInMonth );

                logger.log(Level.INFO , "calc basic : {0}" , calc_basic);
                logger.log(Level.INFO , "calc hra : {0}" , calc_hra);
                logger.log(Level.INFO , "calc conv : {0}" , calc_conv);
                logger.log(Level.INFO , "calc washingAllowance : {0}" , calc_washingAllowance);
                logger.log(Level.INFO , "calc overtime : {0}" , calc_overtime);

                pf_salary = calc_basic;
                esic_salary = Math.ceil( (calc_basic + calc_hra + calc_conv + calc_overtime) );
                calc_salary = Math.ceil( (esic_salary + calc_washingAllowance + calc_incentive) );

                logger.log(Level.INFO , "esic_salary : {0}" , esic_salary);
                logger.log(Level.INFO , "calc_salary : {0}" , calc_salary);

                pf_deduction = Math.ceil( (pf_salary * PF_DEDUCTION_RATE) / 100 );
                esic_deduction = Math.ceil( (esic_salary * ESIC_DEDUCTION_RATE) / 100 );

                logger.log(Level.INFO , "pf Deduction : {0}" , pf_deduction);
                logger.log(Level.INFO , "esic Deduction : {0}" , esic_deduction);

                total_deduction = Math.ceil( (pf_deduction + esic_deduction) );
                netPayableAmount =Math.ceil (calc_salary - total_deduction);

                logger.log(Level.INFO , "total deduction : {0}" , total_deduction);
                logger.log(Level.INFO , "Net Payable Amount : {0}" , netPayableAmount);

                EmployeeDatabaseHandler.insertEmployeeDetails(
                        new double[]{
                            total_salary,
                            attendance,
                            total_basic,
                            total_hra,
                            total_conv,
                            total_overtime,
                            total_washingAllowance,
                            calc_basic,
                            calc_hra,
                            calc_conv,
                            calc_overtime,
                            calc_washingAllowance,
                            calc_incentive,
                            pf_salary,
                            pf_deduction,
                            esic_salary,
                            esic_deduction,
                            total_deduction,
                            calc_salary,
                            netPayableAmount,
                            (double) daysInMonth,
                            pf_paid_by_employee,
                            pf_paid_by_employer

                        }, uan
                );

            }

        } else {

            try {
                fileReader = new FileReader(path_client_csv);
                BufferedReader fileBufferedReader = new BufferedReader(fileReader);

                while ((input = fileBufferedReader.readLine()) != null) {
                    input = input.trim();
                    inputArr = input.split(",");

                    uan = Long.parseLong(inputArr[0]);

                    if (inputArr.length == 4 || inputArr.length == 3) {
                        // first 2 columns : uan_no and name
                        // EmployeeDatabaseHandler.printEmployeeDetails(inputArr[0]);

                        if (inputArr.length == 3) {
                            total_salary = Double.parseDouble(inputArr[1]);
                            attendance = Double.parseDouble(inputArr[2]);
                        } else {
                            total_salary = Double.parseDouble(inputArr[2]);
                            attendance = Double.parseDouble(inputArr[3]);
                        }

                    } else if (inputArr.length == 2) {
                        // cols : uan_no , salary
                        EmployeeDatabaseHandler.printEmployeeDetails(inputArr[0]);

                        total_salary = Double.parseDouble(inputArr[1]);

                        System.out.print("attendance : ");
                        attendance = Double.parseDouble(mainBufferedReader.readLine().trim());

                    } else if (inputArr.length == 1) {
                        System.out.print("total salary : ");
                        total_salary = Double.parseDouble(mainBufferedReader.readLine().trim());

                        System.out.print("attendace : ");
                        attendance = Double.parseDouble(mainBufferedReader.readLine().trim());

                    } else {
                        System.out.println("empty client csv file");
                        break;
                    }
                    total_basic = Math.ceil( (total_salary * percentBasic) / 100 );
                    total_hra =Math.ceil (total_salary * percentHra) / 100;
                    total_conv =Math.ceil (total_salary * percentConv) / 100;
                    total_washingAllowance = Math.ceil( (total_salary * percentWashingAllowance) / 100 );
                    total_overtime = Math.ceil( (total_salary * percentOvertime) / 100 );

                    calc_basic = Math.ceil( (total_basic * attendance) / daysInMonth );
                    calc_hra = Math.ceil( (total_hra * attendance) / daysInMonth );
                    calc_conv = Math.ceil( (total_conv * attendance) / daysInMonth );
                    calc_overtime = Math.ceil( (total_overtime * attendance) / daysInMonth );
                    calc_washingAllowance = Math.ceil( (total_washingAllowance * attendance) / daysInMonth );

                    pf_salary = calc_basic;
                    esic_salary = Math.ceil( (calc_basic + calc_hra + calc_conv + calc_overtime) );
                    calc_salary = Math.ceil( (esic_salary + calc_washingAllowance + calc_incentive) );

                    pf_deduction = Math.ceil( (pf_salary * PF_DEDUCTION_RATE) / 100 );
                    esic_deduction = Math.ceil( (esic_salary * ESIC_DEDUCTION_RATE) / 100 );

                    total_deduction = Math.ceil( (pf_deduction + esic_deduction) );
                    netPayableAmount = Math.ceil( (calc_salary - total_deduction) );


                    EmployeeDatabaseHandler.insertEmployeeDetails(
                            new double[]{
                                total_salary,
                                attendance,
                                total_basic,
                                total_hra,
                                total_conv,
                                total_overtime,
                                total_washingAllowance,
                                calc_basic,
                                calc_hra,
                                calc_conv,
                                calc_overtime,
                                calc_washingAllowance,
                                calc_incentive,
                                pf_salary,
                                pf_deduction,
                                esic_salary,
                                esic_deduction,
                                total_deduction,
                                calc_salary,
                                netPayableAmount,
                                (double) daysInMonth,
                                pf_paid_by_employee,
                                pf_paid_by_employer

                            }, uan
                    );
                }
                fileBufferedReader.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            } finally {
                // Manager.closeEmployeeConnection();
                /* connection closed inside cmd */
                if (fileReader != null) {
                    fileReader.close();
                }
            }
        }
    }

}
