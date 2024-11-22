package com.foxpro;
import java.sql.ResultSet;
import java.sql.SQLException;

class SalaryStructureFactory {

    public static SalaryStructure getSalaryStructure( long pfRegNumber) {
        SalaryStructure salaryStructure = null;
        try {
            Object details = Manager.getEstablishmentSalaryStructureDetails(pfRegNumber);
            salaryStructure = new SalaryStructure(
                    ((ResultSet) details).getDouble("basic"),
                    ((ResultSet) details).getDouble("hra"),
                    ((ResultSet) details).getDouble("convence"),
                    ((ResultSet) details).getDouble("overtime"),
                    ((ResultSet) details).getDouble("washingAllowance"),
                    ((ResultSet) details).getDouble("msl1"),
                    ((ResultSet) details).getDouble("msl2"),
                    ((ResultSet) details).getDouble("msl3")
            );

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return salaryStructure;
    }
}
