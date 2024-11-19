package com.foxpro;


class Employees{

    String month = "";
    int year , pfRegNumber;
    String pathPfCSV= "" , pathClientCSV= "";

    String[] months = {"JAN" , "FEB" , "MAR" , "APR" , "MAY" , "JUN" , "JUL" , "AUG" , "SEP" , "OCT" , "NOV" , "DEC"};


    Employees( int pfRegNumber , int year , String month , String pathPfCSV , String pathClientCSV) {
        this.pfRegNumber = pfRegNumber;
        this.year = year;
        this.month = month;
        this.pathPfCSV = pathPfCSV;
        this.pathClientCSV = pathClientCSV;

    }

    public void addEmployees(){
        
    }



}