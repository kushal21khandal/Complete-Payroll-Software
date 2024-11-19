package com.foxpro;

import java.sql.ResultSet;
import java.sql.SQLException;

class EstablishmentFactory {

    public static Establishment getEstablishment(int pfRegNumber) {

        int esicRegNumber = 0,
                phoneNumber = 0;

        String companyName = "",
                ownerName = "",
                address = "",
                dateOfPfRegistration = "", // have to use regex to separate date month year
                dateOfEsicRegistration = "";
        try {
            Object details = Manager.getEstablishmentDetails(pfRegNumber);
            esicRegNumber = ((ResultSet) details ).getInt("esicRegNumber");
            esicRegNumber = ((ResultSet) details ).getInt("esicRegNumber");
            phoneNumber = ((ResultSet) details ).getInt("phoneNumber");
            companyName = ((ResultSet) details ).getString("companyName");
            ownerName = ((ResultSet) details ).getString("ownerName");
            address = ((ResultSet) details ).getString("address");
            dateOfPfRegistration = ((ResultSet) details ).getString("dateOfPfRegistration");
            dateOfEsicRegistration = ((ResultSet) details ).getString("dateOfEsicRegistration");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new Establishment(pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);

    }

}
