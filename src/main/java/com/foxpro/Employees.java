package com.foxpro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.foxpro.databaseManager.EmployeeDatabaseHandler;

class Employees {

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

        Manager.checkAndCreateDir(pfRegNumber, year, month, region_optional);
        Manager.executeCreateTableCommand(pfRegNumber, year, month, region_optional);
        Manager.executeFillTableCommand(pfRegNumber, year, month, region_optional, path_pf_csv);
        Manager.initiateEmployeesConnection(pfRegNumber, year, month, region_optional);

    }

    void addEmployees(BufferedReader mainBufferedReader) throws IOException {
        FileReader fileReader = null;
        long uan = 0;
        String[] inputArr;
        String input;

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
                    EmployeeDatabaseHandler.printEmployeeDetailsByName(input);
                } else {
                    uan = Long.parseLong(input);
                    EmployeeDatabaseHandler.printEmployeeDetails(input);
                }

                System.out.print("salary : ");
                total_salary = Double.parseDouble(mainBufferedReader.readLine().trim());
                System.out.print("attendance : ");
                attendance = Double.parseDouble(mainBufferedReader.readLine().trim());

                total_basic = (total_salary * percentBasic) / 100;
                total_hra = (total_salary * percentHra) / 100;
                total_conv = (total_salary * percentConv) / 100;
                total_washingAllowance = (total_salary * percentWashingAllowance) / 100;
                total_overtime = (total_salary * percentOvertime) / 100;

                calc_basic = (total_basic * attendance) / daysInMonth;
                calc_hra = (total_hra * attendance) / daysInMonth;
                calc_conv = (total_conv * attendance) / daysInMonth;
                calc_overtime = (total_overtime * attendance) / daysInMonth;
                calc_washingAllowance = (total_washingAllowance * attendance) / daysInMonth;

                pf_salary = calc_basic;
                esic_salary = (calc_basic + calc_hra + calc_conv + calc_overtime);
                calc_salary = (esic_salary + calc_washingAllowance + calc_incentive);

                pf_deduction = (pf_salary * PF_DEDUCTION_RATE) / 100;
                esic_deduction = (esic_salary * ESIC_DEDUCTION_RATE) / 100;

                total_deduction = (pf_deduction + esic_deduction);
                netPayableAmount = (calc_salary - total_deduction);

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

                        System.out.print("attendace : ");
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
                    total_basic = (total_salary * percentBasic) / 100;
                    total_hra = (total_salary * percentHra) / 100;
                    total_conv = (total_salary * percentConv) / 100;
                    total_washingAllowance = (total_salary * percentWashingAllowance) / 100;
                    total_overtime = (total_salary * percentOvertime) / 100;

                    calc_basic = (total_basic * attendance) / daysInMonth;
                    calc_hra = (total_hra * attendance) / daysInMonth;
                    calc_conv = (total_conv * attendance) / daysInMonth;
                    calc_overtime = (total_overtime * attendance) / daysInMonth;
                    calc_washingAllowance = (total_washingAllowance * attendance) / daysInMonth;

                    pf_salary = calc_basic;
                    esic_salary = (calc_basic + calc_hra + calc_conv + calc_overtime);
                    calc_salary = (esic_salary + calc_washingAllowance + calc_incentive);

                    pf_deduction = (pf_salary * PF_DEDUCTION_RATE) / 100;
                    esic_deduction = (esic_salary * ESIC_DEDUCTION_RATE) / 100;

                    total_deduction = (pf_deduction + esic_deduction);
                    netPayableAmount = (calc_salary - total_deduction);

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
                Manager.closeEmployeeConnection();
                if (fileReader != null) {
                    fileReader.close();
                }
            }
        }
    }

}
