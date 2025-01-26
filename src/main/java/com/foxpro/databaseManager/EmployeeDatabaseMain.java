package com.foxpro.databaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
;

class EmployeeDatabaseMain {

    static void printEmployeeDetails(Connection conn, int uan_No) throws SQLException {
        String query = "SELECT  memberId , name , doj , father/husband_name ,isAadharVerified from data where uan = ? ";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, uan_No);
        ResultSet res = preparedStatement.executeQuery();

        while (res.next()) {
            System.out.print(res.getString("memberId") + "\t" + res.getString("name") + "\t" + res.getString("doj") + "\t" + res.getString("father/husband_name") + "\t" + res.getString("isAadharVerified") + "\t");
        }

    }

    static long printEmployeeDetailsByName(Connection conn, String name) throws SQLException {
        String query = "SELECT uan , memberId , name , doj , father/husband_name ,isAadharVerified from data where name = ? ";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, name);
        ResultSet res = preparedStatement.executeQuery();

        long uan = 0 ;

        while (res.next()) {
            System.out.print(res.getString("memberId") + "\t" + res.getString("name") + "\t" + res.getString("doj") + "\t" + res.getString("father/husband_name") + "\t" + res.getString("isAadharVerified") + "\t");

            uan = Long.parseLong( res.getInt("uan") + "" );
        }
        return uan;

    }

    static Object getAllEmployeeDetails(Connection conn) throws SQLException{
        return conn.prepareStatement("SELECT * FROM data").executeQuery();
    }


    static void insertEmployeeDetails(Connection conn ,double[] arr, long uan) throws SQLException {
        String query = "UPDATE data SET totalSalary=?,attendance=?,basic=?,hra=?,convence=?,overtime=?,washingAllowance=?,calc_basic=?,calc_hra=?,calc_convence=?,calc_overtime=?,calc_washingAllowance=?,calc_incentive=?,pf_salary=?,pfDeduction=?,esic_salary=?,esicDeduction=?,totalDeduction=?,calc_salary=?,netPayableAmount=?,totalDays=?,pfPaidByEmployee=?,pfPaidByEmployer=? WHERE uan=?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);

        for (int i = 0; i < arr.length; i++){
            preparedStatement.setDouble(i + 1 , arr[i]);
        }
        preparedStatement.setLong(arr.length + 1 , uan);
        preparedStatement.executeUpdate();

    }


    static Object getSumTotal(Connection conn) throws  SQLException{

        String query = "SELECT sum(basic) as sum_basic , sum(hra) as sum_hra , sum(convence) as sum_convence , sum(washingAllowance) as sum_washingAllowance , sum(overtime) as sum_overtime , sum(totalSalary) as sum_totalSalary , sum(totalDays) as sum_totalDays , sum(attendance) as sum_attendance , sum(calc_basic) as sum_calc_basic , sum(calc_hra) as sum_calc_hra , sum(calc_convence) as sum_calc_convence , sum(calc_washingAllowance) as sum_calc_washingAllowance , sum(calc_overtime) as sum_calc_overtime , sum(calc_salary) as sum_calc_salary , sum(pf_salary) as sum_pf_salary , sum(esicDeduction) as sum_esicDeduction , sum(pfDeduction) as sum_pfDeduction , sum(totalDeduction) as sum_totalDeduction , sum(netPayableAmount) as sum_netPayableAmount , sum(calc_incentive) as sum_calc_incentive , sum(esic_salary) as sum_esic_salary from data";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        return preparedStatement.executeQuery();

    }

    static void fillMonthTable(Connection conn , String[] lineArr) throws SQLException{
        String query = "INSERT INTO data (uan ,memberId ,name ,gender ,dob ,doj ,father_husband_name ,relation ,maritialStatus ,mobile ,emailId ,aadhaar ,pan ,bankAccNo_IFSCcode ,nomination ,isAadharVerified ) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";


        int i = 1;
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(i , Long.parseLong(lineArr[i-1]));


        while(++i < lineArr.length){
            preparedStatement.setString(i , lineArr[i -1 ]);
        }
        preparedStatement.executeUpdate();

    }


    static void updateNextMonthDatabase(Connection conn) throws SQLException{
        String query = "UPDATE data SET totalSalary=0,attendance=0,basic=0,hra=0,convence=0,overtime=0,washingAllowance=0,calc_basic=0,calc_hra=0,calc_convence=0,calc_overtime=0,calc_washingAllowance=0,calc_incentive=0,pf_salary=0,pfDeduction=0,esic_salary=0,esicDeduction=0,totalDeduction=0,calc_salary=0,netPayableAmount=0,totalDays=0,pfPaidByEmployee=0,pfPaidByEmployer=0";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }

}
