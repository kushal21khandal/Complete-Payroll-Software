package com.foxpro;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.foxpro.databaseManager.EmployeeDatabaseHandler;
import com.foxpro.databaseManager.EstablishmentDatabaseHandler;
import com.foxpro.fileManager.FileComponentHandler;

class Manager {


    static class GenerateReport {
        /*
         * connection established and closed inside generateReport function
         */

        private static String getConsolidatedReportInnerFormat(
            int employeeIndex ,
            long esicRegNumber ,
            String employeeName ,
            int basic ,
            int hra ,
            int conv ,
            int washingAllowance ,
            int hardDuty ,
            int totalWithoutReduction ,
            int pfSalary ,
            int esicAdvance ,
            int pfDeduction ,
            int totalDeduction ,
            int netPayableAmount ,
            String fahtersName ,
            int esicSalary ,
            int pfAccNo ,
            int daysWorked ,
            int actualDays ,
            int complementaryDays ,
            int calcBasic ,
            int calcHra ,
            int calcConv ,
            int calcWashing ,
            int calcHardDuty ,
            int calcTotal ,
            long uanNo ,
            int incentive
            ){
            // return String.format(" %d   %d      %s              %d    %d    %d    %d    %d          %d    %d       %d     %d  -          %d      %d\n                     %s                                                                 %d      -       -     -\n               %d %d=%d        +  %d      %d    %d     %d    %d    %d          %d                            -\n%d           0.0                                         %d\n                                                      0    %d\n _________________________________________________________________________________________________________________________________________________________\n"
            // , employeeIndex , esicRegNumber , employeeName , basic ,hra , conv , washingAllowance , hardDuty , totalWithoutReduction , pfSalary , esicAdvance , pfDeduction , totalDeduction , netPayableAmount , fahtersName , esicSalary , pfAccNo , daysWorked , actualDays , complementaryDays , calcBasic , calcHra , calcConv , calcWashing , calcHardDuty , calcTotal , uanNo ,  incentive , incentive);
            // return String.format(" %d   %s      %s              %d    %d    %d    %d    %d          %d    %d       %d     %d  -          %d      %d\n                     %s                                                                 %d      -       -     -\n               %d %d.0=%d.0        +  %d.0      %d    %d     %d    %d    %d          %d                            -\n     %d           0.0                            %d\n                                                      0    %d\n _________________________________________________________________________________________________________________________________________________________\n" , employeeIndex , esicRegNumber + "" , employeeName , basic ,hra , conv , washingAllowance , hardDuty , totalWithoutReduction , pfSalary , esicAdvance , pfDeduction , totalDeduction , netPayableAmount , fahtersName , esicSalary , pfAccNo , daysWorked , actualDays , complementaryDays , calcBasic , calcHra , calcConv , calcWashing , calcHardDuty , calcTotal , uanNo ,  incentive , incentive);
            return String.format("""
                <w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> %d   %s      %s              %d    %d    %d    %d    %d          %d    %d       %d     </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>%d  -</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">          %d      %d      </w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">                     %s                                                                 %d      -       -     -   </w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">               %d %d.00=%d.0        </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>+  %d.0</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">      %d    %d     %d    %d    %d          %d                  </w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">          -   </w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">     %d           0.0                            %d</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">                                                      0    %d</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> _____________________________________________________________________________________________________________</w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>____________________________________________</w:t></w:r></w:p>""" , employeeIndex , esicRegNumber + "" , employeeName , basic ,hra , conv , washingAllowance , hardDuty , totalWithoutReduction , pfSalary , esicAdvance , pfDeduction , totalDeduction , netPayableAmount , fahtersName , esicSalary , pfAccNo , daysWorked , actualDays , complementaryDays , calcBasic , calcHra , calcConv , calcWashing , calcHardDuty , calcTotal , uanNo ,  incentive , incentive);
        }

        private static String getConsolidatedReportNetTotal(
            int total_basic ,
            int hra ,
            int conv ,
            int washingAllowance ,
            int hardDuty ,
            int total_salary ,
            int daysWorked ,
            int actualDays ,
            int complementaryDays ,
            int calc_basic ,
            int calc_hra ,
            int calc_conv ,
            int calc_washingAllowance ,
            int calc_hardDuty ,
            int calcTotal ,
            int pfSalary ,
            int esicAdvance ,
            int pf_deduction ,
            int total_deduction ,
            int netPayableAmount ,
            int total_incentive_paid ,
            int total_esic_salary
        ){

            // return String.format("\n===================================================================================================================================================================\n                                                 %d   %d  %d   %d   %d         %d\n       NET TOTAL  %d %d+   0.0+  %d    %d   %d  %d   %d   %d         %d  %d     %d   %d      0    %d    %d\n                            0.0+   0.0                    %d                                        %d                0      0\n                                                      0   %d                                                                     0\n ===================================================================================================================================================================\n" ,
            //  return String.format(" ===================================================================================================================================================================\n                                                 %d   %d  %d   %d   %d         %d\n       NET TOTAL  %d.00 %d.00+   0.0+  %d.0    %d   %d  %d   %d   %d         %d  %d     %d   %d      0    %d    %d\n                            0.0+   0.0                    %d                                        %d                0      0\n                                                      0   %d                                                                     0\n ===================================================================================================================================================================\n ===================================================================================================================================================================\n  SALARY LIABLITY                                %d   %d  %d   %d   %d         %d\n       NET TOTAL        %d.00+   0.0+  %d.0     %d   %d  %d   %d   %d         %d  %d     %d   %d      0    %d     %d\n                           0.0+   0.0                     %d                                        %d                0      0\n                                                          %d                                                                     0\n===================================================================================================================================================================" ,
            //  return String.format(" ===================================================================================================================================================================\n                                                 %d   %d  %d   %d   %d         %d\n       NET TOTAL  %d.00 %d.00+   0.0+  %d.0    %d   %d  %d   %d   %d         %d  %d     %d   %d      0    %d    %d\n                            0.0+   0.0                    %d                                        %d                0      0\n                                                      0   %d                                                                     0\n ===================================================================================================================================================================\n ===================================================================================================================================================================\n  SALARY LIABLITY                                %d   %d  %d   %d   %d         %d\n       NET TOTAL        %d.00+   0.0+  %d.0     %d   %d  %d   %d   %d         %d  %d     %d   %d      0    %d     %d\n                           0.0+   0.0                     %d                                        %d                0      0\n                                                          %d                                                                     0\n===================================================================================================================================================================" ,
             return String.format("""
                <w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> =====================================================================================================================================</w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>==============================</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">                                                 %d   </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>%d  %d</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">   %d   %d         %d</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">       NET </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>TOTAL  %d.00</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> %d.00+   0.0+  %d.0    %d   %d  %d   %d   %d         %d  %d     %d   </w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>%d      0    %d    %d</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">                            0.0+   0.0                    %d                                        %d                0      </w:t></w:r><w:proofErr w:type="spellStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>0</w:t></w:r><w:proofErr w:type="spellEnd"/></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">                                                      0   %d                         </w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">                                            0                               </w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> ===================================================================================================================================================================</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> ===========</w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>========================================================================================================================================================</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">  SALARY LIABLITY                                %d   </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>%d  %d</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">   %d   %d         %d</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">       NET TOTAL        %d.00+   0.0</w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>+  %d.0</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">     %d   %d  %d   %d   %d         %d  %d     %d   %d      0    %d     %d</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">                           0.0+   0.0                     %d                                     </w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">   %d                0      </w:t></w:r><w:proofErr w:type="spellStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>0</w:t></w:r><w:proofErr w:type="spellEnd"/></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">                                                          %d                                                                     0</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>======================================================================================</w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>=============================================================================</w:t></w:r></w:p><w:p w:rsidR="004E3A4D" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:br w:type="page"/></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:lastRenderedPageBreak/><w:cr/></w:r></w:p><w:sectPr w:rsidR="004E3A4D" w:rsidRPr="00502A2F" w:rsidSect="00502A2F"><w:pgSz w:w="16840" w:h="11907" w:orient="landscape" w:code="9"/><w:pgMar w:top="289" w:right="238" w:bottom="289" w:left="340" w:header="709" w:footer="709" w:gutter="0"/><w:cols w:space="708"/><w:docGrid w:linePitch="360"/></w:sectPr></w:body></w:document>""" ,
             total_basic ,
             hra ,
             conv ,
             washingAllowance ,
             hardDuty ,
             total_salary ,
             daysWorked ,
             actualDays ,
             complementaryDays ,
             calc_basic ,
             calc_hra ,
             calc_conv ,
             calc_washingAllowance ,
             calc_hardDuty ,
             calcTotal ,
             pfSalary ,
             esicAdvance ,
             pf_deduction ,
             total_deduction ,
             netPayableAmount ,
             total_incentive_paid ,
             total_esic_salary ,
             total_incentive_paid,

             total_basic ,
             hra ,
             conv ,
             washingAllowance ,
             hardDuty ,
             total_salary ,
             daysWorked ,
             actualDays ,
             complementaryDays ,
             calc_basic ,
             calc_hra ,
             calc_conv ,
             calc_washingAllowance ,
             calc_hardDuty ,
             calcTotal ,
             pfSalary ,
             esicAdvance ,
             pf_deduction ,
             total_deduction ,
             netPayableAmount ,
             total_incentive_paid ,
             total_esic_salary ,
            total_incentive_paid
            );
        }

        private static String getConsolidatedReportTileFormat(
            String month ,
            int daysInMonth ,
            int pageIndex ,
            int year ,
            String establishmentName ,
            String address ,
            int estCode
            ){
            /*
             * estCode : pfRegNumber
             */
            // return String.format("\n                                                   Register of Salary for the Month of %s %d,%d \n                                                                                                            Page %d\n  Name of the Estt. %s\n  Address     %s\n  Est_Code  : %d\n ===================================================================================================================================================================\n S_n|   Ins.   |PF |  Name of the Employee    |BASIC   |HRA    |CONV   |WASHING|HRD_DT|Arrier |Total| PF SALARY|          Deductions           |  Net   |  Signature\n    |    No.   |A/c|  (Father/Husband Name)   |        |       |       |       |      |(Ot_hr)|     |ESI SALARY|-------|------|------|---------| Amount |  ---------\n    |   POST   |No.|T_day =W.day  +E.L. +H.L. |        |INCENTI|       |       |      |O_time |     |          | E.S.I.| P.F. |I_TAX | Total   | Payable|  Date of\n    |   PF RT  |   |       C.L.   +CCL        |        |       |       |       |      |       |     |          |ADVANCE|P_TAX |S_DPT |Deduction|        |  Payment\n    |  UAN NO  |   |                          |        |       |       |       |      |       |     |          |       |      |S_TAX |         |        |\n===================================================================================================================================================================\n" , month , daysInMonth , year , pageIndex , establishmentName , address , estCode  );


            // return String.format("\n                                                   Register of Salary for the Month of %s %d,%d\n\n\n  Name of the Estt. %s                                                                             {  }             Page  %d\n  Address    %s\n  Est_Code  : %d\n ===================================================================================================================================================================\n S_n|   Ins.   |PF |  Name of the Employee    |BASIC   |HRA    |CONV   |WASHING|HRD_DT|Arrier |Total| PF SALARY|          Deductions           |  Net   |  Signature\n    |    No.   |A/c|  (Father/Husband Name)   |        |       |       |       |      |(Ot_hr)|     |ESI SALARY|-------|------|------|---------| Amount |  ---------\n    |   POST   |No.|T_day =W.day  +E.L. +H.L. |        |INCENTI|       |       |      |O_time |     |          | E.S.I.| P.F. |I_TAX | Total   | Payable|  Date of\n    |   PF RT  |   |       C.L.   +CCL        |        |       |       |       |      |       |     |          |ADVANCE|P_TAX |S_DPT |Deduction|        |  Payment\n    |  UAN NO  |   |                          |        |       |       |       |      |       |     |          |       |      |S_TAX |         |        |\n ===================================================================================================================================================================\n", month , daysInMonth , year , establishmentName ,  pageIndex  , address , estCode);


            /* address divided into 2 : 7-8-9 ANAND NAGAR SIRSI  &      ROAD JAIPUR */
            return String.format("""
                                <w:body><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">                                                   Register of Salary for the Month of %s %d</w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>,%d</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">        </w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">  </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">Name of the </w:t></w:r><w:proofErr w:type="spellStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>Estt</w:t></w:r><w:proofErr w:type="spellEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>.</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> %s                                                                             {  }             </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>Page  %d</w:t></w:r><w:proofErr w:type="gramEnd"/></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> </w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> Address     %s </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>%s</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">                                            </w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">  </w:t></w:r><w:proofErr w:type="spellStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>Est_</w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>Code</w:t></w:r><w:proofErr w:type="spellEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">  :</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> %d                                                                                                                </w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> =========================</w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>==========================================================================================================================================</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> </w:t></w:r><w:proofErr w:type="spellStart"/><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>S_n</w:t></w:r><w:proofErr w:type="spellEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>|   Ins.</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">   |PF </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>|  Name</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> of the Employee    |BASIC   |HRA    |CONV   |</w:t></w:r><w:proofErr w:type="spellStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>WASHING|HRD_DT|Arrier</w:t></w:r><w:proofErr w:type="spellEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> |Total| PF SALARY|    </w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">      Deductions           |  Net   |  Signature</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">    |    No.   |A/c</w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>|  (</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>Father/Husband Name)   |        |       |       |       |      |(</w:t></w:r><w:proofErr w:type="spellStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>Ot_hr</w:t></w:r><w:proofErr w:type="spellEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>)|     |ESI SALARY|-------|------|------|---------| Amount |  ---------</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">    |   POST   |</w:t></w:r><w:proofErr w:type="spellStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>No.|T_day</w:t></w:r><w:proofErr w:type="spellEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> =</w:t></w:r><w:proofErr w:type="spellStart"/><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>W.day</w:t></w:r><w:proofErr w:type="spellEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">  +</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">E.L. </w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>+H.L. |        |INCENTI|       |       |      |</w:t></w:r><w:proofErr w:type="spellStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>O_time</w:t></w:r><w:proofErr w:type="spellEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> |     |          | E.S.I.| P.F. |I_TAX | Total   | Payable|  Date of </w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">    |   PF </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>RT  |</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">   |       C.L.   +CCL        |        |       |       |       |      |       |     |          |ADVANCE|P_TAX |S_DP</w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">T |Deduction|        </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>|  Payment</w:t></w:r><w:proofErr w:type="gramEnd"/></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve">    </w:t></w:r><w:proofErr w:type="gramStart"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>|  UAN</w:t></w:r><w:proofErr w:type="gramEnd"/><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> NO  |   |                          |        |       |       |       |      |       |     |          |       |      |S_TAX |         |        |</w:t></w:r></w:p><w:p w:rsidR="00000000" w:rsidRPr="00502A2F" w:rsidRDefault="004E3A4D" w:rsidP="00F02543"><w:pPr><w:pStyle w:val="PlainText"/><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr></w:pPr><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t xml:space="preserve"> ===================================================================</w:t></w:r><w:r w:rsidRPr="00502A2F"><w:rPr><w:rFonts w:ascii="Courier New" w:hAnsi="Courier New" w:cs="Courier New"/><w:sz w:val="16"/><w:szCs w:val="16"/></w:rPr><w:t>================================================================================================</w:t></w:r></w:p>""", month , daysInMonth , year , establishmentName ,  pageIndex  , address , estCode);
        }


    //     private static String getStandaloneReportTileFormat(){

    //     }
    //    private static String getStandaloneReportInnerFormat(){

    //     }



        static void generateReport(long pfRegNumber , int year , String month , String regionOptional, int daysInMonth) throws  SQLException , FileNotFoundException , IOException{

            /*
             * 2 * word documents required to be created
             * 1 : consolidated payslip
             * 2 : per person pay slip
             *
             * for every page in consolidated payslip space for 8 ( i.e 7 persons , 1 for the header )
             * and the ending should include the total of everything , which itself is a separate header
             *
             * for every per person standalone payslip , format not provided yet
             *
             *
             * watch the calculations and them take steps
             *
             *
             * FIRST :
             * get the total format and set the required variables
             * do for consolidated then do for standalone
             */


            int pageIndex = 1;
            int employeeIndex = 0 , employeeIndexAccurator = -1;

            int eps_salary = 0 ;
            int pfDeduction , eps_amount;


            XWPFRun headerRun ;
            XWPFRun dataRun;
            XWPFRun sum_totalRun;


            XWPFDocument consolidatedPayslip = new XWPFDocument();
            FileOutputStream fout = null ;


            FileWriter output = null;

            String consolidatedReportTitle;
            String consolidatedInnerData;
            String consolidatedReportNetTotal;

            // Establishment details for header
            ResultSet estab = (ResultSet)Manager.getEstablishmentDetails(pfRegNumber);


            // establishing employee connection before quering
            Manager.initiateEmployeesConnection(pfRegNumber, year, month , regionOptional);
            ResultSet emp = (ResultSet)EmployeeDatabaseHandler.getAllEmployeeDetails();
            ResultSet emp_sum = (ResultSet)EmployeeDatabaseHandler.getSumTotal();


            boolean hasNextEmployee = true;


            // String unformattedHeader = "<?xml version="1.0" encoding="UTF-8" standalone="yes"?>\n<w:document xmlns:ve="http:/"+ "/schemas.openxmlformats.org/markup-compatibility/2006" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:r="http:/" + "/schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:m="http:/" + "/schemas.openxmlformats.org/officeDocument/2006/math" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:wp="http:/" + "/schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w="http:/" + "/schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:wne="http:/" + "/schemas.microsoft.com/office/word/2006/wordml">";
            String unformattedHeader = """
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<w:document xmlns:ve="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml">
                    """;


            try {

                fout = new FileOutputStream( regionOptional == null ? FileComponentHandler.generatePath(PATH_MAIN , new String[]{"data" , pfRegNumber + "" , year + "" , month ,"combined_" + pfRegNumber + "_" + month + "_" + year + ".docx"}) : FileComponentHandler.generatePath(PATH_MAIN , new String[]{ "data" , pfRegNumber + "" , year + "" , month , regionOptional ,"combined_" + pfRegNumber + "_" + regionOptional + "_" + month + "_" + year + ".docx"}));


                output = new FileWriter( regionOptional == null ? FileComponentHandler.generatePath(PATH_MAIN , new String[]{"data" , pfRegNumber + "" , year + "" , month ,"out_" + pfRegNumber + "_" + month + "_" + year + ".txt"}) : FileComponentHandler.generatePath(PATH_MAIN , new String[]{ "data" , pfRegNumber + "" , year + "" , month , regionOptional ,"out_" + pfRegNumber + "_" + regionOptional + "_" + month + "_" + year + ".txt"}));

                BufferedWriter bufferedWriter = new BufferedWriter(output);


                // while ( emp.next()){
                /* beacause in the execution of first if condition : one employee will get missed */
                while (hasNextEmployee == true){


                    if (employeeIndex % 8 == 0) {
                        consolidatedReportTitle = getConsolidatedReportTileFormat(month, daysInMonth, pageIndex++, year, estab.getString("companyName"), estab.getString("address"), estab.getInt("pfRegNumber"));


                        XWPFParagraph header = consolidatedPayslip.createParagraph();
                        header.setAlignment(ParagraphAlignment.CENTER);
                        headerRun = header.createRun();
                        headerRun.setText(consolidatedReportTitle);
                        headerRun.setFontFamily("Courier New");
                        headerRun.setFontSize(8);

                        employeeIndexAccurator++;
                    }
                    else{
                        consolidatedInnerData = getConsolidatedReportInnerFormat(
                            employeeIndex  - employeeIndexAccurator ,
                            estab.getLong("esicRegNumber") ,
                            emp.getString("name")  ,
                            (int)emp.getDouble("basic") ,
                            (int)emp.getDouble("hra") ,
                            (int)emp.getDouble("convence")  ,
                            (int)emp.getDouble("washingAllowance") ,
                            (int)emp.getDouble("overtime") ,
                            (int)emp.getDouble("totalSalary") ,
                            (int)emp.getDouble("pf_salary") ,
                            (int)emp.getDouble("esicDeduction") ,
                            (int)emp.getDouble("pfDeduction") ,
                            (int)emp.getDouble("totalDeduction") ,
                            (int)emp.getDouble("netPayableAmount") ,
                            emp.getString("father_husband_name") ,
                            (int)emp.getDouble("esic_salary") ,
                            (int) ( emp.getDouble("memberId") % 100000000  ) ,
                            (int) ( emp.getDouble("attendance")) ,
                            (int) ( emp.getDouble("attendance")) ,
                            0 ,
                            (int)emp.getDouble("calc_basic") ,
                            (int)emp.getDouble("calc_hra") ,
                            (int)emp.getDouble("calc_convence") ,
                            (int)emp.getDouble("calc_washingAllowance") ,
                            (int)emp.getDouble("calc_overtime") ,
                            (int)emp.getDouble("calc_salary") ,
                            (long) emp.getInt("uan") ,
                            (int)emp.getDouble("calc_incentive")
                        );
                        XWPFParagraph data = consolidatedPayslip.createParagraph();
                        data.setAlignment(ParagraphAlignment.BOTH);
                        // data.createRun().setText(consolidatedInnerData);
                        dataRun = data.createRun();
                        dataRun.setText(consolidatedInnerData);
                        dataRun.setFontFamily("Courier New");
                        dataRun.setFontSize(8);

                        eps_salary = (int)( (emp.getDouble("calc_basic") * 8.33) /100 );
                        eps_salary = eps_salary > 15000 ? 15000 : eps_salary ;

                        pfDeduction = (int)emp.getDouble("pfDeduction");
                        eps_amount = (int)( ( eps_salary * 15) /100 );

                        bufferedWriter.write(emp.getLong("uan")+"#~#"+emp.getString("name").replaceFirst("M?(r|rs|s)?.?\\s+" , "" ) +"#~#" +(int)emp.getDouble("calc_salary") + "#~#" + (int)emp.getDouble("pf_salary") + "#~#" + eps_salary + "#~#" +eps_salary + "#~#" + pfDeduction + "#~#" +  (pfDeduction - eps_amount) + "#~#" + eps_amount + "#~#" +(int)( emp.getDouble("totalDays") - emp.getDouble("attendance")  )+ "#~#0\n");

                        hasNextEmployee = emp.next();
                    }

                    employeeIndex++;

                }






                consolidatedReportNetTotal = getConsolidatedReportNetTotal(
                    (int)emp_sum.getDouble("sum_basic") ,
                    (int)emp_sum.getDouble("sum_hra"),
                    (int)emp_sum.getDouble("sum_convence"),
                    (int)emp_sum.getDouble("sum_washingAllowance"),
                    (int)emp_sum.getDouble("sum_overtime"),
                    (int)emp_sum.getDouble("sum_totalSalary"),
                    (int)emp_sum.getDouble("sum_totalDays"),
                    (int)emp_sum.getDouble("sum_attendance"),
                    0 ,
                    (int)emp_sum.getDouble("sum_calc_basic"),
                    (int)emp_sum.getDouble("sum_calc_hra"),
                    (int)emp_sum.getDouble("sum_calc_convence"),
                    (int)emp_sum.getDouble("sum_calc_washingAllowance"),
                    (int)emp_sum.getDouble("sum_calc_overtime"),
                    (int)emp_sum.getDouble("sum_calc_salary"),
                    (int)emp_sum.getDouble("sum_pf_salary"),
                    (int)emp_sum.getDouble("sum_esicDeduction"),
                    (int)emp_sum.getDouble("sum_pfDeduction"),
                    (int)emp_sum.getDouble("sum_totalDeduction"),
                    (int)emp_sum.getDouble("sum_netPayableAmount"),
                    (int)emp_sum.getDouble("sum_calc_incentive"),
                    (int)emp_sum.getDouble("sum_esic_salary")
                );


                XWPFParagraph sum_total = consolidatedPayslip.createParagraph();
                sum_total.setAlignment(ParagraphAlignment.BOTH);
                sum_totalRun = sum_total.createRun();
                sum_totalRun.setText(consolidatedReportNetTotal);
                sum_totalRun.setFontFamily("Courier New");
                sum_totalRun.setFontSize(8);



                consolidatedPayslip.write(fout);








                bufferedWriter.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally{
                Manager.closeEmployeeConnection();
                if ( fout != null){
                    fout.close();
                }
                consolidatedPayslip.close();

                if ( output != null){
                    output.close();
                }

            }
         }


    }

    private static final String PATH_MAIN = Config.getPathMain();

    public static void initiateMainConnection() {
        EstablishmentDatabaseHandler.initiateConnection(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"main.db"}));
    }

    public static void closeMainConnection() {
        EstablishmentDatabaseHandler.closeConnection();
    }

    public static Object getEstablishmentDetails(long pfRegNumber) throws SQLException {
        return EstablishmentDatabaseHandler.getEstablishmentDetails(pfRegNumber);
    }

    // Establishment
    public static void createEstablishment(long pfRegNumber, long esicRegNumber, long phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {

        if (EstablishmentDatabaseHandler.checkForEstablishment(pfRegNumber) == true) {
            System.out.println("establihsment already created");
        } else {
            EstablishmentDatabaseHandler.insertEstablishment(pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
            FileComponentHandler.createDir(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", Config.getThisYear() + ""
            });
        }

    }

    public static void updateEstablishment(long pfRegNumber, long esicRegNumber, long phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {
        EstablishmentDatabaseHandler.updateEstablishment(pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
    }

    public static void removeEstablishment(long pfRegNumber) {
        /* no need for removing a establishment , but if this feature necesary , have to delete the entire folder also of that establishment */
        EstablishmentDatabaseHandler.removeEstablishment(pfRegNumber);
    }

    // salary structure
    public static void updateSalaryStructure(long pfRegNumber, double basic, double hra, double convence, double overtime, double washingAllowance, double msl1, double msl2, double msl3) {
        EstablishmentDatabaseHandler.updateSalaryStructure(pfRegNumber, basic, hra, convence, overtime, washingAllowance, msl1, msl2, msl3);
    }

    public static Object getEstablishmentSalaryStructureDetails(long pfRegNumber) throws SQLException {
        return EstablishmentDatabaseHandler.getEstablishmentSalaryStructureDetails(pfRegNumber);
    }









    // employees
    public static void checkAndCreateDir(long pfRegNumber, int year, String month , String regionOptional) {

        if ( regionOptional == null){


            FileComponentHandler.createDir(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month
            });
            FileComponentHandler.createFile(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month, month + ".db"
            });
        }
        else{
            FileComponentHandler.createDir(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month , regionOptional
            });
            FileComponentHandler.createFile(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month, regionOptional ,month + ".db"
            });

        }


    }

    public static void initiateEmployeesConnection(long pfRegNumber, int year, String month, String region_optional) {

        if ( region_optional != null){
            EmployeeDatabaseHandler.initiateConnection(FileComponentHandler.generatePath(PATH_MAIN, new String[]{
            "data", pfRegNumber+"", year+"", month, region_optional , month+".db"
        }));

        }
        else{
            EmployeeDatabaseHandler.initiateConnection(FileComponentHandler.generatePath(PATH_MAIN, new String[]{
            "data", pfRegNumber+"", year+"", month,  month+".db"
        }));

        }

    }

    public static void closeEmployeeConnection() {
        EmployeeDatabaseHandler.closeConnection();
    }

    public static void executeCreateTableCommand(long pfRegNumber, int year, String month, String region_optional) {
        if ( region_optional != null){
            EmployeeDatabaseHandler.executeCreateMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"commands", "table_pfSiteCSV.txt"}), FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber + "", year + "", month, region_optional,month + ".db"}));
        }
        else{
            EmployeeDatabaseHandler.executeCreateMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"commands", "table_pfSiteCSV.txt"}), FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber + "", year + "", month, month + ".db"}));

        }
    }

    public static void executeFillTableCommand(long pfRegNumber, int year, String month, String region_optional, String pathToPfCSVFile) {
        if ( region_optional != null){
            EmployeeDatabaseHandler.executeFillMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber +"", year +"", month , region_optional, month+".db"}), pathToPfCSVFile);
        }
        else{
            EmployeeDatabaseHandler.executeFillMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber +"", year +"", month, month+".db"}), pathToPfCSVFile);
        }
    }

}
