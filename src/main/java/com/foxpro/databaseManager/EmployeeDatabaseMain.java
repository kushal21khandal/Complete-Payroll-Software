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

    static void printEmployeeDetailsByName(Connection conn, String name) throws SQLException {
        String query = "SELECT uan , memberId , name , doj , father/husband_name ,isAadharVerified from data where name = ? ";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, name);
        ResultSet res = preparedStatement.executeQuery();

        while (res.next()) {
            System.out.print(res.getString("memberId") + "\t" + res.getString("name") + "\t" + res.getString("doj") + "\t" + res.getString("father/husband_name") + "\t" + res.getString("isAadharVerified") + "\t");
        }
    }
    
    static Object getAllEmployeeDetails(Connection conn) throws SQLException{
        return conn.prepareStatement("SELECT * FROM data").executeQuery();
    }


    static void insertEmployeeDetails(Connection conn ,double[] arr, long uan) throws SQLException {
        String query = "INSERT INTO data (totalSalary ,attendance ,basic ,hra,convence ,overtime ,washingAllowance ,calc_basic ,calc_hra,calc_conv ,calc_overtime ,calc_washingAllowance ,calc_incentive ,pf_salary,pfDeduction ,esic_salary ,esicDeduction ,totalDeduction ,calc_salary,netPayableAmount ,totalDays ,pfPaidByEmployee ,pfPaidByEmployer ) VALUES(? , ? , ? ,? , ? , ? , ? , ? , ? ,? , ? , ? , ? , ? , ? ,? , ? , ? , ? , ? , ? ,? , ? , ?) where uan =  ? ";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        for ( int i = 0; i < arr.length; i++){
            preparedStatement.setDouble(i + 1 , arr[i]);
        }
        preparedStatement.setLong(arr.length , uan);
        preparedStatement.executeUpdate();

    }


}
