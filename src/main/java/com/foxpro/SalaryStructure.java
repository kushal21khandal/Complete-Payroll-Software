package com.foxpro;

class SalaryStructure {

    double basic,
            hra,
            convence,
            overtime,
            washingAllowance,
            msl1,
            msl2,
            msl3;

    SalaryStructure(double basic, double hra, double convence, double overtime, double washingAllowance, double msl1, double msl2, double msl3) {
        this.basic = basic;
        this.hra = hra;
        this.convence = convence;
        this.overtime = overtime;
        this.washingAllowance = washingAllowance;
        this.msl1 = msl1;
        this.msl2 = msl2;
        this.msl3 = msl3;
    }

    public void updateSalaryStructure(long pfRegNumber ,double basic, double hra, double convence, double overtime, double washingAllowance, double msl1, double msl2, double msl3) {
        Manager.updateSalaryStructure(pfRegNumber, basic, hra, convence, overtime, washingAllowance, msl1, msl2, msl3);
    }

}
