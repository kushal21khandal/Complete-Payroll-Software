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
        String query = "INSERT INTO data (totalSalary ,attendance ,basic ,hra,convence ,overtime ,washingAllowance ,calc_basic ,calc_hra,calc_conv ,calc_overtime ,calc_washingAllowance ,calc_incentive ,pf_salary,pfDeduction ,esic_salary ,esicDeduction ,totalDeduction ,calc_salary,netPayableAmount ,totalDays ,pfPaidByEmployee ,pfPaidByEmployer ) VALUES(? , ? , ? ,? , ? , ? , ? , ? , ? ,? , ? , ? , ? , ? , ? ,? , ? , ? , ? , ? , ? ,? ) WHERE uan =  ? ";

        PreparedStatement preparedStatement = conn.prepareStatement(query);

        // for (int i = 0; i < arr.length; i++){
        //     preparedStatement.setDouble(i + 1 , arr[i]);
        // }
        preparedStatement.setDouble(1 , arr[0]);
        preparedStatement.setDouble(2 , arr[1]);
        preparedStatement.setDouble(3 , arr[2]);
        preparedStatement.setDouble(4 , arr[3]);
        preparedStatement.setDouble(5 , arr[4]);
        preparedStatement.setDouble(6 , arr[5]);
        preparedStatement.setDouble(7 , arr[6]);
        preparedStatement.setDouble(8 , arr[7]);
        preparedStatement.setDouble(9 , arr[8]);
        preparedStatement.setDouble(10 , arr[9]);
        preparedStatement.setDouble(11 , arr[10]);
        preparedStatement.setDouble(12 , arr[11]);
        preparedStatement.setDouble(13 , arr[12]);
        preparedStatement.setDouble(14 , arr[13]);
        preparedStatement.setDouble(15 , arr[14]);
        preparedStatement.setDouble(16 , arr[15]);
        preparedStatement.setDouble(17, arr[16]);
        preparedStatement.setDouble(18, arr[17]);
        preparedStatement.setDouble(19, arr[18]);
        preparedStatement.setDouble(20 , arr[19]);
        preparedStatement.setDouble(21 , arr[20]);
        preparedStatement.setDouble(22, arr[21]);
        preparedStatement.setLong(arr.length , uan);
        preparedStatement.executeUpdate();

    }


    static Object getSumTotal(Connection conn) throws  SQLException{

        String query = "SELECT sum(basic) as sum_basic , sum(hra) as sum_hra , sum(convence) as sum_convence , sum(washingAllowance) as sum_washingAllowance , sum(overtime) as sum_overtime , sum(totalSalary) as sum_totalSalary , sum(totalDays) as sum_totalDays , sum(attendance) as sum_attendance , sum(calc_basic) as sum_calc_basic , sum(calc_hra) as sum_calc_hra , sum(calc_convence) as sum_calc_convence , sum(calc_washingAllowance) as sum_calc_washingAllowance , sum(calc_overtime) as sum_calc_overtime , sum(calc_salary) as sum_calc_salary , sum(pf_salary) as sum_pf_salary , sum(esicDeduction) as sum_esicDeduction , sum(pfDeduction) as sum_pfDeduction , sum(totalDeduction) as sum_totalDeduction , sum(netPayableAmount) as sum_netPayableAmount , sum(calc_incentive) as sum_calc_incentive , sum(esic_salary) as sum_esic_salary from data";
        PreparedStatement preparedStatement = conn.prepareCall(query);
        return preparedStatement.executeQuery();

    }

    static void fillMonthTable(Connection conn , String[] lineArr) throws SQLException{
        String query = "INSERT INTO data (uan ,memberId ,name ,gender ,dob ,doj ,father_husband_name ,relation ,maritialStatus ,mobile ,emailId ,aadhaar ,pan ,bankAccNo_IFSCcode ,nomination ,isAadharVerified ) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1 , Long.parseLong(lineArr[0]));
        preparedStatement.setString(2 , lineArr[1]);
        preparedStatement.setString(3 , lineArr[2]);
        preparedStatement.setString(4 , lineArr[3]);
        preparedStatement.setString(5 , lineArr[4]);
        preparedStatement.setString(6 , lineArr[5]);
        preparedStatement.setString(7 , lineArr[6]);
        preparedStatement.setString(8 , lineArr[7]);
        preparedStatement.setString(9 , lineArr[8]);
        preparedStatement.setString(10 , lineArr[9]);
        preparedStatement.setString(11 , lineArr[10]);
        preparedStatement.setString(12 , lineArr[11]);
        preparedStatement.setString(13 , lineArr[12]);
        preparedStatement.setString(14 , lineArr[13]);
        preparedStatement.setString(15 , lineArr[14]);
        preparedStatement.setString(16 , lineArr[15]);
        // preparedStatement.execute(query);/
        preparedStatement.executeUpdate();

    }

}
