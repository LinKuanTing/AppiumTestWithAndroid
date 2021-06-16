package com.example.wghtest.Level3.StressMood;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.ArrayList;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.tmoutLoading;

public class TestCaseStressMood extends WDFnL3StressMood{

    private static String __strFileName;
    private static String __strFilePath;

    private static String __strWellnessIdxRecordID = "android:id/content";
    private static String __strBreathTrainPicXpath = "//*[@resource-id=\"tw.com.wgh3h:id/date_icon\"]";

    Logger logger = LoggerFactory.getLogger(TestCaseStressMood.class);

    public void LoadRecord(){
        int time = 0;
        while (true){

            try {
                clickRecord();
                WebDriverWait wait = new WebDriverWait(adDriver,tmoutLoading);
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(__strWellnessIdxRecordID)));
                new WDFnL3WellnessIndexesRecord(1);
                logger.info(strPassMsg);
                break;
            }catch (Exception eNoFindElement){
                if (time == 3){
                    logger.warn(strFailMsg);
                    logger.debug("Reason: can't find record or record data don't exist");
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                    break;
                }else {
                    time++;
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                }
            }
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    public void CompareRecord() throws IOException {
        clickRecord();
        ArrayList alRecordData = new ArrayList();
        try {
            for (int i = 1; i<=3; i++){
                WDFnL3WellnessIndexesRecord wdFnL3WellnessIndexesRecord = new WDFnL3WellnessIndexesRecord(i);
                alRecordData.add(wdFnL3WellnessIndexesRecord.getDate()+"/"+wdFnL3WellnessIndexesRecord.getHR());
            }
        }catch (Exception eWeakNetwork){
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find record by weak network or over 90 days");

            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            return;
        }

        WDFnL3WellnessIndexesRecord wdFnL3WellnessIndexesRecord = new WDFnL3WellnessIndexesRecord();

        if (wdFnL3WellnessIndexesRecord.compare(alRecordData)){
            logger.info(strPassMsg);
        }
        else{
            logger.warn(strFailMsg);
            logger.debug("Expect data: " + wdFnL3WellnessIndexesRecord.alReadLine);
            logger.debug("Read  data : " + alRecordData);
        }
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    public void LoadBreathTrainRecord() throws InterruptedException {
        clickBreathingTraining();
        clickBreathTrainRecord();
        //檢查90天內的紀錄
        for(int i=0; i<5; i++){
            try{
                adDriver.findElement(By.id(_strButton1ID)).click();
                try {
                    adDriver.findElement(By.xpath(__strBreathTrainPicXpath));

                }catch (Exception eNoFindElement){
                    logger.warn(strFailMsg);
                    logger.debug("Reason: load fail which can't find breathing training record");
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                    return;
                }

            }catch (Exception eNoFindElement){ }
            if (i<4){
                new WDFnL3BreathingTrainRecord().clickPreviousYear();
            }
        }
        Thread.sleep(3000);

        logger.info(strPassMsg);
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

    }

    public void CompareBreathTrainRecord() throws IOException, InterruptedException {
        clickBreathTrainRecord();
        WDFnL3BreathingTrainRecord wdFnL3BreathingTrainRecord = new WDFnL3BreathingTrainRecord();

        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFileName = "BreathingTrainRecord";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList alReadLine = fnFileEvent.getContent(__strFilePath);

        try{
            int sameNum = 0;
            for (int i=0; i<alReadLine.size(); i++){
                String[] strReadLineData = alReadLine.get(i).toString().split("/");
                //[0]年月  [1]日  [2]時間  [3]等級
                wdFnL3BreathingTrainRecord.clickDate(strReadLineData[0],strReadLineData[1]);
                ArrayList alDateRecord = wdFnL3BreathingTrainRecord.getDateRecord();
                if (alDateRecord.size()==0){
                    logger.warn(strFailMsg);
                    return;
                }

                for (int j=0; j<alDateRecord.size(); j++){
                   if (alDateRecord.get(j).toString().contains(strReadLineData[2]) && alDateRecord.get(j).toString().contains(strReadLineData[3])){
                        sameNum++;
                    }
                }
            }

            if (sameNum == 3){
                logger.info(strPassMsg);
            }
            else {
                logger.warn(strFailMsg);
                logger.debug("only find "+ sameNum + " pieces of data");
            }
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
            logger.debug("Reason: breathing training record load fail or no such record");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        }

        Thread.sleep(5000);

        try {
            if (adDriver.findElement(By.id("tw.com.wgh3h:id/action_title")).getText().contains("Record")){
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            }
        }catch (Exception eNoFindElement){
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            //if load breath training record bug is fixed
            // try-catch can remove
        }


    }
}
