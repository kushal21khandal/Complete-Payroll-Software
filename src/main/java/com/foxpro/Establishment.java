package com.foxpro;

class Establishment {

    int pfRegNumber,
            esicRegNumber,
            phoneNumber;

    String companyName = "",
            ownerName = "",
            address = "",
            dateOfPfRegistration = "", // have to use regex to separate date month year DD-MM-YYYY
            dateOfEsicRegistration = "";

    Establishment(int pfRegNumber, int esicRegNumber, int phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {
        this.pfRegNumber = pfRegNumber;
        this.esicRegNumber = esicRegNumber;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.address = address;
        this.dateOfPfRegistration = dateOfPfRegistration;
        this.dateOfEsicRegistration = dateOfEsicRegistration;
    }

    public void addEstablishment() {
        Manager.createEstablishment(pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);

    }

    public void updateEstablishment(int pfRegNumber, int esicRegNumber, int phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {
        Manager.updateEstablishment(pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
    }

    public void removeEstablishment(int pfRegNumber) {
        Manager.removeEstablishment(pfRegNumber);

    }

}
