package com.example.wghtest.Level2.Personal;

import com.example.wghtest.other.FnFileEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.adDriver;

public class TestCaseHealthPlan extends WDFnL2HealthPlan {
    private String __strENMessage = "Data incomplete\n" + "Height Range：120~220cm\n";
    private String __strCHMessage = "*資料填寫不完整請確認\n" + "身高範圍：120~220公分\n";

    private String __strFilePath;
    private String __strFileName = "AccountInfo";

    Logger logger;

    public TestCaseHealthPlan(){
        logger = LoggerFactory.getLogger(TestCaseHealthPlan.class);
    }

    public void CheckHeightRange(){
        new WDFnL2Personal().clickHealthPlan();

        String strMinHeight = "110";
        String strMaxHeight = "230";

        setHeight(strMinHeight);
        clickSave();


        if (getMessage().equals(__strENMessage) || getMessage().equals(__strCHMessage)){
            adDriver.findElementById(_strButton1ID).click();

            setHeight(strMaxHeight);
            clickSave();
            if (getMessage().equals(__strENMessage) || getMessage().equals(__strCHMessage)) {
                adDriver.findElementById(_strButton1ID).click();

                logger.info(strPassMsg);
            }else {
                logger.warn(strFailMsg);
                logger.debug("Reason: Out of height range");
                System.out.println(getMessage());
                adDriver.findElementById(_strButton1ID).click();
            }

        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Out of height range");
            System.out.println(getMessage());
            adDriver.findElementById(_strButton1ID).click();
        }

    }

    public void SetInfo() throws IOException, InterruptedException {

        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);

        new WDFnL2Personal().clickHealthPlan();

        setHeight("170");
        setHealthPlan();

        if (checkSaveSuccessfullyMsg()){
            logger.info(strPassMsg);

            FileWriter fw = new FileWriter(__strFilePath,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("###Age\r\n");
            bw.write(_intAge+"\r\n");
            bw.write("###Height\r\n");
            bw.write(_dbHeight+"\r\n");
            bw.write("###InitialWeight\r\n");
            bw.write(_dbInitialWeight+"\r\n");
            bw.write("###TargetWeight\r\n");
            bw.write(_dbTargetWeight+"\r\n");
            bw.write("###Daily ExerciseNo (light->1, normal->2, heavy->3)"+"\r\n");
            bw.write(_intExerciseNo+"\r\n");
            bw.write("###Calories Reduce"+"\r\n");
            bw.write(_intReduceCal+"\r\n");
            bw.write("###Breakfast mealtime"+"\r\n");
            bw.write(_strBreakfastStart+"~"+_strBreakfastEnd+"\r\n");
            bw.write("###Lunch mealtime"+"\r\n");
            bw.write(_strLunchStart+"~"+_strLunchEnd+"\r\n");
            bw.write("###Dinner mealtime"+"\r\n");
            bw.write(_strDinnerStart+"~"+_strDinnerEnd+"\r\n");
            bw.write("###Fasting mealtime"+"\r\n");
            bw.write(_strFastingStart+"~"+_strFastingEnd+"\r\n");
            bw.flush();
            bw.close();
        }else {
            logger.warn(strFailMsg);
        }
    }
}
