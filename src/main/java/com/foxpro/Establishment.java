package com.foxpro;

class Establishment {


    class SalaryStructure{
        float
            basic,
            hra,
            convence ,
            overtime ,
            washingAllowance ,
            msl1 ,
            msl2 ,
            msl3 ;

        SalaryStructure(float basic , float hra , float convence , float overtime , float washingAllowance , float msl1 , float msl2 , float msl3){
            this.basic = basic ;
            this.hra = hra;
            this.convence = convence;
            this.overtime = overtime;
            this.washingAllowance = washingAllowance;
            this.msl1 = msl1;
            this.msl2 = msl2;
            this.msl3 = msl3;
        }


        public void updateSalaryStructure(float basic , float hra , float convence , float overtime , float washingAllowance , float msl1 , float msl2 , float msl3){
            Manager.updateSalaryStructure(basic , hra , convence , overtime , washingAllowance , msl1 , msl2 , msl3);
        }

    }

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
