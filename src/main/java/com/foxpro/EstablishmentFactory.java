package com.foxpro;

import java.sql.ResultSet;
import java.sql.SQLException;

class EstablishmentFactory {

    static class SalaryStructureFactory {

        public static Establishment.SalaryStructure getSalaryStructure( Establishment establishment ,long pfRegNumber) {
            Establishment.SalaryStructure salaryStructure = null;
            try {
                Object details = Manager.getEstablishmentSalaryStructureDetails(pfRegNumber);
                salaryStructure = establishment.new SalaryStructure(
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

    public static Establishment getEstablishment(long pfRegNumber) {

        long esicRegNumber = 0,
                phoneNumber = 0;

        String companyName = "",
                ownerName = "",
                address = "",
                dateOfPfRegistration = "", // have to use regex to separate date month year
                dateOfEsicRegistration = "";
        try {
            Object details = Manager.getEstablishmentDetails(pfRegNumber);
            esicRegNumber = ((ResultSet) details).getLong("esicRegNumber");
            esicRegNumber = ((ResultSet) details).getLong("esicRegNumber");
            phoneNumber = ((ResultSet) details).getLong("phoneNumber");
            companyName = ((ResultSet) details).getString("companyName");
            ownerName = ((ResultSet) details).getString("ownerName");
            address = ((ResultSet) details).getString("address");
            dateOfPfRegistration = ((ResultSet) details).getString("dateOfPfRegistration");
            dateOfEsicRegistration = ((ResultSet) details).getString("dateOfEsicRegistration");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new Establishment(pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);

    }

}
