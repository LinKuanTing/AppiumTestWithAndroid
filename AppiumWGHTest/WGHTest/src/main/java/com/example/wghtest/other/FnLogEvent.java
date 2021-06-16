package com.example.wghtest.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FnLogEvent {
    public static String strPassMsg = "PASS";
    public static String strFailMsg = "(FAIL)";
    private static String __levelInfo = "INFO",__levelWarn = "WARN",__levelDebug = "DEBUG";

    private static String __strFilePath = "D:\\TestData\\TestLog\\Log.log";

    ArrayList arylstLog = new ArrayList();
    private static int __numPASS = 0 , __numFAIL = 0;

    public void logFormat() throws IOException {
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));

        while (br.ready()){
            String strReadLine = br.readLine().replaceAll(".java","").replaceAll("execute","");
            arylstLog.add(strReadLine);
        }

        //delete DEBUG date and file location message
        for (int i=0; i<arylstLog.size(); ++i){
            if (arylstLog.get(i).equals(__levelDebug)){
                arylstLog.remove(i+1);
                arylstLog.remove(i+1);
            }
        }

        //計算PASS/FAIL
        for(int i=0; i<arylstLog.size(); ++i){
            if(arylstLog.get(i).equals(__levelInfo)){
                __numPASS++;
            }
            else if(arylstLog.get(i).equals(__levelWarn)){
                __numFAIL++;
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
        Date date = new Date();
        String strDate = dateFormat.format(date);
        File file = new File("D:\\TestData\\TestLog\\Log" + strDate + ".log");
        file.createNewFile();

        FileWriter fw = new FileWriter(file);
        fw.write(new String().format("%-30s%-60s%s%n","Time","Method","Result"));
        fw.write("---------------------------------------------------------------------------------------------------\r\n");

        for (int i=0; i<arylstLog.size(); i++){
            String str = "";
            switch (arylstLog.get(i).toString()){
                case "INFO":
                    str = String.format("%-30s%-60s%s%n",arylstLog.get(i+1),arylstLog.get(i+2),arylstLog.get(i+3));
                    fw.write(str);
                    break;
                case "WARN":
                    str = String.format("%-30s%-60s%s%n",arylstLog.get(i+1),arylstLog.get(i+2),arylstLog.get(i+3));
                    fw.write(str);
                    break;
                case "DEBUG":
                    //str = String.format("%s%n",arylstLog.get(i+1));
                    //fw.write(str);
                    break;
                default:
                    continue;
            }
        }


        fw.write("---------------------------------------------------------------------------------------------------\r\n");
        fw.write("Performance : \r\n");

        for (int i=0; i<arylstLog.size(); i++){
            String str = "";
            switch (arylstLog.get(i).toString()){
                case "DEBUG":
                    if (arylstLog.get(i-4).equals(__levelInfo)) {
                        str = String.format("    %-45s@%s%n        %s%n",arylstLog.get(i-2),arylstLog.get(i-3),arylstLog.get(i + 1));
                        fw.write(str);
                    }
                    break;
                default:
                    continue;
            }
        }

        fw.write("\r\nTotal test cases FAILED : " + __numFAIL + " / "  + (__numFAIL+__numPASS) +"\r\n");

        for (int i=0; i<arylstLog.size(); i++){
            String str = "";
            switch (arylstLog.get(i).toString()){
                case "WARN":
                    str = String.format("    %-45s@%s%n",arylstLog.get(i+2),arylstLog.get(i+1));
                    fw.write(str);
                    break;
                case "DEBUG":
                    if (arylstLog.get(i-4).equals(__levelWarn) || arylstLog.get(i-2).equals(__levelDebug)){
                        str = String.format("        %s%n",arylstLog.get(i+1));
                        fw.write(str);
                    }
                    break;
                default:
                    continue;
            }
        }

        fw.write("\r\nTotal test cases PASSED : " + __numPASS +  " / " + (__numFAIL+__numPASS) +"\r\n");
        fw.flush();
        fw.close();

  }


}
