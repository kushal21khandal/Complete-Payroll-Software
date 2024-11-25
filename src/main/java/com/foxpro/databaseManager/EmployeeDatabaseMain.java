package com.foxpro.databaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class EmployeeDatabaseMain {



    static void printEmployeeDetails(Connection conn, int uan_No) throws SQLException {
        String query = "SELECT  memberId , name , doj , father/husband_name ,isAadharVerified from data where uan = ? ";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, uan_No);
        ResultSet res = preparedStatement.executeQuery();

        while (res.next()) {
            System.out.print(res.getString("memberId") + "\t" + res.getString("name")+ "\t" +res.getString("doj")+ "\t" +  res.getString("father/husband_name")+ "\t" + res.getString("isAadharVerified")+ "\t");
        }

    }

    static Object getAllEmployeeDetails(Connection conn) throws SQLException{
        return conn.prepareStatement("SELECT * FROM data").executeQuery();
    }


}
