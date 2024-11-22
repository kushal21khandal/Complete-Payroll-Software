package com.foxpro;

class Establishment {


    class SalaryStructure{
        double
            basic,
            hra,
            convence ,
            overtime ,
            washingAllowance ,
            msl1 ,
            msl2 ,
            msl3 ;

        SalaryStructure(double basic , double hra , double convence , double overtime , double washingAllowance , double msl1 , double msl2 , double msl3){
            this.basic = basic ;
            this.hra = hra;
            this.convence = convence;
            this.overtime = overtime;
            this.washingAllowance = washingAllowance;
            this.msl1 = msl1;
            this.msl2 = msl2;
            this.msl3 = msl3;
        }


        public void updateSalaryStructure(double basic , double hra , double convence , double overtime , double washingAllowance , double msl1 , double msl2 , double msl3){
            Manager.updateSalaryStructure(pfRegNumber , basic , hra , convence , overtime , washingAllowance , msl1 , msl2 , msl3);
        }

    }

    long pfRegNumber,
            esicRegNumber,
            phoneNumber;

    String companyName = "",
            ownerName = "",
            address = "",
            dateOfPfRegistration = "", // have to use regex to separate date month year DD-MM-YYYY
            dateOfEsicRegistration = "";

    Establishment(long pfRegNumber, long esicRegNumber, long phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {
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

    public void updateEstablishment(long pfRegNumber, long esicRegNumber, long phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {
        Manager.updateEstablishment(pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
    }

    public void removeEstablishment(long pfRegNumber) {
        Manager.removeEstablishment(pfRegNumber);

    }

}
